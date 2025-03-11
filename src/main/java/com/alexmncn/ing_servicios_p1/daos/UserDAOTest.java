package com.alexmncn.ing_servicios_p1.daos;

import com.alexmncn.ing_servicios_p1.dtos.UserDTO;

import java.util.ArrayList;
import java.util.List;


public class UserDAOTest implements UserDAOInterface {
    @Override
    public boolean registerUser(UserDTO user) {
        return true;
    }

    @Override
    public boolean loginUser(UserDTO user) {
        String username = user.getUsername();
        String password = user.getPassword();

        List<UserDTO> users = getUsers();

        for (UserDTO user_:users){
            if (username.equals(user_.getUsername())) {
                return password.equals(user_.getPassword());
            }
        }

        return false;
    }

    @Override
    public List<UserDTO> getUsers( ){
        List<UserDTO> users = new ArrayList<>();
        UserDTO user1 = new UserDTO("admin","admin");
        UserDTO user2 = new UserDTO("amc00300","12345678");
        UserDTO user3 = new UserDTO("adri","adri");
        users.add(user1); users.add(user2); users.add(user3);
        return users;
    }

    @Override
    public UserDTO getUserByUsername(String username) {
        List<UserDTO> users = new ArrayList<>();
        return new UserDTO("alex","alex");
    }
}
