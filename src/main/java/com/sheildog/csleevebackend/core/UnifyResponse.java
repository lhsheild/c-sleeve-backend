package com.sheildog.csleevebackend.core;

import com.sheildog.csleevebackend.exception.CreateSuccess;

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

    public static void createSuccess(int code){
        throw new CreateSuccess(code);
    }
}
