package xyz.crucials.disourceshop.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import xyz.crucials.disourceshop.entities.AccountEntity;

import java.util.Optional;

public interface AccountRepository extends JpaRepository<AccountEntity, String> {

    Optional<AccountEntity> findByUsername(String username);

}
