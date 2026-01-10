package dz.energy.energy_backend.controller;

import dz.energy.energy_backend.model.Product;
import dz.energy.energy_backend.model.Review;
import dz.energy.energy_backend.repository.ProductRepository;
import dz.energy.energy_backend.repository.ReviewRepository;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/products")
@CrossOrigin("*")
public class ReviewController {

    private final ProductRepository productRepo;
    private final ReviewRepository reviewRepo;
    private static final String uploadDir = "uploads"; // dossier uploads à la racine

    public ReviewController(ProductRepository productRepo, ReviewRepository reviewRepo) {
        this.productRepo = productRepo;
        this.reviewRepo = reviewRepo;

        // créer le dossier uploads si inexistant
        new File(uploadDir).mkdirs();
    }

    // ➕ Ajouter un review
    @PostMapping(
            value = "/{productId}/reviews",
            consumes = "multipart/form-data"
    )
    public Review addReview(
            @PathVariable Integer productId,
            @RequestParam("comment") String comment,
            @RequestParam("image") MultipartFile image
    ) throws IOException {

        Product product = productRepo.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        // dossier uploads (local + render)
        File uploadDir = new File("uploads");
        if (!uploadDir.exists()) uploadDir.mkdirs();

        String fileName = System.currentTimeMillis() + "_" + image.getOriginalFilename();
        File dest = new File(uploadDir, fileName);
        image.transferTo(dest);

        Review review = new Review();
        review.setComment(comment);
        review.setImageUrl(
                "https://energy-backend-ba31.onrender.com/uploads/" + fileName
        );
        review.setProduct(product);

        return reviewRepo.save(review);
    }



    // 📥 Récupérer les reviews
    @GetMapping("/{productId}/reviews")
    public List<Review> getReviews(@PathVariable Integer productId) {
        return reviewRepo.findByProductId(productId);
    }
}
