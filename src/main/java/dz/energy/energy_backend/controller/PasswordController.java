package dz.energy.energy_backend.controller;

import dz.energy.energy_backend.dto.LoginRequest;
import dz.energy.energy_backend.model.Admin;
import dz.energy.energy_backend.model.Seller;
import dz.energy.energy_backend.repository.AdminRepository;
import dz.energy.energy_backend.repository.SellerRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

@Controller
@RequestMapping("/password")
public class PasswordController {

    private final AdminRepository adminRepo;
    private final SellerRepository sellerRepo;
    private final PasswordEncoder passwordEncoder;

    public PasswordController(AdminRepository adminRepo,
                              SellerRepository sellerRepo,
                              PasswordEncoder passwordEncoder) {
        this.adminRepo = adminRepo;
        this.sellerRepo = sellerRepo;
        this.passwordEncoder = passwordEncoder;
    }

    // =========================
    // PAGE MOT DE PASSE OUBLIÉ
    // =========================
    @GetMapping("/forgot")
    public String forgotPasswordPage() {
        return "admin/forgot-password";
    }

    @PostMapping("/forgot")
    public String handleForgotPassword(@RequestParam String email, Model model) {
        Optional<Admin> adminOpt = adminRepo.findByEmail(email);
        Optional<Seller> sellerOpt = sellerRepo.findByEmail(email);

        if (adminOpt.isEmpty() && sellerOpt.isEmpty()) {
            model.addAttribute("error", "Email introuvable");
            return "admin/forgot-password";
        }

        // Simulation du lien de reset
        String resetLink = "http://localhost:8080/password/reset?email=" + email;
        System.out.println("Lien de réinitialisation : " + resetLink);

        model.addAttribute("success", "Un lien de réinitialisation a été envoyé à votre email");
        return "admin/forgot-password";
    }

    // =========================
    // PAGE RESET PASSWORD
    // =========================
    @GetMapping("/reset")
    public String resetPasswordPage(@RequestParam String email, Model model) {
        Optional<Admin> adminOpt = adminRepo.findByEmail(email);
        Optional<Seller> sellerOpt = sellerRepo.findByEmail(email);

        if (adminOpt.isEmpty() && sellerOpt.isEmpty()) {
            model.addAttribute("error", "Lien invalide");
            model.addAttribute("loginRequest", new LoginRequest());
            return "admin/login";
        }

        model.addAttribute("email", email);
        return "admin/reset-password";
    }

    // =========================
    // SAUVEGARDE NOUVEAU PASSWORD
    // =========================
    @PostMapping("/reset")
    public String handleResetPassword(@RequestParam String email,
                                      @RequestParam String newPassword,
                                      @RequestParam String confirmPassword,
                                      Model model) {

        if (!newPassword.equals(confirmPassword)) {
            model.addAttribute("error", "Les mots de passe ne correspondent pas");
            model.addAttribute("email", email);
            return "admin/reset-password";
        }

        Optional<Admin> adminOpt = adminRepo.findByEmail(email);
        Optional<Seller> sellerOpt = sellerRepo.findByEmail(email);

        if (adminOpt.isPresent()) {
            Admin admin = adminOpt.get();
            admin.setPassword(passwordEncoder.encode(newPassword));
            adminRepo.save(admin);
        } else if (sellerOpt.isPresent()) {
            Seller seller = sellerOpt.get();
            seller.setPassword(passwordEncoder.encode(newPassword));
            sellerRepo.save(seller);
        } else {
            model.addAttribute("error", "Erreur lors de la réinitialisation");
            model.addAttribute("loginRequest", new LoginRequest());
            return "admin/login";
        }

        model.addAttribute("success", "Mot de passe modifié avec succès");
        model.addAttribute("loginRequest", new LoginRequest());
        return "admin/login";
    }
}
