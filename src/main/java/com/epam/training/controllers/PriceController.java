package com.epam.training.controllers;

import com.epam.training.data.dto.PriceDto;
import com.epam.training.facades.interfaces.IPriceFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@RestController
@RequestMapping("/products")
public class PriceController {

    private IPriceFacade priceFacade;

    @Autowired
    public PriceController(final IPriceFacade priceFacade) {
        this.priceFacade = priceFacade;
    }

    @GetMapping("/{id}/price")
    public PriceDto findById(@PathVariable final Long id) {
        return priceFacade.findByProductId(id);
    }

    @PostMapping("/{id}/price")
    @ResponseStatus(HttpStatus.CREATED)
    public PriceDto create(@RequestBody @Valid final PriceDto priceDto, @PathVariable final Long id) {
        return priceFacade.addProductPrice(priceDto, id);
    }

    @PutMapping("/{id}/price")
    public PriceDto update(@RequestBody @Valid final PriceDto priceDto, final @PathVariable Long id) {
        return priceFacade.updateProductPrice(priceDto, id);
    }

    @DeleteMapping("/{id}/price")
    public void delete(@PathVariable final Long id) {
        priceFacade.deleteFromProduct(id);
    }


}
