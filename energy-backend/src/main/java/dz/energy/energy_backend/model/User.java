//package dz.energy.energy_backend.model;
//
//import java.util.Date;
//
//public abstract class User {
//
//    protected String firstName;
//    protected String lastName;
//    protected String email;
//    protected Date birthDate;
//    protected String password;
//    protected Integer phoneNumber;
//    protected Integer id;
//
//    public User() {}
//
//    public User(String f, String l, String e, Date b, String p, Integer ph){
//        this.firstName = f;
//        this.lastName = l;
//        this.email = e;
//        this.birthDate = b;
//        this.password = p;
//        this.phoneNumber = ph;
//    }
//
//    public void updatePassword(String oldPass, String newPass){
//        if(this.password.equals(oldPass)) this.password = newPass;
//    }
//
//
//    public String getFirstName() { return firstName; }
//    public String getLastName() { return lastName; }
//    public String getEmail() { return email; }
//    public Date getBirthDate() { return birthDate; }
//    public String getPassword() { return password; }
//    public Integer getPhoneNumber() { return phoneNumber; }
//
//    public void setFirstName(String v){ this.firstName = v; }
//    public void setLastName(String v){ this.lastName = v; }
//    public void setEmail(String v){ this.email = v; }
//    public void setBirthDate(Date v){ this.birthDate = v; }
//    public void setPassword(String v){ this.password = v; }
//    public void setPhoneNumber(Integer v){ this.phoneNumber = v; }
//    public Integer getId(){ return id; }
//    public void setId(Integer id){ this.id = id; }
//
//}


//
//package dz.energy.energy_backend.model;
//
//import jakarta.persistence.*;
//import java.util.Date;
//
//@Entity
//@Inheritance(strategy = InheritanceType.JOINED)
//public abstract class User {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    protected Integer id;
//
//    protected String firstName;
//    protected String lastName;
//    protected String email;
//
//    @Temporal(TemporalType.DATE)
//    protected Date birthDate;
//
//    protected String password;
//    protected Integer phoneNumber;
//
//    public User() {}
//
//    public User(String f, String l, String e, Date b, String p, Integer ph){
//        this.firstName = f;
//        this.lastName = l;
//        this.email = e;
//        this.birthDate = b;
//        this.password = p;
//        this.phoneNumber = ph;
//    }
//
//    // getters / setters (inchangés)
//    public String getFirstName() { return firstName; }
//    public String getLastName() { return lastName; }
//    public String getEmail() { return email; }
//    public Date getBirthDate() { return birthDate; }
//    public String getPassword() { return password; }
//    public Integer getPhoneNumber() { return phoneNumber; }
//
//    public void setFirstName(String v){ this.firstName = v; }
//    public void setLastName(String v){ this.lastName = v; }
//    public void setEmail(String v){ this.email = v; }
//    public void setBirthDate(Date v){ this.birthDate = v; }
//    public void setPassword(String v){ this.password = v; }
//    public void setPhoneNumber(Integer v){ this.phoneNumber = v; }
//
//    public Integer getId(){ return id; }
//    public void setId(Integer id){ this.id = id; }
//
//    public void setRole(Role role) {
//    }
//}


package dz.energy.energy_backend.model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO) // ✅ TRÈS IMPORTANT
    protected Integer id;

    protected String firstName;
    protected String lastName;

    @Column(unique = true, nullable = false)
    protected String email;

    protected LocalDate birthDate;

    protected String password;
    protected Integer phoneNumber;

    @Enumerated(EnumType.STRING)
    protected Role role;

    public User() {}

    // ===== GETTERS / SETTERS =====

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }

    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public LocalDate getBirthDate() { return birthDate; }
    public void setBirthDate(LocalDate birthDate) { this.birthDate = birthDate; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public Integer getPhoneNumber() { return phoneNumber; }
    public void setPhoneNumber(Integer phoneNumber) { this.phoneNumber = phoneNumber; }

    public Role getRole() { return role; }
    public void setRole(Role role) { this.role = role; }
}
