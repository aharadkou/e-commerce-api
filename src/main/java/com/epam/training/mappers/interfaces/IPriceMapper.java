package com.epam.training.mappers.interfaces;

import com.epam.training.mappers.impl.CurrencyMapperImpl;
import com.epam.training.data.Price;
import com.epam.training.data.dto.PriceDto;
import org.mapstruct.Mapper;

@Mapper(uses = CurrencyMapperImpl.class, componentModel = "spring")
public interface IPriceMapper extends IEntityMapper<Price, PriceDto> {

    @Override
    PriceDto toDto(final Price product);

    @Override
    Price toEntity(final PriceDto priceDto);

}
