package com.example.icecreamfactory.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResponseNotFoundException extends RuntimeException{
    String c;

    public ResponseNotFoundException(String message){
        super(message);
    }
}