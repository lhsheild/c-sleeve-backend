package com.sheildog.csleevebackend.core;

import com.sheildog.csleevebackend.core.configuration.ExceptionCodeConfiguration;
import com.sheildog.csleevebackend.exception.http.HttpException;
import com.sheildog.csleevebackend.exception.http.ParameterException;
import com.sheildog.csleevebackend.exception.http.ServerErrorException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.List;

/**
 * @author a7818
 */
@ControllerAdvice
public class GlobalExceptionAdvice {
//    private final ExceptionCodeConfiguration exceptionCodeConfiguration;
//
//    public GlobalExceptionAdvice(ExceptionCodeConfiguration exceptionCodeConfiguration) {
//        this.exceptionCodeConfiguration = exceptionCodeConfiguration;
//    }
    @Autowired
    private ExceptionCodeConfiguration exceptionCodeConfiguration;

    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    @ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR)
    public UnifyResponse handleException(HttpServletRequest req, Exception e) {
//        System.out.println(e);
        String requestUrl = req.getRequestURI();
        String method = req.getMethod();
        UnifyResponse message = new UnifyResponse(9999, "服务器异常", method + " " + requestUrl);
        return message;
    }

    @ExceptionHandler(value = HttpException.class)
    @ResponseBody
    public ResponseEntity<UnifyResponse> handleHttpException(HttpServletRequest req, HttpException e) {
//        System.out.println(e);
        String requestUrl = req.getRequestURI();
        String method = req.getMethod();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpStatus status = HttpStatus.resolve(e.getHttpStatusCode());
        UnifyResponse message = new UnifyResponse(e.getCode(), exceptionCodeConfiguration.getMessage(e.getCode()), method + " " + requestUrl);
        ResponseEntity<UnifyResponse> r = new ResponseEntity<>(message, headers, status);
        return r;
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    public UnifyResponse handleBeanValidation(HttpServletRequest req, MethodArgumentNotValidException e) {
        String requestUrl = req.getRequestURI();
        String method = req.getMethod();

        List<ObjectError> errors = e.getBindingResult().getAllErrors();
        String message = this.formatAllErrorMessages(errors);
        UnifyResponse r = new UnifyResponse(10001, message, method + " " + requestUrl);
        return r;
    }

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseBody
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    public UnifyResponse handleConstraintValidation(HttpServletRequest req, ConstraintViolationException e) {
        String requestUrl = req.getRequestURI();
        String method = req.getMethod();

        String message = e.getMessage();
        UnifyResponse r = new UnifyResponse(10001, message, method + " " + requestUrl);
        return r;
    }

    @ExceptionHandler(value = ServerErrorException.class)
    @ResponseBody
    @ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR)
    public UnifyResponse handleServerErrorException(HttpServletRequest req, ServerErrorException e) {
        String requestUrl = req.getRequestURI();
        String method = req.getMethod();
        UnifyResponse r = new UnifyResponse(e.getCode(), exceptionCodeConfiguration.getMessage(e.getCode()), method + " " + requestUrl);
        return r;
    }

    @ExceptionHandler(value = ParameterException.class)
    @ResponseBody
    @ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR)
    public UnifyResponse handleParameterException(HttpServletRequest req, ParameterException e) {
        String requestUrl = req.getRequestURI();
        String method = req.getMethod();
        UnifyResponse r = new UnifyResponse(e.getCode(), exceptionCodeConfiguration.getMessage(e.getCode()), method + " " + requestUrl);
        return r;
    }

    private String formatAllErrorMessages(List<ObjectError> errors) {
        StringBuilder errorMsg = new StringBuilder();
        errors.forEach(error ->
                errorMsg.append(error.getDefaultMessage()).append(';')
        );
        return errorMsg.toString();
    }
}
