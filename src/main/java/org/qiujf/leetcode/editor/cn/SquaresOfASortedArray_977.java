//有序数组的平方
package org.qiujf.leetcode.editor.cn;

public class SquaresOfASortedArray_977 {
    public static void main(String[] args) {
        Solution solution = new SquaresOfASortedArray_977().new Solution();
    }

    //leetcode submit region begin(Prohibit modification and deletion)
    class Solution {
        public int[] sortedSquares(int[] nums) {
            if (nums.length == 0) {
                return nums;
            }
            int[] ret = new int[nums.length];
            int i = 0, j = nums.length - 1;
            int index = ret.length - 1;
            while (i <= j) {
                if (Math.abs(nums[i]) > Math.abs(Math.abs(nums[j]))) {
                    ret[index--] = nums[i] * nums[i];
                    i++;
                } else {
                    ret[index--] = nums[j] * nums[j];
                    j--;
                }

            }
            return ret;
        }
    }
//leetcode submit region end(Prohibit modification and deletion)

}