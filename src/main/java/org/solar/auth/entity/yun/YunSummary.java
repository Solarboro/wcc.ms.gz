package org.solar.auth.entity.yun;


import lombok.Data;
import java.io.Serializable;

@Data
public class YunSummary implements Serializable {

    String sku;
    String color;
    String size;
    String area;
    Integer count;
}
