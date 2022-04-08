package com.tfkfan.domain;

import com.tfkfan.domain.pk.CategoryProductModelPK;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * A Category.
 */
@Entity
@Table(name = "category_model")
public class CategoryProductModel implements Serializable {
    public static final String ENTITY_NAME = "category_model";
    private static final long serialVersionUID = 1L;

    @EmbeddedId
    private CategoryProductModelPK id;

    @ManyToOne
    @MapsId("categoryId")
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    @ManyToOne
    @MapsId("modelId")
    @JoinColumn(name = "model_id", nullable = false)
    private ProductModel model;


    public CategoryProductModel() {
    }

    public CategoryProductModel(CategoryProductModelPK id) {
        this.id = id;
    }

    public CategoryProductModel(Category category, ProductModel model) {
        if (Objects.nonNull(category) && Objects.nonNull(model))
            this.id = new CategoryProductModelPK(category.getId(), model.getId());
        this.category = category;
        this.model = model;
    }

    public CategoryProductModelPK getId() {
        return id;
    }

    public void setId(CategoryProductModelPK id) {
        this.id = id;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public ProductModel getModel() {
        return model;
    }

    public void setModel(ProductModel model) {
        this.model = model;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        CategoryProductModel that = (CategoryProductModel) o;

        return new EqualsBuilder().append(id, that.id).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37).append(id).toHashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CategoryProductModel{" +
            "id=" + getId() +
            "}";
    }
}
