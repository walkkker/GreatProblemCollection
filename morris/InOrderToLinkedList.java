package morris;

import BTRecursive.Node;

/**
 * Morris + 二叉树递归套路
 */
public class InOrderToLinkedList {

    // 方法一就是 Morris

    /**
     * 核心就是 Morris中序遍历 改链表时， 当前结点的左指针随便改， 而当 节点跳转到下一个节点时（pre = cur, cur = cur.right），这个 pre节点的右指针 是可以进行更改的，不会造成错误。 因为 修改左指针的时候，右指针不变，然后跳向正确的右指针。 当跳到下一个节点时，上一个节点的右指针已经无用了，所以可以修改 Pre 的右指针。
     * 也可以举例来画。
     */
    class Solution {
        public Node treeToDoublyList(Node root) {
            // 特殊情况讨论
            if (root == null) {
                return null;
            }
            Node cur = root;
            Node pre = null;
            Node head = null;
            while (cur != null) {
                if (cur.left != null) {
                    Node mostRight = cur.left;
                    while (mostRight.right != null && mostRight.right != cur) {
                        mostRight = mostRight.right;
                    }
                    // 【错误点】一定是要看 左树最右节点的 【右指针】！！！
                    // if (mostRight == null) 这是原来的错误，找了好久，太笨了！
                    if (mostRight.right == null) {
                        mostRight.right = cur;
                        cur = cur.left;
                        continue;
                    } else {
                        mostRight.right = null;
                    }
                }
                // 这边是核心，morris遍历 中序 改 链表
                if (head == null) {
                    head = cur;
                    System.out.println(head.val);
                }

                // if (pre == null) {
                //     pre = cur;
                // } else {
                //     pre.right = cur;
                //     cur.left = pre;
                //     pre = cur;
                // }
                // 上面的好理解，下面的好写
                if (pre != null) {
                    pre.right = cur;
                    cur.left = pre;
                }
                pre = cur;
                cur = cur.right;
            }
            head.left = pre;
            pre.right = head;
            return head;
        }
    }

    // 方法二就是 二叉树的 递归套路
    class Solution2 {
        public Node treeToDoublyList(Node root) {
            // 一定要加上 特殊处理
            if (root == null) {
                return null;
            }
            Info info = process(root);
            info.head.left = info.tail;
            info.tail.right = info.head;
            return info.head;
        }

        public class Info {
            Node head;
            Node tail;

            public Info(Node h, Node t) {
                head = h;
                tail = t;
            }
        }

        public Info process(Node root) {
            if (root == null) {
                return new Info(null, null);
            }
            Info left = process(root.left);
            Info right = process(root.right);
            Node head = root;
            Node tail = root;

            if (left.head != null) {
                left.tail.right = root;
                root.left = left.tail;
                head = left.head;
            }

            if (right.head != null) {
                root.right = right.head;
                right.head.left = root;
                tail = right.tail;
            }
            return new Info(head, tail);
        }
    }
}
