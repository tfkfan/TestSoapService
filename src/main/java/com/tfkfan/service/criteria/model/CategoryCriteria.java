package com.tfkfan.service.criteria.model;

import java.io.Serializable;
import java.util.Objects;

import org.springdoc.api.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.BooleanFilter;
import tech.jhipster.service.filter.Filter;
import tech.jhipster.service.filter.InstantFilter;
import tech.jhipster.service.filter.LongFilter;
import tech.jhipster.service.filter.StringFilter;

/**
 * Criteria class for the {@link com.tfkfan.domain.Category} entity.
 * For example the following could be a valid request:
 * {@code /categories?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
public class CategoryCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter code;

    private StringFilter name;

    private StringFilter description;

    private LongFilter parentCategoryId;

    private Boolean parentCategoryIdNull;

    private BooleanFilter isHidden;

    private InstantFilter creationDate;

    private InstantFilter modificationDate;

    private Boolean distinct;

    public CategoryCriteria() {
    }

    public CategoryCriteria(CategoryCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.code = other.code == null ? null : other.code.copy();
        this.name = other.name == null ? null : other.name.copy();
        this.description = other.description == null ? null : other.description.copy();
        this.parentCategoryId = other.parentCategoryId == null ? null : other.parentCategoryId.copy();
        this.parentCategoryIdNull = other.parentCategoryIdNull;
        this.isHidden = other.isHidden == null ? null : other.isHidden.copy();
        this.creationDate = other.creationDate == null ? null : other.creationDate.copy();
        this.modificationDate = other.modificationDate == null ? null : other.modificationDate.copy();
        this.distinct = other.distinct;
    }

    @Override
    public CategoryCriteria copy() {
        return new CategoryCriteria(this);
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

    public CategoryCriteria code(StringFilter code) {
        this.code = code;
        return this;
    }

    public void setCode(StringFilter code) {
        this.code = code;
    }

    public StringFilter getName() {
        return name;
    }

    public CategoryCriteria name(StringFilter name) {
        this.name = name;
        return this;
    }

    public void setName(StringFilter name) {
        this.name = name;
    }

    public StringFilter getDescription() {
        return description;
    }

    public CategoryCriteria description(StringFilter description) {
        this.description = description;
        return this;
    }

    public void setDescription(StringFilter description) {
        this.description = description;
    }

    public LongFilter getParentCategoryId() {
        return parentCategoryId;
    }

    public CategoryCriteria parentCategoryId(LongFilter parentCategoryId) {
        this.parentCategoryId = parentCategoryId;
        return this;
    }

    public void setParentCategoryId(LongFilter parentCategoryId) {
        this.parentCategoryId = parentCategoryId;
    }

    public Boolean getParentCategoryIdNull() {
        return parentCategoryIdNull;
    }

    public void setParentCategoryIdNull(Boolean parentCategoryIdNull) {
        this.parentCategoryIdNull = parentCategoryIdNull;
    }

    public CategoryCriteria parentCategoryIdNull(Boolean parentCategoryIdNull) {
        this.parentCategoryIdNull = parentCategoryIdNull;
        return this;
    }

    public BooleanFilter getIsHidden() {
        return isHidden;
    }

    public CategoryCriteria isHidden(BooleanFilter isHidden) {
        this.isHidden = isHidden;
        return this;
    }

    public void setIsHidden(BooleanFilter isHidden) {
        this.isHidden = isHidden;
    }

    public InstantFilter getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(InstantFilter creationDate) {
        this.creationDate = creationDate;
    }

    public InstantFilter getModificationDate() {
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
        final CategoryCriteria that = (CategoryCriteria) o;
        return (
            Objects.equals(id, that.id) &&
                Objects.equals(code, that.code) &&
                Objects.equals(name, that.name) &&
                Objects.equals(description, that.description) &&
                Objects.equals(parentCategoryId, that.parentCategoryId) &&
                Objects.equals(isHidden, that.isHidden) &&
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
            parentCategoryId,
            isHidden,
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
            (parentCategoryId != null ? "parentCategoryCode=" + parentCategoryId + ", " : "") +
            (isHidden != null ? "is_hidden=" + isHidden + ", " : "") +
            (creationDate != null ? "creation_date=" + creationDate + ", " : "") +
            (modificationDate != null ? "modification_date=" + modificationDate + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
