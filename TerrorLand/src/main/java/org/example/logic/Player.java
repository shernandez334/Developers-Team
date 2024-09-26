package org.example.logic;

public class Player extends User{
    public Player(String name, String password, String email) {
        super(name, password, email);
    }
    public Player(String name, String email, int id) {
        super(name, email, id);
    }
}
