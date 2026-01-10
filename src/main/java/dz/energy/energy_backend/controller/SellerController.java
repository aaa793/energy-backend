package dz.energy.energy_backend.controller;

import dz.energy.energy_backend.model.Product;
import dz.energy.energy_backend.model.ProductItem;
import dz.energy.energy_backend.model.Seller;
import dz.energy.energy_backend.service.ImageUploadService;
import dz.energy.energy_backend.service.ProductItemService;
import dz.energy.energy_backend.service.ProductService;
import dz.energy.energy_backend.service.SellerService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Controller
@RequestMapping("/seller")
public class SellerController {

    private final ProductItemService itemService;
    private final ProductService productService;
    private final SellerService sellerService;
    private final ImageUploadService imageUploadService;

    public SellerController(ProductItemService itemService,
                            ProductService productService,
                            SellerService sellerService,
                            ImageUploadService imageUploadService) {
        this.itemService = itemService;
        this.productService = productService;
        this.sellerService = sellerService;
        this.imageUploadService = imageUploadService;
    }

    // ================= LISTE PRODUITS =================
    @GetMapping("/products")
    public String products(@RequestParam Integer sellerId, Model model) {

        Seller seller = sellerService.findById(sellerId);
        if (seller == null) {
            return "redirect:/error";
        }

        model.addAttribute("items", itemService.findBySeller(sellerId));
        model.addAttribute("sellerId", sellerId);
        return "admin/seller/products";
    }

    // ================= FORM AJOUT =================
    @GetMapping("/products/new")
    public String addForm(@RequestParam Integer sellerId, Model model) {

        Seller seller = sellerService.findById(sellerId);
        if (seller == null) {
            return "redirect:/error";
        }

        model.addAttribute("sellerId", sellerId);
        return "admin/seller/product-form";
    }

    // ================= CREATE PRODUIT =================
    @PostMapping("/products/save")
    public String saveProduct(
            @RequestParam String name,
            @RequestParam String description,
            @RequestParam String serialNumber,
            @RequestParam double price,
            @RequestParam MultipartFile image,
            @RequestParam Integer sellerId
    ) throws IOException {

        Seller seller = sellerService.findById(sellerId);
        if (seller == null) {
            return "redirect:/error";
        }

        // Image obligatoire à la création
        if (image == null || image.isEmpty()) {
            return "redirect:/seller/products/new?sellerId=" + sellerId;
        }

        String imageUrl = imageUploadService.uploadImage(image);

        Product product = new Product();
        product.setName(name);
        product.setDescription(description);
        product.setSerialNumber(serialNumber);
        product.setImageUrl(imageUrl);

        productService.save(product);

        ProductItem item = new ProductItem();
        item.setProduct(product);
        item.setSeller(seller);
        item.setPrice(price);

        itemService.save(item);

        return "redirect:/seller/products?sellerId=" + sellerId;
    }

    // ================= FORM EDIT =================
    @GetMapping("/products/edit/{itemId}")
    public String editForm(@PathVariable Integer itemId,
                           @RequestParam Integer sellerId,
                           Model model) {

        ProductItem item = itemService.findById(itemId);

        if (item == null || !item.getSeller().getId().equals(sellerId)) {
            return "redirect:/error";
        }

        model.addAttribute("item", item);
        model.addAttribute("sellerId", sellerId);

        return "admin/seller/product-edit-form";
    }

    // ================= UPDATE PRODUIT =================
    @PostMapping("/products/update")
    public String updateProduct(
            @RequestParam Integer itemId,
            @RequestParam String name,
            @RequestParam String description,
            @RequestParam String serialNumber,
            @RequestParam double price,
            @RequestParam(required = false) MultipartFile image,
            @RequestParam Integer sellerId
    ) throws IOException {

        ProductItem item = itemService.findById(itemId);

        if (item == null || !item.getSeller().getId().equals(sellerId)) {
            return "redirect:/error";
        }

        Product product = item.getProduct();

        product.setName(name);
        product.setDescription(description);
        product.setSerialNumber(serialNumber);

        // Image facultative en update
        if (image != null && !image.isEmpty()) {
            String imageUrl = imageUploadService.uploadImage(image);
            product.setImageUrl(imageUrl);
        }

        productService.save(product);

        item.setPrice(price);
        itemService.save(item);

        return "redirect:/seller/products?sellerId=" + sellerId;
    }

    // ================= DELETE =================
    @GetMapping("/products/delete/{itemId}")
    public String delete(@PathVariable Integer itemId,
                         @RequestParam Integer sellerId) {

        ProductItem item = itemService.findById(itemId);

        if (item != null && item.getSeller().getId().equals(sellerId)) {
            itemService.delete(itemId);
        }

        return "redirect:/seller/products?sellerId=" + sellerId;
    }
}
