package morris;

import BTBasic.TreeNode;

import java.util.ArrayList;
import java.util.List;

public class MorrisTraversal {

    public class Node {
        int val;
        Node left;
        Node right;

        public Node(int v) {
            val = v;
            left = null;
            right = null;
        }
    }

    public static void morris(Node root) {
        if (root == null) {
            return;
        }

        Node cur = root;
        Node mostRight = null;

        while (cur != null) {
            // Step1 先判断有没有左孩子
            // 有左孩子的话，两个分支判断； 没有左孩子的话，直接跳过if，cur向右移动
            if (cur.left != null) {
                mostRight = cur.left;
                // 【错误点 - 注意while条件有两个】寻找 左树的 最右节点
                while (mostRight.right != null && mostRight.right != cur) {
                    mostRight = mostRight.right;
                }
                // 此时到达左树的最右节点，判断该节点的right指针指向 cur/null 来判断是第一次/二次到达
                if (mostRight.right == null) {
                    mostRight.right = cur;
                    cur = cur.left;
                    // 一定不要忘掉这句话，往左走然后直接 continue
                    continue;
                } else {
                    mostRight.right = null;   // 第二次到达cur的时候就把mostRight.right修改回来
                }
            }
            // 这里这个 cur = cur.right 包含了 第二次到达左子树节点的情况 以及 没有左子树的情况
            cur = cur.right;
        }
    }

    // 前序遍历， 就是 有左树的第一次 + 无左树的第一次
    class Solution {
        public List<Integer> preorderTraversal(TreeNode root) {
            List<Integer> ans = new ArrayList<>();
            TreeNode cur = root;
            while (cur != null) {
                if (cur.left != null) {
                    TreeNode mostRight = cur.left;
                    while (mostRight.right != null && mostRight.right != cur) {
                        mostRight = mostRight.right;
                    }
                    if (mostRight.right == null) {
                        ans.add(cur.val);
                        mostRight.right = cur;
                        cur = cur.left;
                        continue;
                    } else {
                        mostRight.right = null;
                    }
                } else {
                    ans.add(cur.val);
                }
                cur = cur.right;
            }
            return ans;
        }
    }

    // 中序遍历就是 有左树的第二次 + 无左树的第一次
    // BST寻找第K小的元素
    class Solution2 {
        public int kthSmallest(TreeNode root, int k) {
            int times = 0;
            int ans = 0;

            TreeNode cur = root;

            while (cur != null) {
                // 决定cur接下来的遍历方向
                if (cur.left != null) {
                    TreeNode mostRight = cur.left;
                    while (mostRight.right != null && mostRight.right != cur) {
                        mostRight = mostRight.right;
                    }
                    if (mostRight.right == null) {
                        mostRight.right = cur;
                        cur = cur.left;
                        continue;
                    } else {
                        mostRight.right = null;
                    }
                }
                // 这里就是 中序遍历的位置 -> 有左树的第二次到达 + 无左树的第一次到达
                if (++times == k) {
                    ans = cur.val;
                }
                cur = cur.right;
            }
            return ans;
        }
    }

    // 【难点】后序遍历
    // 注意 两个添加位置 + reverse函数 + printEdge() 逆序打印右边界的函数
    class Solution3 {
        public List<Integer> postorderTraversal(TreeNode root) {
            List<Integer> ans = new ArrayList<>();
            TreeNode cur = root;
            while (cur != null) {
                if (cur.left != null) {
                    TreeNode mostRight = cur.left;
                    while (mostRight.right != null && mostRight.right != cur) {
                        mostRight = mostRight.right;
                    }
                    if (mostRight.right == null) {
                        mostRight.right = cur;
                        cur = cur.left;
                        continue;
                    } else {
                        mostRight.right = null;
                        // 【添加位置1】 在 每个到达第二次的节点处，逆序打印它的左树右边界
                        printEdge(cur.left, ans);
                    }
                }
                cur = cur.right;
            }
            // 【添加位置2】别忘了，最后 要单独 逆序 打印 整棵树的 右边界。
            printEdge(root, ans);
            return ans;
        }

        public void printEdge(TreeNode node, List<Integer> ans) {
            TreeNode tail = reverse(node);
            TreeNode cur = tail;
            while (cur != null) {
                ans.add(cur.val);
                cur = cur.right;
            }
            reverse(tail);
        }

        // 沿着 right 指针 逆序
        public TreeNode reverse(TreeNode node) {
            TreeNode pre = null;
            TreeNode cur = node;
            while (cur != null) {
                TreeNode next = cur.right;
                cur.right = pre;
                pre = cur;
                cur = next;
            }
            return pre;
        }
    }
}


