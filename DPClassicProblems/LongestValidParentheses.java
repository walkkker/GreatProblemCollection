package DPClassicProblems;

public class LongestValidParentheses {
    class Solution {
        public int longestValidParentheses(String s) {
            if (s == null || s.length() < 2) {
                return 0;
            }
            char[] chs = s.toCharArray();
            int[] dp = new int[chs.length];
            dp[0] = 0;
            int max = 0;
            for (int i = 1; i < dp.length; i++) {
                if (chs[i] == '(') {
                    dp[i] = 0;
                } else {
                    // 【核心公式】
                    // 【注意】 一定要判断 L >= 0 和 L-1 >= 0 再执行后续判断。不然越界
                    int L = i - dp[i - 1] - 1;
                    if (L >= 0 && chs[L] == '(') {
                        dp[i] = 2 + dp[i - 1];
                        if (L - 1 >= 0) {
                            dp[i] += dp[L - 1];
                        }
                        max = Math.max(max, dp[i]);
                    }
                }
            }
            return max;
        }
    }
}
