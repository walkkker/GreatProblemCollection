package TopInterviewClassicQuestions;

class Node{
    int val;
    Node left;
    Node right;
    Node next;
}

public class connect {
    // 本题 实现 额外空间复杂度O（1） -> 自制 队列 -> 复用 二叉树节点的 next 指针
// 【核心】 自己实现队列 + BFS
    class Solution {
        public Node connect(Node root) {
            if (root == null) {
                return null;
            }
            MyQueue queue = new MyQueue();
            queue.offer(root);
            while (!queue.isEmpty()) {
                int size = queue.size();
                Node pre = null;
                while (size-- > 0) {
                    Node cur = queue.poll();
                    if (cur.left != null) {
                        queue.offer(cur.left);
                    }
                    if (cur.right != null) {
                        queue.offer(cur.right);
                    }
                    // 先写的这一版，写完后发现可以精简代码。然后再做的优化
                    // if (pre == null) {
                    //     pre = cur;
                    // } else {
                    //     pre.next = cur;
                    //     pre = cur;
                    // }
                    if (pre != null) {
                        pre.next = cur;
                    }
                    pre = cur;
                }
            }
            return root;
        }

        public class MyQueue{
            Node head;
            Node tail;
            int size;

            public MyQueue() {
                head = null;
                tail = null;
                size = 0;
            }


            // offer poll 别忘了 随时更改 size
            public void offer(Node node) {
                size++;
                if (head == null) {
                    head = node;
                    tail = node;
                } else {
                    tail.next = node;
                    tail = node;
                }
            }

            // 【错误点】 poll这里有个细节要注意， 弹出节点的next指针一定要 重新置null
            // 因为 这个跟 之前链表删除不同，head=head.next了，原来head对应节点并不会被删除，因为有ans 或者原来对应的树上节点 引用。 所以因为还有其他引用，将该节点从 链表上取下来的时候要讲next指针 置 null。 （这一点不同于 使用linkedlist容器，因为那个会进行二次封装，next指针不会用到。我们复用了，如果不还原的话，每一层最后的指针都会还有值，不是指向null）。总之，节点弹出队列时，应该与进入队列时 无差异！！！。
            public Node poll() {
                size--;
                Node ans = head;
                head = head.next;
                if (head == null) {
                    tail = null;
                }
                // 【错误点】一定不要漏了这一点，弹出时 还原节点的 next 指针
                ans.next = null;
                return ans;
            }

            public int size() {
                return size;
            }

            public boolean isEmpty() {
                return size == 0;
            }
        }
    }
}
