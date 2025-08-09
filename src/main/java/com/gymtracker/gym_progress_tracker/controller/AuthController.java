package com.gymtracker.gym_progress_tracker.controller;

import com.gymtracker.gym_progress_tracker.entity.User;
import com.gymtracker.gym_progress_tracker.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class AuthController {

    @Autowired
    private UserService userService;

    @GetMapping("/login")
    public String loginPage(){
        return "login";
    }

    @GetMapping("/register")
    public String registerPage(){
        return "register";
    }

    @PostMapping("/register")
    public String registerUser(
            @RequestParam String username,
            @RequestParam String email,
            @RequestParam String password,
            @RequestParam String confirmPassword,
            @RequestParam(required = false) String firstName,
            @RequestParam(required = false) String lastName,
            RedirectAttributes redirectAttributes,
            Model model
    ){
        try {
            // Validate passwords match
            if (!password.equals(confirmPassword)) {
                model.addAttribute("error", "Passwords do not match!");
                return "register";
            }

            // Validate password length
            if (password.length() < 6) {
                model.addAttribute("error", "Password must be at least 6 characters long!");
                return "register";
            }

            // Register the user
            User user = userService.registerUser(username, email, password, firstName, lastName);

            redirectAttributes.addFlashAttribute("success",
                    "Registration successful! Welcome " + user.getFirstName() + "! Please log in.");

            return "redirect:/login";

        } catch (RuntimeException e) {
            model.addAttribute("error", e.getMessage());
            model.addAttribute("username", username);
            model.addAttribute("email", email);
            model.addAttribute("firstName", firstName);
            model.addAttribute("lastName", lastName);
            return "register";
        }



    }
}
