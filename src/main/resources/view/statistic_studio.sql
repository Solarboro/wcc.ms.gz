CREATE OR REPLACE VIEW statistic_studio (period, producer, agent, cost, count)
AS
select period,
    (select concat(lastname,',', firstname) from iuser where id = producer) producer,
    (select concat(lastname,',', firstname) from iuser where id = agent) agent,
    cost,
    count
from
     (
          select date_format(date_sub(from_unixtime(manufacture_date / 1000), INTERVAL 24 * 6 - 8 HOUR),
                             '%Y-%m') period,
                 i_user_id producer,
                 p.agent_id agent,
                 sum(cost) cost,
                 count(cost) count

          from iauth.sample_order
                   left join product p on sample_order.id = p.sample_order_id
          where iauth.sample_order.cost > 0
          group by date_format(date_sub(from_unixtime(manufacture_date / 1000), INTERVAL 24 * 6 - 8 HOUR),
                               '%Y-%m'),
                   i_user_id,
                   p.agent_id
      ) statistic_studio
;