package com.example.shop26.service;


import com.example.shop26.model.Offer;
import com.example.shop26.model.Price;
import com.example.shop26.model.Product;
import com.example.shop26.model.Quantity;
import com.example.shop26.repository.OfferRepository;
import com.example.shop26.repository.PriceRepository;
import com.example.shop26.repository.ProductRepository;
import com.example.shop26.repository.QuantityRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class OfferServiceIntegrationTest {

    @Autowired
    private OfferService offerService;

    @Autowired
    private OfferRepository offerRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private QuantityRepository quantityRepository;

    @Autowired
    private PriceRepository priceRepository;

    @BeforeEach
    void setUp() {
        offerRepository.deleteAll();
        productRepository.deleteAll();
        quantityRepository.deleteAll();
        priceRepository.deleteAll();
    }

    @Test
    void testAddOffer() {
        // Arrange
        Product product = new Product();
        product.setProductId(1L);
        product.setProductName("Product A");
        product.setProductBrand("Brand A");
        product.setProductSize("Large");

        Quantity quantity = new Quantity();
        quantity.setValue(5);

        Price price = new Price();
        price.setValue(19.99f);

        Offer offer = new Offer();
        offer.setProduct(product);
        offer.setQuantity(quantity);
        offer.setPrice(price);

        Offer savedOffer = offerService.addOffer(offer);

        assertEquals(offer.getProduct().getProductId(), savedOffer.getProduct().getProductId());
        assertEquals(offer.getQuantity().getValue(), savedOffer.getQuantity().getValue());
        assertEquals(offer.getPrice().getValue(), savedOffer.getPrice().getValue());
    }

    @Test
    void testGetAllOffers() {
        Offer offer = new Offer();
        Product product = new Product();
        product.setProductId(1L);
        product.setProductName("Product A");
        product.setProductBrand("Brand A");
        product.setProductSize("Large");
        productRepository.save(product);

        Quantity quantity = new Quantity();
        quantity.setValue(5);
        quantityRepository.save(quantity);

        Price price = new Price();
        price.setValue(19.99f);
        priceRepository.save(price);

        offer.setProduct(product);
        offer.setQuantity(quantity);
        offer.setPrice(price);
        offerRepository.save(offer);

        List<Offer> offers = offerService.getAllOffers();

        assertEquals(1, offers.size());
        assertEquals("Product A", offers.get(0).getProduct().getProductName());
    }
}