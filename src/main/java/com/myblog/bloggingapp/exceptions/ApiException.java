package com.myblog.bloggingapp.exceptions;

public class ApiException extends RuntimeException{
    
    public ApiException(String message){
        super(message);
    }
    public ApiException(){
        super();
    }
}
