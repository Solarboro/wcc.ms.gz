package org.solar.auth.service.yun.impl;

import lombok.AllArgsConstructor;
import org.solar.auth.entity.yun.YunBOrder;
import org.solar.auth.entity.yun.repo.YunBOrderRepo;
import org.solar.auth.service.yun.YunBOrderService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class YunBOrderServiceImpl implements YunBOrderService {

    YunBOrderRepo yunBOrderRepo;

    @Override
    public List<YunBOrder> create(List<YunBOrder> yunBOrders) {

        for (YunBOrder yunBOrder : yunBOrders) {
            yunBOrder.setId(null);
        }

        return yunBOrderRepo.saveAll(yunBOrders);
    }

    @Override
    public YunBOrder put(YunBOrder yunBOrder) {
        return yunBOrderRepo.save(yunBOrder);
    }

    @Override
    public List<YunBOrder> retrieveAll() {
        return yunBOrderRepo.findAll();
    }

    @Override
    public YunBOrder retrieve(Long id) {
        return yunBOrderRepo.findById(id).get();
    }

    @Override
    public void delete(Long id) {
        yunBOrderRepo.deleteById(id);
    }
}
