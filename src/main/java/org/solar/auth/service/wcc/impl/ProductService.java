package org.solar.auth.service.wcc.impl;

import org.solar.auth.entity.wcc.Product;

public interface ProductService {

    Product newProduct(Product product, String username);
}
