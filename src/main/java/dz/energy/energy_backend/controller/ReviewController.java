package dz.energy.energy_backend.controller;

import dz.energy.energy_backend.model.Product;
import dz.energy.energy_backend.model.Review;
import dz.energy.energy_backend.repository.ProductRepository;
import dz.energy.energy_backend.repository.ReviewRepository;
import dz.energy.energy_backend.service.ImageUploadService;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/products")
@CrossOrigin(origins = "*")  // ✅ autorise toutes les origines (Android, Web, autres)
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

    /**
     * Ajouter un avis pour un produit avec une image
     * @param id id du produit
     * @param comment texte de l'avis
     * @param image fichier image de l'avis
     * @return Review créé
     */
    @PostMapping(
            value = "/{id}/reviews",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE
    )
    public Review addReview(
            @PathVariable Integer id,
            @RequestParam("comment") String comment,
            @RequestParam("image") MultipartFile image
    ) {

        // Vérifier si le produit existe
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Produit introuvable"));

        // Vérifier si une image est envoyée
        String imageUrl = null;
        if (image != null && !image.isEmpty()) {
            imageUrl = imageUploadService.uploadImage(image);
        }

        // Créer et sauvegarder le review
        Review review = new Review();
        review.setComment(comment);
        review.setImageUrl(imageUrl);
        review.setProduct(product);

        return reviewRepository.save(review);
    }
}
