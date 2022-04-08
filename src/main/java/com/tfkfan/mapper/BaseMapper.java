package com.tfkfan.mapper;

import com.tfkfan.webservices.types.PageInfo;
import org.mapstruct.Named;
import org.springframework.data.domain.Page;

/**
 * @author Baltser Artem tfkfan
 */
public interface BaseMapper {
    String PAGE_INFO_MAPPING = "PAGE_INFO_MAPPING";

    @Named(PAGE_INFO_MAPPING)
    default <E> PageInfo pageInfo(Page<E> page){
        PageInfo info = new PageInfo();
        info.setPageNum(page.getPageable().getPageNumber());
        info.setPageSize(page.getPageable().getPageSize());
        info.setPageTotal(page.getTotalPages());
        info.setItemsCount(page.getTotalElements());
        info.setHasNextPage(page.hasNext());

        return info;
    }
}
