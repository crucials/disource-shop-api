package xyz.crucials.disourceshop.entities;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import xyz.crucials.disourceshop.models.ProductType;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table(name = "account")
@NoArgsConstructor
@EqualsAndHashCode
public class AccountEntity implements UserDetails {
    @Id
    private String username;
    private String password;
    private boolean active;

    @Enumerated(EnumType.STRING)
    @Getter
    @Setter
    private ProductType lastViewedProductType;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "cart", joinColumns = @JoinColumn(name = "account_id"),
            inverseJoinColumns = @JoinColumn(name = "cart_item_id"))
    @Getter
    private List<CartItemEntity> cart = new ArrayList<>();

    public AccountEntity(String username, String password, boolean active) {
        this.username = username;
        this.password = password;
        this.active = active;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return active;
    }

    @Override
    public boolean isAccountNonLocked() {
        return active;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return active;
    }

    @Override
    public boolean isEnabled() {
        return active;
    }

    public void addItemToCart(CartItemEntity cartItem) {
        cart.add(cartItem);
    }

    public void removeItemFromCart(CartItemEntity cartItemToRemove) {
        cart = cart.stream().filter(cartItem -> !cartItem.getId().equals(cartItemToRemove.getId()))
                .collect(Collectors.toList());
    }
}
