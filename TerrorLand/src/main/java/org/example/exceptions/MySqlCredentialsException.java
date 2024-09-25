package org.example.exceptions;

public class MySqlCredentialsException extends Exception{
    public MySqlCredentialsException(String message, Exception e){
        super(message, e);
    }
}
