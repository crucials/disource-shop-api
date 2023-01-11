package xyz.crucials.disourceshop.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import xyz.crucials.disourceshop.models.ProductDTO;

@Entity
@Table(name = "cart_item")
@NoArgsConstructor
@Getter
@Setter
public class CartItemEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    private Long productId;
    private int count;
    private double price;
    private String name;
    private String photoBaseFilename;

    public CartItemEntity(Long productId, int count, String name, String photoBaseFilename, double price) {
        this.productId = productId;
        this.count = count;
        this.name = name;
        this.photoBaseFilename = photoBaseFilename;
        this.price = price;
    }
    
    public static CartItemEntity fromProductDTO(ProductDTO productDTO) {
        return new CartItemEntity(productDTO.getId(), 1, productDTO.getName(), productDTO.getPhotoBaseFilename(),
                productDTO.getPrice());
    }
}
