package dz.energy.energy_backend.controller;

import dz.energy.energy_backend.model.*;
import dz.energy.energy_backend.repository.*;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/products")
@CrossOrigin("*")
public class ReviewController {

    private final ProductRepository productRepo;
    private final ReviewRepository reviewRepo;

    public ReviewController(ProductRepository productRepo, ReviewRepository reviewRepo) {
        this.productRepo = productRepo;
        this.reviewRepo = reviewRepo;
    }

    // ➕ Ajouter un review à un produit
    @PostMapping("/{productId}/reviews")
    public Review addReview(
            @PathVariable Integer productId,
            @RequestBody Review review) {

        Product product = productRepo.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        review.setProduct(product);
        return reviewRepo.save(review);
    }

    // 📥 Récupérer les reviews d’un produit
    @GetMapping("/{productId}/reviews")
    public List<Review> getReviews(@PathVariable Integer productId) {
        return reviewRepo.findByProductId(productId);
    }
}
