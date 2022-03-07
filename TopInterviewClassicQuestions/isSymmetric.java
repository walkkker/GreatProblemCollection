package TopInterviewClassicQuestions;

import java.util.*;

class TreeNode {
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

// 难点在于迭代
public class isSymmetric {

    // 递归方式做
    class Solution1 {
        public boolean isSymmetric(TreeNode root) {
            return process(root.left, root.right);
        }

        public boolean process(TreeNode left, TreeNode right) {
            if (left == null && right == null) {
                return true;
            }

            if (left == null ^ right == null) {
                return false;
            }

            if (left.val != right.val) {
                return false;
            }

            return process(left.left, right.right) && process(left.right, right.left);

        }
    }

    // 迭代方法

}
