package xyz.crucials.disourceshop.exception;

public class ReviewAlreadyPostedException extends Exception {

    public ReviewAlreadyPostedException() {
        super("You can't post more than one review on one product");
    }

}
