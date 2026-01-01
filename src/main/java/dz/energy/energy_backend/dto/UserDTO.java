package dz.energy.energy_backend.dto;

import dz.energy.energy_backend.model.Role;
import java.time.LocalDate;

public class UserDTO {
    private Integer id; // ajouté pour l’édition
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private Integer phoneNumber;
    private LocalDate birthDate;
    private Role role;

    // ===== GETTERS / SETTERS =====
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }

    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public Integer getPhoneNumber() { return phoneNumber; }
    public void setPhoneNumber(Integer phoneNumber) { this.phoneNumber = phoneNumber; }

    public LocalDate getBirthDate() { return birthDate; }
    public void setBirthDate(LocalDate birthDate) { this.birthDate = birthDate; }

    public Role getRole() { return role; }
    public void setRole(Role role) { this.role = role; }
}
