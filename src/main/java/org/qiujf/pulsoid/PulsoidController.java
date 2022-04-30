package org.qiujf.pulsoid;


import com.fasterxml.jackson.core.JsonProcessingException;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.qiujf.pulsoid.service.PulsoidBaseService;
import org.qiujf.utils.JsonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.util.Date;

@Controller
public class PulsoidController {

    @Autowired
    PulsoidBaseService pulsoidBaseService;

    @ResponseBody
    @GetMapping(value = "pulsoid")
    public void getpulsoid() {
        System.out.println(pulsoidBaseService.count());
    }


    @ResponseBody
    @GetMapping(value = "getHeartRate")
    public Pulsoid getHeartRate() throws IOException {
        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet("https://dev.pulsoid.net/api/v1/data/heart_rate/latest");
        httpGet.addHeader("Authorization", "Bearer b3ae8b07-533d-472e-9fd0-10d610db393d");
        CloseableHttpResponse response1 = httpclient.execute(httpGet);
        Pulsoid pulsoid;
        try {
            System.out.println(response1.getStatusLine());
            HttpEntity entity = response1.getEntity();
            String result = EntityUtils.toString(entity, "UTF-8");
            System.out.println(result);
            pulsoid = JsonUtil.paseJson(Pulsoid.class, result);

        } finally {
            response1.close();
        }
        return pulsoid;

    }


    @Scheduled(cron = "0/2 * * * * *")
    public void cron() throws IOException {
        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet("https://dev.pulsoid.net/api/v1/data/heart_rate/latest");
        httpGet.addHeader("Authorization", "Bearer b3ae8b07-533d-472e-9fd0-10d610db393d");
        CloseableHttpResponse response1 = httpclient.execute(httpGet);
        Pulsoid pulsoid;
        try {
            System.out.println(response1.getStatusLine());
            HttpEntity entity = response1.getEntity();
            String result = EntityUtils.toString(entity, "UTF-8");
            System.out.println(result);
            pulsoid = JsonUtil.paseJson(Pulsoid.class, result);

        } finally {
            response1.close();
        }
        if (pulsoid != null && pulsoid.getHearRate() != null) {
            PulsoidBase maxPulsoid = pulsoidBaseService.getMaxPulsoid();
            if(needChangeEndTime(pulsoid, maxPulsoid)){
                maxPulsoid.setEndTime(new Date());
                pulsoidBaseService.updateById(maxPulsoid);
            }else{
                PulsoidBase pulsoidBase = new PulsoidBase();
                pulsoidBase.setStartTime(new Date());
                pulsoidBase.setEndTime(new Date());
                pulsoidBase.setUserid(1);
                pulsoidBase.setHeartrate(pulsoid.getHearRate());
                pulsoidBaseService.save(pulsoidBase);
            }
        }
    }

    private boolean needChangeEndTime(Pulsoid pulsoid, PulsoidBase maxPulsoid) {
        return maxPulsoid != null && (Math.abs(maxPulsoid.getHeartrate() - pulsoid.getHearRate()) <2);
    }
}
