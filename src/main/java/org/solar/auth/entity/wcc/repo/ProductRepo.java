package org.solar.auth.entity.wcc.repo;

import org.solar.auth.entity.wcc.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepo extends JpaRepository<Product, Long> {
}
