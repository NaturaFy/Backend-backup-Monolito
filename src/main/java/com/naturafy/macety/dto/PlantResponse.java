package com.naturafy.macety.dto;

import java.time.LocalDateTime;

public record PlantResponse(
        Long id,
        String name,
        String species,
        String description,
        String imageUrl,
        LocalDateTime createdAt,
        UserResponse user
) {}
