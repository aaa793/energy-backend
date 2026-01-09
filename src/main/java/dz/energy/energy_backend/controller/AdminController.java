package dz.energy.energy_backend.controller;

import dz.energy.energy_backend.dto.UserDTO;
import dz.energy.energy_backend.model.*;
import dz.energy.energy_backend.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final UserService service;
    private final BCryptPasswordEncoder passwordEncoder;

    public AdminController(UserService service) {
        this.service = service;
        this.passwordEncoder = new BCryptPasswordEncoder(); // 🔒 Encodage mot de passe
    }

    // ===== PAGE FRONT: LIST USERS =====
    @GetMapping("/users")
    public String listUsersPage(Model model) {
        model.addAttribute("users", service.findAll());
        return "admin/users";
    }

    // ===== PAGE FRONT: ADD / EDIT USER =====
    @GetMapping("/users/new")
    public String addUserPage(Model model) {
        model.addAttribute("user", new UserDTO());
        model.addAttribute("roles", Role.values());
        return "admin/user-form";
    }

    @GetMapping("/users/edit/{id}")
    public String editUserPage(@PathVariable Integer id, Model model) {
        User user = service.findById(id);
        if (user != null) {
            UserDTO dto = new UserDTO();
            dto.setId(user.getId());
            dto.setFirstName(user.getFirstName());
            dto.setLastName(user.getLastName());
            dto.setEmail(user.getEmail());
            dto.setPassword(""); // 🔒 On ne préremplit pas le mot de passe
            dto.setPhoneNumber(user.getPhoneNumber());
            dto.setBirthDate(user.getBirthDate());
            dto.setRole(user.getRole());

            model.addAttribute("user", dto);
            model.addAttribute("roles", Role.values());
            return "admin/user-form";
        } else {
            return "redirect:/admin/users";
        }
    }

    // ===== FRONT FORM SUBMIT =====
    @PostMapping("/users/save")
    public String saveUserForm(@ModelAttribute("user") UserDTO userDTO) {

        User newUser;

        if (userDTO.getId() != null) {
            // ===== UPDATE EXISTING USER =====
            newUser = service.findById(userDTO.getId());
            if (newUser == null) {
                newUser = new Admin(); // fallback
            }
        } else {
            // ===== CREATE NEW USER =====
            switch (userDTO.getRole()) {
                case ADMIN: newUser = new Admin(); break;
                case SELLER: newUser = new Seller(); break;
                case WORKER: newUser = new Worker(); break;
                case CLIENT: newUser = new Client(); break;
                default: newUser = new Admin();
            }
        }

        // Copier les champs
        newUser.setFirstName(userDTO.getFirstName());
        newUser.setLastName(userDTO.getLastName());
        newUser.setEmail(userDTO.getEmail());

        // 🔒 Encoder le mot de passe uniquement si il est renseigné
        if (userDTO.getPassword() != null && !userDTO.getPassword().isEmpty()) {
            newUser.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        }

        newUser.setPhoneNumber(userDTO.getPhoneNumber());
        newUser.setBirthDate(userDTO.getBirthDate());
        newUser.setRole(userDTO.getRole());

        service.save(newUser);

        return "redirect:/admin/users";
    }

    // ===== DELETE USER =====
    @GetMapping("/users/delete/{id}")
    public String deleteUser(@PathVariable Integer id) {
        service.delete(id);
        return "redirect:/admin/users";
    }

    // ===== REST API: LIST USERS (JSON) =====
    @GetMapping("/api/users")
    @ResponseBody
    public ResponseEntity<?> listUsersJson() {
        return ResponseEntity.ok(service.findAll());
    }

    // ===== REST API: SAVE USER (JSON) =====
    @PostMapping("/api/users/save")
    @ResponseBody

    public ResponseEntity<?> saveUserJson(@RequestBody UserDTO userDTO) {

        User newUser;
        switch (userDTO.getRole()) {
            case ADMIN: newUser = new Admin(); break;
            case SELLER: newUser = new Seller(); break;
            case WORKER: newUser = new Worker(); break;
            case CLIENT: newUser = new Client(); break;
            default: newUser = new Admin();
        }

        if (userDTO.getId() != null) newUser.setId(userDTO.getId());

        newUser.setFirstName(userDTO.getFirstName());
        newUser.setLastName(userDTO.getLastName());
        newUser.setEmail(userDTO.getEmail());

        if (userDTO.getPassword() != null && !userDTO.getPassword().isEmpty()) {
            newUser.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        }

        newUser.setPhoneNumber(userDTO.getPhoneNumber());
        newUser.setBirthDate(userDTO.getBirthDate());
        newUser.setRole(userDTO.getRole());

        User savedUser = service.save(newUser);
        return ResponseEntity.ok(savedUser);
    }
}
