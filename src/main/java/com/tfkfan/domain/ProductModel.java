package com.tfkfan.domain;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

/**
 * A Category.
 */
@Entity
@Table(name = "product_model")
public class ProductModel implements Serializable {
    public static final String ENTITY_NAME = "model";
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "product_model_sequence")
    @SequenceGenerator(name = "product_model_sequence", initialValue = 50, allocationSize = 1)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "code", nullable = false, unique = true)
    private String code;

    @NotNull
    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @Column(name = "description")
    private String description;

    @NotNull
    @Column(name = "creation_date", nullable = false)
    private Instant creationDate;

    @NotNull
    @Column(name = "modification_date", nullable = false)
    private Instant modificationDate;

    @ManyToMany(mappedBy = "models")
    private Set<Category> categories = new HashSet<>();

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

    public ProductModel id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return this.code;
    }

    public ProductModel code(String code) {
        this.setCode(code);
        return this;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return this.name;
    }

    public ProductModel name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return this.description;
    }

    public ProductModel description(String description) {
        this.setDescription(description);
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Instant getCreationDate() {
        return this.creationDate;
    }

    public ProductModel creation_date(Instant creation_date) {
        this.setCreationDate(creation_date);
        return this;
    }

    public void setCreationDate(Instant creation_date) {
        this.creationDate = creation_date;
    }

    public Instant getModificationDate() {
        return this.modificationDate;
    }

    public ProductModel modification_date(Instant modification_date) {
        this.setModificationDate(modification_date);
        return this;
    }

    public void setModificationDate(Instant modification_date) {
        this.modificationDate = modification_date;
    }

    public Set<Category> getCategories() {
        return categories;
    }

    public void setCategories(Set<Category> categories) {
        this.categories = categories;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        ProductModel that = (ProductModel) o;

        return new EqualsBuilder().append(id, that.id).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37).append(id).toHashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ProductModel{" +
            "id=" + getId() +
            ", code='" + getCode() + "'" +
            ", name='" + getName() + "'" +
            ", description='" + getDescription() + "'" +
            ", creationDate='" + getCreationDate() + "'" +
            ", modificationDate='" + getModificationDate() + "'" +
            "}";
    }
}
