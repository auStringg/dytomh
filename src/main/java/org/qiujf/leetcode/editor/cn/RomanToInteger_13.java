//罗马数字转整数
package org.qiujf.leetcode.editor.cn;

import java.util.HashMap;
import java.util.Map;

public class RomanToInteger_13{
      public static void main(String[] args) {
           Solution solution = new RomanToInteger_13().new Solution();
          System.out.println(solution.romanToInt("MCMXCIV"));
      }


      //leetcode submit region begin(Prohibit modification and deletion)
class Solution {
    public int romanToInt(String s) {
        Integer ret = 0;
        Map<String,Integer> map = new HashMap<>();
        map.put("I",1);
        map.put("V",5);
        map.put("X",10);
        map.put("L",50);
        map.put("C",100);
        map.put("D",500);
        map.put("M",1000);
        map.put("IV",4);
        map.put("IX",9);
        map.put("XL",40);
        map.put("XC",90);
        map.put("CD",400);
        map.put("CM",900);
        for (int i = s.length()-1; i>=0; i--) {
            if(i >0){
                String substring = s.substring(i - 1);
                if(map.containsKey(substring)){
                    s = s.substring(0,i-1);
                    ret += map.get(substring);
                    i-=1;
                    continue;
                }
            }
            String substring = s.substring(i);
            s = s.substring(0,i);
            ret += map.get(substring);
        }


        return ret;
    }
}
//leetcode submit region end(Prohibit modification and deletion)

  }