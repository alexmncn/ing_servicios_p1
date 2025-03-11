package com.alexmncn.ing_servicios_p1.daos;

import com.alexmncn.ing_servicios_p1.dtos.UserDTO;

import java.util.List;

public interface UserDAOInterface {
    public boolean registerUser(UserDTO user);

    public boolean loginUser(UserDTO user);

    public List<UserDTO> getUsers();

    public UserDTO getUserByUsername(String username);
}
