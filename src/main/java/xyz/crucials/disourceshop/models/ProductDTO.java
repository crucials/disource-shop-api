package xyz.crucials.disourceshop.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import xyz.crucials.disourceshop.entities.AccountEntity;
import xyz.crucials.disourceshop.entities.ProductEntity;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Getter
public class ProductDTO {
    private Long id;
    private final String name;
    private final String description;
    private final String photoBaseFilename;
    private final double price;
    private final ProductType type;
    private final int averageRate;
    private final List<ReviewDTO> reviews;

    public static ProductDTO fromEntity(ProductEntity productEntity) {
        List<ReviewDTO> convertedReviews = new ArrayList<>();
        productEntity.getReviews().forEach(review -> {
            ReviewDTO convertedReview = new ReviewDTO(review.getId(), review.getText(), review.getRate(),
                    review.getLikedUsernames(),
                    review.getAuthorAccount().getUsername());

            convertedReviews.add(convertedReview);
        });

        return new ProductDTO(productEntity.getId(), productEntity.getName(), productEntity.getDescription(),
                productEntity.getPhotoBaseFilename(), productEntity.getPrice(), productEntity.getType(),
                productEntity.getAverageRate(), convertedReviews);
    }
}
