package org.solar.auth.entity.wcc;

import lombok.Data;
import org.solar.auth.entity.BEntity;

import javax.persistence.Entity;

@Data
@Entity
public class Payment extends BEntity {


    Float amount;
    Long date;
    String title;
    String comment;
    Boolean isPaid;

}
