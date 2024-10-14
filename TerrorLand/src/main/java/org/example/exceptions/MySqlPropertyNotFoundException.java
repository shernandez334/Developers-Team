package org.example.exceptions;

public class MySqlPropertyNotFoundException extends MySqlRuntimeException{
    public MySqlPropertyNotFoundException(String message, Exception e){
        super(message, e);
    }
}
