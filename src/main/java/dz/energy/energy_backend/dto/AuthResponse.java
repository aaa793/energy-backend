package dz.energy.energy_backend.dto;

import dz.energy.energy_backend.model.Role;

public class AuthResponse {

    private Integer id;
    private String email;
    private Role role;

    public AuthResponse(Integer id, String email, Role role) {
        this.id = id;
        this.email = email;
        this.role = role;
    }

    public Integer getId() { return id; }
    public String getEmail() { return email; }
    public Role getRole() { return role; }
}