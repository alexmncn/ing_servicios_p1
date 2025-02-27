package com.alexmncn.ing_servicios_p1.dtos;

import java.io.Serializable;

public class User_ implements Serializable {
    private String username;
    private String email;
    private String name;
    private String lastName;

    public User_() {
        // No Args
    }

    public User_(String username, String email, String name, String lastName) {
        this.username = username;
        this.email = email;
        this.name = name;
        this.lastName = lastName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getLastName(){
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
}
