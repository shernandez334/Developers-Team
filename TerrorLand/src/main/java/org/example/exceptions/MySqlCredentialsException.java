package org.example.exceptions;

public class MySqlCredentialsException extends MySqlException{
    public MySqlCredentialsException(String message, Exception e){
        super(message, e);
    }
}
