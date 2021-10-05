package com.home.closematch.exception;

import lombok.Data;

public class ServiceErrorException extends RuntimeException{
    private final int errorCode;
    private final String msg;

    public ServiceErrorException(int errorCode, String msg){
        super(msg);
        this.errorCode = errorCode;
        this.msg = msg;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public String getMsg() {
        return msg;
    }
}
