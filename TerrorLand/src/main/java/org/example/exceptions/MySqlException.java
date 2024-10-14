package org.example.exceptions;

public class MySqlException extends Exception{
    public MySqlException(String message, Exception e){
        super(message, e);
    }
    public MySqlException(String message){
        super(message);
    }
}
