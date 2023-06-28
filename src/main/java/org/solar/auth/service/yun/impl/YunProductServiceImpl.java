package org.solar.auth.service.yun.impl;

import lombok.AllArgsConstructor;
import org.solar.auth.entity.repo.IUserRepo;
import org.solar.auth.entity.yun.YunBOrder;
import org.solar.auth.entity.yun.YunFOrder;
import org.solar.auth.entity.yun.YunProduct;
import org.solar.auth.entity.yun.YunSummary;
import org.solar.auth.entity.yun.repo.YunBOrderRepo;
import org.solar.auth.entity.yun.repo.YunFOrderRepo;
import org.solar.auth.entity.yun.repo.YunProductRepo;
import org.solar.auth.entity.yun.repo.YunSummaryRepo;
import org.solar.auth.exception.ErrorCode;
import org.solar.auth.exception.GenericException;
import org.solar.auth.service.yun.YunProductService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
@AllArgsConstructor
public class YunProductServiceImpl implements YunProductService {

    YunProductRepo yunProductRepo;

    YunBOrderRepo yunBOrderRepo;

    YunFOrderRepo yunFOrderRepo;

    IUserRepo userRepo;

    YunSummaryRepo yunSummaryRepo;

    @Override
    public YunProduct create(YunProduct yunProduct, Long id) {
        yunProduct.setId(null);
        yunProduct.setPullBy(userRepo.findById(id).get());
        //
        YunBOrder yunBOrder = new YunBOrder();
        if(yunProduct.getYunBOrder() != null){
            yunBOrder = Optional.ofNullable(yunBOrderRepo.findByOrderNo(yunProduct.getYunBOrder().getOrderNo())).orElse(new YunBOrder());
            BeanUtils.copyProperties(yunProduct.getYunBOrder(), yunBOrder, new String[]{"id"});

        }

        yunProduct.setYunBOrder(yunBOrder);
        yunProductRepo.saveAndFlush(yunProduct);
        return yunProduct;
    }

    @Override
    public List<YunProduct> create(List<YunProduct> yunProducts, Long id) {

        return yunProducts.stream().map(value -> create(value, id)).collect(Collectors.toList());
    }

    @Override
    public YunProduct update(YunProduct yunProduct) {

        YunProduct raw = yunProductRepo.findById(yunProduct.getId()).get();
        
        BeanUtils.copyProperties(yunProduct, raw, new String[]{"pullBy","toStoreOprtUser","toSubStoreUser","toFactoryUser"});

        return yunProductRepo.saveAndFlush(raw);
    }

    @Override
    public List<YunProduct> retrieveAll() {
        return yunProductRepo.findAll();
    }

    @Override
    public YunProduct retrieve(Long id) {
        return yunProductRepo.findById(id).get();
    }

    @Override
    public void delete(Long id) {
        YunProduct yunProduct = yunProductRepo.findById(id).get();
        if(yunProduct.getStatus() != YunProduct.Status.pending)
            throw new GenericException(ErrorCode.PRODUCT_STATUS_INVALID);

        yunProductRepo.deleteById(id);
    }

    @Override
    public YunProduct toPending(Long id, Long uid) {
        YunProduct yunProduct = yunProductRepo.findById(id).get();

        if(yunProduct.getStatus() != YunProduct.Status.subStore)
            throw new GenericException(ErrorCode.PRODUCT_STATUS_INVALID);

        yunProduct.setStatus(YunProduct.Status.pending);
        return yunProductRepo.saveAndFlush(yunProduct);
    }

    @Override
    public YunProduct toStore(Long id, Long uid) {
        YunProduct yunProduct = yunProductRepo.findById(id).get();

        yunProduct.setStatus(YunProduct.Status.store);
        yunProduct.setToStoreDate(System.currentTimeMillis());
        yunProduct.setToStoreOprtUser(userRepo.findById(id).get());
        return yunProductRepo.saveAndFlush(yunProduct);
    }

    @Override
    public YunProduct toSubStore(Long id, Long uid) {

        YunProduct yunProduct = yunProductRepo.findById(id).get();

        yunProduct.setStatus(YunProduct.Status.subStore);
        yunProduct.setToSubStoreDate(System.currentTimeMillis());
        yunProduct.setToSubStoreUser(userRepo.findById(uid).get());
        return yunProductRepo.saveAndFlush(yunProduct);
    }

    @Override
    public YunProduct rollback(Long id) {
        YunProduct yunProduct = yunProductRepo.findById(id).get();
        return yunProductRepo.saveAndFlush(yunProduct);
    }

    @Override
    public List<YunFOrder> retrieveAllFOrder() {
        return yunFOrderRepo.findAll();
    }

    @Override
    public YunFOrder toFactory(List<Long> id, Long uid) {

        List<YunProduct> yunProducts = yunProductRepo.findAllById(id);

        long ts = System.currentTimeMillis();

        //
        for (YunProduct yunProduct : yunProducts) {
            yunProduct.setStatus(YunProduct.Status.toFactory);
            yunProduct.setToFactoryDate(ts);
            yunProduct.setToFactoryUser(userRepo.findById(uid).get());
        }

        //
        YunFOrder yunFOrder = new YunFOrder();
        yunFOrder.setYunProducts(yunProducts);
        yunFOrder.setToFactoryDate(ts);
        yunFOrder.setToFactoryUser(userRepo.findById(uid).get());

        return yunFOrderRepo.save(yunFOrder);
    }

    @Override
    public YunFOrder updateFOrder(YunFOrder yunFOrder, Long uid) {
        YunFOrder raw = yunFOrderRepo.findById(yunFOrder.getId()).get();

        // update comment
        Optional.ofNullable(yunFOrder.getComment()).ifPresent(v -> raw.setComment(v));

        return yunFOrderRepo.saveAndFlush(raw);

    }

    @Override
    public List<YunProduct> rollbackFromFactory(Long factoryId) {
        YunFOrder yunFOrder = yunFOrderRepo.findById(factoryId).get();
        yunFOrder.getYunProducts().forEach(
                v -> {
                    v.setStatus(YunProduct.Status.subStore);
                    v.setToFactoryDate(null);
                    v.setToFactoryUser(null);
                }
        );
        yunFOrderRepo.save(yunFOrder);

        // TODO: 2023/6/19 check the result on direct remove record. how 's value in products
        yunFOrderRepo.deleteById(factoryId);
        return null;
    }

    @Override
    public List<YunSummary> getAllYunSummary() {
        return yunSummaryRepo.getALl();
    }
}
