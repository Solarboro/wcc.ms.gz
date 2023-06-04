package org.solar.auth.entity.wcc;

import lombok.Data;
import org.solar.auth.entity.BEntity;

import javax.persistence.Entity;

@Data
@Entity
public class Factory extends BEntity {

    String name;
    String mobile;

}
