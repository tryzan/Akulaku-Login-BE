package com.akulaku.exceptionhandler;

public class CustomException extends RuntimeException{
    public CustomException(){}

    public CustomException(String msg){
        super(msg);
    }
}
