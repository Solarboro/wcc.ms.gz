CREATE OR REPLACE VIEW yunSummary (sku, color, size, area, count)
AS
select sku, color, size, ifnull( to_sub_store_area, '未置放') area, sum(count) count
from yun_product
where status = 'subStore'
group by sku, color, size, ifnull( to_sub_store_area, '未置放')
;