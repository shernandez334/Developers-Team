package org.example.model;

public class User {
    private final int id;
    private final String name;
    private final String password;
    private final String email;


    public User(String name, String password, String email) {
        this.id = -1;
        this.name = name;
        this.password = password;
        this.email = email;
    }

    public User(String name, String email, int id) {
        this.id = id;
        this.name = name;
        this.password = "";
        this.email = email;
    }

    public int getId(){
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public String getPassword() {
        return this.password;
    }

    public String getEmail() {
        return this.email;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
