package org.solar.auth.entity.yun;

import lombok.Data;
import org.solar.auth.entity.BEntity;

import javax.persistence.Entity;

@Data
@Entity
public class YunBOrder extends BEntity {

    String platform;
    String orderNo;

    Boolean switchOrNot;

    String name;
    String mobile;
    String address;
}

