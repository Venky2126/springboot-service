package com.example.socialpost.app.controller;


import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.socialpost.app.model.User;
import com.example.socialpost.app.service.UserService;

@Controller
@RequestMapping("/")
public class RegistrationController {

    private final UserService userService;
    
    // Validation patterns
    private static final Pattern USERNAME_PATTERN = Pattern.compile("^[a-zA-Z0-9]{4,20}$");
    private static final Pattern PASSWORD_PATTERN = Pattern.compile("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).{8,}$");
    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[A-Za-z0-9+_.-]+@(.+)$");

    public RegistrationController(UserService userService) {
        this.userService = userService;
    }

    //http://localhost:8080/register
    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("user", new User());
        return "register";
    }
    
    //http://localhost:8080/
    @GetMapping("")
    public String showLoginForm(Model model) {
        model.addAttribute("user", new User());
        return "login";
    }
    
    //http://localhost:8080/welcome
    @GetMapping("/feed")
    public String showWelcomeForm(Model model) {
        return "feed";
    }

    @PostMapping("/add")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> registerUser(@RequestBody User user) {
        Map<String, Object> response = new HashMap<>();

        try {
            // Validate username
            if (!USERNAME_PATTERN.matcher(user.getUsername()).matches()) {
                response.put("error", "Username must be 4-20 alphanumeric characters");
                return ResponseEntity.badRequest().body(response);
            }

            // Check if username exists
            if (userService.usernameExists(user.getUsername())) {
                response.put("error", "Username already taken");
                return ResponseEntity.badRequest().body(response);
            }

            // Validate password
            if (!PASSWORD_PATTERN.matcher(user.getPassword()).matches()) {
                response.put("error", "Password must be at least 8 characters with uppercase, lowercase and number");
                return ResponseEntity.badRequest().body(response);
            }

            // Validate email
            if (!EMAIL_PATTERN.matcher(user.getEmail()).matches()) {
                response.put("error", "Invalid email format");
                return ResponseEntity.badRequest().body(response);
            }

            // Check if email exists
            if (userService.emailExists(user.getEmail())) {
                response.put("error", "Email already registered");
                return ResponseEntity.badRequest().body(response);
            }

            // Validate display name length
            if (user.getDisplayName() != null && user.getDisplayName().length() > 30) {
                response.put("error", "Display name must be 30 characters or less");
                return ResponseEntity.badRequest().body(response);
            }

            // Validate bio length
            if (user.getBio() != null && user.getBio().length() > 150) {
                response.put("error", "Bio must be 150 characters or less");
                return ResponseEntity.badRequest().body(response);
            }

            // Save user
            User savedUser = userService.registerUser(user);
            
            // Prepare success response
            response.put("success", true);
            response.put("message", "Registration successful");
            response.put("userId", savedUser.getUseId());
            
            return ResponseEntity.ok(response);

        } catch (Exception e) {
            // Log the error
            e.printStackTrace();
            
            // Return generic error message
            response.put("error", "An unexpected error occurred. Please try again.");
            return ResponseEntity.internalServerError().body(response);
        }
    }
}