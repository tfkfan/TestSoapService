package com.tfkfan.repository;

import com.tfkfan.domain.Category;
import com.tfkfan.domain.CategoryProductModel;
import com.tfkfan.domain.pk.CategoryProductModelPK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the CategoryProductModel entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CategoryProductModelRepository extends JpaRepository<CategoryProductModel, CategoryProductModelPK>, JpaSpecificationExecutor<CategoryProductModel> {}
