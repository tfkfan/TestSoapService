package com.tfkfan.service;

import com.tfkfan.domain.*; // for static metamodels
import com.tfkfan.domain.Category;
import com.tfkfan.repository.CategoryRepository;
import com.tfkfan.service.criteria.CategoryCriteria;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech.jhipster.service.QueryService;

/**
 * Service for executing complex queries for {@link Category} entities in the database.
 * The main input is a {@link CategoryCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link Category} or a {@link Page} of {@link Category} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class CategoryQueryService extends QueryService<Category> {

    private final Logger log = LoggerFactory.getLogger(CategoryQueryService.class);

    private final CategoryRepository categoryRepository;

    public CategoryQueryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    /**
     * Return a {@link List} of {@link Category} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<Category> findByCriteria(CategoryCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Category> specification = createSpecification(criteria);
        return categoryRepository.findAll(specification);
    }


    @Transactional(readOnly = true)
    public Optional<Category> findOneByCriteria(CategoryCriteria criteria) {
        log.debug("find one by criteria : {}", criteria);
        final Specification<Category> specification = createSpecification(criteria);
        return categoryRepository.findOne(specification);
    }
    /**
     * Return a {@link Page} of {@link Category} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<Category> findByCriteria(CategoryCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Category> specification = createSpecification(criteria);
        return categoryRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(CategoryCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Category> specification = createSpecification(criteria);
        return categoryRepository.count(specification);
    }

    /**
     * Function to convert {@link CategoryCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Category> createSpecification(CategoryCriteria criteria) {
        Specification<Category> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Category_.id));
            }
            if (criteria.getCode() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCode(), Category_.code));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), Category_.name));
            }
            if (criteria.getDescription() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDescription(), Category_.description));
            }
            if (criteria.getParentCategoryId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getParentCategoryId(), Category_.parentCategoryId));
            }
            if (criteria.getIsHidden() != null) {
                specification = specification.and(buildSpecification(criteria.getIsHidden(), Category_.isHidden));
            }
            if (criteria.getCreationDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCreationDate(), Category_.creationDate));
            }
            if (criteria.getModificationDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getModificationDate(), Category_.modificationDate));
            }
        }
        return specification;
    }
}
