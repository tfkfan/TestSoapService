package com.tfkfan.service.criteria;

import com.tfkfan.domain.Category_;
import com.tfkfan.domain.ProductModel;
import com.tfkfan.domain.ProductModel_;
import com.tfkfan.repository.ProductModelRepository;
import com.tfkfan.service.criteria.model.ProductModelCriteria;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service for executing complex queries for {@link ProductModel} entities in the database.
 * The main input is a {@link ProductModelCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link ProductModel} or a {@link Page} of {@link ProductModel} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ProductModelQueryService extends BaseQueryService<ProductModel> {

    private final Logger log = LoggerFactory.getLogger(ProductModelQueryService.class);

    private final ProductModelRepository productModelRepository;

    public ProductModelQueryService(ProductModelRepository productModelRepository) {
        this.productModelRepository = productModelRepository;
    }

    /**
     * Return a {@link List} of {@link ProductModel} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<ProductModel> findByCriteria(ProductModelCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<ProductModel> specification = createSpecification(criteria);
        return productModelRepository.findAll(specification);
    }


    @Transactional(readOnly = true)
    public Optional<ProductModel> findOneByCriteria(ProductModelCriteria criteria) {
        log.debug("find one by criteria : {}", criteria);
        final Specification<ProductModel> specification = createSpecification(criteria);
        return productModelRepository.findOne(specification);
    }
    /**
     * Return a {@link Page} of {@link ProductModel} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<ProductModel> findByCriteria(ProductModelCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<ProductModel> specification = createSpecification(criteria);
        return productModelRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(ProductModelCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<ProductModel> specification = createSpecification(criteria);
        return productModelRepository.count(specification);
    }

    /**
     * Function to convert {@link ProductModelCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<ProductModel> createSpecification(ProductModelCriteria criteria) {
        Specification<ProductModel> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), ProductModel_.id));
            }
            if (criteria.getCode() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCode(), ProductModel_.code));
            }
            if (criteria.getCategoryCode() != null) {
                specification = specification.and(buildReferringEntitySpecification(criteria.getCategoryCode(), ProductModel_.categories, Category_.code));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), ProductModel_.name));
            }
            if (criteria.getDescription() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDescription(), ProductModel_.description));
            }
            if (criteria.getCreationDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCreationDate(), ProductModel_.creationDate));
            }
            if (criteria.getModificationDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getModificationDate(), ProductModel_.modificationDate));
            }
        }
        return specification;
    }
}
