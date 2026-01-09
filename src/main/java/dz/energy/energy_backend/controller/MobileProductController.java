package dz.energy.energy_backend.controller;

import dz.energy.energy_backend.dto.MobileProductDTO;
import dz.energy.energy_backend.model.Product;
import dz.energy.energy_backend.model.ProductItem;
import dz.energy.energy_backend.service.ProductItemService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/mobile")
@CrossOrigin("*")
public class MobileProductController {

    private final ProductItemService itemService;

    public MobileProductController(ProductItemService itemService) {
        this.itemService = itemService;
    }

    @GetMapping("/products")
    public List<MobileProductDTO> getProductsForMobile() {

        return itemService.findAll().stream().map(item -> {

            Product product = item.getProduct();

            MobileProductDTO dto = new MobileProductDTO();
            dto.setName(product.getName());
            dto.setDescription(product.getDescription());
            dto.setPrice(item.getPrice());

            // 🔹 Catégorie (exemple simple)
            dto.setCategory("Panneaux Solaires");

            // 🔹 URL de l'image pour Android
            dto.setImageResId("http://192.168.1.2:8081/uploads/" + product.getImageUrl());


            return dto;
        }).toList();
    }

}
