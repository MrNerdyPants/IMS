package com.sys.ims.util;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

@Service
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@Scope("prototype")
public class ApiResponse {

    private Integer code;
    private String message;
    private Object response;

    public ApiResponse() {
        super();
    }

    public ApiResponse(final int code, final String message, final Object response) {
        super();
        this.code = code;
        this.message = message;
        this.response = response;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getResponse() {
        return response;
    }

    public void setResponse(Object response) {
        this.response = response;
    }

    @Override
    public String toString() {
        return "ApiResponse [code=" + code + ", message=" + message + ", response=" + response + "]";
    }
}
