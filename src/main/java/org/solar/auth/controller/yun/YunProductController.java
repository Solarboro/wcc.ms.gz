package org.solar.auth.controller.yun;


import lombok.AllArgsConstructor;
import org.solar.auth.entity.yun.YunFOrder;
import org.solar.auth.entity.yun.YunProduct;
import org.solar.auth.service.yun.YunProductService;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@CrossOrigin
public class YunProductController {


    YunProductService yunProductService;

    @PostMapping("yun/product/")
    public YunProduct create(@RequestBody YunProduct yunProduct, Authentication authentication){

        return yunProductService.create(yunProduct, (Long) authentication.getPrincipal());
    }

    @PostMapping("yun/product")
    public List<YunProduct>  create(@RequestBody List<YunProduct> yunProducts, Authentication authentication){

        return yunProductService.create(yunProducts, (Long) authentication.getPrincipal());
    }

    @PostMapping("yun/product/{id}")
    public YunProduct update(@RequestBody YunProduct yunProduct, @PathVariable Long id){
        return yunProductService.update(yunProduct);
    }

    @GetMapping("yun/product")
    public List<YunProduct> retrieveAll(){
        return yunProductService.retrieveAll();
    }

    @GetMapping("yun/product/{id}")
    public YunProduct retrieve(@PathVariable Long id){
        return yunProductService.retrieve(id);
    }
    @DeleteMapping("yun/product/{id}")
    public void delete(@PathVariable Long id){
        yunProductService.delete(id);
    }

    @GetMapping("yun/product/{id}/toPending")
    public YunProduct toPending(@PathVariable Long id, Authentication authentication) {
        return yunProductService.toPending(id, (Long) authentication.getPrincipal());
    }


    @GetMapping("yun/product/{id}/toStore")
    public YunProduct toStore(@PathVariable Long id, Authentication authentication) {
        return yunProductService.toStore(id, (Long) authentication.getPrincipal());
    }

    @GetMapping("yun/product/{id}/toSubStore")
    public YunProduct toSubStore(@PathVariable Long id, Authentication authentication) {
        return yunProductService.toSubStore(id, (Long) authentication.getPrincipal());
    }

    @PostMapping("yun/product/{id}/toFactory")
    public YunFOrder toFactory(@RequestBody List<Long> ids, Authentication authentication) {
        return yunProductService.toFactory(ids, (Long) authentication.getPrincipal());
    }

    @GetMapping("yun/product/{id}/rollback")
    public YunProduct rollback(@PathVariable Long id) {
        return yunProductService.rollback(id);
    }

    @GetMapping("yun/factory/{factoryId}")
    public List<YunProduct> rollbackFromFactory(@PathVariable Long factoryId){
        return yunProductService.rollbackFromFactory(factoryId);
    }

    @GetMapping("yun/factory")
    public List<YunFOrder> retrieveAllFOrder(){
        return yunProductService.retrieveAllFOrder();
    }

}
