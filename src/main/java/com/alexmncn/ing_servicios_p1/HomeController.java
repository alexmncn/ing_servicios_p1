package com.alexmncn.ing_servicios_p1;

import com.alexmncn.ing_servicios_p1.data.User;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.apache.juli.logging.Log;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.beans.JavaBean;
import java.util.logging.Logger;

@Controller
public class HomeController {
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
        User user = new User(username, email, name, last_name); // New user object
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
            User user = (User) session.getAttribute("user");

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
}