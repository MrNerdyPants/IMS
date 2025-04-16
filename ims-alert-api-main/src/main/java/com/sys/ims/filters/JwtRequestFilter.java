package com.sys.ims.filters;

import com.sys.ims.util.*;
import io.jsonwebtoken.JwtException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
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
    private  JwtUtil jwtUtil;
//    @Autowired
//    private  AppInitializer initializer;
    @Autowired
    private  Utility utility;


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws JwtException, ServletException, IOException {
        String jwt = jwtUtil.parseJwt(request);
        ContentCachingRequestWrapper requestWrapper = new ContentCachingRequestWrapper(request);
        ContentCachingResponseWrapper responseWrapper = new ContentCachingResponseWrapper(response);
        if (jwt != null && jwtUtil.validateJwtToken(jwt, request)) {
            String username = jwtUtil.extractUsername(jwt);
            UserDetails userDetails = null;
            if (utility.isUserExist(username)) {
//                userDetails = initializer.getUserCache().get(username);
            } else {
                request.setAttribute("loginAgain","Login Again");
            }

            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, null);
            authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
        filterChain.doFilter(requestWrapper, responseWrapper);
        responseWrapper.copyBodyToResponse();
    }


}
