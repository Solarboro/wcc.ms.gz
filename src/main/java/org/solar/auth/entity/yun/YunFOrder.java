package org.solar.auth.entity.yun;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.solar.auth.entity.BEntity;
import org.solar.auth.entity.IUser;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import java.util.List;

@Data
@Entity
public class YunFOrder extends BEntity {

    String comment;

    @OneToMany
    @JoinColumn(name = "yun_f_order_id")
    List<YunProduct> yunProducts;


    @OneToOne
    @JsonIgnore
    IUser toFactoryUser;
    Long toFactoryDate;
    public String getToFactoryOprt(){
        return toFactoryUser == null ? "" : toFactoryUser.getFullname();
    }

}
