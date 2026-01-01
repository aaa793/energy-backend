//package dz.energy.energy_backend.model;
//
//import java.util.Date;
//
//public class Client extends User {
//
//    private Location location;    // nouvelle relation 1-to-1
//    private Task task;            // reste comme avant
//    public Client() {}
//
//    public Client(String f, String l, String e, Date b, String p, Integer ph) {
//        super(f, l, e, b, p, ph);
//    }
//
//    public void addLocation(Location loc){
//        if(loc == null) return;
//
//        // Si la Location appartient déjà à un autre client → on supprime
//        if(loc.getClient() != null)
//            loc.removeClient();
//
//        // Si ce client possède déjà une Location → on enlève l'ancienne
//        if(getLocation() != null)
//            removeLocation();
//
//        setLocation(loc);
//        loc.setClient(this);
//    }
//
//    public void removeLocation(){
//        if(getLocation() != null){
//            getLocation().setClient(null);
//            setLocation(null);
//        }
//    }
//
//    public Location getLocation(){ return location; }
//    public void setLocation(Location l){ this.location = l; }
//
//    // === RELATION ONE-TO-ONE AVEC TASK ===
//    public void addTask(Task t){
//        if(t == null) return;
//
//        if(t.getClient() != null)
//            t.removeClient();
//
//        if(getTask() != null)
//            removeTask();
//
//        setTask(t);
//        t.setClient(this);
//    }
//
//    public void removeTask(){
//        if(getTask() != null){
//            getTask().setClient(null);
//            setTask(null);
//        }
//    }
//
//    public Task getTask(){ return task; }
//    public void setTask(Task t){ this.task = t; }
//}
package dz.energy.energy_backend.model;

import jakarta.persistence.*;

@Entity
public class Client extends User {

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "location_id")
    private Location location;

    @OneToOne(mappedBy = "client")
    private Task task;

    public Client() {}

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public Task getTask() {
        return task;
    }

    public void setTask(Task task) {
        this.task = task;
    }
}
