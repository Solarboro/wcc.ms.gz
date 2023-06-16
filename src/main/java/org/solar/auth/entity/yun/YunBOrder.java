package org.solar.auth.entity.yun;

import lombok.Data;
import org.solar.auth.entity.BEntity;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Data
@Entity
public class YunBOrder extends BEntity {
    public enum Status{
        pending, subStandard, sellStore, backFactory;
    }

//    line 1
    String sku;
    String color;
    String size;
    Boolean ls;

//    line 2
    String pddOrderId;
    Boolean exchange;
    String comment;


//    Stage info
    @Enumerated(EnumType.STRING)
    Status status;

    String subStandardArea;
    Long subStandardDate;
    Long subStandardAuthor;

    Long sellStoreDate;
    Long SellStoreAuthor;

    Long backFactoryDate;
    Long backFactoryAuthor;

//    extra
    String pddCust;
    String pddCustNo;
    String pddCustAddress;
}

