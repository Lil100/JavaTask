package com.todo.user.controller;

import com.todo.user.entity.User;
import com.todo.user.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
@CrossOrigin
public class AuthController {
    private final AuthService authService;


    @PostMapping("/signup")
    public ResponseEntity<String> signUp(@RequestBody User user) {
        try {
            authService.signUp(user);
            return ResponseEntity.ok("User signed up successfully!");
        } catch (RuntimeException e) {
            return ResponseEntity.status(409).body("User already exists");
        }
    }

    @PostMapping("/signin")
    public ResponseEntity<String> signIn(@RequestBody User user) {
        try {
            String token = authService.signIn(user);
            return ResponseEntity.ok("User successfully signed in! Token: " + token);
        } catch (RuntimeException e) {
            return ResponseEntity.status(401).body("Invalid username or password");
        }
    }

}
