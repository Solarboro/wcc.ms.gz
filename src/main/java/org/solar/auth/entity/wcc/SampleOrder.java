package org.solar.auth.entity.wcc;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import org.solar.auth.entity.BEntity;
import org.solar.auth.entity.IUser;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

@Data
@Entity
public class SampleOrder extends BEntity {

    @OneToOne
    @JsonIgnoreProperties({"iUsers"})
    SampleStudios sampleStudios;

    @OneToOne
    IUser iUser;

    long manufactureDate = 0;

    float cost = 0;

    String comment = "";

}
