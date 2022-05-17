
import org.junit.Test;
import org.junit.runner.RunWith;
import org.qiujf.LearnApplication;
import org.qiujf.pulsoid.service.PulsoidBaseService;
import org.qiujf.scheduled.service.AutotaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;
import java.util.concurrent.Callable;

@SpringBootTest(classes = LearnApplication.class,webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)

@RunWith(SpringRunner.class)
public class MyTest {
    @Autowired
    AutotaskService autotaskService;
    @Autowired
    PulsoidBaseService pulsoidBaseService;

    @Test
    public void test01() {
        System.out.println(autotaskService.count());
       // System.out.println(pulsoidBaseService.count());
    }


}
