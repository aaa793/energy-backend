package dz.energy.energy_backend.dto;

public class ProductItemDTO {

    private String name;
    private String description;
    private String serialNumber;
    private double price;

    public ProductItemDTO() {}

    public ProductItemDTO(String name, String description,
                          String serialNumber, double price) {
        this.name = name;
        this.description = description;
        this.serialNumber = serialNumber;
        this.price = price;
    }

    // Getters / Setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
