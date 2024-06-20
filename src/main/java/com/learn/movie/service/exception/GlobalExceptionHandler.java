package com.learn.movie.service.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.enterprise.inject.Produces;


@ControllerAdvice
public class GlobalExceptionHandler {

    static  class ErrorResponse{
        private String reason;
        private String message;

        public ErrorResponse(String reason, String message) {
            this.reason = reason;
            this.message = message;
        }
    }

    @ExceptionHandler({InvalidDataException.class, HttpMessageNotReadableException.class})
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleinvalidDataException(Throwable t){

        System.out.println(">>> Global Exception - InvalidDataException :::");
        return new ErrorResponse(HttpStatus.BAD_REQUEST.getReasonPhrase(), t.getMessage());
    }

    @ExceptionHandler({DataNotFoundException.class, RuntimeException.class})
    public ErrorResponse handleDataNotFoundException(Throwable ex){
        System.out.println(">>> Global Exception - DataNotFoundException -- :::");
        return new ErrorResponse(HttpStatus.NOT_FOUND.getReasonPhrase(), ex.getMessage());
    }

    @ExceptionHandler(Exception.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponse handleUnkownExceptions(Exception ex){
        System.out.println(">>> Global Exception - Main_Exception Occured :::");
        return new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(), ex.getMessage());
    }


}
