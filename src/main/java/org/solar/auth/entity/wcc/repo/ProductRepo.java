package org.solar.auth.entity.wcc.repo;

import org.solar.auth.entity.wcc.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductRepo extends JpaRepository<Product, Long> {

    @Query(value = "select * from product", nativeQuery = true)
    List<Product> findAll();

    @Query(value = "from Product where agent.id = :uid")
    List<Product> findMyAll(@Param("uid") Long uid);


    // select 701 product

    @Query(value = "from Product where sampleOrder.sampleStudios.id = 1")
    List<Product> find701Products();
}
