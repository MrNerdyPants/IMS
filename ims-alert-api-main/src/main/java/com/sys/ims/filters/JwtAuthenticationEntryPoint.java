package com.sys.ims.filters;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sys.ims.util.ApiResponse;
import com.sys.ims.util.Utility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;

@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {
    private static final long serialVersionUID = -8970718410437077606L;
    @Autowired
    private Utility  utility;

    @Override
    public void commence(HttpServletRequest request,
                         HttpServletResponse response,
                         AuthenticationException authException) throws IOException {
        ApiResponse apiResponse;
        final String tokenExpired = (String) request.getAttribute("tokenExpired");
        final String tokeMalformed = (String) request.getAttribute("tokeMalformed");
        final String tokenUnsupported = (String) request.getAttribute("tokenUnsupported");
        final String illegalArgument = (String) request.getAttribute("illegalArgument");
        final String signatureError = (String) request.getAttribute("signatureError");
        final String loginAgain = (String) request.getAttribute("loginAgain");

        if (Utility.isNotNullAndEmpty(tokenExpired)) {
            apiResponse = utility.buildApiResponse(4001,"Jwt token has expired!",null);
        } else if (Utility.isNotNullAndEmpty(tokeMalformed)) {
            apiResponse = utility.buildApiResponse(4002,"Jwt token is malformed!",null);
        } else if (Utility.isNotNullAndEmpty(tokenUnsupported)) {
            apiResponse = utility.buildApiResponse(4003,"Sorry, this toke is not supported by this application",null);
        } else if (Utility.isNotNullAndEmpty(illegalArgument)) {
            apiResponse = utility.buildApiResponse(4004,"Jwt illegal argument passed",null);
        } else if (Utility.isNotNullAndEmpty(signatureError)) {
            apiResponse = utility.buildApiResponse(4005, "Jwt signature does not match!", null);
        }
        else if(Utility.isNotNullAndEmpty(loginAgain)){
            apiResponse = utility.buildApiResponse(4006, "Please login again", null);
        }else{
            apiResponse = utility.buildApiResponse(HttpServletResponse.SC_UNAUTHORIZED,"Invalid user credentials",null);
        }

        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        OutputStream responseStream = response.getOutputStream();
        ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue(responseStream, apiResponse);
        responseStream.flush();
    }
}

