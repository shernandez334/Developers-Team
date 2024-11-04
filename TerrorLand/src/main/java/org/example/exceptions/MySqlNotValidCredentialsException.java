package org.example.exceptions;

public class MySqlNotValidCredentialsException extends Exception{
    public MySqlNotValidCredentialsException(String message){
        super(message);
    }
}
