package com.tfkfan.service.criteria.model;

import org.springdoc.api.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * Criteria class for the {@link com.tfkfan.domain.ProductModel} entity.
 * For example the following could be a valid request:
 * {@code /categories?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
public class ProductModelCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter code;

    private StringFilter categoryCode;

    private StringFilter name;

    private StringFilter description;

    private InstantFilter creationDate;

    private InstantFilter modificationDate;

    private Boolean distinct;

    public ProductModelCriteria() {}

    public ProductModelCriteria(ProductModelCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.code = other.code == null ? null : other.code.copy();
        this.categoryCode = other.categoryCode == null ? null : other.categoryCode.copy();
        this.name = other.name == null ? null : other.name.copy();
        this.description = other.description == null ? null : other.description.copy();
        this.creationDate = other.creationDate == null ? null : other.creationDate.copy();
        this.modificationDate = other.modificationDate == null ? null : other.modificationDate.copy();
        this.distinct = other.distinct;
    }

    @Override
    public ProductModelCriteria copy() {
        return new ProductModelCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public LongFilter id() {
        if (id == null) {
            id = new LongFilter();
        }
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getCode() {
        return code;
    }

    public void setCode(StringFilter code) {
        this.code = code;
    }

    public ProductModelCriteria code(StringFilter code){
        this.code = code;
        return this;
    }

    public StringFilter getCategoryCode() {
        return categoryCode;
    }

    public void setCategoryCCode(StringFilter categoryCode) {
        this.categoryCode = categoryCode;
    }

    public ProductModelCriteria categoryCode(StringFilter categoryCode){
        this.categoryCode = categoryCode;
        return this;
    }

    public StringFilter getName() {
        return name;
    }

    public StringFilter name() {
        if (name == null) {
            name = new StringFilter();
        }
        return name;
    }

    public void setName(StringFilter name) {
        this.name = name;
    }

    public StringFilter getDescription() {
        return description;
    }

    public StringFilter description() {
        if (description == null) {
            description = new StringFilter();
        }
        return description;
    }

    public void setDescription(StringFilter description) {
        this.description = description;
    }

    public InstantFilter getCreationDate() {
        return creationDate;
    }

    public InstantFilter creation_date() {
        if (creationDate == null) {
            creationDate = new InstantFilter();
        }
        return creationDate;
    }

    public void setCreationDate(InstantFilter creationDate) {
        this.creationDate = creationDate;
    }

    public InstantFilter getModificationDate() {
        return modificationDate;
    }

    public InstantFilter modification_date() {
        if (modificationDate == null) {
            modificationDate = new InstantFilter();
        }
        return modificationDate;
    }

    public void setModificationDate(InstantFilter modificationDate) {
        this.modificationDate = modificationDate;
    }

    public Boolean getDistinct() {
        return distinct;
    }

    public void setDistinct(Boolean distinct) {
        this.distinct = distinct;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final ProductModelCriteria that = (ProductModelCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(code, that.code) &&
            Objects.equals(name, that.name) &&
            Objects.equals(description, that.description) &&
            Objects.equals(creationDate, that.creationDate) &&
            Objects.equals(modificationDate, that.modificationDate) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            code,
            name,
            description,
            creationDate,
            modificationDate,
            distinct
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CategoryCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (code != null ? "code=" + code + ", " : "") +
            (name != null ? "name=" + name + ", " : "") +
            (description != null ? "description=" + description + ", " : "") +
            (creationDate != null ? "creation_date=" + creationDate + ", " : "") +
            (modificationDate != null ? "modification_date=" + modificationDate + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
