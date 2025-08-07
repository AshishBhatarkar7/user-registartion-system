package com.userAuthentication;

import com.userAuthentication.model.User;
import com.userAuthentication.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

@Controller
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    // Show registration page
    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("user", new User());
        return "register";
    }

    // Process registration
    @PostMapping("/register")
    public String registerUser(@Valid @ModelAttribute("user") User user,
                               BindingResult result,
                               Model model) {
        if (userRepository.findByEmail(user.getEmail()) != null) {
            result.rejectValue("email", "error.user", "An account with this email already exists.");
        }

        if (result.hasErrors()) {
            return "register";
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        model.addAttribute("successMessage", "User registered successfully!");
        return "register";
    }

    // Show login page
    @GetMapping("/login")
    public String showLoginForm(@RequestParam(value = "error", required = false) String error,
                                @RequestParam(value = "logout", required = false) String logout,
                                Model model) {
        if (error != null) {
            model.addAttribute("errorMessage", "Invalid username or password.");
        }
        if (logout != null) {
            model.addAttribute("logoutMessage", "You have been logged out successfully.");
        }
        return "login";
    }

    // After login, redirect to dashboard
    @GetMapping("/dashboard")
    public String showDashboard() {
        return "dashboard";
    }
    
    
}
