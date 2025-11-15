package com.naturafy.macety.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Datos para registrar un nuevo usuario")
public class RegisterRequest {

    @Schema(description = "Nombre del usuario", example = "Laura Gómez")
    private String name;

    @Schema(description = "Email del usuario", example = "laura@example.com")
    private String email;

    @Schema(description = "Contraseña", example = "12345678")
    private String password;

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
}
