package com.tfkfan.mapper;

import org.mapstruct.Named;

/**
 * @author Baltser Artem tfkfan
 */
public interface DtoMapper<E,D> extends BaseMapper{
    String DTO_MAPPING = "DTO_MAPPING";

    @Named(DTO_MAPPING)
    D toDto(E entity);
}
