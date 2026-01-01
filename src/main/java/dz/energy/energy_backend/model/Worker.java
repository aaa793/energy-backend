//package dz.energy.energy_backend.model;
//
//import java.util.Date;
//import java.util.HashSet;
//import java.util.Set;
//
//public class Worker extends User {
//
//    private Set<Task> tasks;
//
//    public Worker() {}
//
//    public Worker(String f, String l, String e, Date b, String p, Integer ph){
//        super(f, l, e, b, p, ph);
//        tasks = new HashSet<>();
//    }
//
//
//    public void addTask(Task t){
//        if(!getTasks().contains(t)){
//            if(t.getWorker() != null) t.removeWorker();
//            t.setWorker(this);
//            getTasks().add(t);
//        }
//    }
//
//    public void removeTask(Task t){
//        if(getTasks().contains(t)){
//            getTasks().remove(t);
//            t.setWorker(null);
//        }
//    }
//
//    public void reportTaskProgress(){
//        System.out.println("Reporting task progress...");
//    }
//
//    public Set<Task> getTasks(){ return tasks; }
//    public void setTasks(Set<Task> t){ this.tasks = t; }
//}
package dz.energy.energy_backend.model;

import jakarta.persistence.*;
import java.util.*;

@Entity
public class Worker extends User {

    @OneToMany(mappedBy = "worker")
    private Set<Task> tasks = new HashSet<>();

    public Worker() {}

    public Worker(String f, String l, String e, Date b, String p, Integer ph){
        super();
    }

    public void addTask(Task t){
        if(!tasks.contains(t)){
            t.setWorker(this);
            tasks.add(t);
        }
    }

    public void removeTask(Task t){
        tasks.remove(t);
        t.setWorker(null);
    }

    public Set<Task> getTasks(){ return tasks; }
    public void setTasks(Set<Task> t){ this.tasks = t; }
}
