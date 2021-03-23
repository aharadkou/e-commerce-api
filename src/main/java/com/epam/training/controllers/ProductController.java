package com.epam.training.controllers;

import com.epam.training.data.dto.ProductDto;
import com.epam.training.facades.interfaces.IProductFacade;
import com.epam.training.validation.PageValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedResources;
import org.springframework.hateoas.Resource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.math.BigDecimal;

@RestController
@RequestMapping("/products")
public class ProductController {

    private final IProductFacade productFacade;

    private final PageValidator pageValidator;

    @Autowired
    public ProductController(final IProductFacade productFacade,
                             final PageValidator pageValidator) {
        this.productFacade = productFacade;
        this.pageValidator = pageValidator;
    }

    @GetMapping("/{id}")
    public ProductDto findById(@PathVariable final Long id) {
        return productFacade.findById(id);
    }

    @GetMapping
    public HttpEntity<PagedResources<Resource<ProductDto>>>
    getAllPaged(final Pageable pageable,
                final PagedResourcesAssembler<ProductDto> assembler) {
        Page<ProductDto> products = productFacade.findAll(pageable);
        pageValidator.validate(pageable, products);
        return ResponseEntity.ok()
                .body(assembler.toResource(products));
    }

    @GetMapping("/name/{name}")
    public ProductDto findByName(@PathVariable final String name) {
        return productFacade.findByName(name);
    }

    @GetMapping("/category/{id}")
    public ProductDto findByCategoryId(@PathVariable final Long id) {
        return productFacade.findByCategoryId(id);
    }


    @GetMapping("/price")
    public HttpEntity<PagedResources<Resource<ProductDto>>>
    findByPrice(@RequestParam final BigDecimal amount,
                @RequestParam final String currency,
                final Pageable pageable,
                final PagedResourcesAssembler<ProductDto> assembler) {
        Page<ProductDto> products = productFacade.findByPrice(amount, currency, pageable);
        pageValidator.validate(pageable, products);
        return ResponseEntity.ok()
                .body(assembler.toResource(products));
    }

    @GetMapping("/price/range")
    public HttpEntity<PagedResources<Resource<ProductDto>>>
    findByPriceRange(@RequestParam final BigDecimal from,
                     @RequestParam final BigDecimal to,
                     @RequestParam final String currency,
                     final Pageable pageable,
                     final PagedResourcesAssembler<ProductDto> assembler) {
        Page<ProductDto> products = productFacade.findByPriceRange(from, to, currency, pageable);
        pageValidator.validate(pageable, products);
        return ResponseEntity.ok()
                .body(assembler.toResource(products));

    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ProductDto create(@RequestBody @Valid final ProductDto productDto) {
        return productFacade.save(productDto);
    }

    @PutMapping("/{id}")
    public ProductDto update(@RequestBody @Valid final ProductDto productDto, final @PathVariable Long id) {
        return productFacade.update(productDto, id);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable final Long id) {
        productFacade.deleteById(id);
    }

}
