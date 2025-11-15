package com.naturafy.macety.controller;

import com.naturafy.macety.dto.PlantRequest;
import com.naturafy.macety.dto.PlantResponse;
import com.naturafy.macety.dto.UserResponse;
import com.naturafy.macety.entity.Plant;
import com.naturafy.macety.entity.User;
import com.naturafy.macety.repository.PlantRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/plants")
public class PlantController {

    private final PlantRepository plantRepository;

    public PlantController(PlantRepository plantRepository) {
        this.plantRepository = plantRepository;
    }

    @PostMapping
    public ResponseEntity<?> addPlant(@RequestBody PlantRequest request, Authentication authentication) {
        User user = (User) authentication.getPrincipal();

        Plant plant = new Plant();
        plant.setName(request.getName());
        plant.setSpecies(request.getSpecies());
        plant.setDescription(request.getDescription());
        plant.setImageUrl(request.getImageUrl());
        plant.setUser(user);

        Plant savedPlant = plantRepository.save(plant);

        Map<String, Object> response = new HashMap<>();
        response.put("message", "Planta añadida correctamente");
        response.put("plant", convertToDto(savedPlant));

        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<PlantResponse>> getMyPlants(Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        List<Plant> plants = plantRepository.findByUserId(user.getId());

        List<PlantResponse> response = plants.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());

        return ResponseEntity.ok(response);
    }

    private PlantResponse convertToDto(Plant plant) {
        User user = plant.getUser();
        UserResponse userDto = new UserResponse(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getAvatar(),
                user.getLocation(),
                user.getCreatedAt()
        );

        return new PlantResponse(
                plant.getId(),
                plant.getName(),
                plant.getSpecies(),
                plant.getDescription(),
                plant.getImageUrl(),
                plant.getCreatedAt(),
                userDto
        );
    }
}
