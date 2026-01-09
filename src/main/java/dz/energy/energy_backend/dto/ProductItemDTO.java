package dz.energy.energy_backend.dto;

public class ProductItemDTO {

    private String name;
    private String description;
    private String serialNumber;
    private String imageUrl;   // 🖼️ NOUVEAU
    private double price;

    public ProductItemDTO() {}

    // getters / setters
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getSerialNumber() { return serialNumber; }
    public void setSerialNumber(String serialNumber) { this.serialNumber = serialNumber; }

    public String getImageUrl() { return imageUrl; }
    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }

    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }
}
