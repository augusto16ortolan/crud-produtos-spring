package com.atitus.crud_product.product;

import com.atitus.crud_product.brand.BrandService;
import com.atitus.crud_product.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private BrandService brandService;

    public List<Product> findAll() {
        UUID userId = getUserId();
        return productRepository.findByUserId(userId);
    }

    public Optional<Product> findById(UUID id) {
        UUID userId = getUserId();
        return productRepository.findByIdAndUserId(id, userId);
    }

    public Product save(Product product) {
        UUID userId = getUserId();
        User user = new User();
        user.setId(userId);
        product.setUser(user);

        final var brand = brandService.findById(product.getBrandId());

        product.setBrand(brand.get());

        return productRepository.save(product);
    }

    public void deleteById(UUID id) {
        UUID userId = getUserId();
        Optional<Product> product = productRepository.findByIdAndUserId(id, userId);
        product.ifPresent(productRepository::delete);
    }

    private UUID getUserId() {
        final var user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return user.getId();
    }
}
