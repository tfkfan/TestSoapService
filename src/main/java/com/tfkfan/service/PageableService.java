package com.tfkfan.service;

import com.tfkfan.webservices.types.PageSettings;
import org.springframework.data.domain.Pageable;

/**
 * @author Baltser Artem tfkfan
 */
public interface PageableService {
    Pageable pageable(PageSettings pageSettings);
}
