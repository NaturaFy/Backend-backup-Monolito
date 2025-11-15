package com.naturafy.macety.controller;

import com.naturafy.macety.entity.User;
import com.naturafy.macety.repository.UserRepository;
import com.naturafy.macety.security.JwtService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthUserController {

    private final JwtService jwtService;
    private final UserRepository userRepository;

    public AuthUserController(JwtService jwtService, UserRepository userRepository) {
        this.jwtService = jwtService;
        this.userRepository = userRepository;
    }

    @GetMapping("/me")
    public ResponseEntity<?> getCurrentUser(@RequestHeader("Authorization") String authHeader) {
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return ResponseEntity.status(401).body("Token no proporcionado o inválido");
        }

        String token = authHeader.substring(7);
        if (!jwtService.validateToken(token)) {
            return ResponseEntity.status(401).body("Token inválido o expirado");
        }

        String userId = jwtService.extractUserId(token);
        Optional<User> userOpt = userRepository.findById(Long.parseLong(userId));

        if (userOpt.isEmpty()) {
            return ResponseEntity.status(404).body("Usuario no encontrado");
        }

        User user = userOpt.get();
        user.setPassword(null);

        return ResponseEntity.ok(user);
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout() {
        return ResponseEntity.ok().body("{\"message\": \"Sesión cerrada correctamente\"}");
    }
}
