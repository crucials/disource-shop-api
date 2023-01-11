package xyz.crucials.disourceshop.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import xyz.crucials.disourceshop.entities.AccountEntity;
import xyz.crucials.disourceshop.entities.ProductEntity;
import xyz.crucials.disourceshop.entities.ReviewEntity;
import xyz.crucials.disourceshop.exception.ProductNotFoundException;
import xyz.crucials.disourceshop.exception.ReviewAlreadyPostedException;
import xyz.crucials.disourceshop.models.ProductDTO;
import xyz.crucials.disourceshop.models.ProductType;
import xyz.crucials.disourceshop.models.ReviewDTO;
import xyz.crucials.disourceshop.repositories.AccountRepository;
import xyz.crucials.disourceshop.repositories.ProductRepository;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class ProductService {
    private final ProductRepository productRepository;
    private final AccountRepository accountRepository;
    private final Random random = new Random();

    @Autowired
    public ProductService(ProductRepository productRepository, AccountRepository accountRepository) {
        this.productRepository = productRepository;
        this.accountRepository = accountRepository;
    }

    public List<ProductDTO> getAllProducts() {
        return productRepository.findAll().stream().map(ProductDTO::fromEntity).toList();
    }

    public List<ProductDTO> getRecommendedProducts(int count) {
        List<ProductDTO> products = getAllProducts();
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if(authentication != null && authentication.isAuthenticated()
                && !(authentication instanceof AnonymousAuthenticationToken)) {
            AccountEntity currentAccount = (AccountEntity) authentication.getPrincipal();
            ProductType lastViewedProductType = currentAccount.getLastViewedProductType();

            if(lastViewedProductType != null) {
                List<ProductDTO> recommendedProducts = products.stream()
                        .filter(product -> product.getType() == currentAccount.getLastViewedProductType()).toList();

                return pickRandomElementsFromList(recommendedProducts, count);
            }
            else {
                return pickRandomElementsFromList(products, count);
            }
        }
        else {
            return pickRandomElementsFromList(products, count);
        }
    }

    public ProductDTO getProductById(Long id) throws ProductNotFoundException {
        ProductEntity product = productRepository.findById(id).orElseThrow(() ->
                new ProductNotFoundException(id));

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication != null && authentication.isAuthenticated()
                && !(authentication instanceof AnonymousAuthenticationToken)) {
            AccountEntity currentAccount = (AccountEntity) authentication.getPrincipal();
            currentAccount.setLastViewedProductType(product.getType());
            accountRepository.save(currentAccount);
        }

        return ProductDTO.fromEntity(product);
    }

    public Long addReviewToProduct(Long productId, ReviewDTO reviewDTO) throws ProductNotFoundException,
            ReviewAlreadyPostedException {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        AccountEntity currentAccount = (AccountEntity) authentication.getPrincipal();
        ProductEntity product = productRepository.findById(productId).orElseThrow(() ->
                new ProductNotFoundException(productId));

        if(product.getReviews().stream().anyMatch(review -> review.getAuthorAccount()
                .getUsername().equals(currentAccount.getUsername()))) {
            throw new ReviewAlreadyPostedException();
        }

        ReviewEntity reviewEntity = ReviewEntity.fromDTO(reviewDTO);
        product.addReview(reviewEntity);

        productRepository.save(product);
        return product.getReviews().stream().filter(review -> review.getAuthorAccount().getUsername().equals(currentAccount.getUsername())).findAny()
                .get().getId();
    }

    public void addProduct(ProductDTO productDTO) {
        ProductEntity convertedProduct = ProductEntity.fromDTO(productDTO);
        productRepository.save(convertedProduct);
    }

    private <E> List<E> pickRandomElementsFromList(List<E> list, int count) {
        List<E> listCopy = new ArrayList<>(list);
        Collections.shuffle(listCopy);
        return count > listCopy.size() ? listCopy.subList(0, listCopy.size()) : listCopy.subList(0, count);
    }
}
