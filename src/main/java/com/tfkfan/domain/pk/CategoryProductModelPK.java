package com.tfkfan.domain.pk;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

/**
 * @author Baltser Artem tfkfan
 */
@Embeddable
public class CategoryProductModelPK implements Serializable {
    @Column(name = "category_id", nullable = false)
    private Long categoryId;
    @Column(name = "model_id", nullable = false)
    private Long modelId;

    public CategoryProductModelPK() {
    }

    public CategoryProductModelPK(Long categoryId, Long modelId) {
        this.categoryId = categoryId;
        this.modelId = modelId;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public Long getModelId() {
        return modelId;
    }

    public void setModelId(Long modelId) {
        this.modelId = modelId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        CategoryProductModelPK that = (CategoryProductModelPK) o;

        return new EqualsBuilder().append(categoryId, that.categoryId).append(modelId, that.modelId).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37).append(categoryId).append(modelId).toHashCode();
    }
}
