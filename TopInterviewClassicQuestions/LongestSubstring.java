package TopInterviewClassicQuestions;

public class LongestSubstring {

    /** 有点分治的感觉。
     1. 不能 单纯使用滑动窗口 -> 因为 窗口变大了并没有单调性
     2. 因为只包含有 小写字母：
     所以 枚举种类法：每一种情况下使用 滑动窗口
     子串 只含有一种字符， 每种都>=k
     子串 只含有2种字符，每种都>=k
     ...
     子串 只含有26种字符，每种都>=k
     3. count = new int[26]
     这个词频统计 的数组 可以跟 窗口内的字母种类数+窗口内达标的字母种类数 相联系
     */
    class Solution {
        public int longestSubstring(String s, int k) {
            int ans = 0;
            char[] chs = s.toCharArray();
            // 【错误点】是分治成了 1种字符，2种字符，所以总共26种字符！！不是k的分治
            for (int times = 1; times <= 26; times++) {
                int numDiff = 0;
                int numSatisfy = 0;
                int[] cnt = new int[26];
                int R = 0;
                for (int L = 0; L < chs.length; L++) {
                    while (R < chs.length && (numDiff < times || (numDiff == times && cnt[chs[R] - 'a'] > 0))) {
                        numDiff += cnt[chs[R] - 'a'] == 0 ? 1 : 0;
                        if ( ++cnt[chs[R] - 'a'] == k) {
                            numSatisfy++;
                        }
                        R++;
                    }
                    // 【错误点】numSatisfy == k错！！！
                    // 由于我们分治了，所以求得是kinds下，当numSatisfy满足kinds了，就说明有numSatisfy的字符种类 出现次数都>=k了，此时 当 numSatisfy == kinds，就可以收集结果
                    if (numSatisfy == times) {
                        ans = Math.max(ans, R - L);
                    }

                    if (cnt[chs[L] - 'a'] == 1) {
                        numDiff--;
                    }
                    // 【错误点】 if (--cnt[chs[L] - 'a'] < k)
                    // 这句话 也不对
                    // 只有当前字符满足了 k次，那么由于L++，所以要--。 只有这一次。如果用上述错误点的话，那么 没减一次，都有可能会 -numSatisfy， 就错误了。
                    if (cnt[chs[L] - 'a'] == k) {
                        numSatisfy--;
                    }
                    // 这个最后单独才能--
                    cnt[chs[L] - 'a']--;
                }
            }
            return ans;
        }
    }
}
