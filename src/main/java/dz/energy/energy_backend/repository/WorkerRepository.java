package dz.energy.energy_backend.repository;

import dz.energy.energy_backend.model.Worker;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface WorkerRepository extends JpaRepository<Worker, Integer> {
    Optional<Worker> findByEmail(String email);
}