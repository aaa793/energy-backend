package dz.energy.energy_backend.service;

import dz.energy.energy_backend.model.Product;
import dz.energy.energy_backend.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    private final ProductRepository repo;

    public ProductService(ProductRepository repo) {
        this.repo = repo;
    }

    // Récupérer tous les produits
    public List<Product> findAll() {
        return repo.findAll();
    }

    // Récupérer un produit par son ID
    public Product findById(Integer productId) {
        Optional<Product> optional = repo.findById(productId);
        return optional.orElse(null); // retourne null si pas trouvé
    }

    // Sauvegarder un produit (insert ou update)
    public Product save(Product product) {
        return repo.save(product);
    }

    // Supprimer un produit
    public void delete(Integer id) {
        repo.deleteById(id);
    }


}