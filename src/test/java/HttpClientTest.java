import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;


public class HttpClientTest {



    public static void main(String[] args) throws IOException {
        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet("https://dev.pulsoid.net/api/v1/data/heart_rate/latest");
        httpGet.addHeader("Authorization","Bearer b3ae8b07-533d-472e-9fd0-10d610db393d");
        CloseableHttpResponse response1 = httpclient.execute(httpGet);
        try {
            System.out.println(response1.getStatusLine());
            HttpEntity entity = response1.getEntity();
            String result = EntityUtils.toString(entity,"UTF-8");
            System.out.println(result);

            EntityUtils.consume(entity);
        } finally {
            response1.close();
        }


    }


    public HttpClientTest() throws IOException {
    }


}
