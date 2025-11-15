package com.naturafy.macety.controller;

import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.Optional;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.naturafy.macety.entity.User;
import com.naturafy.macety.repository.UserRepository;
import com.naturafy.macety.security.JwtService;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@RestController
@RequestMapping("/api/v1/auth")
@Validated
public class AuthController {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public AuthController(UserRepository userRepository,
                          PasswordEncoder passwordEncoder,
                          JwtService jwtService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody RegisterRequest request) {

        if (userRepository.existsByEmail(request.getEmail())) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(new ErrorResponse("El correo ya está registrado"));
        }

        String hashed = passwordEncoder.encode(request.getPassword());

        User user = new User();
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setPassword(hashed);

        User saved = userRepository.save(user);
        saved.setPassword(null);

        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequest request) {

        Optional<User> userOpt = userRepository.findByEmail(request.getEmail());

        if (userOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new ErrorResponse("Correo o contraseña incorrecta"));
        }

        User user = userOpt.get();

        boolean matches = passwordEncoder.matches(request.getPassword(), user.getPassword());
        if (!matches) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new ErrorResponse("Correo o contraseña incorrecta"));
        }

        String token = jwtService.generateToken(
                String.valueOf(user.getId()),
                user.getEmail(),
                user.getName()
        );

        String refreshToken = UUID.randomUUID().toString();

        User userSafe = new User();
        userSafe.setId(user.getId());
        userSafe.setName(user.getName());
        userSafe.setEmail(user.getEmail());
        userSafe.setAvatar(user.getAvatar());
        userSafe.setLocation(user.getLocation());
        userSafe.setPassword(null);
        userSafe.setCreatedAt(user.getCreatedAt());

        AuthResponse resp = new AuthResponse(token, refreshToken, "Bearer",
                jwtService.getExpirationInSeconds(), userSafe);

        return ResponseEntity.ok(resp);
    }

    public static class LoginRequest {
        @NotBlank(message = "El email es obligatorio")
        @Email(message = "Email inválido")
        private String email;

        @NotBlank(message = "La contraseña es obligatoria")
        private String password;

        public LoginRequest() {}

        public String getEmail() { return email; }
        public void setEmail(String email) { this.email = email; }

        public String getPassword() { return password; }
        public void setPassword(String password) { this.password = password; }
    }

    public static class RegisterRequest {
        @NotBlank(message = "El nombre es obligatorio")
        private String name;

        @NotBlank(message = "El email es obligatorio")
        @Email(message = "Email inválido")
        private String email;

        @NotBlank(message = "La contraseña es obligatoria")
        @Size(min = 8, message = "La contraseña debe tener al menos 8 caracteres")
        private String password;

        public RegisterRequest() {}

        public String getName() { return name; }
        public void setName(String name) { this.name = name; }

        public String getEmail() { return email; }
        public void setEmail(String email) { this.email = email; }

        public String getPassword() { return password; }
        public void setPassword(String password) { this.password = password; }
    }

    public static class AuthResponse {
        private String access_token;
        private String refresh_token;
        private String token_type;
        private long expires_in;
        private User user;

        public AuthResponse(String access_token, String refresh_token, String token_type, long expires_in, User user) {
            this.access_token = access_token;
            this.refresh_token = refresh_token;
            this.token_type = token_type;
            this.expires_in = expires_in;
            this.user = user;
        }

        public String getAccess_token() { return access_token; }
        public void setAccess_token(String access_token) { this.access_token = access_token; }

        public String getRefresh_token() { return refresh_token; }
        public void setRefresh_token(String refresh_token) { this.refresh_token = refresh_token; }

        public String getToken_type() { return token_type; }
        public void setToken_type(String token_type) { this.token_type = token_type; }

        public long getExpires_in() { return expires_in; }
        public void setExpires_in(long expires_in) { this.expires_in = expires_in; }

        public User getUser() { return user; }
        public void setUser(User user) { this.user = user; }
    }

    public static class ErrorResponse {
        private String message;
        private String timestamp;

        public ErrorResponse() {}

        public ErrorResponse(String message) {
            this.message = message;
            this.timestamp = ZonedDateTime.now(ZoneOffset.UTC).toString();
        }

        public String getMessage() { return message; }
        public void setMessage(String message) { this.message = message; }

        public String getTimestamp() { return timestamp; }
        public void setTimestamp(String timestamp) { this.timestamp = timestamp; }
    }
}
