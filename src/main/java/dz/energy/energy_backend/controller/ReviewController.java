package dz.energy.energy_backend.controller;

import dz.energy.energy_backend.model.Product;
import dz.energy.energy_backend.model.Review;
import dz.energy.energy_backend.repository.ProductRepository;
import dz.energy.energy_backend.repository.ReviewRepository;
import dz.energy.energy_backend.service.ImageUploadService;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/products")
@CrossOrigin(origins = "*")
public class ReviewController {

    private final ProductRepository productRepository;
    private final ReviewRepository reviewRepository;
    private final ImageUploadService imageUploadService;

    public ReviewController(
            ProductRepository productRepository,
            ReviewRepository reviewRepository,
            ImageUploadService imageUploadService
    ) {
        this.productRepository = productRepository;
        this.reviewRepository = reviewRepository;
        this.imageUploadService = imageUploadService;
    }

    @PostMapping(
            value = "/{id}/reviews",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE
    )
    public ResponseEntity<?> addReview(
            @PathVariable Integer id,
            @RequestPart("comment") String comment,
            @RequestPart(value = "image", required = false) MultipartFile image
    ) {
        try {
            // 1️⃣ Vérifier produit
            Product product = productRepository.findById(id).orElse(null);
            if (product == null) {
                return ResponseEntity
                        .status(HttpStatus.NOT_FOUND)
                        .body("Produit introuvable");
            }

            // 2️⃣ Upload image
            String imageUrl = null;
            if (image != null && !image.isEmpty()) {
                imageUrl = imageUploadService.uploadImage(image);
            }

            // 3️⃣ Créer review
            Review review = new Review();
            review.setComment(comment);
            review.setImageUrl(imageUrl);
            review.setProduct(product);

            Review savedReview = reviewRepository.save(review);

            return ResponseEntity.ok(savedReview);

        } catch (Exception e) {
            e.printStackTrace(); // visible dans Render Logs
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erreur lors de l'ajout de la review");
        }
    }
}
