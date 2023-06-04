package org.solar.auth.entity.wcc.repo;

import org.solar.auth.entity.wcc.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProductRepo extends JpaRepository<Product, Long> {

    @Query(value = "select * from product", nativeQuery = true)
    List<Product> findAll();
}
