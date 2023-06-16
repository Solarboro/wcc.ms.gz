package org.solar.auth.entity.yun;

import lombok.Data;
import org.solar.auth.entity.BEntity;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
public class YunProvider extends BEntity {

    public Long getValue(){return getId();}

    String label;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "yun_provider_id")
    List<YunProductSpec> yunProductSpecs = new ArrayList<>();
}
