package DPClassicProblems;

public class LongestPalindromeSubseq {

    // V1
    public int longestPalindromeSubseq(String s) {
        char[] str = s.toCharArray();
        int N = str.length;
        int[][] dp = new int[N][N];
        for (int i = N - 1; i >= 0; i--) {
            for (int j = i; j <= N - 1; j++) {
                if (i == j) {
                    dp[i][j] = 1;
                } else {
                    dp[i][j] = Math.max(dp[i + 1][j], dp[i][j - 1]);
                    if (str[i] == str[j]) {
                        dp[i][j] = Math.max(dp[i][j], 2 + dp[i + 1][j - 1]);
                    }
                }
            }
        }
        return dp[0][N - 1];
    }

    // 空间压缩版本，最优解
    public int longestPalindromeSubseqPro(String s) {
        char[] str = s.toCharArray();
        int N = str.length;
        int[] dp = new int[N];
        for (int i = N - 1; i >= 0; i--) {
            int tmp = dp[i];
            dp[i] = 1;
            for (int j = i + 1; j <= N - 1; j++) {
                int ans;
                ans = Math.max(dp[j], dp[j - 1]);
                if (str[i] == str[j]) {
                    ans = Math.max(dp[j], 2 + tmp);
                }
                tmp = dp[j];
                dp[j] = ans;
            }
        }
        return dp[N - 1];
    }
}
