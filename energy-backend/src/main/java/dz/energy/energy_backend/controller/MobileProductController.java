package dz.energy.energy_backend.controller;

import dz.energy.energy_backend.dto.ProductItemDTO;
import dz.energy.energy_backend.model.ProductItem;
import dz.energy.energy_backend.service.ProductItemService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/mobile")
@CrossOrigin // IMPORTANT pour Android
public class MobileProductController {

    private final ProductItemService itemService;

    public MobileProductController(ProductItemService itemService) {
        this.itemService = itemService;
    }

    // 👉 Produits visibles pour le client mobile
    @GetMapping("/products")
    public List<ProductItemDTO> getAllProducts() {

        return itemService.findAll()
                .stream()
                .map(item -> new ProductItemDTO(
                        item.getProduct().getName(),
                        item.getProduct().getDescription(),
                        item.getProduct().getSerialNumber(),
                        item.getPrice()
                ))
                .toList();
    }
}
