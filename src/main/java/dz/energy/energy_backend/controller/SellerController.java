package dz.energy.energy_backend.controller;

import dz.energy.energy_backend.dto.ProductItemDTO;
import dz.energy.energy_backend.model.Product;
import dz.energy.energy_backend.model.ProductItem;
import dz.energy.energy_backend.model.Seller;
import dz.energy.energy_backend.service.ProductItemService;
import dz.energy.energy_backend.service.ProductService;
import dz.energy.energy_backend.service.SellerService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;



import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Controller
@RequestMapping("/seller")
public class SellerController {

    private final ProductItemService itemService;
    private final ProductService productService;
    private final SellerService sellerService;

    public SellerController(ProductItemService itemService,
                            ProductService productService,
                            SellerService sellerService) {
        this.itemService = itemService;
        this.productService = productService;
        this.sellerService = sellerService;
    }

    // ===== Liste produits du seller =====
    @GetMapping("/products")
    public String products(@RequestParam Integer sellerId, Model model) {
        model.addAttribute("items", itemService.findBySeller(sellerId));
        model.addAttribute("sellerId", sellerId);
        return "admin/seller/products";
    }

    // ===== Form ajout produit =====
    @GetMapping("/products/new")
    public String addForm(@RequestParam Integer sellerId, Model model) {
        model.addAttribute("itemDTO", new ProductItemDTO());
        model.addAttribute("sellerId", sellerId);
        return "admin/seller/product-form";
    }

    // ===== Sauvegarde produit + image =====
    @PostMapping("/products/save") // ✅ CORRIGÉ
    public String saveProduct(
            @RequestParam String name,
            @RequestParam String description,
            @RequestParam String serialNumber,
            @RequestParam double price,
            @RequestParam("image") MultipartFile image,
            @RequestParam Integer sellerId
    ) throws IOException {

        // 📁 dossier images
        String uploadDir = "src/main/resources/static/uploads/";
        Files.createDirectories(Paths.get(uploadDir));

        // 📸 nom fichier
        String fileName = System.currentTimeMillis() + "_" + image.getOriginalFilename();
        Path filePath = Paths.get(uploadDir + fileName);
        Files.write(filePath, image.getBytes());

        // 📦 Product
        Product product = new Product();
        product.setName(name);
        product.setDescription(description);
        product.setSerialNumber(serialNumber);
        product.setImageUrl("/uploads/" + fileName);

        productService.save(product); // ✅ UTILISER SERVICE

        // 👤 Seller
        Seller seller = sellerService.findById(sellerId);

        // 🧾 ProductItem (lien seller ↔ product)
        ProductItem item = new ProductItem();
        item.setProduct(product);
        item.setSeller(seller);
        item.setPrice(price);

        itemService.save(item);

        return "redirect:/seller/products?sellerId=" + sellerId;
    }


    // ===== Supprimer produit =====
    @GetMapping("/products/delete/{id}")
    public String delete(@PathVariable Integer id,
                         @RequestParam Integer sellerId) {
        itemService.delete(id);
        return "redirect:/seller/products?sellerId=" + sellerId;
    }
}
