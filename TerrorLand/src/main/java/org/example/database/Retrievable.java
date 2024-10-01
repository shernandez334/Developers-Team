package org.example.database;

public interface Retrievable {
    default Integer retrieve(int id){
        return id;
    }
}
