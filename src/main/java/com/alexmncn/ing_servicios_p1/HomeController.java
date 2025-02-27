package com.alexmncn.ing_servicios_p1;

import com.alexmncn.ing_servicios_p1.daos.UserDAOInterface;
import com.alexmncn.ing_servicios_p1.dtos.UserDTO;
import com.alexmncn.ing_servicios_p1.dtos.User_;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.Objects;

@Controller
public class HomeController {
    @Autowired
    private UserDAOInterface userDAO;


    @GetMapping(value = "/")
    public String homeV(){
        return "home";
    }

    @GetMapping(value = "/form")
    public String formVG() {
        return "form";
    }

    @PostMapping(value = "/form")
    public String formVP(
            Model model,
            HttpServletRequest req,
            HttpServletResponse res,
            @RequestParam("username") String username,
            @RequestParam("email") String email,
            @RequestParam("name") String name,
            @RequestParam("last_name") String last_name
    ) {
        // New session
        User_ user = new User_(username, email, name, last_name); // New user object
        HttpSession session = req.getSession(); // Get or create new session
        session.setAttribute("user", user); // Save user data in session

        // Cookie perm.
        Cookie c = new Cookie("user", user.getUsername());
        c.setMaxAge(60*60); // 1 hour
        c.setPath("/");
        res.addCookie(c); // Add cookie to response

        // Add param. values to model
        model.addAttribute("username", username);
        model.addAttribute("email", email);
        model.addAttribute("name", name);
        model.addAttribute("last_name", last_name);
        return "user";
    }

    @GetMapping(value = "/userdata")
    public String userDataVG(Model model, HttpServletRequest req) {
        HttpSession session = req.getSession(false);

        // Check if session exists
        if (session != null) {
            User_ user = (User_) session.getAttribute("user");

            // Add param. values to model
            model.addAttribute("username", user.getUsername());
            model.addAttribute("email", user.getEmail());
            model.addAttribute("name", user.getName());
            model.addAttribute("last_name", user.getLastName());
        }

        // Check cookies
        Cookie[] cookies = req.getCookies();
        String cookieName = "user";
        String cookieValue = "";

        // Get the value of cookie with name 'cookieName'
        if (cookies != null) {
            for (Cookie cookie:cookies) {
                if (cookieName.equals(cookie.getName())) cookieValue = cookie.getValue();
            }
        }

        System.out.println(cookieName +": " + cookieValue);

        return "user";
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
                ArrayList<UserDTO> users = userDAO.getUsers(); // Get all users

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