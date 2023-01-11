package xyz.crucials.disourceshop.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xyz.crucials.disourceshop.entities.ReviewEntity;
import xyz.crucials.disourceshop.exception.ReviewNotFoundException;
import xyz.crucials.disourceshop.repositories.ReviewRepository;

@Service
public class ReviewService {
    private final ReviewRepository reviewRepository;

    @Autowired
    public ReviewService(ReviewRepository reviewRepository) {
        this.reviewRepository = reviewRepository;
    }

    public void likeReview(Long id) throws ReviewNotFoundException {
        ReviewEntity review = reviewRepository.findById(id).orElseThrow(() -> new ReviewNotFoundException(id));
        review.like();
        reviewRepository.save(review);
    }
}
