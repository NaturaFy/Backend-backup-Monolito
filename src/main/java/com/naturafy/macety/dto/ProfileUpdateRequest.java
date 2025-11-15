package com.naturafy.macety.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Datos para actualizar el perfil del usuario")
public class ProfileUpdateRequest {

    @Schema(description = "Nombre del usuario", example = "Laura Gómez")
    private String name;

    @Schema(description = "URL del avatar del usuario", example = "https://i.pravatar.cc/150?img=7")
    private String avatar;

    @Schema(description = "Ubicación del usuario", example = "Buenos Aires, Argentina")
    private String location;

    // Getters y setters
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getAvatar() { return avatar; }
    public void setAvatar(String avatar) { this.avatar = avatar; }

    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }
}
