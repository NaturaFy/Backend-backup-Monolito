package com.naturafy.macety.entity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class PlantTest {

    private Plant plant;
    private User user;

    @BeforeEach
    void setUp() {
        // Preparamos objetos comunes para los tests
        plant = new Plant();
        user = new User();
        user.setId(1L);
        user.setName("Test User");
    }

    @Test
    void testGettersAndSetters() {
        LocalDateTime now = LocalDateTime.now();

        plant.setId(1L);
        plant.setName("Monstera Deliciosa");
        plant.setSpecies("Monstera");
        plant.setDescription("Planta tropical de interior.");
        plant.setImageUrl("http://example.com/monstera.jpg");
        plant.setCreatedAt(now);
        plant.setUser(user);

        assertEquals(1L, plant.getId());
        assertEquals("Monstera Deliciosa", plant.getName());
        assertEquals("Monstera", plant.getSpecies());
        assertEquals("Planta tropical de interior.", plant.getDescription());
        assertEquals("http://example.com/monstera.jpg", plant.getImageUrl());
        assertEquals(now, plant.getCreatedAt());
        assertEquals(user, plant.getUser());
    }

    @Test
    void testUserAssociation() {
        plant.setUser(user);
        assertNotNull(plant.getUser());
        assertEquals(1L, plant.getUser().getId());
        assertEquals("Test User", plant.getUser().getName());
    }

    @Test
    void testCreatedAtIsNotNullByDefault() {
        // El constructor por defecto inicializa createdAt
        Plant newPlant = new Plant();
        assertNotNull(newPlant.getCreatedAt());
    }


}
