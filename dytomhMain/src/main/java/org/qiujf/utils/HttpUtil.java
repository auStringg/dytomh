package org.qiujf.utils;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.qiujf.scheduled.vo.HttpTaskVo;

import java.io.IOException;

public class HttpUtil {

    public static int httpClientRun(HttpTaskVo vo) throws IOException {
        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet(vo.getUri());
        vo.getHeaders().forEach(httpGet::addHeader);

        try (CloseableHttpResponse response1 = httpclient.execute(httpGet)) {
            System.out.println(vo.getUri() + "   " + response1.getStatusLine());
            HttpEntity entity = response1.getEntity();
//            System.out.println(EntityUtils.toString(entity, "UTF-8"));
            return response1.getStatusLine().getStatusCode();
        }

    }

    public static boolean httpClientReturnSuccess(HttpTaskVo vo)  {
        try(CloseableHttpClient httpclient = HttpClients.createDefault()) {
            HttpGet httpGet = new HttpGet(vo.getUri());
            vo.getHeaders().forEach(httpGet::addHeader);
            System.out.print(vo.getUri() + " ");
            try (CloseableHttpResponse response = httpclient.execute(httpGet)) {
                System.out.println("  " + response.getStatusLine());
                return response.getStatusLine().getStatusCode() != 200;
            } catch (IOException e) {
                System.out.println(e);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return false;
    }
}
