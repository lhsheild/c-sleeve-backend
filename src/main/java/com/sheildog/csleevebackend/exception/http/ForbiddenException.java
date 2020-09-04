package com.sheildog.csleevebackend.exception.http;

/**
 * @author a7818
 */
public class ForbiddenException extends HttpException {
    public ForbiddenException(int code) {
        this.code = code;
        this.httpStatusCode = 403;
    }
}
