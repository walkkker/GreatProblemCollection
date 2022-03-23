package topInterviewHardQuestions;

import java.util.*;

public class LadderLength {

    class Solution {
        public int ladderLength(String beginWord, String endWord, List<String> wordList) {
            HashSet<String> dict = new HashSet<>(wordList);
            if (!dict.contains(endWord)) {
                return 0;
            }
            HashSet<String> startSet = new HashSet<>();
            HashSet<String> endSet = new HashSet<>();
            HashSet<String> visited = new HashSet<>();  // 防止重复遍历 BFS
            startSet.add(beginWord);
            endSet.add(endWord);
            int len = 2;
            while (!startSet.isEmpty()) {
                HashSet<String> nextSet = new HashSet<>();
                for (String word : startSet) {
                    for (int i = 0; i < word.length(); i++) {
                        char[] ch = word.toCharArray();  // 【错误点】这一行的位置必须放在这里，不能在for前面。必须保证每一次 只改动一个位置的字符
                        for (char c = 'a'; c <= 'z'; c++) {
                            if (c != word.charAt(i)) {
                                ch[i] = c;
                                String next = String.valueOf(ch);
                                if (endSet.contains(next)) {   // 这就是 汇合了，说明连通了
                                    return len;
                                }
                                if (dict.contains(next) && !visited.contains(next)) {
                                    nextSet.add(next);  // 这个意思就是遍历到了next
                                    visited.add(next);  // 每添加进队列一个元素，要加入set，防止重复入队列，造成死循环 （下次再遇到 就不仅队列了）
                                }
                            }
                        }

                    }
                }
                // 每次重定位 -> 使窄的那一侧成为 startSet, 宽的那一侧成为 endSet. (因为每次遍历窄的那一侧)
                // 重定位 下一次循环 中的 开始和结束集合
                startSet = nextSet.size() < endSet.size() ? nextSet : endSet;
                endSet = startSet == nextSet ? endSet : nextSet;
                // 【重点】千万别忘了，此时 由于多了一个nextSet，所以集合数目++
                len++;  // 如果最终连通的话，集合数量就是总步数
            }
            return 0;  // 最终全遍历完了，没有汇合，那么说明没有路径，返回0
        }
    }
}
