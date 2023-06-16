package org.solar.auth.entity.yun;


import lombok.Data;
import org.solar.auth.entity.BEntity;

import javax.persistence.Entity;

@Data
@Entity
public class YunProductSpec extends BEntity {

    String sku;
}
