//比较含退格的字符串
package org.qiujf.leetcode.editor.cn;

public class BackspaceStringCompare_844 {
    public static void main(String[] args) {
        Solution solution = new BackspaceStringCompare_844().new Solution();
        System.out.println(solution.backspaceCompare("bbbextm", "bbb#extm"));
    }

    //leetcode submit region begin(Prohibit modification and deletion)
    class Solution {

        public boolean backspaceCompare(String s, String t) {
            boolean ret = true;
            int firstIndex = s.length() - 1;
            int secondIndex = t.length() - 1;
            int backNumI = 0, bakcNumJ = 0;
            while (firstIndex > -1 || secondIndex > -1) {
                while (firstIndex > -1) {
                    if ('#' == s.charAt(firstIndex)) {
                        backNumI++;
                        firstIndex--;
                    } else if (backNumI > 0) {
                        backNumI--;
                        firstIndex--;
                    } else {
                        break;
                    }
                }
                while (secondIndex > -1) {
                    if ('#' == t.charAt(secondIndex)) {
                        bakcNumJ++;
                        secondIndex--;
                    } else if (bakcNumJ > 0) {
                        bakcNumJ--;
                        secondIndex--;
                    } else {
                        break;
                    }
                }
                if (firstIndex > -1 && secondIndex > -1) {
                    if (s.charAt(firstIndex) != t.charAt(secondIndex)) {
                        return false;
                    }
                } else {
                    if (firstIndex > -1 || secondIndex > -1) {
                        return false;
                    }
                }
                firstIndex--;
                secondIndex--;
            }

            return ret;
        }


    }
//leetcode submit region end(Prohibit modification and deletion)

}