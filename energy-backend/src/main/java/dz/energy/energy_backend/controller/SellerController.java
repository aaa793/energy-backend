//package dz.energy.energy_backend.controller;
//
//import dz.energy.energy_backend.dto.ProductItemDTO;
//import dz.energy.energy_backend.model.*;
//import dz.energy.energy_backend.service.ProductItemService;
//import dz.energy.energy_backend.service.ProductService;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//
//@Controller
//@RequestMapping("/seller")
//public class SellerController {
//
//    private final ProductItemService itemService;
//    private final ProductService productService;
//
//    public SellerController(ProductItemService itemService, ProductService productService) {
//        this.itemService = itemService;
//        this.productService = productService;
//    }
//
//    // ================= FRONT =================
//
//    // Page liste produits
//    @GetMapping("/products")
//    public String products(Model model) {
//        Integer sellerId = 1; // seller connecté (temporaire)
//        model.addAttribute("items", itemService.findBySeller(sellerId));
//        return "admin/seller/products";
//    }
//
//    // Page ajout produit
//    @GetMapping("/products/new")
//    public String addForm(Model model) {
//        model.addAttribute("itemDTO", new ProductItemDTO());
//        return "admin/seller/product-form";
//    }
//
//    // Sauvegarde via formulaire
//    @PostMapping("/products/save")
//    public String saveFromForm(@ModelAttribute("itemDTO") ProductItemDTO dto) {
//
//        // 1️⃣ Création du produit
//        Product product = new Product();
//        product.setName(dto.getName());
//        product.setDescription(dto.getDescription());
//        product.setSerialNumber(dto.getSerialNumber());
//
//        productService.save(product);
//
//        // 2️⃣ Création ProductItem
//        Seller seller = new Seller();
//        seller.setId(1); // Seller connecté (temporaire)
//
//        ProductItem item = new ProductItem();
//        item.setPrice(dto.getPrice());
//        item.setSeller(seller);
//        item.setProduct(product);
//
//        itemService.save(item);
//
//        return "redirect:/seller/products";
//    }
//
//    // Suppression
//    @GetMapping("/products/delete/{id}")
//    public String delete(@PathVariable Integer id) {
//        itemService.delete(id);
//        return "redirect:/seller/products";
//    }
//
//    @RestController
//    @RequestMapping("/api/mobile")
//    @CrossOrigin // IMPORTANT pour Android
//    public class MobileProductController {
//
//        private final ProductItemService itemService;
//
//        public MobileProductController(ProductItemService itemService) {
//            this.itemService = itemService;
//        }
//
//        // 👉 Produits visibles pour le client
//        @GetMapping("/products")
//        public List<ProductItem> getAllProducts() {
//            return itemService.findAll(); // tous les produits
//        }
//    }
//
//}



package dz.energy.energy_backend.controller;

import dz.energy.energy_backend.dto.ProductItemDTO;
import dz.energy.energy_backend.model.*;
import dz.energy.energy_backend.service.ProductItemService;
import dz.energy.energy_backend.service.ProductService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/seller")
public class SellerController {

    private final ProductItemService itemService;
    private final ProductService productService;

    public SellerController(ProductItemService itemService,
                            ProductService productService) {
        this.itemService = itemService;
        this.productService = productService;
    }

    // ================= FRONT =================

    // Liste produits du seller
    @GetMapping("/products")
    public String products(Model model) {
        Integer sellerId = 1; // seller connecté (temporaire)
        model.addAttribute("items", itemService.findBySeller(sellerId));
        return "admin/seller/products";
    }

    // Form ajout produit
    @GetMapping("/products/new")
    public String addForm(Model model) {
        model.addAttribute("itemDTO", new ProductItemDTO());
        return "admin/seller/product-form";
    }

    // Sauvegarde produit
    @PostMapping("/products/save")
    public String saveFromForm(@ModelAttribute("itemDTO") ProductItemDTO dto) {

        // 1️⃣ Créer Product
        Product product = new Product();
        product.setName(dto.getName());
        product.setDescription(dto.getDescription());
        product.setSerialNumber(dto.getSerialNumber());
        productService.save(product);

        // 2️⃣ Créer ProductItem
        Seller seller = new Seller();
        seller.setId(1); // seller connecté (temporaire)

        ProductItem item = new ProductItem();
        item.setPrice(dto.getPrice());
        item.setSeller(seller);
        item.setProduct(product);
        itemService.save(item);

        return "redirect:/seller/products";
    }

    // Supprimer produit
    @GetMapping("/products/delete/{id}")
    public String delete(@PathVariable Integer id) {
        itemService.delete(id);
        return "redirect:/seller/products";
    }
}
