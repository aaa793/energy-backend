package dz.energy.energy_backend.repository;

import dz.energy.energy_backend.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository
        extends JpaRepository<Product, Integer> {
}
