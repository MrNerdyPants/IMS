package com.sys.ims.util;


import org.springframework.stereotype.Component;

@Component
public class ErrorDetails {

    private String fieldName;
    private String message;

    public ErrorDetails() {
    }

    public ErrorDetails(String fieldName, String message) {
        this.fieldName = fieldName;
        this.message = message;
    }

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}

