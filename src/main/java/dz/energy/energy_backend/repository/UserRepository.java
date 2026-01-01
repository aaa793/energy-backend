package dz.energy.energy_backend.repository;

import dz.energy.energy_backend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {
}
