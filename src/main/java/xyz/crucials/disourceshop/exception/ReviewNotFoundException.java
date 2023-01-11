package xyz.crucials.disourceshop.exception;

public class ReviewNotFoundException extends Exception {

    public ReviewNotFoundException(Long id) {
        super("Review with id " + id + " not found");
    }

}
