package com.epam.training.mappers.interfaces;

import com.epam.training.mappers.impl.CurrencyMapperImpl;
import com.epam.training.data.Product;
import com.epam.training.data.dto.ProductDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(uses = CurrencyMapperImpl.class, componentModel = "spring")
public interface IProductMapper extends IEntityMapper<Product, ProductDto> {

    @Mapping(source = "category.id", target = "categoryId")
    ProductDto toDto(final Product product);

    @Override
    @Mapping(source = "categoryId", target = "category.id")
    Product toEntity(final ProductDto productDto);

}
