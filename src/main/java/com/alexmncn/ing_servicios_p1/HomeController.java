package com.alexmncn.ing_servicios_p1;

import com.alexmncn.ing_servicios_p1.daos.ArticleDAOInterface;
import com.alexmncn.ing_servicios_p1.daos.UserDAOInterface;
import com.alexmncn.ing_servicios_p1.dtos.ArticleDTO;
import com.alexmncn.ing_servicios_p1.dtos.UserDTO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class HomeController {
    @Autowired
    private UserDAOInterface userDAO;

    @Autowired
    private ArticleDAOInterface articleDAO;


    @GetMapping(value = "/")
    public String homeV(){
        return "redirect:/articles";
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
            return "/login";
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
            RedirectAttributes redirectAttributes,
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

            // Get user role
            String user_role = userDAO.getUserByUsername(username).getRole();

            // If admin role return admin panel view
            if (user_role.equals("admin")) {
                return "redirect:/adminpanel";
            }

            return "redirect:/articles";
        } else {
            model.addAttribute("e_message", "Usuario no registrado");
            return "login";
        }
    }

    @GetMapping(value = "/logout")
    public String logoutVG(
            RedirectAttributes redirectAttributes,
            HttpServletRequest req
    ) {
        // Get session if exists
        HttpSession session = req.getSession(false);

        if (session != null) {
            session.invalidate(); // Delete session
            redirectAttributes.addFlashAttribute("e_message", "Has cerrado sesión.");
        } else {
            redirectAttributes.addFlashAttribute("e_message", "No había una sesión activa.");
        }

        return "redirect:/login";
    }

    @GetMapping(value = "/articles")
    public String articlesVG(
            Model model,
            RedirectAttributes redirectAttributes,
            HttpServletRequest req
    ) {
        String username;
        // Get session if exists
        HttpSession session = req.getSession(false);

        if (session != null) {
            username = session.getAttribute("username").toString();

            // Get articles
            List<ArticleDTO> featuredArticles = articleDAO.getFeaturedArticles();
            List<ArticleDTO> newArticles = articleDAO.getNewArticles();

            model.addAttribute("username", username);
            model.addAttribute("featured_articles", featuredArticles);
            model.addAttribute("new_articles", newArticles);
            return "articles";
        } else {
            redirectAttributes.addFlashAttribute("e_message", "Debes iniciar sesión para ver los artículos");
            return "redirect:/login";
        }
    }

    @GetMapping(value = "/adminpanel")
    public String adminPanelVG(
            Model model,
            HttpServletRequest req,
            RedirectAttributes redirectAttributes
    ) {
        HttpSession session = req.getSession(false); // Get session
        String user_role;

        if (session != null) {
            String username = session.getAttribute("username").toString();

            if (username != null) {
                // Get user role
                user_role = userDAO.getUserByUsername(username).getRole();

                // If admin role return admin panel view
                if (user_role.equals("admin")) {
                    List<UserDTO> users = userDAO.getUsers(); // Get all users

                    model.addAttribute("username", username);
                    model.addAttribute("users", users); // Add users to model
                    return "adminpanel";
                }
            }
        }

        redirectAttributes.addFlashAttribute("e_message", "No has iniciado sesión. Debes ser admin.");
        return "redirect:/login";
    }
}