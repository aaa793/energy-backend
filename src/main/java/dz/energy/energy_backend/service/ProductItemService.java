package dz.energy.energy_backend.service;

import dz.energy.energy_backend.model.ProductItem;
import dz.energy.energy_backend.repository.ProductItemRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductItemService {

    private final ProductItemRepository repo;

    public ProductItemService(ProductItemRepository repo) {
        this.repo = repo;
    }

    public List<ProductItem> findBySeller(Integer sellerId) {
        return repo.findBySellerId(sellerId);
    }

    public ProductItem findById(Integer id) {
        return repo.findById(id).orElse(null);
    }

    public ProductItem save(ProductItem item) {
        return repo.save(item);
    }

    public void delete(Integer id) {
        repo.deleteById(id);
    }
    public List<ProductItem> findAll() {
        return repo.findAll();
    }

}
