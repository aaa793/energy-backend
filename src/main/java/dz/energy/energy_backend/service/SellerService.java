package dz.energy.energy_backend.service;

import dz.energy.energy_backend.model.Seller;
import dz.energy.energy_backend.repository.SellerRepository;
import org.springframework.stereotype.Service;

@Service
public class SellerService {

    private final SellerRepository repo;

    public SellerService(SellerRepository repo) {
        this.repo = repo;
    }

    public Seller findById(Integer sellerId) {
        return repo.findById(sellerId)
                .orElseThrow(() -> new RuntimeException("Seller non trouvé !"));
    }
}