package dz.energy.energy_backend.service;

import dz.energy.energy_backend.dto.*;
import dz.energy.energy_backend.model.*;
import dz.energy.energy_backend.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.time.LocalDate;

@Service
public class AuthService {

    private final ClientRepository clientRepo;
    private final SellerRepository sellerRepo;
    private final WorkerRepository workerRepo;
    private final AdminRepository adminRepo;
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public AuthService(ClientRepository c, SellerRepository s, WorkerRepository w, AdminRepository a) {
        this.clientRepo = c;
        this.sellerRepo = s;
        this.workerRepo = w;
        this.adminRepo = a;
    }

    public AuthResponse register(RegisterRequest request) {

        // Vérifier email déjà utilisé
        if (clientRepo.findByEmail(request.getEmail()).isPresent()
                || sellerRepo.findByEmail(request.getEmail()).isPresent()
                || workerRepo.findByEmail(request.getEmail()).isPresent()
                || adminRepo.findByEmail(request.getEmail()).isPresent()) {
            throw new RuntimeException("Email déjà utilisé");
        }

        String roleStr = request.getRole() == null ? "CLIENT" : request.getRole();
        Role role = Role.valueOf(roleStr);

        User user;

        switch (role) {
            case CLIENT:
                user = new Client();
                break;
            case SELLER:
                user = new Seller();
                break;
            case WORKER:
                user = new Worker();
                break;
            case ADMIN:
                user = new Admin();
                break;
            default:
                user = new Client();
        }

        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setPhoneNumber(request.getPhoneNumber());


        if (request.getBirthDate() != null && !request.getBirthDate().isEmpty()) {
            try {
                user.setBirthDate(LocalDate.parse(request.getBirthDate())); // yyyy-MM-dd
            } catch (Exception e) {
                throw new RuntimeException("Format de date invalide (yyyy-MM-dd)");
            }
        }


        user.setRole(role);

        // Sauvegarder selon le type
        if (user instanceof Client) clientRepo.save((Client) user);
        else if (user instanceof Seller) sellerRepo.save((Seller) user);
        else if (user instanceof Worker) workerRepo.save((Worker) user);
        else if (user instanceof Admin) adminRepo.save((Admin) user);

        return new AuthResponse(user.getId(), user.getEmail(), user.getRole());
    }

    public AuthResponse login(LoginRequest request) {
        String email = request.getEmail();
        String password = request.getPassword();

        User user = clientRepo.findByEmail(email).orElse(null);
        if (user == null) user = sellerRepo.findByEmail(email).orElse(null);
        if (user == null) user = workerRepo.findByEmail(email).orElse(null);
        if (user == null) user = adminRepo.findByEmail(email).orElse(null);

        if (user == null) throw new RuntimeException("Utilisateur introuvable");
        if (!passwordEncoder.matches(password, user.getPassword()))
            throw new RuntimeException("Mot de passe incorrect");

        return new AuthResponse(user.getId(), user.getEmail(), user.getRole());
    }

    // Vérifie si un email existe dans n'importe quel repository
    public boolean emailExists(String email) {
        return clientRepo.findByEmail(email).isPresent()
                || sellerRepo.findByEmail(email).isPresent()
                || workerRepo.findByEmail(email).isPresent()
                || adminRepo.findByEmail(email).isPresent();
    }


    public void resetPassword(String email,


                                      String newPassword) {
        User user = clientRepo.findByEmail(email).orElse(null);
        if (user == null) user = workerRepo.findByEmail(email).orElse(null);
        if (user == null) user = sellerRepo.findByEmail(email).orElse(null);
        if (user == null) user = adminRepo.findByEmail(email).orElse(null);

        if (user == null) throw new RuntimeException("Utilisateur introuvable");

        user.setPassword(passwordEncoder.encode(newPassword));

        // Sauvegarder selon le type
        if (user instanceof Client) clientRepo.save((Client) user);
        else if (user instanceof Seller) sellerRepo.save((Seller) user);
        else if (user instanceof Worker) workerRepo.save((Worker) user);
        else if (user instanceof Admin) adminRepo.save((Admin) user);
    }



}
