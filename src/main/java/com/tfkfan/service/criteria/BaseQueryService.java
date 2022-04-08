package com.tfkfan.service.criteria;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.transaction.annotation.Transactional;
import tech.jhipster.service.QueryService;
import tech.jhipster.service.filter.StringFilter;

import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;

/**
 * @author Baltser Artem tfkfan
 */
@Transactional(
    readOnly = true
)
public abstract class BaseQueryService<ENTITY> extends QueryService<ENTITY> {
    public <X extends Comparable<? super X>> Specification<ENTITY> buildIsNullNotNullSpecification(boolean isNull,
                                                                                                     SingularAttribute<ENTITY, X> field) {
        return (root, query, builder) -> {
            return isNull ? builder.isNull(root.get(field)) : builder.isNotNull(root.get(field));
        };
    }
}
