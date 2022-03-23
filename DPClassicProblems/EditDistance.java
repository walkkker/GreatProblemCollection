package DPClassicProblems;

public class EditDistance {
    class Solution {
        public int minDistance(String word1, String word2) {
            return editDistance(word1, word2, 1, 1, 1);
        }

        // 完全按照 README 进行的 状态转移
        public int editDistance(String word1, String word2, int add, int delete, int replace) {
            char[] chs1 = word1.toCharArray();
            char[] chs2 = word2.toCharArray();
            int N = chs1.length;
            int M = chs2.length;
            int[][] dp = new int[N + 1][M + 1];
            dp[0][0] = 0;
            for (int i = 1; i <= N; i++) {
                dp[i][0] = delete + dp[i - 1][0];
            }
            for (int j = 1; j <= M; j++) {
                dp[0][j] = delete + dp[0][j - 1];
            }

            for (int i = 1; i <= N; i++) {
                for (int j = 1; j <= M; j++) {
                    int p1 = dp[i][j - 1] + add;
                    int p2 = dp[i - 1][j] + delete;
                    int p3 = chs1[i - 1] == chs2[j - 1] ? dp[i - 1][j - 1] : dp[i - 1][j - 1] + replace;
                    dp[i][j] = Math.min(Math.min(p1, p2), p3);
                }
            }

            return dp[N][M];
        }
    }
}
