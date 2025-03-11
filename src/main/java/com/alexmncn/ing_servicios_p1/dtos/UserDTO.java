package com.alexmncn.ing_servicios_p1.dtos;

import java.io.Serializable;

public class UserDTO implements Serializable {
    private int id;
    private String username;
    private String password;
    private String role;

    public UserDTO () {}

    public UserDTO(String username, String password) {
        this.username = username;
        this.password = password;
        this.role = "user"; // All users by def
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
