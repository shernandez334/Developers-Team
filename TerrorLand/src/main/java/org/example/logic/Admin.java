package org.example.logic;

public class Admin extends User{
    public Admin(String name, String password, String email) {
        super(name, password, email);
    }
    public Admin(String name, String email, int id) {
        super(name, email, id);
    }
}
