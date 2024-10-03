package com.example.shop26.dao.mapper;

import com.example.shop26.dao.OfferDAO;
import com.example.shop26.model.Offer;
import com.example.shop26.model.Price;
import com.example.shop26.model.Product;
import com.example.shop26.model.Quantity;

public class OfferMapper {
    public static Offer DaoToOffer(OfferDAO offerDAO) {
        Offer offer = new Offer();
        Product product = new Product();
        product.setProductId(offerDAO.getProductId());
        product.setProductName(offerDAO.getProductName());
        product.setProductBrand(offerDAO.getProductBrand());
        product.setProductSize(offerDAO.getProductSize());
        Quantity quantity = new Quantity();
        quantity.setValue(offerDAO.getQuantity());
        Price price = new Price();
        price.setValue(offerDAO.getPrice());
        offer.setProduct(product);
        offer.setQuantity(quantity);
        offer.setPrice(price);
        return offer;
    }
    public static OfferDAO OfferToDao(Offer offer) {
        Long productId = offer.getProduct() != null ? offer.getProduct().getProductId() : null;
        String productName = offer.getProduct() != null ? offer.getProduct().getProductName() : null;
        String productBrand = offer.getProduct() != null ? offer.getProduct().getProductBrand() : null;
        String productSize = offer.getProduct() != null ? offer.getProduct().getProductSize() : null;
        Integer quantityValue = offer.getQuantity() != null ? offer.getQuantity().getValue() : null;
        Float priceValue = offer.getPrice() != null ? offer.getPrice().getValue() : null;

        return new OfferDAO(productId, productName, productBrand, productSize, quantityValue, priceValue);
    }
}
