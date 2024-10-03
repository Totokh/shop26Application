package com.example.shop26.controller;

import com.example.shop26.dao.OfferDAO;
import com.example.shop26.model.Offer;
import com.example.shop26.model.Price;
import com.example.shop26.model.Product;
import com.example.shop26.model.Quantity;
import com.example.shop26.service.OfferService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Collections;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class OfferControllerTest {

    final private MockMvc mockMvc;

    @Mock
    private OfferService offerService;

    @InjectMocks
    private OfferController offerController;

    public OfferControllerTest() {
        MockitoAnnotations.openMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(offerController).build();
    }

    @Test
    void testOfferAll() throws Exception {
        when(offerService.getAllOffers()).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/api/offer/all"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isArray());

        verify(offerService, times(1)).getAllOffers();
    }

    @Test
    void testOfferAdd() throws Exception {
        OfferDAO offerDAO = new OfferDAO(1L, "Product A", "Brand A", "Large", 5, 19.99f);
        Offer offer = new Offer();
        Product product = new Product();
        product.setProductId(1L);
        product.setProductName("Product A");
        product.setProductBrand("Brand A");
        product.setProductSize("Large");

        Quantity quantity = new Quantity();
        quantity.setValue(5);

        Price price = new Price();
        price.setValue(19.99f);

        offer.setProduct(product);
        offer.setQuantity(quantity);
        offer.setPrice(price);

        when(offerService.addOffer(any())).thenReturn(offer);

        mockMvc.perform(post("/api/offer/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"productId\":1,\"productName\":\"Product A\",\"productBrand\":\"Brand A\",\"productSize\":\"Large\",\"quantity\":5.0,\"price\":19.99}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.productId").value(1))
                .andExpect(jsonPath("$.productName").value("Product A"))
                .andExpect(jsonPath("$.productBrand").value("Brand A"))
                .andExpect(jsonPath("$.productSize").value("Large"))
                .andExpect(jsonPath("$.quantity").value(5.0))
                .andExpect(jsonPath("$.price").value(19.99f));

        verify(offerService, times(1)).addOffer(any());
    }

    @Test
    void testOfferUpdate() throws Exception {
        mockMvc.perform(put("/api/offer/update")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"productId\":1,\"productName\":\"Product A\",\"productBrand\":\"Brand A\",\"productSize\":\"Large\",\"quantity\":5.0,\"price\":19.99}"))
                .andExpect(status().isOk());

        verify(offerService, times(1)).editOffer(any());
    }
}