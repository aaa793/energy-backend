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
    public Review addReview(
            @PathVariable Integer id,
            @RequestParam("comment") String comment,
            @RequestParam("image") MultipartFile image
    ) {

        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Produit introuvable"));

        String imageUrl = imageUploadService.uploadImage(image);

        Review review = new Review();
        review.setComment(comment);
        review.setImageUrl(imageUrl);
        review.setProduct(product);

        return reviewRepository.save(review);
    }


}
