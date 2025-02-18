package com.alexmncn.ing_servicios_p1;

import com.alexmncn.ing_servicios_p1.data.User;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.beans.JavaBean;

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
            @RequestParam("username") String username,
            @RequestParam("email") String email,
            @RequestParam("name") String name,
            @RequestParam("last_name") String last_name
    ) {
        User user = new User(username, email, name, last_name); // New user object
        HttpSession session = req.getSession(); // Get or create new session
        session.setAttribute("user", user); // Save user data in session

        model.addAttribute("username", username);
        model.addAttribute("email", email);
        model.addAttribute("name", name);
        model.addAttribute("last_name", last_name);
        return "user";
    }

    @GetMapping(value = "/userdata")
    public String userDataVG(Model model, HttpServletRequest req) {
        HttpSession session = req.getSession(false);

        if (session != null) {
            User user = (User) session.getAttribute("user");

            model.addAttribute("username", user.getUsername());
            model.addAttribute("email", user.getEmail());
            model.addAttribute("name", user.getName());
            model.addAttribute("last_name", user.getLastName());
        }

        return "user";
    }
}