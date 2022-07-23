//不同路径
package org.qiujf.leetcode.editor.cn;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class UniquePaths_62 {
    public static void main(String[] args) {
        Solution solution = new UniquePaths_62().new Solution();
    }

    //leetcode submit region begin(Prohibit modification and deletion)
    class Solution {
        int m = 0;
        int n =0;
        Map<Integer,Integer> ret = new HashMap<>();


        public int uniquePaths(int m, int n) {
            this.m = m;
            this.n = n;
            ret.put(m * 1000 + n,1);
            caclEndPath(1,1);
            return ret.get(1001);
        }
        public int caclEndPath(int i,int j){
            Integer key = i * 1000 + j;
            if(ret.containsKey(key)){
                return ret.get(key);
            }
            int tmp = 0;
            if(i <m){
                tmp +=caclEndPath(i + 1,j);
            }
            if(j <n){
                tmp +=caclEndPath(i,j+ 1);
            }
            ret.put(key,tmp);
            return tmp;
        }
    }
//leetcode submit region end(Prohibit modification and deletion)

}