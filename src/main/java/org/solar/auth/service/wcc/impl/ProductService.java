package org.solar.auth.service.wcc.impl;

import org.solar.auth.entity.wcc.*;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;

import java.util.List;

public interface ProductService {

    // product
    @Cacheable(cacheNames = "oProducts", key = "#uid")
    List<Product> myProducts(Long uid);
    List<Product> allProducts();
    List<Product> all701Products();
    @CacheEvict(cacheNames = "oProducts", key = "#uid")
    Product newProduct(Product product, Long uid);
    @CacheEvict(cacheNames = "oProducts", key = "#uid")
    void delProduct(Long productId, Long uid);



    // product status
    @CacheEvict(cacheNames = "oProducts", key = "#uid")
    Product fallbackProductStatus(Long productId, Long uid);

    // retrieve

    // update
    @CacheEvict(cacheNames = "oProducts", key = "#uid")
    Product updateBasic(Product product, Long uid);

    // sample
    @CacheEvict(cacheNames = "oProducts", key = "#uid")
    SampleOrder updateStudio(Long productId, Long uid, Long studioId);

    // todo
    @CacheEvict(cacheNames = "oProducts", allEntries = true)
    SampleOrder updateDetail(Long productId, Long uid, SampleOrder sampleOrder);

    // payment
    @CacheEvict(cacheNames = "oProducts", key = "#uid")
    Payment newPayment(Long productId, Long uid, Payment payment);
    @CacheEvict(cacheNames = "oProducts", key = "#uid")
    Payment updatePayment(Long productId, Long uid, Payment payment);
    @CacheEvict(cacheNames = "oProducts", key = "#uid")
    void delPayment(Long productId, Long uid, Long paymentId);

    // cust order
    @CacheEvict(cacheNames = "oProducts", key = "#uid")
    CustOrder newCustOrder(Long productId, Long uid, CustOrder custOrder);
    @CacheEvict(cacheNames = "oProducts", key = "#uid")
    CustOrder putCustOrder(Long productId, Long uid, CustOrder custOrder);
    @CacheEvict(cacheNames = "oProducts", key = "#uid")
    void delCustOrder(Long productId, Long uid, Long custOrderId);

    // Material
    @CacheEvict(cacheNames = "oProducts", key = "#uid")
    Material newMaterial(Long productId, Long uid, Material material);
    @CacheEvict(cacheNames = "oProducts", key = "#uid")
    Material putMaterial(Long productId, Long uid, Material material);
    @CacheEvict(cacheNames = "oProducts", key = "#uid")
    void delMaterial(Long productId, Long uid, Long materialId);

    // Statistic Studio
    List<StatisticStudio> getAllStatisticStudio();

}
