package com.alexmncn.ing_servicios_p1.daos;

import com.alexmncn.ing_servicios_p1.dtos.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;
import java.util.NoSuchElementException;

@Repository
public class UserDAOJdbc implements UserDAOInterface {
    private JdbcTemplate jdbcTemplate;
    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    private final RowMapper<UserDTO> mapper = (rs, numRow) -> {
        UserDTO user = new UserDTO();
        user.setId(rs.getInt("id"));
        user.setUsername(rs.getString("username"));
        user.setPassword(rs.getString("password"));
        user.setRole(rs.getString("role"));
        return user;
    };

    @Override
    public boolean registerUser(UserDTO user) {
        String sql = "INSERT INTO users (username,password) values(?,?)";
        int n = this.jdbcTemplate.update(sql, user.getUsername(), user.getPassword());
        return n == 1;
    }

    @Override
    public boolean loginUser(UserDTO user) {
        UserDTO user_ = getUserByUsername(user.getUsername());

        if (user_ != null) {
            return user.getPassword().equals(user_.getPassword());
        }

        return false;
    }

    @Override
    public List<UserDTO> getUsers() {
        String sql = "SELECT * FROM users";
        return this.jdbcTemplate.query(sql, mapper);
    }

    @Override
    public UserDTO getUserByUsername(String username) {
        String sql = "SELECT * FROM users WHERE username = ?";
        List<UserDTO> users = this.jdbcTemplate.query(sql, mapper, username);

        UserDTO user;

        try {
            user = users.getFirst();
        } catch (NoSuchElementException _) {
            return null;
        }

        return user;
    }
}