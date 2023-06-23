package org.solar.auth.controller.yun;


import lombok.AllArgsConstructor;
import org.solar.auth.entity.yun.YunProvider;
import org.solar.auth.service.yun.YunProviderService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@AllArgsConstructor
public class YunProviderController {


    YunProviderService yunProviderService;

    @PostMapping("yun/provider")
    YunProvider create(@RequestBody YunProvider yunProvider){
        return yunProviderService.create(yunProvider);
    }

    @PutMapping("yun/provider")
    YunProvider put(@RequestBody YunProvider yunProvider){
        return yunProviderService.create(yunProvider);
    }

    @GetMapping("yun/provider")
    List<YunProvider> retrieveAll(){
        return yunProviderService.retrieveAll();
    }

    @DeleteMapping("yun/provider/{id}")
    void delete(@PathVariable Long id){
        yunProviderService.delete(id);
    }
}
