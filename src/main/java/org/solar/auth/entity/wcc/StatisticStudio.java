package org.solar.auth.entity.wcc;

import lombok.Data;

import java.io.Serializable;

@Data
public class StatisticStudio implements Serializable {
    String period;

    String producer;

    String agent;

    Double cost;

    Integer count;
}
