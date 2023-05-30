package org.solar.auth.service.wcc.impl;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.solar.auth.entity.IUser;
import org.solar.auth.entity.wcc.Product;
import org.solar.auth.entity.wcc.repo.ProductRepo;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ProductServiceImpl implements ProductService{


    ProductRepo productRepo;

    @Override
    public Product newProduct(Product product, String username) {

        IUser iUser = new IUser();
//        iUser.setUsername(username);
        iUser.setId(1L);
        product.setIUser(iUser);
        return productRepo.saveAndFlush(product);
    }
}
