package topInterviewHardQuestions;

import java.util.*;

public class code140_WordBreakII {
    // 与 单词拆分I 基本相同
// 前缀树trie + 一维dp表 + 回溯（带剪枝）
    class Solution {
        public List<String> wordBreak(String s, List<String> wordDict) {
            // STEP1 建立前缀树trie
            Node root = trie(wordDict);
            // STEP2 - 建立dp表
            char[] str = s.toCharArray();
            int N = str.length;
            boolean[] dp = new boolean[N + 1];
            dp[N] = true;
            for (int i  = N - 1; i >= 0; i--) {
                Node cur = root;
                for (int j = i; j < N; j++) {
                    int path = str[j] - 'a';
                    if (cur.nexts[path] == null) {
                        break;
                    }
                    cur = cur.nexts[path];
                    if (cur.end != null && dp[j + 1]) {
                        dp[i] = true;
                        break;
                    }
                }
            }
            // dp 表建立完成
            // STEP3, 回溯（带剪枝）
            List<String> ans = new ArrayList<>();
            List<String> path = new ArrayList<>();
            process(str, 0, dp, root, path, ans);
            return ans;
        }

        // 先把前缀树建立起来
        // 首先定义 前缀树节点 （带一个小心眼，减少substring，直接在 前缀树节点上添加end标记）
        public class Node {
            String end;
            Node[] nexts;

            public Node() {
                end = null;
                nexts = new Node[26];
            }
        }

        // STEP1 : 传一个 单词列表 作为参数， 【建立】字典树
        public Node trie(List<String> wordDict) {
            Node root = new Node();
            for (String word : wordDict) {   // 增强 for 循环
                char[] str = word.toCharArray();
                Node cur = root;    // 每次进来一个单词，要从root 从头开始
                for (int i = 0; i < str.length; i++) {
                    int path = str[i] - 'a';
                    if (cur.nexts[path] == null) {
                        cur.nexts[path] = new Node();
                    }
                    cur = cur.nexts[path];
                }
                // 这个时候它会停在最后一个节点上， 那么此时 该节点也是 end 节点，赋值
                cur.end = word;  // 【错误点】前缀树建立 不熟练。 节点内属性应用不熟练 【错句子】 end = word;
            }
            return root;
        }

        public void process(char[] str, int index, boolean[] dp, Node root, List<String> path, List<String> ans) {
            if (index == str.length) {
                StringBuilder builder = new StringBuilder();
                for (int i = 0; i < path.size() - 1; i++) {
                    builder.append(path.get(i));
                    builder.append(" ");
                }
                builder.append(path.get(path.size() - 1));
                ans.add(builder.toString());
                return;
            }
            Node cur = root;
            for (int j = index; j < str.length; j++) {
                int pathCh = str[j] - 'a';  // 【错误点2】名字复用的严重啊，搞清楚名字的同时使用地点，不要用重
                if (cur.nexts[pathCh] == null) {
                    return;
                } else {
                    cur = cur.nexts[pathCh];
                    if (cur.end != null && dp[j + 1]) {  // 重要剪枝 -》使得递归一定只走正确的路
                        path.add(cur.end);
                        process(str, j + 1, dp, root, path, ans);
                        path.remove(path.size() - 1);
                    }
                }
            }
        }
    }
}
