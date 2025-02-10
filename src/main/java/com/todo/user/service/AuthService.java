package com.todo.user.service;

import com.todo.user.JwtUtil;
import com.todo.user.entity.User;
import com.todo.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public String signUp(User user) {
        Optional<User> existingUser = userRepository.findByUsername(user.getUsername());
        if (existingUser.isPresent()) {
            throw new RuntimeException("Username already exists");
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        return "User registered successfully";
    }

    public String signIn(User user) {
        System.out.println("🔍 Checking username: " + user.getUsername());
        System.out.println("🔎 Searching for user in DB: " + user.getUsername());

        User existingUser = userRepository.findByUsername(user.getUsername())
                .orElseThrow(() -> {
                    System.out.println("❌ User not found: " + user.getUsername());
                    return new RuntimeException("User not found: " + user.getUsername());
                });

        System.out.println("✅ Found user: " + existingUser.getUsername());
        System.out.println("🔑 Raw password entered: " + user.getPassword());
        System.out.println("🔐 Stored (hashed) password: " + existingUser.getPassword());
        boolean matches = passwordEncoder.matches(user.getPassword(), existingUser.getPassword());
        System.out.println("🔄 Password matches: " + matches);

        if (!passwordEncoder.matches(user.getPassword(), existingUser.getPassword())) {
            System.out.println("❌ Password does not match!");
            throw new RuntimeException("Invalid username or password");
        }

        System.out.println("✅ Password matched successfully!");
        String token = jwtUtil.generateToken(existingUser.getUsername());
        System.out.println("🛡️ Token generated: " + token);

        return token;
    }

}


