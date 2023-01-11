package xyz.crucials.disourceshop.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import xyz.crucials.disourceshop.entities.AccountEntity;
import xyz.crucials.disourceshop.entities.CartItemEntity;
import xyz.crucials.disourceshop.exception.CartItemNotFoundException;
import xyz.crucials.disourceshop.exception.ProductNotFoundException;
import xyz.crucials.disourceshop.models.ProductDTO;
import xyz.crucials.disourceshop.repositories.AccountRepository;
import xyz.crucials.disourceshop.repositories.CartItemRepository;

import java.util.List;

@Service
public class CartService {
    private final AccountRepository accountRepository;
    private final CartItemRepository cartItemRepository;
    private final ProductService productService;

    @Autowired
    public CartService(AccountRepository accountRepository, CartItemRepository cartItemRepository,
                       ProductService productService) {
        this.accountRepository = accountRepository;
        this.cartItemRepository = cartItemRepository;
        this.productService = productService;
    }

    public List<CartItemEntity> getCart() {
        return getCurrentAccount().getCart();
    }

    public void addProductToCart(Long productId) throws ProductNotFoundException {
        AccountEntity currentAccount = getCurrentAccount();
        ProductDTO productToAdd = productService.getProductById(productId);
        currentAccount.addItemToCart(CartItemEntity.fromProductDTO(productToAdd));
        accountRepository.save(currentAccount);
    }

    public void removeItemFromCart(Long itemId) throws CartItemNotFoundException {
        AccountEntity currentAccount = getCurrentAccount();
        CartItemEntity cartItemToDelete = getCartItemById(itemId);
        currentAccount.removeItemFromCart(cartItemToDelete);
        accountRepository.save(currentAccount);
    }

    public void incrementCartItemCount(Long cartItemId) throws CartItemNotFoundException {
        CartItemEntity cartItem = getCartItemById(cartItemId);
        cartItem.setCount(cartItem.getCount() + 1);
        cartItemRepository.save(cartItem);
    }

    public void decrementCartItemCount(Long cartItemId) throws CartItemNotFoundException {
        CartItemEntity cartItem = getCartItemById(cartItemId);
        int cartItemCount = cartItem.getCount();
        if(cartItemCount > 1) {
            cartItem.setCount(cartItem.getCount() - 1);
        }
        cartItemRepository.save(cartItem);
    }

    private AccountEntity getCurrentAccount() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return (AccountEntity) authentication.getPrincipal();
    }

    private CartItemEntity getCartItemById(Long id) throws CartItemNotFoundException {
        return cartItemRepository.findById(id).orElseThrow(() -> new CartItemNotFoundException(id));
    }
}