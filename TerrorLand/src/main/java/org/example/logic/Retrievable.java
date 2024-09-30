package org.example.logic;

public interface Retrievable {
    default Integer retrieve(int id){
        return id;
    }
}
