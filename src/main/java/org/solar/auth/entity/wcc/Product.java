package org.solar.auth.entity.wcc;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.hibernate.annotations.SQLUpdate;
import org.hibernate.annotations.Where;
import org.solar.auth.entity.BEntity;
import org.solar.auth.entity.IUser;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;


@Data
@Entity
@Where(clause = "product_status <> 'done'")
public class Product extends BEntity {

    // ==== Basi Info
    String style;

    String model;

    String image;

    boolean enable = true;

    String comment;

    @Enumerated(EnumType.STRING)
    ProductStatus productStatus = ProductStatus.pending;

    @OneToOne
    @JsonIgnore
    @JoinColumn(nullable = false)
    IUser agent = new IUser();
    public Long getAgentId(){
        return agent.getId();
    }
    public String getAgentFullname(){
        return agent.getFullname();
    }

    // payment
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "product_id")
    List<Payment> payments = new ArrayList<>();

    // ==== Sample Studio
    @OneToOne(cascade = CascadeType.ALL)
    SampleOrder sampleOrder = new SampleOrder();

    // ==== customer Order
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "product_id")
    List<CustOrder> custOrders = Stream.of("xs", "s", "m", "l", "xl", "xxl").map(CustOrder::new).collect(Collectors.toList());

    // ==== product materials
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "product_id")
    List<Material> materials = new ArrayList<>();

    public enum ProductStatus{
        pending, studio, factory, done;
    }

}

