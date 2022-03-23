package morris;

import BTBasic.TreeNode;

public class PreOrderToLinkedList {
    /**
     Morris 先序遍历只能修改左结点，中序遍历可以左右节点同时更改

     前序遍历时，节点遇见第一次时进行 指针修改，此时不可以 将有左子树节点的右指针修改（改了的话第二次到达就不能去原来的右子树了），但是可以发现，
     【重要】对于前序遍历而言，
     （1）每个有左子树的节点 左指针 本来就指向 先序遍历的下一个节点，
     （2）对于 叶子结点/没有左子树的节点，他们只会遍历一次（然后向右跑），而左指针的改变对后续遍历不会有影响。

     综合上述两点，我们说 在先序遍历时，通过左指针 串起 先序遍历，（注意 右指针不能变动）。 然后按照题目的要求，左指针指向null，右指针指向先序遍历下一个节点，所以 我们最后 再单独 指针逆转就可以了。
     【核心】用左指针连，先序遍历 维持 有左子树 的 左指针不变，所以可以继续第二次的左树最右节点检查
     */
    class Solution {
        public void flatten(TreeNode root) {
            if (root == null) {
                return;
            }

            TreeNode cur = root;
            TreeNode pre = null;
            while (cur != null) {
                if (cur.left != null) {
                    TreeNode mostR = cur.left;
                    while (mostR.right != null && mostR.right != cur) {
                        mostR = mostR.right;
                    }
                    if (mostR.right == null) {
                        mostR.right = cur;

                        // 处理都是对 当前的cur 处理，所以在 cur = cur.left之前
                        if (pre != null) {
                            pre.left = cur;
                        }
                        pre = cur;


                        cur = cur.left;
                        continue;
                    } else {
                        mostR.right = null;
                    }
                } else {
                    if (pre != null) {
                        pre.left = cur;
                    }
                    pre = cur;
                }
                cur = cur.right;
            }

            // 当前状态就是 使用左指针串起来的 先序遍历， 头结点是 root
            // 遍历，将每个节点的 right 指向下一个节点（next作为中间量存储cur.left的话，就是next）， left 指向null，然后 cur去往下一个节点
            cur = root;
            // while (cur != null) {
            //     TreeNode next = cur.left;
            //     cur.left = null;
            //     cur.right = next;
            //     cur = next;
            // }
            while (cur != null) {
                cur.right = cur.left;
                cur.left = null;
                cur = cur.right;
            }

        }
    }

}
