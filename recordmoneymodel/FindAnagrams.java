package recordmoneymodel;

/**
 *
 * 欠债表模型 + 滑动窗口（固定大小的滑动窗口 -> 从而可以直接从 欠债表中 获得是否当前开头就是 异位词的开头）
 *
 * 有效还款并且长度相同 -> 一定就是异位次
 *
 * 欠账本都需要什么呢？
 * （1）哈希表
 * （2）int all 记录 真实欠着的数量 -> 为0时表示不欠了
 *
 * 欠账本的算法？
 * 还账时，
 * 如果当前 map.get(还账符号) > 0，那么 map操作-1后， all--
 * 如果当前 map.get(还账符号) <= 0, 那么 map 操作 -1后，all不变
 *
 * 欠账时，
 * 一个很简单的分辨，如果 操作完之后， map.get(欠账符号) > 0， 那么 all++，否则不加
 *
 */
import java.util.*;

public class FindAnagrams {


    class Solution {

        // 数组实现 常数时间很快
        public List<Integer> findAnagrams(String s, String p) {
            // 对于本题，我只要控制住长度，然后 欠账表all为0的话，一定就是 anagram
            List<Integer> ans = new ArrayList<>();
            char[] str = s.toCharArray();
            char[] match = p.toCharArray();
            int matchLen = match.length;
            int[] cnt = new int[26];   // 只有小写字母时，使用数字来做 词频统计,常数时间快很多
            int all = matchLen;
            // 建立欠账表
            for (int i = 0; i < matchLen; i++) {
                cnt[match[i] - 'a']++;
            }

            // 固定窗口模板，注意记录
            // 【注意】 本题要 保证固定窗口大小
            for (int R = 0; R < str.length; R++) {
                if (--cnt[str[R] - 'a'] >= 0) {
                    all--;
                }

                // 此时 L 右移（看是否需要右移）
                // 【错误点】注意，要右移的是 L前一个位置，求的是 L 前一个位置
                // int L = R - matchLen + 1;
                int LPre = R - matchLen;
                if (LPre >= 0 && ++cnt[str[LPre] - 'a'] > 0) {
                    all++;
                }

                // 最终处理 -> 条件，因为上面已经保证L>=0时，时刻 LR 是固定大小窗口，所以 只要 [0,R]>=matchLen 且 满足all==0，就可以 收集信息
                if (R >= matchLen - 1 && all == 0) {
                    ans.add(LPre + 1);
                }
            }
            return ans;
        }
    }

    // 哈希表实现 -> 常数时间慢
    class Solution2 {
        public List<Integer> findAnagrams(String s, String p) {
            // 对于本题，我只要控制住长度，然后 欠账表all为0的话，一定就是 anagram
            List<Integer> ans = new ArrayList<>();
            char[] str = s.toCharArray();
            char[] match = p.toCharArray();
            int matchLen = match.length;
            HashMap<Character, Integer> map = new HashMap<>();
            int all = matchLen;
            // 建立欠账表
            for (int i = 0; i < matchLen; i++) {
                if (!map.containsKey(match[i])) {
                    map.put(match[i], 0);
                }
                map.put(match[i], map.get(match[i]) + 1);
            }

            // 固定窗口模板，注意记录
            // 【注意】 本题要 保证固定窗口大小
            for (int R = 0; R < str.length; R++) {
                if (map.containsKey(str[R])) {
                    map.put(str[R], map.get(str[R]) - 1);
                    if (map.get(str[R]) >= 0) {
                        all--;
                    }
                }

                // 此时 L 右移（看是否需要右移）
                // 【错误点】注意，要右移的是 L前一个位置，求的是 L 前一个位置
                // int L = R - matchLen + 1;
                int LPre = R - matchLen;
                if (LPre >= 0 && map.containsKey(str[LPre])) {
                    map.put(str[LPre], map.get(str[LPre]) + 1);
                    if (map.get(str[LPre]) > 0) {
                        all++;
                    }
                }

                // 最终处理 -> 条件，因为上面已经保证L>=0时，时刻 LR 是固定大小窗口，所以 只要 [0,R]>=matchLen 且 满足all==0，就可以 收集信息
                if (R >= matchLen - 1 && all == 0) {
                    ans.add(LPre + 1);
                }
            }
            return ans;
        }
    }



}
