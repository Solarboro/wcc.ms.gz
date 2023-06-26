package org.solar.auth.entity.yun;

import lombok.Data;
import org.solar.auth.entity.BEntity;

import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.Table;

@Data
@Entity
@Table(name="yunborder", indexes = {
        @Index(name="idx_u_orderNo", columnList = "orderNo", unique = true)
})
public class YunBOrder extends BEntity {

    String platform;
    String orderNo;

    Boolean switchOrNot;

    String name;
    String mobile;
    String address;
}

