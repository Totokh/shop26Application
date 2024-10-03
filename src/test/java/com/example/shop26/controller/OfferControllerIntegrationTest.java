package com.example.shop26.controller;


import com.example.shop26.dao.OfferDAO;
import com.example.shop26.model.Offer;
import com.example.shop26.model.Price;
import com.example.shop26.model.Product;
import com.example.shop26.model.Quantity;
import com.example.shop26.repository.OfferRepository;
import com.example.shop26.repository.PriceRepository;
import com.example.shop26.repository.ProductRepository;
import com.example.shop26.repository.QuantityRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class OfferControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private OfferRepository offerRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private QuantityRepository quantityRepository;

    @Autowired
    private PriceRepository priceRepository;

    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper();
        offerRepository.deleteAll();
        productRepository.deleteAll();
        quantityRepository.deleteAll();
        priceRepository.deleteAll();
    }

    @Test
    void testOfferAdd() throws Exception {
        OfferDAO offerDAO = new OfferDAO(1L, "Product A", "Brand A", "Large", 5, 19.99f);

        mockMvc.perform(post("/api/offer/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(offerDAO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.productId").value(1))
                .andExpect(jsonPath("$.productName").value("Product A"))
                .andExpect(jsonPath("$.productBrand").value("Brand A"))
                .andExpect(jsonPath("$.productSize").value("Large"))
                .andExpect(jsonPath("$.quantity").value(5.0))
                .andExpect(jsonPath("$.price").value(19.99f));
    }

    @Test
    void testOfferAll() throws Exception {
        Offer offer = new Offer();
        offer.setProduct(productRepository.save(new Product(1L, "Product A", "Brand A", "Large")));
        offer.setQuantity(quantityRepository.save(new Quantity(1L, 5)));
        offer.setPrice(priceRepository.save(new Price(1L, 19.99f)));
        offerRepository.save(offer);

        mockMvc.perform(get("/api/offer/all"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", org.hamcrest.Matchers.hasSize(1)))
                .andExpect(jsonPath("$[0].productId").value(1))
                .andExpect(jsonPath("$[0].productName").value("Product A"))
                .andExpect(jsonPath("$[0].productBrand").value("Brand A"))
                .andExpect(jsonPath("$[0].productSize").value("Large"))
                .andExpect(jsonPath("$[0].quantity").value(5.0))
                .andExpect(jsonPath("$[0].price").value(19.99f));
    }

    @Test
    void testOfferUpdate() throws Exception {
        // Arrange
        Offer offer = new Offer();
        Product product = productRepository.save(new Product(1L, "Product A", "Brand A", "Large"));
        offer.setProduct(product);
        Quantity quantity = quantityRepository.save(new Quantity(1L, 5));
        offer.setQuantity(quantity);
        Price price = priceRepository.save(new Price(1L, 19.99f));
        offer.setPrice(price);
        offerRepository.save(offer);

        OfferDAO updatedOfferDAO = new OfferDAO(1L, "Product B", "Brand B", "Medium", 10, 29.99f);
        System.out.println(objectMapper.writeValueAsString(updatedOfferDAO));

        mockMvc.perform(put("/api/offer/update")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedOfferDAO)))
                .andExpect(status().isOk());

        mockMvc.perform(get("/api/offer/all"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].productName").value("Product B"))
                .andExpect(jsonPath("$[0].productBrand").value("Brand B"))
                .andExpect(jsonPath("$[0].productSize").value("Medium"))
                .andExpect(jsonPath("$[0].quantity").value(10.0))
                .andExpect(jsonPath("$[0].price").value(29.99f));
    }
}
