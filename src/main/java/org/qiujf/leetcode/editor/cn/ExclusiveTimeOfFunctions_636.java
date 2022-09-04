//函数的独占时间
package org.qiujf.leetcode.editor.cn;

import java.util.List;

public class ExclusiveTimeOfFunctions_636 {
    public static void main(String[] args) {
        Solution solution = new ExclusiveTimeOfFunctions_636().new Solution();
    }

    //leetcode submit region begin(Prohibit modification and deletion)
    class Solution {
        public int[] exclusiveTime(int n, List<String> logs) {
            //1. start 接start的情况  ，这段时间记录到第一个start的id上
            //2. start 接end的情况 1） 是同一个id 记录到id上 2） 不是同一个id 不存在吧
            //3. end接end 纪录到后一个end上
            //4.end 接 start 不用记录
            //好像就俩种情况？ 1. start start  记录到i1上 2.其他的记录到i2
            int[] ret = new int[n];

            String i1 = "", i2 = "";
            String preFlag = "";
            String t1 = "", t2 = "";
            for (String log : logs) {
                String[] split = log.split(":");
                i2 = split[0];
                String flag = split[1];
                t2 = split[2];
             
                if ("start".equals(preFlag) && "start".equals(flag)) {
                    ret[Integer.parseInt(i1)] += Integer.parseInt(t2) - Integer.parseInt(t1);
                } else if ("start".equals(preFlag) && "end".equals(flag)) {
                    ret[Integer.parseInt(i2)] += Integer.parseInt(t2) - Integer.parseInt(t1)+1;
                } else if ("end".equals(preFlag) && "end".equals(flag)) {
                    ret[Integer.parseInt(i2)] += Integer.parseInt(t2) - Integer.parseInt(t1);
                }

                i1 = i2;
                preFlag = flag;
                t1 = t2;

            }


            return ret;
        }
    }
//leetcode submit region end(Prohibit modification and deletion)

}