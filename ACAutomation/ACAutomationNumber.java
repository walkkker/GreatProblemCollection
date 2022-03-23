package ACAutomation;

import java.util.LinkedList;
import java.util.Queue;

public class ACAutomationNumber {

    public static class Node {
        int endNum;
        Node fail;
        Node[] nexts;

        public Node() {
            endNum = 0;
            fail = null;
            nexts = new Node[26];
        }
    }

    public static class ACAutomation {
        Node root;

        public ACAutomation() {
            root = new Node();
        }

        public void insert(String s) {
            char[] chs = s.toCharArray();
            Node cur = root;
            for (int i = 0; i < chs.length; i++) {
                int path = chs[i] - 'a';
                if (cur.nexts[path] == null) {
                    cur.nexts[path] = new Node();
                }
                cur = cur.nexts[path];
            }
            cur.endNum++;
        }

        public void build() {
            Queue<Node> queue = new LinkedList<>();
            queue.add(root);
            while (!queue.isEmpty()) {
                Node cur = queue.poll();
                for (int i = 0; i < 26; i++) {
                    if (cur.nexts[i] != null) {
                        cur.nexts[i].fail = root;
                        Node cFail = cur.fail;
                        while (cFail != null) {
                            if (cFail.nexts[i] != null) {
                                cur.nexts[i].fail = cFail.nexts[i];
                                break;
                            } else {
                                cFail = cFail.fail;
                            }
                        }
                        queue.add(cur.nexts[i]);
                    }
                }
            }
        }

        public int containNum(String content) {
            char[] chs = content.toCharArray();
            Node cur = root;
            int ans = 0;
            for (int i = 0; i < chs.length; i++) {
                int path = chs[i] - 'a';
                // 【错误点】找了很久的错误，自己看！！ 不认真不细心！对自己代码实现使用的变量不清晰！
//                if (cur.nexts[i] != null) {
//                    cur = cur.nexts[i];
//                } else {
//                    while (cur != null && cur.nexts[i] == null) {
//                        cur = cur.fail;
//                    }
//                    cur = cur == null ? root : cur.nexts[i];
//                }

                if (cur.nexts[path] != null) {
                    cur = cur.nexts[path];
                } else {
                    while (cur != null && cur.nexts[path] == null) {
                        cur = cur.fail;
                    }
                    cur = cur == null ? root : cur.nexts[path];
                }

                // 到达了可用节点之后，follow 沿fail指针走一圈，收集答案
                // 如果是cur==null,使得cur=root的话，下面的代码不会执行，直接跳过，所以无影响。
                Node follow = cur;

                while (follow != root) {
                    if (follow.endNum == -1) {
                        break;
                    }

                    // 【修改区间】
                    if (follow.endNum != 0) {
                        ans += follow.endNum;
                        follow.endNum = -1;
                    }
                    // 【修改区间】

                    follow = follow.fail;
                    // 要注意的是，这里必须新建使用follow，cur指针用来定位下一个字符要检查的节点，一定要时刻保持统一。
                }
            }
            return ans;
        }

        public static void main(String[] args) {
            ACAutomation ac = new ACAutomation();
            ac.insert("dhe");
            ac.insert("he");
            ac.insert("he");
            ac.insert("c");
            ac.build();
            System.out.println(ac.containNum("cdhe"));
        }
    }
}
