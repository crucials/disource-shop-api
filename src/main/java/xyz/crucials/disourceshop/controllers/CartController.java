package xyz.crucials.disourceshop.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import xyz.crucials.disourceshop.entities.CartItemEntity;
import xyz.crucials.disourceshop.exception.CartItemNotFoundException;
import xyz.crucials.disourceshop.exception.ProductNotFoundException;
import xyz.crucials.disourceshop.services.CartService;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/cart")
public class CartController {
    private final CartService cartService;

    @Autowired
    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @GetMapping
    public List<CartItemEntity> getCart() {
        return cartService.getCart();
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> addProductToCart(@PathVariable("id") Long productId) {
        try {
            cartService.addProductToCart(productId);
            return ResponseEntity.ok("Successfully added product to the cart");
        }
        catch (ProductNotFoundException exception) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> removeCartItem(@PathVariable("id") Long itemId) {
        try {
            cartService.removeItemFromCart(itemId);
            return ResponseEntity.ok("Successfully removed item from cart");
        }
        catch (CartItemNotFoundException exception) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}/count")
    public ResponseEntity<String> incrementCartItemCount(@PathVariable("id") Long cartItemId) {
        try {
            cartService.incrementCartItemCount(cartItemId);
            return ResponseEntity.ok("Successfully incremented item count");
        }
        catch (CartItemNotFoundException exception) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}/count")
    public ResponseEntity<String> decrementCartItemCount(@PathVariable("id") Long cartItemId) {
        try {
            cartService.decrementCartItemCount(cartItemId);
            return ResponseEntity.ok("Successfully decremented item count");
        }
        catch (CartItemNotFoundException exception) {
            return ResponseEntity.notFound().build();
        }
    }

}
