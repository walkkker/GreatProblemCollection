package TopInterviewClassicQuestions;

import java.util.*;

public class FirstUniqChar {
    /**
     统计词频

     1. 哈希表
     2. int[26] -> 常数小

     【总结】：
     做词频统计或一些字符串映射时，int[]数组 在个数有限的情况下 是可以 与 哈希表功能互换的。

     并且，由于数组的常数时间小，所以使用int[] 数组可以 使运行的常数时间 大大优化。
     */
    class Solution {

        // 2. int[26] 代替哈希表 作 词频统计   ==》 快很多
        public int firstUniqChar(String s) {
            int[] cnt = new int[26];
            char[] chs = s.toCharArray();
            for (char ch : chs) {
                cnt[ch - 'a']++;
            }

            for (int i = 0; i < chs.length; i++) {
                if (cnt[chs[i] - 'a'] == 1) {
                    return i;
                }
            }

            return -1;
        }


        // 1.哈希表。 但是由于只含有小写字母 -> 所以可以用int[] 代替 哈希表做词频统计。
        public int firstUniqChar1(String s) {
            char[] chs = s.toCharArray();
            HashMap<Character, Integer> map = new HashMap<>();
            for (char ch : chs) {
                if (!map.containsKey(ch)) {
                    map.put(ch, 0);
                }
                map.put(ch, map.get(ch) + 1);
            }

            for (int i = 0; i < chs.length; i++) {
                if (map.get(chs[i]) == 1) {
                    return i;
                }
            }
            return -1;
        }


    }
}
