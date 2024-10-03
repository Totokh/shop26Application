package com.example.shop26.service;


import com.example.shop26.model.Offer;
import com.example.shop26.model.Price;
import com.example.shop26.model.Product;
import com.example.shop26.model.Quantity;
import com.example.shop26.repository.OfferRepository;
import com.example.shop26.repository.PriceRepository;
import com.example.shop26.repository.ProductRepository;
import com.example.shop26.repository.QuantityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OfferService {
    private final OfferRepository offerRepository;
    private final ProductRepository productRepository;
    private final QuantityRepository quantityRepository;
    private final PriceRepository priceRepository;


    @Autowired
    public OfferService(OfferRepository repository, ProductRepository productRepository, QuantityRepository quantityRepository, PriceRepository priceRepository) {
        this.offerRepository = repository;
        this.productRepository = productRepository;
        this.quantityRepository = quantityRepository;
        this.priceRepository = priceRepository;
    }

    public List<Offer> getAllOffers() {
        return offerRepository.findAll();
    }
    public Offer addOffer(Offer offer) {
        if (offer.getProduct() != null) {
            productRepository.save(offer.getProduct());
        }
        if (offer.getQuantity() != null) {
            quantityRepository.save(offer.getQuantity());
        }
        if (offer.getPrice() != null) {
            priceRepository.save(offer.getPrice());
        }
        return offerRepository.save(offer);
    }

    public void editOffer(Offer offer) {
        Long productId = offer.getProduct().getProductId();
        if (productRepository.existsById(productId)) {
            Product existingProduct = productRepository.findById(productId).orElseThrow(() ->
                    new RuntimeException("Product not found (findById step)"));

            // Update only the fields that need to be changed
            existingProduct.setProductName(offer.getProduct().getProductName());
            existingProduct.setProductBrand(offer.getProduct().getProductBrand());
            existingProduct.setProductSize(offer.getProduct().getProductSize());

            // Save the updated Product
            productRepository.saveAndFlush(existingProduct);
            Offer existingOffer = offerRepository.findByProduct_ProductId(productId);

            if (existingOffer != null) {
                // Update Quantity
                if (offer.getQuantity() != null) {
                    Quantity existingQuantity = existingOffer.getQuantity();
                    if (existingQuantity != null && existingQuantity.getValue() != offer.getQuantity().getValue()) {
                        existingQuantity.setValue(offer.getQuantity().getValue());
                        quantityRepository.saveAndFlush(existingQuantity); // Save new quantity first
                        existingOffer.setQuantity(existingQuantity); // Set the new quantity to the offer
                    }
                }

                // Update Price
                if (offer.getPrice() != null) {
                    Price existingPrice = existingOffer.getPrice();
                    if (existingPrice != null && !existingPrice.getValue().equals(offer.getPrice().getValue())) {
                        existingPrice.setValue(offer.getPrice().getValue());
                        priceRepository.saveAndFlush(existingPrice); // Save new price first
                        existingOffer.setPrice(existingPrice); // Set the new price to the offer
                    }
                }

                // Save the updated Offer
                offerRepository.saveAndFlush(existingOffer);
            } else {
                throw new RuntimeException("Offer not found for the given productId");
            }
        }
        else
            throw new RuntimeException("Product not found (existsById step)");
    }
}
