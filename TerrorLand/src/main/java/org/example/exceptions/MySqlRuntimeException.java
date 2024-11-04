package org.example.exceptions;

public class MySqlRuntimeException extends RuntimeException{
    public MySqlRuntimeException(String message, Exception e){
        super(message, e);
    }
    public MySqlRuntimeException(String message){
        super(message);
    }
}
