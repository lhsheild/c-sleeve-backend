package com.sheildog.csleevebackend.exception;

import com.sheildog.csleevebackend.exception.http.HttpException;

public class UpdateSuccess extends HttpException {
    public UpdateSuccess(int code){
        this.httpStatusCode = 200;
        this.code = code;
    }
}
