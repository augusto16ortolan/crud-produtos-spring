package com.atitus.crud_product.brand;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface BrandRepository extends JpaRepository<Brand, UUID> {
    List<Brand> findByUserId(UUID userId);
    Optional<Brand> findByIdAndUserId(UUID id, UUID userId);
}