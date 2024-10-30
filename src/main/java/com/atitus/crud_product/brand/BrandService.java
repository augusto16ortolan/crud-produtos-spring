package com.atitus.crud_product.brand;

import com.atitus.crud_product.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class BrandService {
    @Autowired
    private BrandRepository brandRepository;

    public List<Brand> findAll() {
        UUID userId = getUserId();
        return brandRepository.findByUserId(userId);
    }

    public Optional<Brand> findById(UUID id) {
        UUID userId = getUserId();
        return brandRepository.findByIdAndUserId(id, userId);
    }

    public Brand save(Brand brand) {
        UUID userId = getUserId();
        User user = new User();
        user.setId(userId);
        brand.setUser(user);
        return brandRepository.save(brand);
    }

    public void deleteById(UUID id) {
        UUID userId = getUserId();
        Optional<Brand> brand = brandRepository.findByIdAndUserId(id, userId);
        brand.ifPresent(brandRepository::delete);
    }

    private UUID getUserId() {
        final var user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return user.getId();
    }
}
