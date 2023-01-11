package xyz.crucials.disourceshop.entities;

import jakarta.persistence.*;
import lombok.*;
import xyz.crucials.disourceshop.models.ProductDTO;
import xyz.crucials.disourceshop.models.ProductType;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "product")
@Getter
@NoArgsConstructor
@ToString
public class ProductEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    private String name;
    private String description;
    private String photoBaseFilename;
    private double price;
    @Enumerated(EnumType.STRING)
    private ProductType type;
    @OneToMany(mappedBy = "targetProduct", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private final List<ReviewEntity> reviews = new ArrayList<>();

    public ProductEntity(String name, String description, String photoBaseFilename, double price,
                         ProductType type) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.photoBaseFilename = photoBaseFilename;
        this.type = type;
    }

    public void addReview(ReviewEntity review) {
        reviews.add(review);
        review.setTargetProduct(this);
    }

    public int getAverageRate() {
        double averageRate = 0;
        for (ReviewEntity review : reviews) {
            averageRate += review.getRate();
        }

        return (int) Math.round(averageRate / reviews.size());
    }

    public static ProductEntity fromDTO(ProductDTO productDTO) {
        return new ProductEntity(productDTO.getName(), productDTO.getDescription(), productDTO.getPhotoBaseFilename(),
                productDTO.getPrice(), productDTO.getType());
    }
}
