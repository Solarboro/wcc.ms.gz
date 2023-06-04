package org.solar.auth.service.wcc.impl;

import lombok.AllArgsConstructor;
import org.solar.auth.entity.IUser;
import org.solar.auth.entity.wcc.*;
import org.solar.auth.entity.wcc.repo.ProductRepo;
import org.solar.auth.exception.ErrorCode;
import org.solar.auth.exception.GenericException;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class ProductServiceImpl implements ProductService{


    ProductRepo productRepo;

    @Override
    public Product newProduct(Product product, Long uid) {

        Product raw = new Product();
        Optional.ofNullable(product.getStyle()).ifPresent(value -> raw.setStyle(value));
        Optional.ofNullable(product.getModel()).ifPresent(value -> raw.setModel(value));
        Optional.ofNullable(product.getImage()).ifPresent(value -> raw.setImage(value));
        //
        IUser iUser = new IUser();
        iUser.setId(uid);

        //
        raw.setAgent(iUser);

        return productRepo.saveAndFlush(raw);
    }

    @Override
    public void delProduct(Long productId, Long uid) {
        //
        Product product = productOwnerValidate(productId, uid);

        if (!product.getProductStatus().equals(Product.ProductStatus.pending))
            throw new GenericException(ErrorCode.PRODUCT_DELFAIL);

        productRepo.delete(product);

    }

    @Override
    public Product updateBasic(Product product, Long uid) {
        return null;
    }

    @Override
    public SampleOrder updateStudio(Long productId, Long uid, Long studioId) {
        //
        Product product = productOwnerValidate(productId, uid);

        //
        if (product.getProductStatus() == Product.ProductStatus.pending)
            product.setProductStatus(Product.ProductStatus.studio);

            //
        SampleOrder sampleOrder = product.getSampleOrder();
        SampleStudios sampleStudios = new SampleStudios();
        sampleStudios.setId(studioId);
        sampleOrder.setSampleStudios(sampleStudios);
        sampleOrder.setManufactureDates(System.currentTimeMillis());

        productRepo.flush();
        return sampleOrder;
    }

    @Override
    public SampleOrder updateDetail(Long productId, Long uid, SampleOrder sampleOrder) {
        //
        Product product = productOwnerValidate(productId, uid);

        //
        if (product.getProductStatus() != Product.ProductStatus.studio)
            throw new GenericException(ErrorCode.C_00_002);

        //
        SampleOrder rawSampleOrder = product.getSampleOrder();
        Optional.ofNullable(sampleOrder.getManufactureDate()).ifPresent(value -> rawSampleOrder.setManufactureDate(value));
        Optional.ofNullable(sampleOrder.getCost()).ifPresent(value -> rawSampleOrder.setCost(value));
        Optional.ofNullable(sampleOrder.getComment()).ifPresent(value -> rawSampleOrder.setComment(value));

        productRepo.flush();
        return rawSampleOrder;
    }

    @Override
    public Payment newPayment(Long productId, Long uid, Payment payment) {
        //
        Product product = productOwnerValidate(productId, uid);

        //
        Payment rawPayment = new Payment();
        BeanUtils.copyProperties(payment, rawPayment);
        rawPayment.setId(null);

        //
        product.getPayments().add(rawPayment);
        productRepo.flush();

        //
        return rawPayment;
    }

    @Override
    public Payment updatePayment(Long productId, Long uid, Payment payment) {
        //
        Product product = productOwnerValidate(productId, uid);

        // success
        for (Payment productPayment : product.getPayments()) {
            if (productPayment.getId().equals(payment.getId())) {
                BeanUtils.copyProperties(payment, productPayment);
                productRepo.flush();
                return productPayment;
            }
        }

        // fail
        return null;
    }

    @Override
    public void delPayment(Long productId, Long uid, Long paymentId) {
        //
        Product product = productOwnerValidate(productId, uid);

        product.getPayments().removeIf(payment -> payment.getId().equals(paymentId));

        productRepo.flush();
    }

    @Override
    public CustOrder newCustOrder(Long productId, Long uid, CustOrder custOrder) {
        //
        Product product = productOwnerValidate(productId, uid);

        CustOrder raw = new CustOrder();
        BeanUtils.copyProperties(custOrder, raw);
        raw.setId(null);

        product.getCustOrders().add(raw);
        productRepo.flush();

        return raw;
    }

    @Override
    public CustOrder putCustOrder(Long productId, Long uid, CustOrder custOrder) {
        //
        Product product = productOwnerValidate(productId, uid);

        // success
        for (CustOrder order : product.getCustOrders()) {
            if (order.getId().equals(custOrder.getId())) {
                BeanUtils.copyProperties(custOrder, order);
                productRepo.flush();
                return order;
            }
        }

        // fail
        return null;
    }

    @Override
    public void delCustOrder(Long productId, Long uid, Long custOrderId) {
        //
        Product product = productOwnerValidate(productId, uid);

        product.getCustOrders().removeIf(custOrder -> custOrder.getId().equals(custOrderId));

        //
        productRepo.flush();

    }

    @Override
    public Material newMaterial(Long productId, Long uid, Material material) {
        //
        Product product = productOwnerValidate(productId, uid);

        //
        Material raw = new Material();
        BeanUtils.copyProperties(material, raw);
        raw.setId(null);
        if(product.getProductStatus() == Product.ProductStatus.studio && raw.isFactory())
            product.setProductStatus(Product.ProductStatus.factory);

        //
        product.getMaterials().add(raw);
        productRepo.flush();
        return raw;
    }

    @Override
    public Material putMaterial(Long productId, Long uid, Material material) {
        //
        Product product = productOwnerValidate(productId, uid);

        //
        for (Material productMaterial : product.getMaterials()) {
            if (productMaterial.getId().equals(material.getId())) {
                BeanUtils.copyProperties(material, productMaterial);
                if(product.getProductStatus() == Product.ProductStatus.studio && productMaterial.isFactory())
                    product.setProductStatus(Product.ProductStatus.factory);
                productRepo.flush();
                return productMaterial;
            }
        }

        //
        return null;
    }

    @Override
    public void delMaterial(Long productId, Long uid, Long materialId) {
        //
        Product product = productOwnerValidate(productId, uid);

        product.getMaterials().removeIf(material -> material.getId().equals(materialId));

        productRepo.flush();

    }

    Product productOwnerValidate(Long productId, Long uid){

        Product product = productRepo.findById(productId).orElseThrow(() -> new GenericException(ErrorCode.PRODUCT_NOTFOUND));

        //
        if (product.getAgentId().equals(uid))
            return product;

        //
        throw new GenericException(ErrorCode.PRODUCT_MISMATCH);
    }
}
