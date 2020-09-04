package com.sheildog.csleevebackend.exception.http;

/**
 * @author a7818
 */
public class NotFoundException extends HttpException {
    public NotFoundException(int code) {
        this.httpStatusCode = 404;
        this.code = code;
    }
}
