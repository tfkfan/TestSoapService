package com.tfkfan.web.soap.mapper;

import com.tfkfan.domain.Category;
import com.tfkfan.webservices.types.PageInfo;
import org.mapstruct.Named;
import org.springframework.data.domain.Page;

/**
 * @author Baltser Artem tfkfan
 */
public interface BaseMapper<E,D> {
    String PAGE_INFO_MAPPING = "PAGE_INFO_MAPPING";
    String DTO_MAPPING = "DTO_MAPPING";

    @Named(PAGE_INFO_MAPPING)
    default PageInfo pageInfo(Page<E> page){
        PageInfo info = new PageInfo();
        info.setPageNum(page.getPageable().getPageNumber());
        info.setPageSize(page.getPageable().getPageSize());
        info.setPageTotal(page.getTotalPages());
        info.setItemsCount(page.getTotalElements());
        info.setHasNextPage(page.hasNext());

        return info;
    }

    @Named(DTO_MAPPING)
    D toDto(E entity);
}
