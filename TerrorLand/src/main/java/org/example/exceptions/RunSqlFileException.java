package org.example.exceptions;

public class RunSqlFileException extends RuntimeException{
    public RunSqlFileException(String message, Exception e){
        super(message, e);
    }
}
