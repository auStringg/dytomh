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
import org.qiujf.scheduled.entity.AutoTaskLog;
import org.qiujf.scheduled.entity.AutoTaskParam;
import org.qiujf.scheduled.entity.Autotask;
import org.qiujf.scheduled.service.AutoTaskLogService;
import org.qiujf.scheduled.service.AutoTaskParamService;
import org.qiujf.scheduled.service.AutotaskService;
import org.qiujf.scheduled.vo.HttpTaskVo;
import org.qiujf.utils.HttpUtil;
import org.qiujf.utils.JsonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Component
public class MyScheduledTask {

    @Autowired
    PulsoidBaseService pulsoidBaseService;
    @Autowired
    AutotaskService autotaskService;
    @Autowired
    AutoTaskParamService autoTaskParamService;
    @Autowired
    AutoTaskLogService autotasklogService;

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
     *
     */
    @Scheduled(cron = "1 1 0 * * *")
    public void autoSign() {
        System.out.println("----------------------------------开始签到任务-------------------------");
        List<Autotask> list = autotaskService.list();
        List<HttpTaskVo> vos = new ArrayList<>();
        for (Autotask autotask : list) {
            List<Header> headers = new ArrayList<>();
            HttpTaskVo httpTaskVo = new HttpTaskVo();
            httpTaskVo.setUri(autotask.getUrl());
            QueryWrapper<AutoTaskParam> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("taskid", autotask.getId());
            List<AutoTaskParam> list1 = autoTaskParamService.list(queryWrapper);
            for (AutoTaskParam autoTaskParam : list1) {
                BasicHeader basicHeader = new BasicHeader(autoTaskParam.getName(), autoTaskParam.getValue());
                headers.add(basicHeader);
            }
            httpTaskVo.setHeaders(headers);
            vos.add(httpTaskVo);
        }
        //执行
        List<HttpTaskVo> urls = new ArrayList<>();
        for (HttpTaskVo vo : vos) {
            if (HttpUtil.httpClientReturnSuccess(vo)) {
                urls.add(vo);
            }
        }
        if (urls.size() > 0) {
            retrySign(urls);
        }

    }

    private void retrySign(List<HttpTaskVo> urls) {
        System.out.println("签到失败个数：" + urls.size());
        System.out.println("失败url:");
        for (HttpTaskVo vo : urls) {
            System.out.println(vo.getUri());
            //60后重试
            ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
            executorService.schedule(() -> {
                singerSignTask(vo, 1);
            }, 60, TimeUnit.SECONDS);
            executorService.shutdown();

        }
    }

    public void singerSignTask(HttpTaskVo vo, int reTry) {
        System.out.println(vo.getUri() + " 第" + reTry + "次重试");
        //执行
        if (HttpUtil.httpClientReturnSuccess(vo) && reTry < 3) {
            //一个小时后重试
            ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
            executorService.schedule(() -> {
                singerSignTask(vo, reTry + 1);
            }, 3600, TimeUnit.SECONDS);
            executorService.shutdown();
        }else{
            if(reTry>=3){
                System.out.println("url:"+vo.getUri()+"重试失败！");
                AutoTaskLog autotasklog = new AutoTaskLog();
                autotasklog.setUrl(vo.getUri());
                autotasklog.setTime(new Date());
                autotasklog.setType("sign error");
                autotasklogService.save(autotasklog);
            }
        }
    }
}
