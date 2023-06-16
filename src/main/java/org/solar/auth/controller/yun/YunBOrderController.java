package org.solar.auth.controller.yun;


import lombok.AllArgsConstructor;
import org.solar.auth.entity.yun.YunBOrder;
import org.solar.auth.service.yun.YunBOrderService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@AllArgsConstructor
public class YunBOrderController {

    YunBOrderService yunBOrderService;

    @PostMapping("yun/order")
    List<YunBOrder> create(@RequestBody List<YunBOrder> yunBOrders){
        return yunBOrderService.create(yunBOrders);
    }

    @PutMapping("yun/order")
    YunBOrder update(@RequestBody YunBOrder yunBOrder){
        return yunBOrderService.put(yunBOrder);
    }

    @GetMapping("yun/order")
    List<YunBOrder> retrieveAll(){
        return yunBOrderService.retrieveAll();
    }

    @GetMapping("yun/order/{id}")
    YunBOrder retrieve(@PathVariable Long id){
        return yunBOrderService.retrieve(id);
    }

    @DeleteMapping("yun/order/{id}")
    void delete(@PathVariable Long id){
        yunBOrderService.delete(id);
    }


}
