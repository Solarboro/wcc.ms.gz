package org.solar.auth.service.yun;

import org.solar.auth.entity.yun.YunFOrder;
import org.solar.auth.entity.yun.YunProduct;

import java.util.List;

public interface YunProductService {


    YunProduct create(YunProduct yunProduct, Long id);
    List<YunProduct> create(List<YunProduct> yunProducts, Long id);

    YunProduct update(YunProduct yunProduct);

    List<YunProduct> retrieveAll();

    YunProduct retrieve(Long id);

    void delete(Long id);

    // status change
    YunProduct toStore(Long id, Long uid);
    YunProduct toSubStore(Long id, Long uid);
    YunProduct rollback(Long id);


    List<YunFOrder> retrieveAllFOrder();
    YunFOrder toFactory(List<Long> id, Long uid);
    List<YunProduct> rollbackFromFactory(Long factoryId);

}
