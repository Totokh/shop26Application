package com.example.shop26.dao.mapper;

import com.example.shop26.dao.OfferDAO;
import com.example.shop26.model.Offer;
import com.example.shop26.model.Price;
import com.example.shop26.model.Product;
import com.example.shop26.model.Quantity;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class OfferMapperTest {

    @Test
    void testDaoToOffer() {
        OfferDAO offerDAO = new OfferDAO(1L, "Product A", "Brand A", "Large", 5, 19.99f);

        Offer offer = OfferMapper.DaoToOffer(offerDAO);

        assertNotNull(offer);
        assertNotNull(offer.getProduct());
        assertEquals(1L, offer.getProduct().getProductId());
        assertEquals("Product A", offer.getProduct().getProductName());
        assertEquals("Brand A", offer.getProduct().getProductBrand());
        assertEquals("Large", offer.getProduct().getProductSize());
        assertEquals(5, offer.getQuantity().getValue());
        assertEquals(19.99f, offer.getPrice().getValue());
    }

    @Test
    void testOfferToDao() {
        Product product = new Product();
        product.setProductId(2L);
        product.setProductName("Product B");
        product.setProductBrand("Brand B");
        product.setProductSize("Medium");

        Quantity quantity = new Quantity();
        quantity.setValue(10);

        Price price = new Price();
        price.setValue(29.99f);

        Offer offer = new Offer();
        offer.setProduct(product);
        offer.setQuantity(quantity);
        offer.setPrice(price);

        OfferDAO offerDAO = OfferMapper.OfferToDao(offer);

        assertNotNull(offerDAO);
        assertEquals(2L, offerDAO.getProductId());
        assertEquals("Product B", offerDAO.getProductName());
        assertEquals("Brand B", offerDAO.getProductBrand());
        assertEquals("Medium", offerDAO.getProductSize());
        assertEquals(10, offerDAO.getQuantity());
        assertEquals(29.99f, offerDAO.getPrice());
    }
}