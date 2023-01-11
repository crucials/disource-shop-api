package xyz.crucials.disourceshop.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import xyz.crucials.disourceshop.entities.ProductEntity;

public interface ProductRepository extends JpaRepository<ProductEntity, Long> {


}
