package com.alexmncn.ing_servicios_p1;

import com.alexmncn.ing_servicios_p1.daos.UserDAOInterface;
import com.alexmncn.ing_servicios_p1.dtos.UserDTO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class HomeController {
    @Autowired
    private UserDAOInterface userDAO;


    @GetMapping(value = "/")
    public String homeV(){
        return "home";
    }

    @GetMapping(value = "/register")
    public String registerVG() {
        return "register";
    }

    @PostMapping(value = "/register")
    public String registerVP(
            Model model,
            @RequestParam("username") String username,
            @RequestParam("password") String password,
            @RequestParam("r_password") String r_password
    ) {
        // Check if passwords match
        if(!password.equals(r_password)) {
            model.addAttribute("e_message", "Las contraseñas no coinciden.");
            return "register";
        }

        boolean registerStatus = false;
        UserDTO user = new UserDTO(username, password);

        // Save user
        registerStatus = userDAO.registerUser(user);

        // If register successful, redirect to login
        if (registerStatus) {
            return "login";
        } else {
            model.addAttribute("e_message", "Error en el registro. Inténtalo de nuevo.");
            return "register";
        }
    }

    @GetMapping(value = "/login")
    public String loginVG() {
        return "login";
    }

    @PostMapping(value = "/login")
    public String loginVP(
            Model model,
            HttpServletRequest req,
            @RequestParam("username") String username,
            @RequestParam("password") String password
    ) {
        boolean loginStatus = false;
        UserDTO user = new UserDTO(username, password);

        // Check user credentials
        loginStatus = userDAO.loginUser(user);

        if (loginStatus) {
            // Create session
            HttpSession session = req.getSession(); // Get or create new session
            session.setAttribute("username", user.getUsername()); // Save username in session

            // If admin user return admin panel view
            if (username.equals("admin")) {
                List<UserDTO> users = userDAO.getUsers(); // Get all users

                model.addAttribute("users", users); // Add users to model
                return "adminpanel";
            }

            model.addAttribute("username", user.getUsername());
            return "articles";
        } else {
            model.addAttribute("e_message", "Usuario no registrado");
            return "login";
        }
    }

    @GetMapping(value = "/logout")
    public String logoutVG(
            Model model,
            HttpServletRequest req
    ) {
        // Get session if exists
        HttpSession session = req.getSession(false);

        if (session != null) {
            session.invalidate(); // Delete session
            model.addAttribute("e_message", "Has cerrado sesión.");
        } else {
            model.addAttribute("e_message", "No había una sesión activa.");
        }

        return "login";
    }

    @GetMapping(value = "/articles")
    public String articlesVG(
            Model model,
            HttpServletRequest req
    ) {
        String username;
        // Get session if exists
        HttpSession session = req.getSession(false);

        if (session != null) {
            username = session.getAttribute("username").toString();

            model.addAttribute("username", username);
            return "articles";
        } else {
            model.addAttribute("e_message", "Debes iniciar sesión para ver los artículos");
            return "login";
        }
    }
}