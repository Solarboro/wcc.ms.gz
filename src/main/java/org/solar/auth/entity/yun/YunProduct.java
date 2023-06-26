package org.solar.auth.entity.yun;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.solar.auth.entity.BEntity;
import org.solar.auth.entity.IUser;

import javax.persistence.*;

@Data
@Entity
public class YunProduct extends BEntity {
    public enum Status{
        pending, store, subStore, toFactory;
    }

//    line 1
    String sku;
    String color;
    String size;
    Integer count;
    Boolean ls;
    Boolean switchOrNot;

    String comment;

    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    YunBOrder yunBOrder;
    @Column(name = "yun_f_order_id")
    Long yunFOderId;

//    Stage info
    @Enumerated(EnumType.STRING)
    Status status = Status.pending;
    @Enumerated(EnumType.STRING)
    Status lastStatus;

    @OneToOne
    @JsonIgnore
    @JoinColumn(name = "pull_by_id")
    IUser pullBy;
    public String getPullOprt(){
        return pullBy == null ? "" : pullBy.getFullname();
    }

    @OneToOne
    @JsonIgnore
    IUser toStoreOprtUser;

    Long toStoreDate;
    public String getToStoreOprt(){
        return toStoreOprtUser == null ? "" : toStoreOprtUser.getFullname();
    }

    @OneToOne
    @JsonIgnore
    IUser toSubStoreUser;
    String toSubStoreArea;
    Long toSubStoreDate;
    public String getToSubStoreOprt(){
        return toSubStoreUser == null ? "" : toSubStoreUser.getFullname();
    }

    @OneToOne
    @JsonIgnore
    IUser toFactoryUser;
    Long toFactoryDate;
    public String getToFactoryOprt(){
        return toFactoryUser == null ? "" : toFactoryUser.getFullname();
    }
}

