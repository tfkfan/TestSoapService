package com.tfkfan.domain;

import java.io.Serializable;
import java.time.Instant;
import javax.persistence.*;
import javax.validation.constraints.*;

/**
 * A Category.
 */
@Entity
@Table(name = "category")
public class Category implements Serializable {
    public static final String ENTITY_NAME = "category";
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "category_sequence")
    @SequenceGenerator(name = "category_sequence")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "code", nullable = false)
    private String code;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "parent_category_id")
    private Long parentCategoryId;

    @NotNull
    @Column(name = "is_hidden", nullable = false)
    private Boolean isHidden = false;

    @NotNull
    @Column(name = "creation_date", nullable = false)
    private Instant creationDate;

    @NotNull
    @Column(name = "modification_date", nullable = false)
    private Instant modificationDate;

    @PrePersist
    protected void onCreate() {
        creationDate = Instant.now();
        onUpdate();
    }

    @PreUpdate
    protected void onUpdate() {
        modificationDate = Instant.now();
    }

    public Long getId() {
        return this.id;
    }

    public Category id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return this.code;
    }

    public Category code(String code) {
        this.setCode(code);
        return this;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return this.name;
    }

    public Category name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return this.description;
    }

    public Category description(String description) {
        this.setDescription(description);
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getParentCategoryId() {
        return this.parentCategoryId;
    }

    public Category parentCategoryCode(Long parentCategoryCode) {
        this.setParentCategoryId(parentCategoryCode);
        return this;
    }

    public void setParentCategoryId(Long parentCategoryCode) {
        this.parentCategoryId = parentCategoryCode;
    }

    public Boolean getIsHidden() {
        return this.isHidden;
    }

    public Category is_hidden(Boolean is_hidden) {
        this.setIsHidden(is_hidden);
        return this;
    }

    public void setIsHidden(Boolean is_hidden) {
        this.isHidden = is_hidden;
    }

    public Instant getCreationDate() {
        return this.creationDate;
    }

    public Category creation_date(Instant creation_date) {
        this.setCreationDate(creation_date);
        return this;
    }

    public void setCreationDate(Instant creation_date) {
        this.creationDate = creation_date;
    }

    public Instant getModificationDate() {
        return this.modificationDate;
    }

    public Category modification_date(Instant modification_date) {
        this.setModificationDate(modification_date);
        return this;
    }

    public void setModificationDate(Instant modification_date) {
        this.modificationDate = modification_date;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Category)) {
            return false;
        }
        return id != null && id.equals(((Category) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Category{" +
            "id=" + getId() +
            ", code='" + getCode() + "'" +
            ", name='" + getName() + "'" +
            ", description='" + getDescription() + "'" +
            ", parentCategoryCode=" + getParentCategoryId() +
            ", isHidden='" + getIsHidden() + "'" +
            ", creationDate='" + getCreationDate() + "'" +
            ", modificationDate='" + getModificationDate() + "'" +
            "}";
    }
}
