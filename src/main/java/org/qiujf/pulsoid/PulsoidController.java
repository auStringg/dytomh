package org.qiujf.pulsoid;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.conditions.query.QueryChainWrapper;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class PulsoidController {

    @Autowired
    PulsoidBaseService pulsoidBaseService;

    @ResponseBody
    @GetMapping(value = "pulsoid")
    public Integer getpulsoid() {
        return Math.toIntExact(pulsoidBaseService.count());
    }
    @RequestMapping(value = "/init")
    public String init(){
        return "pulsoid";
    }
    @ResponseBody
    @GetMapping(value = "getPulsoidData")
    public String getPulsoidData(String startDate,String endDate) throws JsonProcessingException {
        //2022-05-01T12:00
        LocalDateTime startTime = LocalDateTime.parse(startDate);
        LocalDateTime endTime = LocalDateTime.parse(endDate);
        QueryWrapper<PulsoidBase> pulsoidQueryWrapper = new QueryWrapper<>();
        QueryWrapper<PulsoidBase> le = pulsoidQueryWrapper.ge("start_time", startTime).le("end_time", endTime);
        List<PulsoidBase> maps = pulsoidBaseService.list(le);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        List<HashMap<String, String>> collect = maps.stream()
                .sorted(Comparator.comparing(PulsoidBase::getStartTime))
                .map(p -> {
                    HashMap<String, String> map = new HashMap<>();
                    map.put("time", simpleDateFormat.format(p.getStartTime()));
                    map.put("rate", p.getHeartrate().toString());
                    return map;
                }).collect(Collectors.toList());


        return JsonUtil.toJson(collect);
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
            HttpEntity entity = response1.getEntity();
            String result = EntityUtils.toString(entity, "UTF-8");
            pulsoid = JsonUtil.paseJson(Pulsoid.class, result);

        } finally {
            response1.close();
        }
        return pulsoid;

    }



}
