package com.epam.training.controllers;

import com.epam.training.data.dto.CategoryDto;
import com.epam.training.facades.interfaces.ICategoryFacade;
import com.epam.training.validation.PageValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedResources;
import org.springframework.hateoas.Resource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/categories")
public class CategoryController {

    private final ICategoryFacade categoryFacade;

    private final PageValidator pageValidator;


    @Autowired
    public CategoryController(final ICategoryFacade categoryFacade,
                              final PageValidator pageValidator) {
        this.categoryFacade = categoryFacade;
        this.pageValidator = pageValidator;
    }


    @GetMapping("/{id}")
    public CategoryDto findById(@PathVariable final Long id) {
        return categoryFacade.findById(id);
    }

    @GetMapping
    public HttpEntity<PagedResources<Resource<CategoryDto>>>
    getAllPaged(final Pageable pageable,
                final PagedResourcesAssembler<CategoryDto> assembler) {
        Page<CategoryDto> categories = categoryFacade.findAll(pageable);
        pageValidator.validate(pageable, categories);
        return ResponseEntity.ok()
                        .body(assembler.toResource(categories));
    }

    @GetMapping("/name/{name}")
    public CategoryDto findByName(@PathVariable final String name) {
        return categoryFacade.findByName(name);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CategoryDto create(@RequestBody @Valid final CategoryDto categoryDto) {
        return categoryFacade.save(categoryDto);
    }

    @PutMapping("/{id}")
    public CategoryDto update(@RequestBody @Valid final CategoryDto categoryDto, final @PathVariable Long id) {
        return categoryFacade.update(categoryDto, id);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable final Long id) {
        categoryFacade.deleteById(id);
    }

}
