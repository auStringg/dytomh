package org.qiujf.scheduled;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicHeader;
import org.apache.http.util.EntityUtils;
import org.qiujf.pulsoid.Pulsoid;
import org.qiujf.pulsoid.PulsoidBase;
import org.qiujf.pulsoid.service.PulsoidBaseService;
import org.qiujf.scheduled.entity.AutoTaskParam;
import org.qiujf.scheduled.entity.Autotask;
import org.qiujf.scheduled.service.AutoTaskParamService;
import org.qiujf.scheduled.service.AutotaskService;
import org.qiujf.scheduled.vo.HttpTaskVo;
import org.qiujf.utils.JsonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
public class MyScheduledTask {

    @Autowired
    PulsoidBaseService pulsoidBaseService;
    @Autowired
    AutotaskService autotaskService;
    @Autowired
    AutoTaskParamService autoTaskParamService;

    //pro已经过期了，无法使用
    // @Scheduled(cron = "0/2 * * * * *")
    public void cron() throws IOException {
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
        if (pulsoid != null && pulsoid.getHearRate() != null) {
            PulsoidBase maxPulsoid = pulsoidBaseService.getMaxPulsoid();
            if (needChangeEndTime(pulsoid, maxPulsoid)) {
                maxPulsoid.setEndTime(new Date());
                pulsoidBaseService.updateById(maxPulsoid);
            } else {
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
        return maxPulsoid != null && (Math.abs(maxPulsoid.getHeartrate() - pulsoid.getHearRate()) < 2);
    }

    /**
     * 每天0点签到
     * @throws IOException
     */
    @Scheduled(cron = "1 56 17 * * *")
    public void autoSign() throws IOException {
        System.out.println("----------------------------------开始签到任务-------------------------");
        List<Autotask> list = autotaskService.list();
        List<HttpTaskVo> vos = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            List<Header> headers = new ArrayList<>();
            HttpTaskVo httpTaskVo = new HttpTaskVo();
            httpTaskVo.setUri(list.get(i).getUrl());
            QueryWrapper<AutoTaskParam> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("taskid", list.get(i).getId());
            List<AutoTaskParam> list1 = autoTaskParamService.list(queryWrapper);
            for (AutoTaskParam autoTaskParam : list1) {
                BasicHeader basicHeader = new BasicHeader(autoTaskParam.getName(), autoTaskParam.getValue());
                headers.add(basicHeader);
            }
            httpTaskVo.setHeaders(headers);
            vos.add(httpTaskVo);
        }
        //执行
        for (HttpTaskVo vo : vos) {
            httpClientRun(vo);
        }

    }

    private void httpClientRun(HttpTaskVo vo) throws IOException {
        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet(vo.getUri());
        vo.getHeaders().forEach(v -> httpGet.addHeader(v));

        CloseableHttpResponse response1 = httpclient.execute(httpGet);
        try {
            System.out.println(vo.getUri() +"   " + response1.getStatusLine());
            HttpEntity entity = response1.getEntity();
            String result = EntityUtils.toString(entity, "UTF-8");
        } finally {
            response1.close();
        }

    }
}
