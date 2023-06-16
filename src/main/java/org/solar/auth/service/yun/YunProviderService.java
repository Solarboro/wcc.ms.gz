package org.solar.auth.service.yun;

import org.solar.auth.entity.yun.YunProductSpec;
import org.solar.auth.entity.yun.YunProvider;

import java.util.List;

public interface YunProviderService {

//    create / update
    YunProvider create(YunProvider yunProvider);

    List<YunProvider> retrieveAll();

    void delete(Long id);

}
