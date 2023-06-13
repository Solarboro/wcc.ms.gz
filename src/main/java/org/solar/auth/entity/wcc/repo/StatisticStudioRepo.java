package org.solar.auth.entity.wcc.repo;


import lombok.AllArgsConstructor;
import org.solar.auth.entity.wcc.StatisticStudio;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@AllArgsConstructor
public class StatisticStudioRepo {

    JdbcTemplate jdbcTemplate;

    public List<StatisticStudio> getALl(){

        String sql = "select * from statistic_studio";

        List<StatisticStudio> statisticStudios = jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(StatisticStudio.class));

        return statisticStudios;
    }
}
