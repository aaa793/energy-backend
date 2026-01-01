package dz.energy.energy_backend.controller;

import dz.energy.energy_backend.dto.UserDTO;
import dz.energy.energy_backend.model.*;
import dz.energy.energy_backend.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final UserService service;

    public AdminController(UserService service) {
        this.service = service;
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
            dto.setFirstName(user.getFirstName());
            dto.setLastName(user.getLastName());
            dto.setEmail(user.getEmail());
            dto.setPassword(user.getPassword());
            dto.setPhoneNumber(user.getPhoneNumber());
            dto.setBirthDate(user.getBirthDate());
            dto.setRole(user.getRole());
            dto.setId(user.getId()); // Ajouté pour l’édition

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
                // Si l'id n'existe pas, on redirige ou crée un nouvel utilisateur
                newUser = new Admin(); // ou throw exception
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
        newUser.setPassword(userDTO.getPassword());
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
        newUser.setPassword(userDTO.getPassword());
        newUser.setPhoneNumber(userDTO.getPhoneNumber());
        newUser.setBirthDate(userDTO.getBirthDate());
        newUser.setRole(userDTO.getRole());

        User savedUser = service.save(newUser);
        return ResponseEntity.ok(savedUser);
    }
}
