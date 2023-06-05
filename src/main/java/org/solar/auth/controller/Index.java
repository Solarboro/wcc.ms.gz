package org.solar.auth.controller;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.solar.auth.entity.repo.IUserRepo;
import org.solar.auth.entity.wcc.*;
import org.solar.auth.entity.wcc.repo.ProductRepo;
import org.solar.auth.service.wcc.impl.ProductService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@AllArgsConstructor
@Log4j2
public class Index {

    IUserRepo iUserRepo;

    ProductService productService;

    ProductRepo productRepo;

    @GetMapping("product")
    public List<Product> getProducts(Authentication authentication){

        //
        for (GrantedAuthority authority : authentication.getAuthorities()) {
            if (authority.getAuthority().equals("ADMIN")) {
                return productRepo.findAll();
            }
        }

        //
        Long uid = (Long)authentication.getPrincipal();
        return productRepo.findMyAll(uid);
    }

    @PostMapping("product")
    public Product newProduct(@RequestBody Product product, Authentication authentication){
        return productService.newProduct(product, (Long) authentication.getPrincipal());
    }
    @PutMapping("product")
    public Product putProduct(@RequestBody Product product, Authentication authentication){
        Long uid = (Long)authentication.getPrincipal();

        //
        return productService.updateBasic(product, uid);
    }

    @GetMapping("product/{productId}")
    public Product getProductById(@PathVariable Long productId, Authentication authentication){
        return productRepo.findById(productId).get();
    }

    @DeleteMapping("product/{productId}")
    public void delProducts(@PathVariable Long productId, Authentication authentication){
        productService.delProduct(productId, (Long) authentication.getPrincipal());
    }

    @PutMapping("product/{productId}/studio")
    public SampleOrder updateProductStudio(@PathVariable Long productId, @RequestBody SampleStudios sampleStudios, Authentication authentication){
        return productService.updateStudio(productId, (Long) authentication.getPrincipal(), sampleStudios.getId());
    }

    @PutMapping("product/{productId}/sample")
    public SampleOrder updateProductSample(@PathVariable Long productId, @RequestBody SampleOrder sampleOrder, Authentication authentication){
        return productService.updateDetail(productId, (Long) authentication.getPrincipal(), sampleOrder);
    }


    @PostMapping("product/{productId}/payment")
    public Payment newPayment(@PathVariable Long productId, @RequestBody Payment payment, Authentication authentication){
        return productService.newPayment(productId, (Long) authentication.getPrincipal(), payment );
    }

    @PutMapping("product/{productId}/payment")
    public Payment putPayment(@PathVariable Long productId, @RequestBody Payment payment, Authentication authentication){
        return productService.updatePayment(productId, (Long) authentication.getPrincipal(), payment );
    }

    @DeleteMapping("product/{productId}/payment/{paymentId}")
    public void delPayment(@PathVariable Long productId, @PathVariable Long paymentId, Authentication authentication){
        productService.delPayment(productId, (Long) authentication.getPrincipal(), paymentId);
    }

    @PostMapping("product/{productId}/custOrder")
    public CustOrder newCustOrder(@PathVariable Long productId, @RequestBody CustOrder custOrder, Authentication authentication){
        return productService.newCustOrder(productId, (Long) authentication.getPrincipal(), custOrder);
    }

    @PutMapping("product/{productId}/custOrder")
    public CustOrder putCustOrder(@PathVariable Long productId, @RequestBody CustOrder custOrder, Authentication authentication){
        return productService.putCustOrder(productId, (Long) authentication.getPrincipal(), custOrder);
    }

    @DeleteMapping("product/{productId}/custOrder/{custOrderId}")
    public void delCustOrder(@PathVariable Long productId, @PathVariable Long custOrderId, Authentication authentication){
        productService.delCustOrder(productId, (Long) authentication.getPrincipal(), custOrderId);
    }

    @PostMapping("product/{productId}/material")
    public Material newMaterial(@PathVariable Long productId, @RequestBody Material material, Authentication authentication){
        return productService.newMaterial(productId, (Long) authentication.getPrincipal(), material);
    }

    @PutMapping("product/{productId}/material")
    public Material putMaterial(@PathVariable Long productId, @RequestBody Material material, Authentication authentication){
        return productService.putMaterial(productId, (Long) authentication.getPrincipal(), material);
    }

    @DeleteMapping("product/{productId}/material/{materialId}")
    public void delMaterial(@PathVariable Long productId, @PathVariable Long materialId, Authentication authentication){
        productService.delMaterial(productId, (Long) authentication.getPrincipal(), materialId);
    }

}
