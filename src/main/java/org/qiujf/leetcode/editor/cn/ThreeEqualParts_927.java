//三等分
package org.qiujf.leetcode.editor.cn;

public class ThreeEqualParts_927 {
    public static void main(String[] args) {
        Solution solution = new ThreeEqualParts_927().new Solution();
        int[] ints = {1, 0, 1, 0, 1};
        solution.threeEqualParts(ints);
    }

    //leetcode submit region begin(Prohibit modification and deletion)
    class Solution {
        private int[] arr;
        public int[] threeEqualParts(int[] arr) {
            this.arr = arr;
            int cnt = 0;
            int n = arr.length;
            for (int v : arr) {
                cnt += v;
            }
            if (cnt % 3 != 0) {
                return new int[]{-1, -1};
            }
            if (cnt == 0) {
                return new int[]{0, n - 1};
            }
            cnt /= 3;
            int i = find(1), j = find(cnt + 1), k = find(cnt * 2 + 1);
            for (; k < n && arr[i] == arr[j] && arr[j] == arr[k]; ++i, ++j, ++k) {
            }
            return k == n ? new int[]{i - 1, j} : new int[]{-1, -1};
        }

        private int find(int x) {
            int s = 0;
            for (int i = 0; i < arr.length; ++i) {
                s += arr[i];
                if (s == x) {
                    return i;
                }
            }
            return 0;
        }


    }
//leetcode submit region end(Prohibit modification and deletion)

}