package dz.energy.energy_backend.controller;

import dz.energy.energy_backend.dto.*;
import dz.energy.energy_backend.service.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin("*")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) { this.authService = authService; }

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@RequestBody RegisterRequest request) {
        return ResponseEntity.ok(authService.register(request));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest request) {
        return ResponseEntity.ok(authService.login(request));
    }

    @GetMapping("/check-email")
    public ResponseEntity<Boolean> checkEmail(@RequestParam String email) {
        boolean exists = authService.emailExists(email);
        return ResponseEntity.ok(exists);
    }

    @PostMapping("/reset-password")
    public ResponseEntity<Map<String, String>> resetPassword(@RequestBody ResetPasswordRequest request) {
        Map<String, String> resp = new HashMap<>();
        try {
            authService.resetPassword(request.getEmail(), request.getNewPassword());
            resp.put("message", "Mot de passe réinitialisé avec succès ✅");
            return ResponseEntity.ok(resp);
        } catch (RuntimeException e) {
            resp.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(resp);
        }
    }

}