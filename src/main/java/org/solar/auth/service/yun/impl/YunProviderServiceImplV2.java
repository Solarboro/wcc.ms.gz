package org.solar.auth.service.yun.impl;


import lombok.AllArgsConstructor;
import org.solar.auth.entity.yun.repo.YunProviderRepo;
import org.springframework.stereotype.Service;

@Service
public class YunProviderServiceImplV2 extends YunProviderServiceImpl{

    public YunProviderServiceImplV2(YunProviderRepo yunProviderRepo) {
        super(yunProviderRepo);
    }
}
