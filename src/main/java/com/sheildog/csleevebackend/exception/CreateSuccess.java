package com.sheildog.csleevebackend.exception;

import com.sheildog.csleevebackend.exception.http.HttpException;

public class CreateSuccess extends HttpException {
    public CreateSuccess(int code){
        this.httpStatusCode = 201;
        this.code = code;
    }
}
