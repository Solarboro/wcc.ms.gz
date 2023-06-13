package org.solar.auth.schedule;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.alidns.model.v20150109.DescribeDomainRecordsRequest;
import com.aliyuncs.alidns.model.v20150109.DescribeDomainRecordsResponse;
import com.aliyuncs.alidns.model.v20150109.UpdateDomainRecordRequest;
import com.aliyuncs.profile.DefaultProfile;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
@Log4j2
@EnableScheduling
public class AliDDNS {

    @Value("${V6PROVIDER}")
    String v6ProviderURL;

    @Value("${ACCESSID}")
    String accessId;

    @Value("${ACCESSKEY}")
    String accessKey;

    @Value("${DOMAIN}")
    String domain;

    @Value("${RR}")
    String rR;

    @Value("${TYPE}")
    String type;

    RestTemplate restTemplate = new RestTemplate();
    @Scheduled(fixedRate = 3*60*1000)
    public void ddns(){
        // get from ali
        // get from pubilc
        // compare and update
        getv6FromDNS(getv6Frompublic());
    }

    void getv6FromDNS(String v6Public){
        if(v6Public.isEmpty()){
            log.info("v6Public isEmpty");
            return;
        }
        DefaultProfile profile = DefaultProfile.getProfile(
                "cn-qingdao",// 地域ID
                accessId,// 您的AccessKey ID
                accessKey);// 您的AccessKey Secret
        IAcsClient client = new DefaultAcsClient(profile);

        // 查询指定二级域名的最新解析记录
        DescribeDomainRecordsRequest describeDomainRecordsRequest = new DescribeDomainRecordsRequest();
        // 主域名
        describeDomainRecordsRequest.setDomainName(domain);
        // 主机记录
        describeDomainRecordsRequest.setRRKeyWord(rR);
        // 解析记录类型
        describeDomainRecordsRequest.setType(type);

        DescribeDomainRecordsResponse acsResponse = null;

        try {
            acsResponse = client.getAcsResponse(describeDomainRecordsRequest);
            acsResponse.getDomainRecords().stream()
                    .filter(record -> !record.getValue().equals(v6Public))
                    .findFirst().ifPresent(
                    record -> {
                        UpdateDomainRecordRequest updateDomainRecordRequest = new UpdateDomainRecordRequest();
                        updateDomainRecordRequest.setRecordId(record.getRecordId());
                        updateDomainRecordRequest.setRR(record.getRR());
                        updateDomainRecordRequest.setValue(v6Public);
                        updateDomainRecordRequest.setType(type);
                        try {
                            log.info("start  update v6 to " + v6Public);
                            client.getAcsResponse(updateDomainRecordRequest);
                            log.info("success update v6 to " + v6Public);
                        } catch (Exception ex) {
                            log.warn(ex.getMessage());
                        }
                    }
            );
        }catch (Exception ex){
            log.warn(ex.getMessage());
        }

    }

    String getv6Frompublic(){

        String v6 = "";
        try {
            v6 = restTemplate.getForObject(v6ProviderURL, String.class);
        }catch (Exception ex){
            log.warn(ex.getMessage());
        }
        return v6;
    }

}
