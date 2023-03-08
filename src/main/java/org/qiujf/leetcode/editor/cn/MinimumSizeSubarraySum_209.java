//长度最小的子数组
package org.qiujf.leetcode.editor.cn;

public class MinimumSizeSubarraySum_209 {
    public static void main(String[] args) {
        Solution solution = new MinimumSizeSubarraySum_209().new Solution();
        int[] nums = {1, 2, 3, 4, 5};
        solution.minSubArrayLen(11, nums);
    }

    //leetcode submit region begin(Prohibit modification and deletion)
    class Solution {
        public int minSubArrayLen(int target, int[] nums) {
            int start = 0;
            int sum = 0;
            int min = nums.length + 1;
            for (int i = 0; i < nums.length; i++) {
                sum += nums[i];

                while (sum >= target) {
                    min = Math.min(min, i - start + 1);
                    sum -= nums[start++];
                }

            }
            if (min == nums.length + 1) {
                return 0;
            }
            return min;
        }
    }
//leetcode submit region end(Prohibit modification and deletion)

}