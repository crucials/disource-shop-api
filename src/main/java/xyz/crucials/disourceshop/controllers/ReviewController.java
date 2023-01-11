package xyz.crucials.disourceshop.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import xyz.crucials.disourceshop.exception.ReviewNotFoundException;
import xyz.crucials.disourceshop.services.ReviewService;

@RestController
@RequestMapping("/reviews")
@CrossOrigin
public class ReviewController {
    private final ReviewService reviewService;

    @Autowired
    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @PutMapping("/{id}/like")
    public ResponseEntity<String> likeReview(@PathVariable Long id) {
        try {
            reviewService.likeReview(id);
            return ResponseEntity.ok("Liked/disliked review successfully");
        }
        catch (ReviewNotFoundException exception) {
            return ResponseEntity.notFound().build();
        }
    }

}
