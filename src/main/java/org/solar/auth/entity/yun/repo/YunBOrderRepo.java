package org.solar.auth.entity.yun.repo;

import org.solar.auth.entity.yun.YunBOrder;
import org.springframework.data.jpa.repository.JpaRepository;

public interface YunBOrderRepo extends JpaRepository<YunBOrder, Long> {


    YunBOrder findByOrderNo(String orderNo);
}
