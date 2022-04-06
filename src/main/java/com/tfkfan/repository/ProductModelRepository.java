package com.tfkfan.repository;

import com.tfkfan.domain.ProductModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the ProductModel entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ProductModelRepository extends JpaRepository<ProductModel, Long>, JpaSpecificationExecutor<ProductModel> {}
