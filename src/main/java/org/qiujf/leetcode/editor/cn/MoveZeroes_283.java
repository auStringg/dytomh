//移动零
package org.qiujf.leetcode.editor.cn;

public class MoveZeroes_283 {
    public static void main(String[] args) {
        Solution solution = new MoveZeroes_283().new Solution();
    }

    //leetcode submit region begin(Prohibit modification and deletion)
    class Solution {
        public void moveZeroes(int[] nums) {
            //用secondIndex指向0的位置，然后用firstIndex的进行交换
            int secondIndex = 0;
            for (int i = 0; i < nums.length; i++) {
                if (nums[i] != 0) {
                    nums[secondIndex++] = nums[i];
                }
            }
            for (int i = secondIndex; i < nums.length; i++) {
                nums[i] = 0;
            }
        }
    }
//leetcode submit region end(Prohibit modification and deletion)

}