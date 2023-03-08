//二分查找
package org.qiujf.leetcode.editor.cn;

public class BinarySearch_704 {
    public static void main(String[] args) {
        Solution solution = new BinarySearch_704().new Solution();
    }

    //leetcode submit region begin(Prohibit modification and deletion)
    class Solution {
        public int search(int[] nums, int target) {
            int start = 0, end = nums.length - 1;
            while (start <= end) {
                int cent = (start + end) / 2;
                if (nums[cent] == target) {
                    return cent;
                } else if (nums[cent] > target) {
                    end = cent - 1;
                } else {
                    start = cent + 1;
                }
            }
            return -1;
        }
    }
//leetcode submit region end(Prohibit modification and deletion)

}