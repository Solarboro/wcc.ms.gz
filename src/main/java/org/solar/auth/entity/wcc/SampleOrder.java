package org.solar.auth.entity.wcc;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.solar.auth.entity.BEntity;
import org.solar.auth.entity.IUser;

import javax.persistence.Entity;
import javax.persistence.OneToOne;

@Data
@Entity
public class SampleOrder extends BEntity {

    @OneToOne
    @JsonIgnore
    SampleStudios sampleStudios;

    @OneToOne
    @JsonIgnore
    IUser iUser;

    Long manufactureDates;
    Long manufactureDate;

    Float cost;

    String comment = "";

    public String getStudioName(){
        return sampleStudios == null ? null : sampleStudios.getName();
    }
    public Long getStudioId(){
        return sampleStudios == null ? null : sampleStudios.getId();
    }
    public Long getProducerId(){
        return iUser == null ? null : iUser.getId();
    }
    public String getProducerName(){
        return iUser == null ? null : iUser.getFullname();
    }
}
