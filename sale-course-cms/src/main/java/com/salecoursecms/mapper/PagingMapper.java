package com.salecoursecms.mapper;

import com.salecoursecms.dto.reponse.PagingResponse;
import org.mapstruct.Mapper;


@Mapper(componentModel = "spring")
public interface PagingMapper {
    PagingResponse createPagingResponse (Integer totalPages, Integer page, Integer size);
}
