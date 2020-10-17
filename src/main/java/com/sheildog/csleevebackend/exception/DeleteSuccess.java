package com.sheildog.csleevebackend.exception;

import com.sheildog.csleevebackend.exception.http.HttpException;

public class DeleteSuccess extends HttpException {
    public DeleteSuccess(int code){
        this.httpStatusCode = 200;
        this.code = code;
    }
}