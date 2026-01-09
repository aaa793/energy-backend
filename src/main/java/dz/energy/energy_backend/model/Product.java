//package dz.energy.energy_backend.model;
//
//import java.util.HashSet;
//import java.util.Set;
//
//public class Product {
//
//    private int idProduct;
//    private String serialNumber;
//    private String name;
//    private String description;
//    private Integer id;
//
//
//    private Set<ProductItem> items;
//    private Set<Task> tasks;
//
//    public Product() {}
//
//    public Product(int id, String sn, String n, String d){
//        this.idProduct = id;
//        this.serialNumber = sn;
//        this.name = n;
//        this.description = d;
//        this.items = new HashSet<>();
//        this.tasks = new HashSet<>();
//    }
//
//
//    public void addProductItem(ProductItem pi){
//        if(!items.contains(pi)){
//            if(pi.getProduct() != null) pi.removeProduct();
//            pi.setProduct(this);
//            items.add(pi);
//        }
//    }
//
//    public void removeProductItem(ProductItem pi){
//        if(items.contains(pi)){
//            items.remove(pi);
//            pi.setProduct(null);
//        }
//    }
//
//    public Set<ProductItem> getItems(){ return items; }
//    public void setItems(Set<ProductItem> items){ this.items = items; }
//
//
//    public void addTask(Task t){
//        if(!tasks.contains(t)){
//            if(t.getProduct() != null) t.removeProduct();
//            t.setProduct(this);
//            tasks.add(t);
//        }
//    }
//
//    public void removeTask(Task t){
//        if(tasks.contains(t)){
//            tasks.remove(t);
//            t.setProduct(null);
//        }
//    }
//
//    public Integer getId(){ return id; }
//    public void setId(Integer id){ this.id = id; }
//
//
//    public Set<Task> getTasks(){ return tasks; }
//    public void setTasks(Set<Task> t){ this.tasks = t; }
//
//    public int getIdProduct(){ return idProduct; }
//    public String getSerialNumber(){ return serialNumber; }
//    public String getName(){ return name; }
//    public String getDescription(){ return description; }
//    public void setDescription(String description) {
//        this.description = description;
//    }
//
//    public void setName(String name) {
//        this.name = name;
//    }
//
//    public void setSerialNumber(String serialNumber) {
//        this.serialNumber = serialNumber;
//    }
//
//    public void setIdProduct(int idProduct) {
//        this.idProduct = idProduct;
//    }
//
//    // Getter pour Hibernate
//    public Set<ProductItem> getProductItems() {
//        return items;
//    }
//
//    // Setter pour Hibernate
//    public void setProductItems(Set<ProductItem> productItems) {
//        this.items = productItems;
//    }
//
//
//}
//package dz.energy.energy_backend.model;
//
//import jakarta.persistence.*;
//import java.util.*;
//import com.fasterxml.jackson.annotation.JsonIgnore;
//
//@Entity
//public class Product {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Integer id;
//
//    private int idProduct;
//    private String serialNumber;
//    private String name;
//    private String description;
//
//    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
//    @JsonIgnore   // 🔥 COUPE LA BOUCLE
//    private Set<ProductItem> items = new HashSet<>();
//
//    @OneToMany(mappedBy = "product")
//    @JsonIgnore
//    private Set<Task> tasks = new HashSet<>();
//
//
//    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
//    @JsonIgnore
//    private Set<Review> reviews = new HashSet<>();
//
//
//
//
//
//    public Product() {}
//
//    // getters / setters
//
//    public Set<Review> getReviews() {
//        return reviews;
//    }
//
//    public void setReviews(Set<Review> reviews) {
//        this.reviews = reviews;
//    }
//
//    public Integer getId() { return id; }
//    public void setId(Integer id) { this.id = id; }
//
//    public int getIdProduct() { return idProduct; }
//    public void setIdProduct(int idProduct) { this.idProduct = idProduct; }
//
//    public String getSerialNumber() { return serialNumber; }
//    public void setSerialNumber(String serialNumber) { this.serialNumber = serialNumber; }
//
//    public String getName() { return name; }
//    public void setName(String name) { this.name = name; }
//
//    public String getDescription() { return description; }
//    public void setDescription(String description) { this.description = description; }
//
//    // ✅ UN SEUL GETTER
//    public Set<ProductItem> getItems() { return items; }
//    public void setItems(Set<ProductItem> items) { this.items = items; }
//
//    public Set<Task> getTasks() { return tasks; }
//    public void setTasks(Set<Task> tasks) { this.tasks = tasks; }
//
//    // helpers
//    public void addProductItem(ProductItem pi) {
//        items.add(pi);
//        pi.setProduct(this);
//    }
//
//    public void removeProductItem(ProductItem pi) {
//        items.remove(pi);
//        pi.setProduct(null);
//    }
//
//    @Override
//    public boolean equals(Object o) {
//        if (this == o) return true;
//        if (!(o instanceof Product)) return false;
//        Product p = (Product) o;
//        return id != null && id.equals(p.id);
//    }
//
//    @Override
//    public int hashCode() {
//        return id != null ? id.hashCode() : 0;
//    }
//}




package dz.energy.energy_backend.model;

import jakarta.persistence.*;
import java.util.*;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id; // ✅ SEUL ID

    private String name;
    private String description;
    private String serialNumber;

    // 🖼️ NOUVEAU : image du produit
    private String imageUrl;

    // ===== Relations =====

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private Set<ProductItem> items = new HashSet<>();

    @OneToMany(mappedBy = "product")
    @JsonIgnore
    private Set<Task> tasks = new HashSet<>();

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private Set<Review> reviews = new HashSet<>();

    public Product() {}

    // ===== getters / setters =====
    public Integer getId() { return id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getSerialNumber() { return serialNumber; }
    public void setSerialNumber(String serialNumber) { this.serialNumber = serialNumber; }

    public String getImageUrl() { return imageUrl; }
    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }

    public Set<ProductItem> getItems() { return items; }
    public Set<Review> getReviews() { return reviews; }

    // equals / hashCode uniquement sur id
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Product)) return false;
        Product p = (Product) o;
        return id != null && id.equals(p.id);
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}
