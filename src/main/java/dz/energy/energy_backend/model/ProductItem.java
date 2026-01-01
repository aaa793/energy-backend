//package dz.energy.energy_backend.model;
//
//public class ProductItem {
//
//    private double price;
//
//    private Seller seller;
//    private Product product;
//    private Task task;
//    private Integer id;
//
//
//    public ProductItem() {}
//
//    public ProductItem(double p){ this.price = p; }
//
//
//    public void addSeller(Seller s){
//        if(!s.getItems().contains(this)){
//            if(getSeller() != null) removeSeller();
//            setSeller(s);
//            s.addProductItem(this);
//        }
//    }
//
//    public void removeSeller(){
//        seller.removeProductItem(this);
//        setSeller(null);
//    }
//
//    /* ===== Product (many→one) ===== */
//    public void addProduct(Product p){
//        if(!p.getItems().contains(this)){
//            if(getProduct() != null) removeProduct();
//            setProduct(p);
//            p.addProductItem(this);
//        }
//    }
//
//    public void removeProduct(){
//        product.removeProductItem(this);
//        setProduct(null);
//    }
//
//
//    public void addTask(Task t){
//        if(!t.getProductItems().contains(this)){
//            if(getTask() != null) removeTask();
//            setTask(t);
//            t.addProductItem(this);
//        }
//    }
//
//    public void removeTask(){
//        task.removeProductItem(this);
//        setTask(null);
//    }
//
//
//    public double getPrice(){ return price; }
//    public void setPrice(double p){ this.price = p; }
//
//    public Seller getSeller(){ return seller; }
//    public void setSeller(Seller s){ this.seller = s; }
//
//    public Product getProduct(){ return product; }
//    public void setProduct(Product p){ this.product = p; }
//
//    public Task getTask(){ return task; }
//    public void setTask(Task t){ this.task = t; }
//    public Integer getId(){ return id; }
//    public void setId(Integer id){ this.id = id; }
//
//}
package dz.energy.energy_backend.model;

import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class ProductItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private double price;

    @ManyToOne
    @JsonIgnore
    private Seller seller;

    @ManyToOne
    @JsonIgnore   // 🔥 COUPE LA BOUCLE ICI
    private Product product;

    @ManyToOne
    @JsonIgnore
    private Task task;

    public ProductItem() {}
    public ProductItem(double price) {
        this.price = price;
    }

    // getters / setters
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }

    public Seller getSeller() { return seller; }
    public void setSeller(Seller seller) { this.seller = seller; }

    public Product getProduct() { return product; }
    public void setProduct(Product product) { this.product = product; }

    public Task getTask() { return task; }
    public void setTask(Task task) { this.task = task; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ProductItem)) return false;
        ProductItem pi = (ProductItem) o;
        return id != null && id.equals(pi.id);
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}
