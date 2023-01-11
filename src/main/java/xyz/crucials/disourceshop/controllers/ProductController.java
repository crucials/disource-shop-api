package xyz.crucials.disourceshop.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import xyz.crucials.disourceshop.exception.ProductNotFoundException;
import xyz.crucials.disourceshop.exception.ReviewAlreadyPostedException;
import xyz.crucials.disourceshop.models.ProductDTO;
import xyz.crucials.disourceshop.models.ReviewDTO;
import xyz.crucials.disourceshop.services.ProductService;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin
@RequestMapping("/products")
public class ProductController {
    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public List<ProductDTO> getAllProducts() {
        return productService.getAllProducts();
    }

    @GetMapping("/recommended")
    public List<ProductDTO> getRecommendedProducts(@RequestParam("count") Optional<Integer> count) {
        return productService.getRecommendedProducts(count.orElse(4));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getProductById(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(productService.getProductById(id));
        }
        catch (ProductNotFoundException exception) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/{id}/reviews")
    public ResponseEntity<?> addReviewToProduct(@PathVariable Long id, @RequestBody ReviewDTO review) {
        try {
            return ResponseEntity.ok(productService.addReviewToProduct(id, review));
        }
        catch (ProductNotFoundException exception) {
            return ResponseEntity.notFound().build();
        }
        catch (ReviewAlreadyPostedException exception) {
            return ResponseEntity.badRequest().body(exception.getMessage());
        }
    }

    @PostMapping
    public void createProduct(@RequestBody ProductDTO product) {
        productService.addProduct(product);
    }
}
