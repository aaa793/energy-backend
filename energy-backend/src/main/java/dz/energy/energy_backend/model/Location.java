//package dz.energy.energy_backend.model;
//
//public class Location {
//
//    private double latitude;
//    private double longitude;
//    private String street;
//    private String city;
//    private String zipCode;
//    private String country;
//
//
//    private Client client;
//
//    public Location() {}
//
//    public Location(double latitude, double longitude, String street,
//                    String city, String zipCode, String country) {
//
//        this.latitude = latitude;
//        this.longitude = longitude;
//        this.street = street;
//        this.city = city;
//        this.zipCode = zipCode;
//        this.country = country;
//    }
//
//
//    public void addClient(Client c){
//        if(c == null) return;
//
//
//        if(c.getLocation() != null)
//            c.removeLocation();
//
//
//        if(getClient() != null)
//            removeClient();
//
//        // Création du lien
//        setClient(c);
//        c.setLocation(this);
//    }
//
//    public void removeClient(){
//        if(getClient() != null){
//            getClient().setLocation(null);
//            setClient(null);
//        }
//    }
//
//    public Client getClient(){ return client; }
//    public void setClient(Client c){ this.client = c; }
//
//
//
//    public double getLatitude(){ return latitude; }
//    public void setLatitude(double v){ this.latitude = v; }
//
//    public double getLongitude(){ return longitude; }
//    public void setLongitude(double v){ this.longitude = v; }
//
//    public String getStreet(){ return street; }
//    public void setStreet(String v){ this.street = v; }
//
//    public String getCity(){ return city; }
//    public void setCity(String v){ this.city = v; }
//
//    public String getZipCode(){ return zipCode; }
//    public void setZipCode(String v){ this.zipCode = v; }
//
//    public String getCountry(){ return country; }
//    public void setCountry(String v){ this.country = v; }
//
//    public String getWilayaCode(){
//        return zipCode != null && zipCode.length() >= 2
//                ? zipCode.substring(0,2)
//                : "";
//    }
//}

package dz.energy.energy_backend.model;

import jakarta.persistence.*;

@Entity
public class Location {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private double latitude;
    private double longitude;
    private String street;
    private String city;
    private String zipCode;
    private String country;

    public Location() {}

    // Getters et setters

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public double getLatitude() { return latitude; }
    public void setLatitude(double latitude) { this.latitude = latitude; }

    public double getLongitude() { return longitude; }
    public void setLongitude(double longitude) { this.longitude = longitude; }

    public String getStreet() { return street; }
    public void setStreet(String street) { this.street = street; }

    public String getCity() { return city; }
    public void setCity(String city) { this.city = city; }

    public String getZipCode() { return zipCode; }
    public void setZipCode(String zipCode) { this.zipCode = zipCode; }

    public String getCountry() { return country; }
    public void setCountry(String country) { this.country = country; }
}
