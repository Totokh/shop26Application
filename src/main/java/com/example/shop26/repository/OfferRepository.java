package com.example.shop26.repository;

import com.example.shop26.model.Offer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OfferRepository extends JpaRepository<Offer, Long> {
    Offer findByProduct_ProductId(Long productId);
}
