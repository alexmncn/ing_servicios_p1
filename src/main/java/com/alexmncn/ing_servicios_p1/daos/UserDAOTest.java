package com.alexmncn.ing_servicios_p1.daos;

import com.alexmncn.ing_servicios_p1.dtos.UserDTO;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

@Repository
public class UserDAOTest implements UserDAOInterface {
    @Override
    public boolean loginUser(UserDTO user) {
        String username = user.getUsername();
        String password = user.getPassword();

        ArrayList<UserDTO> users = getUsers();

        for (UserDTO user_:users){
            if (username.equals(user_.getUsername())) {
                return password.equals(user_.getPassword());
            }
        }

        return false;
    }

    public ArrayList<UserDTO> getUsers( ){
        ArrayList<UserDTO> users = new ArrayList<UserDTO>();
        UserDTO user1 = new UserDTO("admin","admin");
        UserDTO user2 = new UserDTO("amc00300","12345678");
        users.add(user1); users.add(user2);
        return users;
    }
}
