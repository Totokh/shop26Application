package com.example.shop26.repository;

import com.example.shop26.model.Offer;
import com.example.shop26.model.Price;
import com.example.shop26.model.Product;
import com.example.shop26.model.Quantity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class RepositoryTests {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private QuantityRepository quantityRepository;

    @Mock
    private PriceRepository priceRepository;

    @Mock
    private OfferRepository offerRepository;


    private Product product;
    private Quantity quantity;
    private Price price;
    private Offer offer;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        product = new Product();
        product.setProductId(1L);
        product.setProductName("Product A");
        product.setProductBrand("Brand A");
        product.setProductSize("Large");

        quantity = new Quantity();
        quantity.setId(1L);
        quantity.setValue(5);

        price = new Price();
        price.setId(1L);
        price.setValue(19.99f);

        offer = new Offer();
        offer.setId(1L);
        offer.setProduct(product);
        offer.setQuantity(quantity);
        offer.setPrice(price);
    }

    @Test
    public void testSaveOffer() {
        when(productRepository.existsById(any(Long.class))).thenReturn(true);
        when(quantityRepository.existsById(any(Long.class))).thenReturn(true);
        when(priceRepository.existsById(any(Long.class))).thenReturn(true);
        when(offerRepository.save(any(Offer.class))).thenReturn(offer);

        Offer savedOffer = offerRepository.save(offer);
        assertNotNull(savedOffer);
        assertEquals("Product A", savedOffer.getProduct().getProductName());
        verify(offerRepository, times(1)).save(offer);
    }

    @Test
    public void testFindByProductId() {
        when(offerRepository.findByProduct_ProductId(any(Long.class))).thenReturn(offer);

        Offer foundOffer = offerRepository.findByProduct_ProductId(1L);
        assertNotNull(foundOffer);
        assertEquals("Product A", foundOffer.getProduct().getProductName());
    }

    @Test
    public void testDeleteOffer() {
        doNothing().when(offerRepository).delete(any(Offer.class));

        offerRepository.delete(offer);
        verify(offerRepository, times(1)).delete(offer);
    }

    @Test
    public void testFindProductById() {
        when(productRepository.findById(1L)).thenReturn(Optional.of(product));

        Optional<Product> foundProduct = productRepository.findById(1L);
        assertTrue(foundProduct.isPresent());
        assertEquals("Product A", foundProduct.get().getProductName());
    }

    @Test
    public void testSaveQuantity() {
        when(quantityRepository.save(any(Quantity.class))).thenReturn(quantity);

        Quantity savedQuantity = quantityRepository.save(quantity);
        assertNotNull(savedQuantity);
        assertEquals(5, savedQuantity.getValue());
    }

    @Test
    public void testSavePrice() {
        when(priceRepository.save(any(Price.class))).thenReturn(price);

        Price savedPrice = priceRepository.save(price);
        assertNotNull(savedPrice);
        assertEquals(19.99f, savedPrice.getValue());
    }
}