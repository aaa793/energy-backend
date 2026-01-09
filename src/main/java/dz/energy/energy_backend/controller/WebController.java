package dz.energy.energy_backend.controller;

import dz.energy.energy_backend.dto.LoginRequest;
import dz.energy.energy_backend.dto.RegisterRequest;
import dz.energy.energy_backend.dto.AuthResponse;
import dz.energy.energy_backend.model.Role;
import dz.energy.energy_backend.service.AuthService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/admin")
public class WebController {

    private final AuthService authService;

    public WebController(AuthService authService) {
        this.authService = authService;
    }

    // ===== Afficher login =====
    @GetMapping("/login")
    public String loginForm(Model model){
        model.addAttribute("loginRequest", new LoginRequest());
        return "admin/login";
    }

    // ===== Traitement login =====
    @PostMapping("/login")
    public String loginSubmit(@ModelAttribute("loginRequest") LoginRequest loginRequest,
                              RedirectAttributes redirectAttributes) {
        try {
            AuthResponse response = authService.login(loginRequest);

            // Redirection selon rôle
            if (response.getRole() == Role.ADMIN) {
                return "redirect:/admin/users";
            } else if (response.getRole() == Role.SELLER) {
                // Passer l'ID du seller connecté
                return "redirect:/seller/products?sellerId=" + response.getId();
            } else {
                return "redirect:/admin/login";
            }

        } catch (RuntimeException e){
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/admin/login";
        }
    }

    // ===== Afficher signup =====
    @GetMapping("/signup")
    public String signupForm(Model model){
        model.addAttribute("registerRequest", new RegisterRequest());
        return "admin/signup";
    }

    // ===== Traitement signup =====
    @PostMapping("/signup")
    public String signupSubmit(@ModelAttribute("registerRequest") RegisterRequest registerRequest,
                               RedirectAttributes redirectAttributes){
        try {
            authService.register(registerRequest);
            redirectAttributes.addFlashAttribute("success", "Compte créé avec succès ✅");
            return "redirect:/admin/login";
        } catch (RuntimeException e){
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/admin/signup";
        }
    }
}