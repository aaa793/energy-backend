package dz.energy.energy_backend.dto;

public class RegisterRequest {
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String birthDate;
    private Integer phoneNumber;
    private String role;

    // ✅ GETTERS
    public String getFirstName() { return firstName; }
    public String getLastName() { return lastName; }
    public String getEmail() { return email; }
    public String getPassword() { return password; }
    public String getBirthDate() { return birthDate; }
    public Integer getPhoneNumber() { return phoneNumber; }
    public String getRole() { return role; }

    // ✅ SETTERS (TRÈS IMPORTANT)
    public void setFirstName(String firstName) { this.firstName = firstName; }
    public void setLastName(String lastName) { this.lastName = lastName; }
    public void setEmail(String email) { this.email = email; }
    public void setPassword(String password) { this.password = password; }
    public void setBirthDate(String birthDate) { this.birthDate = birthDate; }
    public void setPhoneNumber(Integer phoneNumber) { this.phoneNumber = phoneNumber; }
    public void setRole(String role) { this.role = role; }
}