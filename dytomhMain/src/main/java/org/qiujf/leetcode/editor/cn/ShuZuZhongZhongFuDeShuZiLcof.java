//数组中重复的数字
//_剑指 Offer 03
package org.qiujf.leetcode.editor.cn;

public class ShuZuZhongZhongFuDeShuZiLcof {
    public static void main(String[] args) {
        Solution solution = new ShuZuZhongZhongFuDeShuZiLcof().new Solution();
    }

    //leetcode submit region begin(Prohibit modification and deletion)
    class Solution {
        public int findRepeatNumber(int[] nums) {
            int[] numTmp = new int[nums.length];
            for (int i : nums) {
                if (numTmp[i] == 1) return i;
                numTmp[i] = 1;
            }
            return 0;
        }
    }
//leetcode submit region end(Prohibit modification and deletion)

}