package org.example.exceptions;

public class ElementIdException extends MySqlException {
    public ElementIdException(Exception e) {
        super("The element creation, alongside its Id, could not be completed " +
                "(check your database connection)." + e);
    }
}
