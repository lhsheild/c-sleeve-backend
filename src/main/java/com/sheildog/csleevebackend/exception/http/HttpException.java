package com.sheildog.csleevebackend.exception.http;

/**
 * @author a7818
 */
public class HttpException extends RuntimeException {
    public Integer getCode() {
        return code;
    }

    public Integer getHttpStatusCode() {
        return httpStatusCode;
    }

    protected Integer code;
    protected Integer httpStatusCode = 500;

}
