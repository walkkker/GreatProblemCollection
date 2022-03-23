package ACAutomation;


import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * 该AC自动机 实现 大字符串 中 寻找多个候选词的 问题， 返回 候选词列表。
 */

public class ACAutomationList {

    // 前缀树节点 -> 很重要的节点设计
    public static class Node {
        // 【1】基本元素I - AC自动机的核心 -> fail 指针
        Node fail;
        // 【2】基本元素II - 前缀树节点 指向下级节点的 指针（int[26] 替代 哈希表）
        Node[] nexts;
        // 【3】记录字符串结尾，可以无需遍历直接获得节点字符串。 end == null 表示该节点不是字符串的结尾; end != null 表示该节点是某字符串的结尾，并且记录该字符串的值
        String end;
        // 【4】防止采集重复。 【标记】 该字符串结尾节点 是否 已经被 访问过了。 如果被访问过，则下次遇到直接跳过。
        boolean isEndUsed;



        public Node() {
            fail = null;
            nexts = new Node[26];
            end = null;
            isEndUsed = false;
        }
    }


    // AC自动机本质就是 前缀树
    // 【关键】 前缀树上，字符 是 存在边上的，不是存在节点上的。
    public static class ACAutomation {
        // 前缀树一定要有 头节点
        Node root;

        // 【错误点】前缀树 头节点 不为null, 就是要初始化. 节点只是用来记录信息的，字符是边。
        public ACAutomation() {
            root = new Node();
        }

        // 【1】insert 就是 普通前缀树的插入操作， 配合设计的Node就可以
        public void insert(String s) {
            char[] chs = s.toCharArray();
            // 【步骤】前缀树如何插入，遍历 字符串的每一个字符，有边则走，无边则新建。全部遍历完了之后，记得特殊标记结尾节点。
            Node cur = root;   // 新建一个cur，copy 头节点，然后往下走
            for (int i = 0; i < chs.length; i++) {
                int path = chs[i] - 'a';
                if (cur.nexts[path] == null) {
                    cur.nexts[path] = new Node();
                }
                cur = cur.nexts[path];
            }
            // 走完最后一个字符，到达最后的节点。 特殊标记end
            cur.end = s;
        }


        //【2】build 过程 就是 建立fail指针的过程
        // fail指针，就是当前往下匹配失败的话，去往的节点。 其对应的是 以当前节点作为结尾节点的情况下，剩下哪个字符串的前缀与该后缀拥有最大的匹配长度，fail指针就指向该字符串的结尾节点。
        // BFS 建立的过程
        // 【核心关键】 cur=父节点的时候，建立 cur.children 的 fail指针 =》 因为 如果当前cur更新自己的fail,他是无法找到父节点，从而无法找到父节点的fail指针指向的节点
        public void build() {
            // 做BFS就行
            Queue<Node> queue = new LinkedList<>();
            queue.add(root);
            while (!queue.isEmpty()) {
                Node cur = queue.poll();
                // Step1: 我是cur，我的fail指针已经设置完了，我弹出时 我来设置 我的孩子的fail指针
                // 建立 cur 的 孩子 的fail指针。 弹出节点的fail指针一定已经 设置完成了。
                for (int i = 0; i < 26; i++) {
                    if (cur.nexts[i] != null) {
                        cur.nexts[i].fail = root;   // Step2: 先指向root,后面需要的话再改，不需要的话就是root了

                        // Step3: 与fail指针指向的节点看看 有无相同路。
                        // 先初始化我的fail，如果我的fail有指向相同字符的路径，那么那个位置就是新的前后缀最大匹配，我要连他。没有的话， 那就退而求其次，继续像后面的fail（次最大匹配）去看有无相同路。
                        Node cFail = cur.fail;  // 当前的可选的fail在哪里

                        while (cFail != null) {  // cFail == root, 都可以，至多可以匹配 1 个
                            // 看 有无 指向 相同字符 的边
                            if (cFail.nexts[i] != null) {
                                cur.nexts[i].fail = cFail.nexts[i];
                                break;   // 【注意】结束了就break
                            } else {
                                cFail = cFail.fail;   // 否则的话，那就继续跳fail指针
                            }
                        }
                        // 上边过程。 成功找到了，那就fail指向新的节点了。 没有找到，说明无最长前缀，那么 就等于 默认值 指向root；
                        // 处理完成之后，别忘了 BFS，所以 要加入队列。等待弹出时再更新它的孩子
                        queue.add(cur.nexts[i]);
                    }
                }
            }
        }

        /*
        * 找候选词的时候，其实就是 遍历String content然后 在前缀树上 跳来跳去了。
        * 遍历每一个content的字符，然后在 前缀树上 跳节点
        * 怎么跳呢？ 这就是 查找函数 的关键了
        * (1)如果有路，我就走
        * （2）如果没路，我就要跳fail指针，但是每跳到一个节点，我要看看 有没有路， 如果有路，那我就去这条路。 如果跳到空了都没路，（说明root都没路），那就直接下一个字符吧，不过别忘了将cur复位到root,使得下一个字符可以从头开始匹配；
        * (2)如果没路，（fail指针发挥作用了），去fail指针指向节点，看是否有路
        * 续（2）,一直跳到 fail == null;
        * */
        // 找候选词的时候，其实就是 遍历String content然后 在前缀树上 跳来跳去了。
        public List<String> containWords(String content) {
            List<String> ans = new ArrayList<>();
            char[] chs = content.toCharArray();
            Node cur = root;
            // 【重要】在搜索阶段，我(chs[i])就是想找一个 我能去的节点。
            for (int i = 0; i < chs.length; i++) {
                int path = chs[i] - 'a';
                if (cur.nexts[path] != null) {
                    cur = cur.nexts[path];
                } else {
                    // 没有路怎么办，那么你就要去找你的 fail指针了
                    // 【优化代码请见 ACAutomationNumber.java】这部分代码
                    cur = cur.fail;
                    while (cur != null && cur.nexts[path] == null) {
                        cur = cur.fail;
                    }
                    // 退出时，两个可能性： （1）就是 cur==null  (2)当前跳到的节点 有 nexts[path] 这条路了
                    // 跳到null了都没有路，说明 沿途fail指针所有节点都没有路，下一个字符从头开始搜索。
                    if (cur == null) {   // 跳到null了，说明是在没有满足条件的，只能跳过chs[i]去下一个字符了。 root都没有，就是连我开头的都没有。所以直接下一个，但是要将cur复位
                        cur = root;
                        continue;
                    } else {
                        cur = cur.nexts[path];
                    }
                }
                // 至此，当前的 cur != null
                // 【重要】 开始 沿着 fail 指针，收集答案
                Node follow = cur;
                while (follow != root) {    // != root 因为 到了root也不可能收集 字符串，所以 到了root就停就可以
                    if (follow.isEndUsed) {
                        break;
                    }

                    // 【相关需求改这里】upper 相关更改在这里
                    if (follow.end != null) {
                        ans.add(follow.end);
                        follow.isEndUsed = true;
                    }
                    // lower 相关更改在这里

                    // 最后别忘了，不断跳转 fail 指针
                    follow = follow.fail;
                }
            }
            return ans;
        }


        public static void main(String[] args) {
            ACAutomation ac = new ACAutomation();
            ac.insert("dhe");
            ac.insert("he");
            ac.insert("abcdheks");
            // 设置fail指针
            ac.build();

            List<String> contains = ac.containWords("abcdhekskdjfafhasldkflskdjhwqaeruv");
            for (String word : contains) {
                System.out.println(word);
            }
        }

    }
}
