package xyz.crucials.disourceshop.exception;

public class CartItemNotFoundException extends Exception {

    public CartItemNotFoundException(Long id) {
        super("Cart item with id " + id + " not found");
    }

}
