package org.solar.auth.entity.wcc;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.solar.auth.entity.BEntity;
import org.solar.auth.entity.IUser;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
public class SampleStudios extends BEntity {

    String name;

    @OneToMany
    @JsonIgnore
    List<IUser> iUsers = new ArrayList<>();
}
