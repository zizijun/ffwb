package com.cxtx.exception;

/**
 * Created by jinchuyang on 16/10/19.
 */
public class ParameterInvalidException extends RuntimeException {

    public ParameterInvalidException(Object errorMessage) {
        super(String.valueOf(errorMessage));
    }

    public ParameterInvalidException(Throwable throwable) {
        super(throwable);
    }
}
