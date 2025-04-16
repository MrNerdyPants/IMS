package com.sys.ims.util;

import com.sys.ims.service.impl.UserDetailsImpl;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.SignatureException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import javax.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class JwtUtil {

    Logger logger = LoggerFactory.getLogger(JwtUtil.class);

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parser().setSigningKey(Constants.SECRET_KEY).parseClaimsJws(token).getBody();
    }

    private Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    public String generateToken(UserDetailsImpl userDetails) {

        return createToken(userDetails);
    }

    private String createToken(UserDetailsImpl userDetails) {
//        Set<RoleDto> roles = userDetails.getAuthorities().stream()
//            .map(role -> new RoleDto(null,role.getAuthority())).collect(
//            Collectors.toSet());
//        .claim("roles",roles)
        Map map = new HashMap<>();
        map.put("id",userDetails.getId());
        map.put("companyId", userDetails.getCompanyId());
        map.put("fullName", userDetails.getFullName());
        Jwts.builder();
        return Jwts.builder().setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setClaims(map)
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10))
                .signWith(SignatureAlgorithm.HS256, Constants.SECRET_KEY).compact();
    }

    public boolean validateJwtToken(String authToken, HttpServletRequest request) {
        try {
            Jwts.parser().setSigningKey(Constants.SECRET_KEY).parseClaimsJws(authToken);
            return true;
        } catch (MalformedJwtException e) {
            logger.error("Invalid JWT token: {}", e.getMessage());
            request.setAttribute("tokeMalformed",e.getMessage());
        } catch (ExpiredJwtException e) {
            request.setAttribute("tokenExpired",e.getMessage());
            logger.error("JWT token is expired: {}", e.getMessage());
        } catch (UnsupportedJwtException e) {
            logger.error("JWT token is unsupported: {}", e.getMessage());
            request.setAttribute("tokenUnsupported",e.getMessage());
        } catch (IllegalArgumentException e) {
            logger.error("JWT claims string is empty: {}", e.getMessage());
            request.setAttribute("illegalArgument",e.getMessage());
        }catch (SignatureException e){
            logger.error("JWT Signature is modified: {}", e.getMessage());
            request.setAttribute("signatureError",e.getMessage());
        }

        return false;
    }
    public Claims getClaims(String token){
        Claims claims = extractAllClaims(token);
        return claims;
    }

    public String parseJwt(HttpServletRequest request) {
        String headerAuth = request.getHeader("Authorization");

        if (StringUtils.hasText(headerAuth) && headerAuth.startsWith("Bearer ")) {
            return headerAuth.substring(7);
        }
        return null;
    }
    public String refreshToken(HttpServletRequest request) {

        String token = parseJwt(request);
        Claims claims = extractAllClaims(token);
        Date date = claims.getExpiration();
        Long difference = date.getTime() - new Date().getTime();
        Long differenceInMinutes = (difference/(1000 * 60));
        String result = differenceInMinutes < 60 ? differenceInMinutes + " minutes" : differenceInMinutes/60 + " hours";
        if (differenceInMinutes <= 5l) {
            return Jwts.builder().setSubject(claims.getSubject()).claim("roles",claims.get("roles")).setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10))
                .signWith(SignatureAlgorithm.HS256, Constants.SECRET_KEY).compact();
        }
        return "Token has " + result + " left to expire";
    }

}
