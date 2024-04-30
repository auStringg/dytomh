import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;
public class MyTest2 {



    @Test
    public void testJson() throws IOException {
        File dir = new File("D:\\proj\\data");
        ObjectMapper objectMapper = new ObjectMapper();

        File file = new File("D:\\proj\\data\\result.json");

        Map value = objectMapper.readValue(file, Map.class);
        System.out.println(value.size());

    }

    @Test
    public void testJson2() throws IOException {
        File dir = new File("D:\\proj\\data");
        ObjectMapper objectMapper = new ObjectMapper();

        File file = new File("D:\\proj\\data\\car.json");
        HashMap<String, String> map = new HashMap<>();
        map.put("hello", "world");

        objectMapper.writeValue(new FileOutputStream("D:\\proj\\data\\car.json"),map);

    }

    @Test
    public  void test01(){
        System.out.println(1);
    }
    @Test
    public  void raplacePrint(){
        Class<?> clazz = int.class;   //基本数据类型有Class对象吗？
        System.out.println(Integer.TYPE == int.class);
        System.out.println(Integer.TYPE == Integer.class);
    }
    @Test
    public void ttest02(){
        Class<String[]> clazz = String[].class;
        System.out.println(clazz.getName());  //获取类名称（得到的是包名+类名的完整名称）
        System.out.println(clazz.getSimpleName());
        System.out.println(clazz.getTypeName());
        System.out.println(clazz.getClassLoader());   //获取它的类加载器
        System.out.println(clazz.cast(Integer.parseInt("10")));   //强制类型转换
    }
    @Test
    public void ttest3(){
        Integer i = 10;
        Type type = i.getClass().getGenericSuperclass();
        System.out.println(type);
        System.out.println(type instanceof Class);
    }
    @Test
    public void ttest4(){
        Integer i = 10;
        for (Class<?> anInterface : i.getClass().getInterfaces()) {
            System.out.println(anInterface.getName());
        }

        for (Type genericInterface : i.getClass().getGenericInterfaces()) {
            System.out.println(genericInterface.getTypeName());
        }
    }
    @Test
    public void test5() throws NoSuchFieldException, IllegalAccessException {
        Integer i = 10;

        Field field = Integer.class.getDeclaredField("value");

        Field modifiersField = Field.class.getDeclaredField("modifiers");  //这里要获取Field类的modifiers字段进行修改
        modifiersField.setAccessible(true);
        modifiersField.setInt(field, field.getModifiers() & ~Modifier.FINAL);  //去除final标记

        field.setAccessible(true);
        field.set(i, 100);   //强行设置值

        System.out.println(i);
    }

    @Test
    public void testMap() {
        System.out.println(1 % 3);
        System.out.println(2 % 3);
        System.out.println(3 % 3);
        System.out.println((5 + 3) % 3);
        System.out.println(Integer.MAX_VALUE);

    }
}
