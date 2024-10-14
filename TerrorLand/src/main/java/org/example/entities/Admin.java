package org.example.entities;

public class Admin extends User {

    public Admin(String name, String password, String email) {
        super(name, password, email);
    }

    public Admin(int id, String name, String password, String email) {
        super(id, name, password, email);
    }

}
