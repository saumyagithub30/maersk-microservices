package com.maersk.availabiltyservice.exception;

import lombok.AllArgsConstructor;

public class FinalException extends Exception{
    private int code;
    private String message;

    public FinalException(int code, String message) {
        this.code = code;
        this.message = message;
    }
}
