package com.tfkfan.service.impl;

import com.tfkfan.service.PageableService;
import com.tfkfan.webservices.types.PageInfo;
import com.tfkfan.webservices.types.PageSettings;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.Objects;

/**
 * @author Baltser Artem tfkfan
 */
public abstract class BasePageableServiceImpl implements PageableService {
    private static final Long DEFAULT_PAGE_NUM = 0L;
    private static final Long DEFAULT_PAGE_SIZE = 50L;
    private static final Sort.Direction DEFAULT_DIRECTION = Sort.Direction.DESC;

    public Pageable pageable(PageSettings pageSettings) {
        Long pageNum = Objects.isNull(pageSettings) ? DEFAULT_PAGE_NUM :
            (Objects.isNull(pageSettings.getPageNum()) ? DEFAULT_PAGE_NUM : pageSettings.getPageNum());
        Long pageSize = Objects.isNull(pageSettings) ? DEFAULT_PAGE_SIZE :
            (Objects.isNull(pageSettings.getPageSize()) ? DEFAULT_PAGE_SIZE : pageSettings.getPageSize());
        Sort.Direction direction = Objects.isNull(pageSettings) ? DEFAULT_DIRECTION:
            (Objects.isNull(pageSettings.isDescending()) ? DEFAULT_DIRECTION : toDirection(pageSettings.isDescending()));
        Sort sort = Objects.nonNull(pageSettings.getSortField()) ? Sort.by(direction, pageSettings.getSortField()) : Sort.unsorted();

        return PageRequest.of(pageNum.intValue(),
            pageSize.intValue(),
            sort);
    }

    private Sort.Direction toDirection(Boolean isDescending){
        return isDescending ? Sort.Direction.DESC : Sort.Direction.ASC;
    }
}
