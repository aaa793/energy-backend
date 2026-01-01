//package dz.energy.energy_backend.model;
//
//import java.util.Date;
//import java.util.HashSet;
//import java.util.Set;
//
//public class Seller extends User {
//
//    private Set<ProductItem> items;
//
//    public Seller() {}
//
//    public Seller(String f, String l, String e, Date b, String p, Integer ph){
//        super(f, l, e, b, p, ph);
//        items = new HashSet<>();
//    }
//
//    public void addProductItem(ProductItem pi){
//        if(!items.contains(pi)){
//            if(pi.getSeller() != null) pi.removeSeller();
//            pi.setSeller(this);
//            items.add(pi);
//        }
//    }
//
//    public void removeProductItem(ProductItem pi){
//        if(items.contains(pi)){
//            items.remove(pi);
//            pi.setSeller(null);
//        }
//    }
//
//    /* ===== getters / setters LOGIQUE MÉTIER ===== */
//    public Set<ProductItem> getItems(){
//        return items;
//    }
//
//    public void setItems(Set<ProductItem> items){
//        this.items = items;
//    }
//
//    public Set<ProductItem> getMyProducts(){
//        return items;
//    }
//
//    /* ===== getters / setters POUR HIBERNATE (HBM) ===== */
//    public Set<ProductItem> getProductItems() {
//        return items;          // ✅ CORRECT
//    }
//
//    public void setProductItems(Set<ProductItem> items) {
//        this.items = items;    // ✅ CORRECT
//    }
//}

package dz.energy.energy_backend.model;

import jakarta.persistence.*;
import java.util.*;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Seller extends User {

    @OneToMany(mappedBy = "seller", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore   // 🔥 COUPE LA BOUCLE JSON
    private Set<ProductItem> items = new HashSet<>();

    public Seller() {}

    public Seller(String f, String l, String e, Date b, String p, Integer ph){
        super();
    }

    // helpers
    public void addProductItem(ProductItem pi){
        items.add(pi);
        pi.setSeller(this);
    }

    public void removeProductItem(ProductItem pi){
        items.remove(pi);
        pi.setSeller(null);
    }

    // ✅ UN SEUL GETTER
    public Set<ProductItem> getItems(){
        return items;
    }

    public void setItems(Set<ProductItem> items){
        this.items = items;
    }

    @Override
    public boolean equals(Object o){
        if(this == o) return true;
        if(!(o instanceof Seller)) return false;
        Seller s = (Seller) o;
        return id != null && id.equals(s.id);
    }

    @Override
    public int hashCode(){
        return id != null ? id.hashCode() : 0;
    }
}
