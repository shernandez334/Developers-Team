package org.example.exceptions;

public class ExistingEmailException extends MySqlException{
    public ExistingEmailException(Exception e){
        super("Could not create the new user, there is already a user registered with this email " +
                "(the email field must be unique).", e);
    }
}
