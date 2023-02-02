import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.apache.http.Header;
import org.apache.http.message.BasicHeader;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.qiujf.LearnApplication;
import org.qiujf.pulsoid.service.PulsoidBaseService;
import org.qiujf.scheduled.MyScheduledTask;
import org.qiujf.scheduled.entity.AutoTaskParam;
import org.qiujf.scheduled.entity.Autotask;
import org.qiujf.scheduled.service.AutoTaskParamService;
import org.qiujf.scheduled.service.AutotaskService;
import org.qiujf.scheduled.vo.HttpTaskVo;
import org.qiujf.utils.HttpUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest(classes = LearnApplication.class, webEnvironment = SpringBootTest.WebEnvironment.NONE)
@RunWith(SpringRunner.class)
public class MyTest {
    @Autowired
    AutotaskService autotaskService;
    @Autowired
    PulsoidBaseService pulsoidBaseService;
    @Autowired
    AutoTaskParamService autoTaskParamService;
    @Autowired
    MyScheduledTask myScheduledTask;


    /**
     * 测试单个任务运行情况
     */
    @Test
    public void testSingerTask() throws IOException {
        Autotask autotask = autotaskService.getById(4);

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

        //执行

        System.out.println(HttpUtil.httpClientRun(httpTaskVo));

    }

    /**
     * 测试所有任务运行情况
     */
    @Test
    public void testAllTask() throws IOException {
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
            System.out.println("签到失败个数：" + urls.size());
            System.out.println("失败url:");
            for (HttpTaskVo vo : urls) {
                System.out.println(vo.getUri());

            }

        }

    }

    /**
     * 插入header数据
     */
    @Test
    public void insertHeaderData() {
        List<AutoTaskParam> autoTaskParams = new ArrayList<>();
        String s = """
                accept: text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9
                accept-encoding: gzip, deflate, br
                accept-language: zh-CN,zh;q=0.9,en;q=0.8,en-GB;q=0.7,en-US;q=0.6
                cookie: cf_clearance=D3jhwbiKwVfc0QCoIq8lmXCXMK_HsIzgLI4s9sNE.Iw-1660463584-0-150; tp=MDdmNjAyNDA2MjdkYmYyMWRhMmJlZDY5OTA5M2Q4OTBkY2U5NjI3Yw%3D%3D
                referer: https://kp.m-team.cc/
                sec-ch-ua: "Microsoft Edge";v="105", " Not;A Brand";v="99", "Chromium";v="105"
                sec-ch-ua-mobile: ?0
                sec-ch-ua-platform: "Windows"
                sec-fetch-dest: document
                sec-fetch-mode: navigate
                sec-fetch-site: same-origin
                sec-fetch-user: ?1
                upgrade-insecure-requests: 1
                user-agent: Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/105.0.0.0 Safari/537.36 Edg/105.0.1343.33
                                """;
        //任务id
        int taskid = 5;

        String[] split = s.split("\n");
        for (String s1 : split) {
            String[] header = s1.split(": ");
            AutoTaskParam autoTaskParam = new AutoTaskParam();
            autoTaskParam.setTaskid(taskid);
            autoTaskParam.setType("header");
            autoTaskParam.setName(header[0].trim());
            autoTaskParam.setValue(header[1].trim());
            autoTaskParams.add(autoTaskParam);
        }
        System.out.println(split.length);
        autoTaskParamService.saveBatch(autoTaskParams);
    }

    //所有任务运行
    @Test
    public void testLog() throws IOException {
        myScheduledTask.autoSign();
    }




    @Test
    public void testLogInsert() {
        Autotask autotask = autotaskService.getById(4);

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
        myScheduledTask.singerSignTask(httpTaskVo, 3);
    }

    @Test
    public void asteroidCollision() {

        System.out.println(tranBinary("110"));
        System.out.println(tranBinary(13));
        Boolean test = true;
        haha(test);
        System.out.println(test);
    }

    public void haha(Boolean b) {
        b = false;
    }

    public int tranBinary(String s) {

        int ret = 0;
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            ret = ret * 2 + (c - '0');
        }

        return ret;
    }

    public String tranBinary(int num) {
        String ret = "";
        while (num != 0) {
            int i = num % 2;
            num = num /2;
            ret = i + ret;
        }
        return ret;
    }
    public String addBinary(String a, String b) {
        String ret = "";
        int flag = 0;
        if(a.length() <b.length()){
            String c = a;
            a = b;
            b = c;
        }
        for (int i = a.length(); i > 0; i--) {


        }


        return ret.equals("") ? "0" : ret;
    }

    @Test
    public void testFil() {
        List<Integer> list = new ArrayList<>();
        list.add(1);
        list.add(2);
        list.add(2);
        list.add(4);
        list.stream().filter(i -> i == 2).forEach(System.out::println);
    }


}
