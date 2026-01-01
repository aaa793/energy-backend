//package dz.energy.energy_backend.model;
//
//import java.util.Date;
//import java.util.HashSet;
//import java.util.Set;
//
//public class Task {
//
//    private String description;
//    private String status;
//    private Date createdAt;
//    private Date confirmedAt;
//    private Date achievedAt;
//
//    private Client client;
//    private Worker worker;
//    private Product product;
//    private Set<ProductItem> items;
//    private Integer id;
//
//
//    public Task() {}
//
//    public Task(String d){
//        this.description = d;
//        this.status = "Pending";
//        this.createdAt = new Date();
//        items = new HashSet<>();
//    }
//
//
//    public void addClient(Client c){
//        if(c.getTask() != this){
//            if(getClient() != null) removeClient();
//            setClient(c);
//            c.addTask(this);
//        }
//    }
//
//    public void removeClient() {
//        if (getClient() != null) {
//            getClient().setTask(null);
//        }
//        setClient(null);
//    }
//
//
//    public void addWorker(Worker w){
//        if(!w.getTasks().contains(this)){
//            if(getWorker() != null) removeWorker();
//            setWorker(w);
//            w.addTask(this);
//        }
//    }
//
//    public void removeWorker(){
//        worker.removeTask(this);
//        setWorker(null);
//    }
//
//
//    public void addProduct(Product p){
//        if(!p.getTasks().contains(this)){
//            if(getProduct() != null) removeProduct();
//            setProduct(p);
//            p.addTask(this);
//        }
//    }
//
//    public void removeProduct(){
//        product.removeTask(this);
//        setProduct(null);
//    }
//
//
//    public void addProductItem(ProductItem pi){
//        if(!items.contains(pi)){
//            if(pi.getTask() != null) pi.removeTask();
//            pi.setTask(this);
//            items.add(pi);
//        }
//    }
//
//    public void removeProductItem(ProductItem pi){
//        if(items.contains(pi)){
//            items.remove(pi);
//            pi.setTask(null);
//        }
//    }
//
//
//    public String getDescription(){ return description; }
//    public void setDescription(String d){ this.description = d; }
//
//    public String getStatus(){ return status; }
//    public void setStatus(String s){ this.status = s; }
//
//    public Date getCreatedAt(){ return createdAt; }
//    public Date getConfirmedAt(){ return confirmedAt; }
//    public Date getAchievedAt(){ return achievedAt; }
//
//    public void setConfirmedAt(Date d){ this.confirmedAt = d; }
//    public void setAchievedAt(Date d){ this.achievedAt = d; }
//
//    public Client getClient(){ return client; }
//    public void setClient(Client c){ this.client = c; }
//
//    public Integer getId(){ return id; }
//    public void setId(Integer id){ this.id = id; }
//
//    public Worker getWorker(){ return worker; }
//    public void setWorker(Worker w){ this.worker = w; }
//
//    public Product getProduct(){ return product; }
//    public void setProduct(Product p){ this.product = p; }
//
//    public Set<ProductItem> getProductItems(){ return items; }
//    public void setItems(Set<ProductItem> items){ this.items = items; }
//    public void setCreatedAt(Date createdAt){ this.createdAt = createdAt; }
//
//
//}
package dz.energy.energy_backend.model;

import jakarta.persistence.*;
import java.util.*;

@Entity
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String description;
    private String status;

    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;

    @Temporal(TemporalType.TIMESTAMP)
    private Date confirmedAt;

    @Temporal(TemporalType.TIMESTAMP)
    private Date achievedAt;

    @OneToOne
    private Client client;

    @ManyToOne
    private Worker worker;

    @ManyToOne
    private Product product;

    @OneToMany(mappedBy = "task", cascade = CascadeType.ALL)
    private Set<ProductItem> items = new HashSet<>();

    public Task() {}

    public Task(String d){
        this.description = d;
        this.status = "Pending";
        this.createdAt = new Date();
    }

    /* ================= MÉTIER ================= */

    public void addProductItem(ProductItem pi){
        if(!items.contains(pi)){
            pi.setTask(this);
            items.add(pi);
        }
    }

    public void removeProductItem(ProductItem pi){
        items.remove(pi);
        pi.setTask(null);
    }

    /* ================= GETTERS / SETTERS ================= */

    public Integer getId(){
        return id;
    }

    public void setId(Integer id){
        this.id = id;
    }


    public String getDescription(){
        return description;
    }

    public void setDescription(String description){
        this.description = description;
    }

    public String getStatus(){
        return status;
    }

    public void setStatus(String status){
        this.status = status;
    }

    public Date getCreatedAt(){
        return createdAt;
    }

    public void setCreatedAt(Date createdAt){
        this.createdAt = createdAt;
    }

    public Date getConfirmedAt(){
        return confirmedAt;
    }

    public void setConfirmedAt(Date confirmedAt){
        this.confirmedAt = confirmedAt;
    }

    public Date getAchievedAt(){
        return achievedAt;
    }

    public void setAchievedAt(Date achievedAt){
        this.achievedAt = achievedAt;
    }

    public Client getClient(){
        return client;
    }

    public void setClient(Client client){
        this.client = client;
    }

    public Worker getWorker(){
        return worker;
    }

    public void setWorker(Worker worker){
        this.worker = worker;
    }

    public Product getProduct(){
        return product;
    }

    public void setProduct(Product product){
        this.product = product;
    }

    public Set<ProductItem> getProductItems(){
        return items;
    }

    public void setItems(Set<ProductItem> items){
        this.items = items;
    }

    /* ================= equals / hashCode ================= */

    @Override
    public boolean equals(Object o){
        if(this == o) return true;
        if(!(o instanceof Task)) return false;
        Task t = (Task) o;
        return id != null && id.equals(t.id);
    }

    @Override
    public int hashCode(){
        return id != null ? id.hashCode() : 0;
    }
}
