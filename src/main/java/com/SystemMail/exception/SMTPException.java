package com.SystemMail.exception;

public class  SMTPException extends Exception
{
    private String message;

    public SMTPException(String message){
        this.message = message;
    }

    public String getMessage(){
        return message;
    }
}
