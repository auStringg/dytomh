package org.qiujf.leetcode.editor.cn;

public class Leetcode530 {
       public static class TreeNode {
         int val;
          TreeNode left;
          TreeNode right;
          TreeNode() {}
          TreeNode(int val) { this.val = val; }
          TreeNode(int val, TreeNode left, TreeNode right) {
              this.val = val;
              this.left = left;
              this.right = right;
          }
      }
    int min = Integer.MIN_VALUE;
    public static void main(String[] args) {


    }
    public int getMinimumDifference(TreeNode root) {
        if(root.left != null){
            min = Math.min(min,Math.abs(root.val - root.left.val));
            getMinimumDifference(root.left);
        }
        if(root.right != null){
            min = Math.min(min,Math.abs(root.val - root.right.val));
            getMinimumDifference(root.right);
        }
        System.out.println();

        return min;
    }

}
