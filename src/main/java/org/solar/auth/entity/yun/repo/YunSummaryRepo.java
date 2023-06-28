package org.solar.auth.entity.yun.repo;


import lombok.AllArgsConstructor;
import org.solar.auth.entity.wcc.StatisticStudio;
import org.solar.auth.entity.yun.YunSummary;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@AllArgsConstructor
public class YunSummaryRepo {

    JdbcTemplate jdbcTemplate;

    public List<YunSummary> getALl(){

        String sql = "select sku, color, size, ifnull( to_sub_store_area, '未置放') area, sum(count) count\n" +
                "from yun_product\n" +
                "where status = 'subStore'\n" +
                "group by sku, color, size, ifnull( to_sub_store_area, '未置放')";

        List<YunSummary> statisticStudios = jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(YunSummary.class));

        return statisticStudios;
    }
}
