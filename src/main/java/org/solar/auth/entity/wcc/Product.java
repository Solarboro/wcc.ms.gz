package org.solar.auth.entity.wcc;

import lombok.Data;
import org.solar.auth.entity.BEntity;
import org.solar.auth.entity.IUser;

import javax.persistence.*;
import java.util.List;


@Data
@Entity
public class Product extends BEntity {

    String style;

    String model;

    String image;

    @Enumerated(EnumType.STRING)
    ProductStatus productStatus = ProductStatus.pending;

    @OneToOne
    IUser iUser;

    //  to sample stage
    @OneToMany
    @JoinColumn(name = "product_id")
    List<Material> materials;

    @OneToOne
    SampleOrder sampleOrder;

    public enum ProductStatus{
        pending,sample, factory, shipping, done;
    }


}

