//困于环中的机器人
//_1041
package org.qiujf.leetcode.editor.cn;

public class RobotBoundedInCircle {
    public static void main(String[] args) {
        Solution solution = new RobotBoundedInCircle().new Solution();
        System.out.println(solution.isRobotBounded("GLGLGGLGL"));
    }

    //leetcode submit region begin(Prohibit modification and deletion)
    class Solution {
        int[] val = {1, -1000, -1, 1000};

        public boolean isRobotBounded(String instructions) {
            //
            int xy = 0, flag = 0;
            for (int i = 0; i < 4; i++) {
                for (char c : instructions.toCharArray()) {
                    switch (c) {
                        case 'G':
                            xy = xy + val[flag];
                            System.out.println(xy);

                            break;
                        case 'L':
                            flag = (flag + 1) % 4;
                            break;
                        case 'R':
                            flag = Math.abs((flag - 1) % 4);
                            break;
                    }
                }
                if (xy == 0) return true;
            }
            return false;
        }
    }
//leetcode submit region end(Prohibit modification and deletion)

}