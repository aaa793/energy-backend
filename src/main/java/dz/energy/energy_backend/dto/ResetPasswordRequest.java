package dz.energy.energy_backend.dto;

public class ResetPasswordRequest {
    private String email;
    private String newPassword;

    public String getEmail() { return email; }
    public String getNewPassword() { return newPassword; }

    public void setEmail(String email) { this.email = email; }
    public void setNewPassword(String newPassword) { this.newPassword = newPassword; }
}