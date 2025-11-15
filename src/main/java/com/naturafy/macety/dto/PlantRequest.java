package com.naturafy.macety.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Datos para registrar una nueva planta")
public class PlantRequest {

    @Schema(description = "Nombre de la planta", example = "Monstera Deliciosa")
    private String name;

    @Schema(description = "Especie de la planta", example = "Araceae")
    private String species;

    @Schema(description = "Descripción o notas del usuario", example = "Planta tropical con hojas grandes y perforadas")
    private String description;

    @Schema(description = "URL de imagen", example = "https://example.com/monstera.jpg")
    private String imageUrl;

    // Getters y setters
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getSpecies() { return species; }
    public void setSpecies(String species) { this.species = species; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getImageUrl() { return imageUrl; }
    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }
}
