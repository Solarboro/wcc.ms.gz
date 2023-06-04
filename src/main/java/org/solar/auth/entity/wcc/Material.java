package org.solar.auth.entity.wcc;

import lombok.Data;
import org.solar.auth.entity.BEntity;

import javax.persistence.Entity;
import javax.persistence.Transient;

@Data
@Entity
public class Material extends BEntity {

    String name;
    String label;
    String specification;
    Float price = 0f;
    Float count = 1f;
    int measurement = 0;
    boolean isFactory = false;

    // todo provider

    @Transient
    Float amount;
    public Float getAmount() {
        return price * count;
    }
}
