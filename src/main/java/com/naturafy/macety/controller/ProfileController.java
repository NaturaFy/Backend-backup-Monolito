package com.naturafy.macety.controller;

import com.naturafy.macety.entity.User;
import com.naturafy.macety.repository.PlantRepository;
import com.naturafy.macety.repository.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/profile")
public class ProfileController {

    private final UserRepository userRepository;
    private final PlantRepository plantRepository;

    public ProfileController(UserRepository userRepository, PlantRepository plantRepository) {
        this.userRepository = userRepository;
        this.plantRepository = plantRepository;
    }

    @GetMapping
    public ResponseEntity<?> getProfile(Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        user.setPassword(null);
        return ResponseEntity.ok(user);
    }

    @PutMapping
    public ResponseEntity<?> updateProfile(Authentication authentication,
                                           @RequestBody Map<String, Object> updates) {
        User user = (User) authentication.getPrincipal();

        if (updates.containsKey("name")) {
            user.setName((String) updates.get("name"));
        }
        if (updates.containsKey("avatar")) {
            user.setAvatar((String) updates.get("avatar"));
        }
        if (updates.containsKey("location")) {
            user.setLocation((String) updates.get("location"));
        }

        userRepository.save(user);

        Map<String, Object> response = new HashMap<>();
        response.put("message", "Perfil actualizado correctamente");
        response.put("user", user);

        return ResponseEntity.ok(response);
    }

    @DeleteMapping
    public ResponseEntity<?> deleteProfile(Authentication authentication) {
        User user = (User) authentication.getPrincipal();

        plantRepository.deleteByUser(user);

        userRepository.deleteById(user.getId());

        Map<String, Object> response = new HashMap<>();
        response.put("message", "Cuenta y plantas eliminadas correctamente");

        return ResponseEntity.ok(response);
    }
}
