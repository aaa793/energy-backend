package dz.energy.energy_backend.repository;

import dz.energy.energy_backend.model.Product;
import dz.energy.energy_backend.model.ProductItem;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ProductItemRepository
        extends JpaRepository<ProductItem, Integer> {

    List<ProductItem> findBySellerId(Integer sellerId);


        boolean existsByProduct(Product product);


}