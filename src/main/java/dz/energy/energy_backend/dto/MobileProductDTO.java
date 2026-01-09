package dz.energy.energy_backend.dto;

public class MobileProductDTO {

    private String imageResId;  // <-- changer int en String
    private String name;
    private String description;
    private double price;
    private String category;

    public String getImageResId() { return imageResId; }
    public void setImageResId(String imageResId) { this.imageResId = imageResId; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }

    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }
}
