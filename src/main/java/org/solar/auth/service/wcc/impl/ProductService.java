package org.solar.auth.service.wcc.impl;

import org.solar.auth.entity.wcc.*;

public interface ProductService {

    Product newProduct(Product product, Long uid);
    void delProduct(Long productId, Long uid);

    // retrieve

    // update
    Product updateBasic(Product product, Long uid);

    // sample
    SampleOrder updateStudio(Long productId, Long uid, Long studioId);
    SampleOrder updateDetail(Long productId, Long uid, SampleOrder sampleOrder);

    // payment
    Payment newPayment(Long productId, Long uid, Payment payment);
    Payment updatePayment(Long productId, Long uid, Payment payment);
    void delPayment(Long productId, Long uid, Long paymentId);

    // cust order
    CustOrder newCustOrder(Long productId, Long uid, CustOrder custOrder);
    CustOrder putCustOrder(Long productId, Long uid, CustOrder custOrder);
    void delCustOrder(Long productId, Long uid, Long custOrderId);



    // Material
    Material newMaterial(Long productId, Long uid, Material material);
    Material putMaterial(Long productId, Long uid, Material material);
    void delMaterial(Long productId, Long uid, Long materialId);

    // delete

}
