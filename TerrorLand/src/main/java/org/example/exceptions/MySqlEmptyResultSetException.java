package org.example.exceptions;

public class MySqlEmptyResultSetException extends MySqlException{
    public MySqlEmptyResultSetException(String message){
        super(message);
    }
}
