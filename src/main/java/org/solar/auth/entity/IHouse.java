package org.solar.auth.entity;


import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.MappedSuperclass;
import javax.persistence.Table;


@Data
@Entity
public class IHouse extends BEntity{

    String type;
    String model;
}
