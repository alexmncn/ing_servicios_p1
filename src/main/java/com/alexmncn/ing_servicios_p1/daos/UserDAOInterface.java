package com.alexmncn.ing_servicios_p1.daos;

import com.alexmncn.ing_servicios_p1.dtos.UserDTO;

import java.util.ArrayList;

public interface UserDAOInterface {
    public boolean loginUser(UserDTO user);

    public ArrayList<UserDTO> getUsers();
}
