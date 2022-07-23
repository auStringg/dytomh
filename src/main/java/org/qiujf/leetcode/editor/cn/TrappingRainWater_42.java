//接雨水
package org.qiujf.leetcode.editor.cn;


/**
 * D:\proj\learn\src\main\java\org\qiujf
 *
 *
 * $!velocityTool.camelCaseName(${question.titleSlug})_${question.frontendQuestionId}
 *
 *
 *
 * //${question.title}
 * package org.qiujf.leetcode.editor.cn;
 *  public class $!velocityTool.camelCaseName(${question.titleSlug})_${question.frontendQuestionId}{
 *       public static void main(String[] args) {
 *            Solution solution = new $!velocityTool.camelCaseName(${question.titleSlug})_${question.frontendQuestionId}().new Solution();
 *       }
 *       ${question.code}
 *   }
 */

public class TrappingRainWater_42 {
    public static void main(String[] args) {
        Solution solution = new TrappingRainWater_42().new Solution();
        int[] tmp = {4,2,0,3,2,5};

        System.out.println(solution.trap(tmp));
    }

    //leetcode submit region begin(Prohibit modification and deletion)
    class Solution {
        int sum = 0;
        public int trap(int[] height) {
            int tmp = sum;

            //当前柱子向俩边扫描，左边找到比当前高的柱子 tmpI ,右边找到一个比当前高的柱子 tmpJ,补全tmpI到tmpJ之间的水 ，然后补全柱子

            //补全tmpI到tmpJ之间的水
            //int minH = Math.min(tmpI,tmpJ);
            //sum += minH - height[j] -1;
            //补全柱子
            //height[j] = minH;
            //优化： todo 向俩边扫描的时候可以记录下当前下标 【最高】的左右俩边的柱子， 然后补全，扫描其他的时候可以直接查表获取当前柱子之后最高的柱子！
            for (int i = 0; i < height.length; i++) {
                int tmpI = i - 1;
                int tmpJ = i + 1;
                //向左边找
                while (tmpI >=0) {
                    if (height[tmpI] > height[i]) break;
                    tmpI--;
                }
                //向右边找
                while (tmpJ < height.length) {
                    if (height[tmpJ] > height[i]) break;
                    tmpJ++;
                }
                //补全水
                if (tmpI >= 0 && tmpJ < height.length) {
                    int minH = Math.min(height[tmpI], height[tmpJ]);
                    for (int j = tmpI + 1; j < tmpJ; j++) {
                        sum += minH - height[j];
                        height[j] = minH;
                    }
                }
            }
            //再执行一次补全操作
            if(tmp != sum){
                trap(height);
            }
            return sum;
        }




    }
//leetcode submit region end(Prohibit modification and deletion)

}