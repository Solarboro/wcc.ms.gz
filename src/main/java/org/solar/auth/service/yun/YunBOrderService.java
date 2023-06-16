package org.solar.auth.service.yun;

import org.solar.auth.entity.yun.YunBOrder;

import java.util.List;

public interface YunBOrderService {

    // CURD
    List<YunBOrder> create(List<YunBOrder> yunBOrders);

    YunBOrder put(YunBOrder yunBOrder);

    List<YunBOrder> retrieveAll();

    YunBOrder retrieve(Long id);

    void delete(Long id);

    //

}
