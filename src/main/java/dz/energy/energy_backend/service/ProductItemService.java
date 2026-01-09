package dz.energy.energy_backend.service;

import dz.energy.energy_backend.model.Product;
import dz.energy.energy_backend.model.ProductItem;
import dz.energy.energy_backend.repository.ProductItemRepository;
import dz.energy.energy_backend.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;

//@Service
//public class ProductItemService {
//
//    private final ProductItemRepository repo;
//
//    public ProductItemService(ProductItemRepository repo) {
//        this.repo = repo;
//    }
//
//    public List<ProductItem> findBySeller(Integer sellerId) {
//        return repo.findBySellerId(sellerId);
//    }
//
//    public ProductItem findById(Integer id) {
//        return repo.findById(id).orElse(null);
//    }
//
//    public ProductItem save(ProductItem item) {
//        return repo.save(item);
//    }
//
//    public void delete(Integer id) {
//        repo.deleteById(id);
//    }
//    public List<ProductItem> findAll() {
//        return repo.findAll();
//    }
//
//}




@Service
public class ProductItemService {

    private final ProductItemRepository itemRepo;
    private final ProductRepository productRepo;

    public ProductItemService(ProductItemRepository itemRepo,
                              ProductRepository productRepo) {
        this.itemRepo = itemRepo;
        this.productRepo = productRepo;
    }


    public List<ProductItem> findAll() {
        return itemRepo.findAll();
    }


    public List<ProductItem> findBySeller(Integer sellerId) {
        return itemRepo.findBySellerId(sellerId);
    }

    public ProductItem save(ProductItem item) {
        return itemRepo.save(item);
    }

    public void delete(Integer itemId) {

        ProductItem item = itemRepo.findById(itemId).orElse(null);
        if (item == null) return;

        Product product = item.getProduct();

        // 1️⃣ Supprimer ProductItem
        itemRepo.delete(item);

        // 2️⃣ Supprimer Product associé
        if (product != null) {
            productRepo.delete(product);
        }
    }
}



