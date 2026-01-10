package dz.energy.energy_backend.service;

import dz.energy.energy_backend.model.Product;
import dz.energy.energy_backend.model.ProductItem;
import dz.energy.energy_backend.repository.ProductItemRepository;
import dz.energy.energy_backend.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductItemService {

    private final ProductItemRepository itemRepo;
    private final ProductRepository productRepo;

    public ProductItemService(ProductItemRepository itemRepo,
                              ProductRepository productRepo) {
        this.itemRepo = itemRepo;
        this.productRepo = productRepo;
    }

    // ================= FIND ALL =================
    public List<ProductItem> findAll() {
        return itemRepo.findAll();
    }

    // ================= FIND BY SELLER =================
    public List<ProductItem> findBySeller(Integer sellerId) {
        return itemRepo.findBySellerId(sellerId);
    }

    // ================= FIND BY ID =================
    public ProductItem findById(Integer itemId) {
        return itemRepo.findById(itemId).orElse(null);
    }

    // ================= SAVE =================
    public ProductItem save(ProductItem item) {
        return itemRepo.save(item);
    }

    // ================= DELETE =================
    public void delete(Integer itemId) {

        ProductItem item = itemRepo.findById(itemId).orElse(null);
        if (item == null) return;

        Product product = item.getProduct();

        // 1️⃣ supprimer le lien seller ↔ product
        itemRepo.delete(item);

        // 2️⃣ supprimer le produit UNIQUEMENT s’il n’est plus utilisé
        if (product != null) {
            boolean stillUsed = itemRepo.existsByProduct(product);
            if (!stillUsed) {
                productRepo.delete(product);
            }
        }
    }
}
