package org.solar.auth.entity.wcc;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.solar.auth.entity.BEntity;

import javax.persistence.Entity;
import javax.persistence.Transient;


@Data
@Entity
@NoArgsConstructor
public class CustOrder extends BEntity
{
    String size; // xs s m l xl xxl

    int s1;
    int s2;
    int s3;
    int s4;
    int s5;
    int s6;

    short preCount = 0;

    short tolerance = 0;

    int price = 0;

    @Transient
    short actCount = 0;

    @Transient
    int amount = 0;

    public short getActCount() {
        return (short) (preCount + tolerance);
    }

    public int getAmount() {
        return getActCount() * price ;
    }

    public CustOrder(String size) {
        this.size = size;
    }
}
