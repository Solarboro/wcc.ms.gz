package org.solar.auth.service.yun.impl;

import lombok.AllArgsConstructor;
import org.solar.auth.entity.yun.YunProvider;
import org.solar.auth.entity.yun.repo.YunProviderRepo;
import org.solar.auth.service.yun.YunProviderService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class YunProviderServiceImpl implements YunProviderService {

    YunProviderRepo yunProviderRepo;

    @Override
    public YunProvider create(YunProvider yunProvider) {
        return yunProviderRepo.save(yunProvider);
    }

    @Override
    public List<YunProvider> retrieveAll() {
        return yunProviderRepo.findAll();
    }

    @Override
    public void delete(Long id) {
        yunProviderRepo.deleteById(id);
    }
}
