import org.junit.Test;

import java.util.Arrays;

public class MyTest2 {



    @Test
    public  void test01(){
        System.out.println(1);
    }
    @Test
    public  void raplacePrint(){
        String s = "差错月份、执行退补月份、原峰平谷标志、现峰平谷标志、原蓄冷标志、现蓄冷标志、计量点编号、企业编号、企业名称、售电公司编号、售电公司名称、审批状态、原实际用电量、原峰电量、原平电量、原谷电量、差错峰电量、差错平电量、差错谷电量、修正峰电量、修正平电量、修正谷电量、零售用户原电费、零售用户修正电费、零售退补电费、售电公司退补电费";
        String tmp = ", {\"data#num\": \"MONTH\", \"title\": \"#name\"}";
        int i = 0;
        for (String s1 : Arrays.asList(s.split("、"))) {

            System.out.println(tmp.replaceAll("#num",i+++"").replaceAll("#name", s1));
        }
    }
}