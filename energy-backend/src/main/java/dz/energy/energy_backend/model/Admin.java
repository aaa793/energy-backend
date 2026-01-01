//package dz.energy.energy_backend.model;
//
//
//import java.util.Date;
//import java.util.List;
//
//public class Admin extends User {
//
//    private List<String> permissions;
//
//    public Admin() {} // ✅
//
//    public Admin(String f, String l, String e, Date b, String p, Integer ph, List<String> perms){
//        super(f, l, e, b, p, ph);
//        this.permissions = perms;
//    }
//    public void deleteProduct(Product p){
//        System.out.println("Admin deleted product: " + p.getName());
//    }
//
//    public List<String> getPermissions(){ return permissions; }
//    public void setPermissions(List<String> p){ this.permissions = p; }
//}


package dz.energy.energy_backend.model;

import jakarta.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "admin")
public class Admin extends User {

    @ElementCollection
    @CollectionTable(name = "admin_permissions", joinColumns = @JoinColumn(name = "admin_id"))
    @Column(name = "permission")
    private List<String> permissions;

    public Admin() {}

    public Admin(String f, String l, String e, Date b, String p, Integer ph, List<String> perms){
    super();
        this.permissions = perms;
    }

    public void deleteProduct(Product p){
        System.out.println("Admin deleted product: " + p.getName());
    }

    public List<String> getPermissions(){ return permissions; }
    public void setPermissions(List<String> p){ this.permissions = p; }
}
