import org.junit.Test;
import org.junit.runner.RunWith;
import org.qiujf.LearnApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

@SpringBootTest(classes = {LearnApplication.class}, webEnvironment = SpringBootTest.WebEnvironment.NONE)
@RunWith(SpringRunner.class)
public class RedisTest {
    @Resource
    private RedisTemplate redisTemplate;


    @Test
    public void testAdd(){
        ValueOperations valueOperations = redisTemplate.opsForValue();
        valueOperations.set("qiujf", "hello");
    }

    @Test
    public void testGet() {
        ValueOperations valueOperations = redisTemplate.opsForValue();
        Object qiujf = valueOperations.get("qiujf");
        System.out.println(qiujf);
    }

    @Test
    public void testall() {
        ValueOperations valueOperations = redisTemplate.opsForValue();
        valueOperations.set("qiujf", "hello");
        Object qiujf = valueOperations.get("qiujf");
        System.out.println(qiujf);
    }
}
