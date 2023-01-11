package xyz.crucials.disourceshop.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import xyz.crucials.disourceshop.entities.CartItemEntity;

public interface CartItemRepository extends JpaRepository<CartItemEntity, Long> {



}
