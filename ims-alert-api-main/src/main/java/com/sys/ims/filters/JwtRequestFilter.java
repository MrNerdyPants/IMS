package com.sys.ims.filters;

import com.sys.ims.service.impl.UserDetailsImpl;
import com.sys.ims.util.JwtUtil;
import com.sys.ims.util.Utility;
import io.jsonwebtoken.JwtException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {
    @Autowired
    private JwtUtil jwtUtil;
    //    @Autowired
//    private  AppInitializer initializer;
    @Autowired
    private Utility utility;

    @Lazy
    @Autowired
    private UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws JwtException, ServletException, IOException {
//        String jwt = jwtUtil.parseJwt(request);
        ContentCachingRequestWrapper requestWrapper = new ContentCachingRequestWrapper(request);
        ContentCachingResponseWrapper responseWrapper = new ContentCachingResponseWrapper(response);
//        if (jwt != null && jwtUtil.validateJwtToken(jwt, request)) {
//            String username = jwtUtil.extractUsername(jwt);
//            UserDetails userDetails = null;
//            if (utility.isUserExist(username)) {
////                userDetails = initializer.getUserCache().get(username);
//            } else {
//                request.setAttribute("loginAgain","Login Again");
//            }
//
//            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, null);
//            authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
//
//            SecurityContextHolder.getContext().setAuthentication(authentication);
//        }
//        filterChain.doFilter(requestWrapper, responseWrapper);
//        responseWrapper.copyBodyToResponse();

        String jwt = parseJwt(request);
        if (jwt != null && jwtUtil.validateJwtToken(jwt)) {
            String email = jwtUtil.getEmailFromJwtToken(jwt);

            UserDetailsImpl userDetails = (UserDetailsImpl) userDetailsService.loadUserByUsername(email);
            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                    userDetails, null, userDetails.getAuthorities());

            authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

            // ✅ Set user in SecurityContext
            SecurityContextHolder.getContext().setAuthentication(authentication);


        }
        filterChain.doFilter(requestWrapper, responseWrapper);
        responseWrapper.copyBodyToResponse();
    }


    private String parseJwt(HttpServletRequest request) {
        String headerAuth = request.getHeader("Authorization");

        if (headerAuth != null && headerAuth.startsWith("Bearer ")) {
            return headerAuth.substring(7);
        }

        return null;
    }


}
