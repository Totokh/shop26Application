package com.example.shop26.controller;


import com.example.shop26.dao.OfferDAO;
import com.example.shop26.dao.mapper.OfferMapper;
import com.example.shop26.model.Offer;
import com.example.shop26.service.OfferService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/offer")
@Tag(name = "Offer Management", description = "Operations related to offers")
public class OfferController {
    @Autowired
    private OfferService offerService;

    @GetMapping(value="/all")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Get all offers")
    public List<OfferDAO> OfferAll()
    {
        return offerService.getAllOffers().stream()
                .map(OfferMapper::OfferToDao)
                .toList();
    }

    @PostMapping(value="/add")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Add new offer")
    public OfferDAO OfferAdd(@Parameter(description = "Offer data to be added", required = true) @RequestBody OfferDAO offer)
    {
        return OfferMapper.OfferToDao(offerService.addOffer(OfferMapper.DaoToOffer(offer)));
    }

    @PutMapping(value="/update")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Update an existing offer")
    public void OfferUpdate(@Parameter(description = "Offer data to be updated", required = true) @RequestBody OfferDAO offer)
    {
        offerService.editOffer(OfferMapper.DaoToOffer(offer));
    }
}
