package com.naturafy.macety.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;

@Schema(description = "Datos del usuario")
public class UserResponse {

    private Long id;
    private String name;
    private String email;
    private String avatar;
    private String location;
    private LocalDateTime createdAt;

    public UserResponse() {}

    public UserResponse(Long id, String name, String email, String avatar, String location, LocalDateTime createdAt) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.avatar = avatar;
        this.location = location;
        this.createdAt = createdAt;
    }

    // Getters y setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getAvatar() { return avatar; }
    public void setAvatar(String avatar) { this.avatar = avatar; }
    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}
