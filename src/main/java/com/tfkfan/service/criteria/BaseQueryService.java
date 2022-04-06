package com.tfkfan.service.criteria;

import com.tfkfan.domain.Category;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.transaction.annotation.Transactional;
import tech.jhipster.service.QueryService;

import javax.persistence.metamodel.SingularAttribute;

/**
 * @author Baltser Artem tfkfan
 */
@Transactional(
    readOnly = true
)
public abstract class BaseQueryService<ENTITY> extends QueryService<ENTITY> {

    public <X extends Comparable<? super X>> Specification<Category> buildIsNullNotNullSpecification(boolean isNull, SingularAttribute<Category, X> field) {
        return (root, query, builder) -> {
            return isNull ? builder.isNull(root.get(field)) : builder.isNotNull(root.get(field));
        };
    }
}
