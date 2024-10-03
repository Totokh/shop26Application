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
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class OfferServiceTest {

    @Mock
    private OfferRepository offerRepository;

    @Mock
    private ProductRepository productRepository;

    @Mock
    private QuantityRepository quantityRepository;

    @Mock
    private PriceRepository priceRepository;

    @InjectMocks
    private OfferService offerService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllOffers() {
        when(offerRepository.findAll()).thenReturn(Collections.emptyList());

        var result = offerService.getAllOffers();

        assertEquals(0, result.size());
        verify(offerRepository, times(1)).findAll();
    }

    @Test
    void testAddOffer() {
        Offer offer = new Offer();
        Product product = new Product();
        product.setProductId(1L);
        Quantity quantity = new Quantity();
        quantity.setId(1L);
        Price price = new Price();
        price.setId(1L);

        offer.setProduct(product);
        offer.setQuantity(quantity);
        offer.setPrice(price);

        when(offerRepository.save(offer)).thenReturn(offer);

        Offer result = offerService.addOffer(offer);

        verify(productRepository, times(1)).save(product);
        verify(quantityRepository, times(1)).save(quantity);
        verify(priceRepository, times(1)).save(price);
        verify(offerRepository, times(1)).save(offer);
        assertEquals(offer, result);
    }

    @Test
    void testEditOffer() {
        Offer offer = new Offer();
        Product product = new Product();
        product.setProductId(1L);
        offer.setProduct(product);

        when(productRepository.existsById(1L)).thenReturn(true);
        when(productRepository.findById(1L)).thenReturn(Optional.of(product));
        when(offerRepository.findByProduct_ProductId(1L)).thenReturn(offer);
        when(quantityRepository.saveAndFlush(any(Quantity.class))).thenAnswer(i -> i.getArguments()[0]); // same qty
        when(priceRepository.saveAndFlush(any(Price.class))).thenAnswer(i -> i.getArguments()[0]); // same price


        offerService.editOffer(offer);

        verify(productRepository).saveAndFlush(product);
        verify(offerRepository).saveAndFlush(offer);
        verify(quantityRepository, never()).saveAndFlush(any(Quantity.class));
        verify(priceRepository, never()).saveAndFlush(any(Price.class));
    }

    @Test
    void testEditOfferThrowsException() {
        Offer offer = new Offer();
        Product product = new Product();
        product.setProductId(1L);
        offer.setProduct(product);

        when(offerRepository.existsById(1L)).thenReturn(false);

        assertThrows(RuntimeException.class, () -> offerService.editOffer(offer));

        verify(offerRepository, never()).save(offer);
    }
}
