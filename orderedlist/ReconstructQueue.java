package orderedlist;

import java.util.Comparator;
import java.util.Arrays;

public class ReconstructQueue {

    /**
     使用 SBT
     使用 insert(index, num), get(index) 方法
     实现 O(logN) 时间复杂度

     不能使用数组或链表 -> 时间复杂度都是O（N）


     （1）【核心】解法：
     首先排序 （贪心策略 -> 局部最优推出全局最优）
     （1）身高 （由大到小排）   身高相同时，ki由小到大

     组成序列后，
     [hi,ki] 其中 ki就是要插入的位置，依次插入即可

     （2）【核心优化】我们优化 插入操作， SBT改  -> O(logN)， 最终时间复杂度就变成了 O（N * log N）

     */
    class Solution {
        public int[][] reconstructQueue(int[][] people) {
            Arrays.sort(people, new MyComparator());
            SBT arr = new SBT();
            for (int[] peo : people) {
                arr.add(peo[1], peo);
            }
            for (int i = 0; i < arr.size(); i++) {
                people[i] = arr.get(i);
            }
            return people;
        }

        public class MyComparator implements Comparator<int[]> {
            public int compare(int[] a, int[] b) {
                return a[0] == b[0] ? a[1] - b[1] : b[0] - a[0];
            }
        }

        public class Node {
            int[] peo;
            Node left;
            Node right;
            int size;

            public Node(int[] p) {
                peo = p;
                size = 1;
            }
        }

        public class SBT {
            Node root;

            public Node leftRotate(Node cur) {
                Node right = cur.right;
                cur.right = right.left;
                right.left = cur;
                right.size = cur.size;
                cur.size = (cur.left == null ? 0 : cur.left.size) + (cur.right == null ? 0 : cur.right.size) + 1;
                // 【错误点】旋转之后 不要忘记 返回 新的头部
                return right;
            }

            public Node rightRotate(Node cur) {
                Node left = cur.left;
                cur.left = left.right;
                left.right = cur;
                left.size = cur.size;
                cur.size = (cur.left == null ? 0 : cur.left.size) + (cur.right == null ? 0 : cur.right.size) + 1;
                return left;
            }

            public Node maintain(Node cur) {
                if (cur == null) {
                    return null;
                }
                int L = cur.left == null ? 0 : cur.left.size;
                int R = cur.right == null ? 0 : cur.right.size;
                int LL = cur.left != null && cur.left.left != null ? cur.left.left.size : 0;
                int LR = cur.left != null && cur.left.right != null ? cur.left.right.size : 0;
                int RR = cur.right != null && cur.right.right != null ? cur.right.right.size : 0;
                int RL = cur.right != null && cur.right.left != null ? cur.right.left.size : 0;
                if (LL > R) {
                    cur = rightRotate(cur);
                    cur.right = maintain(cur.right);
                    cur = maintain(cur);
                } else if (LR > R) {
                    cur.left = leftRotate(cur.left);
                    cur = rightRotate(cur);
                    cur.left = maintain(cur.left);
                    cur.right = maintain(cur.right);
                    cur = maintain(cur);
                } else if (RR > L) {
                    cur = leftRotate(cur);
                    cur.left = maintain(cur.left);
                    cur = maintain(cur);
                } else if (RL > L) {
                    cur.right = rightRotate(cur.right);
                    cur = leftRotate(cur);
                    cur.left = maintain(cur.left);
                    cur.right = maintain(cur.right);
                    cur = maintain(cur);
                }
                return cur;
            }

            public void add(int index, int[] peo) {
                root = add(root, index, peo);
            }

            private Node add(Node cur, int index, int[] peo) {
                if (cur == null) {
                    return new Node(peo);
                }

                cur.size++;
                int pos = cur.left == null ? 0 : cur.left.size;
                if (index <= pos) {
                    cur.left = add(cur.left, index, peo);
                } else {
                    cur.right = add(cur.right, index - pos - 1, peo);
                }
                return maintain(cur);
            }

            public int[] get(int index) {
                Node cur = root;
                while (cur != null) {
                    int pos = cur.left == null ? 0 : cur.left.size;
                    if (index == pos) {
                        return cur.peo;
                    } else if (index < pos) {
                        cur = cur.left;
                    } else {
                        cur = cur.right;
                        index -= pos + 1;
                    }
                }
                return null;
            }

            public int size() {
                return root.size;
            }

        }

    }
}
