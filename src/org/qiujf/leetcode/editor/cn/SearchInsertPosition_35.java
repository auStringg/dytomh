//搜索插入位置
package org.qiujf.leetcode.editor.cn;

public class SearchInsertPosition_35 {
    public static void main(String[] args) {
        Solution solution = new SearchInsertPosition_35().new Solution();
    }

    //leetcode submit region begin(Prohibit modification and deletion)
    class Solution {
        public int searchInsert(int[] nums, int target) {
            // 分别处理如下四种情况
            // 目标值在数组所有元素之前 [0,0)
            // 目标值等于数组中某一个元素 return middle
            // 目标值插入数组中的位置 [left, right) ，return right 即可
            // 目标值在数组所有元素之后的情况 [left, right)，因为是右开区间，所以 return right
            int start = 0, end = nums.length;
            int cent = 0;
            while (start < end) {
                cent = (start + end) / 2;
                if (nums[cent] == target) {
                    return cent;
                } else if (nums[cent] > target) {
                    end = cent;
                } else {
                    start = cent + 1;
                }
            }


            return end;
        }
    }
//leetcode submit region end(Prohibit modification and deletion)

}