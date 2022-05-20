
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicHeader;
import org.apache.http.util.EntityUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.qiujf.LearnApplication;
import org.qiujf.pulsoid.service.PulsoidBaseService;
import org.qiujf.scheduled.service.AutotaskService;
import org.qiujf.scheduled.vo.HttpTaskVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

@SpringBootTest(classes = LearnApplication.class,webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)

@RunWith(SpringRunner.class)
public class MyTest {
    @Autowired
    AutotaskService autotaskService;
    @Autowired
    PulsoidBaseService pulsoidBaseService;

    @Test
    public void test01() throws IOException {
        HttpTaskVo httpTaskVo = new HttpTaskVo();
        httpTaskVo.setUri("https://kp.m-team.cc");
        List<Header> headers = new ArrayList<>();
        headers.add(new BasicHeader("cookie","cf_clearance=uumxseXckxtiGb.3uHVBdXccxLX_mypGOjbtqq8C4T8-1650081177-0-150; tp=N2MyYWE0YzcxNGE2YzUyNWM0NWU2ODg2MDgyYmZjMzFmNDg5NzkyYw%3D%3D"));
        httpTaskVo.setHeaders(headers);
        httpClientRun(httpTaskVo);
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
