package org.solar.auth.entity.yun;

import lombok.Data;
import org.solar.auth.entity.BEntity;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import java.util.List;

@Data
@Entity
public class YunFOrder extends BEntity {

    String comment;

    @OneToMany
    @JoinColumn(name = "yun_f_order_id")
    List<YunProduct> yunProducts;

}
