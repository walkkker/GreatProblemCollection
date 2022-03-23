package topInterviewHardQuestions;

import java.util.*;

public class FindWords {

    /**
     执行用时： 14 ms , 在所有 Java 提交中击败了 94.67% 的用户
     内存消耗： 41.5 MB , 在所有 Java 提交中击败了 26.33% 的用户
     */
// 非常考验递归写法
// 前缀树设计（重点-因为涉及到剪枝以及结果生成） + dfs + dfs不走回头路 + 有效剪枝
    class Solution {
        public List<String> findWords(char[][] board, String[] words) {
            // 首先创建前缀树
            Node root = buildTrie(words);
            List<String> ans = new ArrayList<>();

            // 每个位置 作为开头 看看有无单词。所以每个位置都要dfs
            for (int i = 0; i < board.length; i++) {
                for (int j = 0; j < board[0].length; j++) {
                    process(board, i, j, root, ans);
                }
            }

            return ans;
        }



        // 核心关键： dfs 设计。  这个dfs是拿着(i,j)在前缀树 上dfs （带回溯，不走回头路）
        // 【注意】有返回值，表示 该节点处 成功收集的 单词个数
        public int process(char[][] board, int i, int j, Node cur, List<String> ans) {
            // 因为 下面 在递归时做了范围限制的 剪枝， 所以到达的点一定不会越界
            // if (i < 0 || i >= board.length || j < 0 || j >= board[0].length) {
            //     return 0;
            // }

            // 不走回头路 的 标记
            if (board[i][j] == 0) {
                return 0;
            }

            // 没越界的话，此时 board[i][j] 有效
            // 看一下 是否前缀树 上有 这个点/是否要走前缀树
            int pathIndex = board[i][j] - 'a';
            if (cur.nexts[pathIndex] == null || cur.nexts[pathIndex].pass == 0) {
                return 0;
            }


            int fix = 0;
            cur = cur.nexts[pathIndex];
            // 可以收集答案的情况
            if (cur.end != null && !cur.isEndUsed) {
                ans.add(cur.end);
                // cur.pass--;  // 这个千万不要漏掉，只有收集答案时，沿途节点的pass都要-1
                cur.isEndUsed = true;  // 收集过了，其他不要收集了
                //return 1; // 【错误点】这个时候收集了，不返回，因为后面可能还有单词呢
                fix = 1;  // 有 fix 的话，上面那个 pass-- 就不要了，等吧后续结果一起做pass减法
            }

            // 否则的话，就是当前不收集，往四周搜索
            // 搜索前，防止走回头路，要将当前位置记录，然后更改0
            char tmp = board[i][j];
            board[i][j] = 0;
            if (i - 1 >= 0) {
                fix += process(board, i - 1, j, cur, ans);
            }
            if (i + 1 <= board.length - 1) {   // 【错误点】边界别搞错了！！！
                fix += process(board, i + 1, j, cur, ans);
            }
            if (j - 1 >= 0) {
                fix += process(board, i, j - 1, cur, ans);
            }
            if (j + 1 <= board[0].length - 1) {
                fix += process(board, i, j + 1, cur, ans);
            }

            // 递归完了，别忘了还原字符
            board[i][j] = tmp;

            // 这个时候我会收集完 fix
            cur.pass -= fix;

            return fix;
        }


        // 构造前缀树
        // 【错误点】pass++ 位置写错了， 注意！
        public Node buildTrie(String[] words) {
            Node root = new Node();
            for (String str : words) {
                Node cur = root;   // 【错误点-位置错了】每一个单词 都要从root开始插
                cur.pass++;
                char[] chs = str.toCharArray();
                for (int i = 0; i < chs.length; i++) {
                    int pathIndex = chs[i] - 'a';
                    if (cur.nexts[pathIndex] == null) {
                        cur.nexts[pathIndex] = new Node();
                    }
                    cur = cur.nexts[pathIndex];
                    cur.pass++;
                }
                // 下面就是 对 单个单词 最后一个字符后面的节点的 处理
                cur.end = str;
            }
            return root;
        }


        public class Node {
            int pass;
            boolean isEndUsed;
            String end;    // 添加了这个的话 就不需要List<>path去收集路径了，发现 当前前缀树节点可以收集答案， 直接把字符串添加进ans中去就行了。即减少了递归参数，关键不需要遍历Character生成 String，再次常数优化。
            Node[] nexts;

            public Node() {
                pass = 0;
                isEndUsed = false;
                end = null;
                nexts = new Node[26];
            }
        }
    }
}
