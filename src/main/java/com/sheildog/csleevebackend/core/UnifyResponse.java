package com.sheildog.csleevebackend.core;

/**
 * @author a7818
 */
public class UnifyResponse {
    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public String getRequest() {
        return request;
    }

    private int code;
    private String message;
    private String request;

    public UnifyResponse(int code, String message, String request){
        this.code = code;
        this.message = message;
        this.request = request;
    }
}
