package DPClassicProblems;

public class LongestCommonSubsequence {


    // 无空间压缩
    public int longestCommonSubsequence(String text1, String text2) {
        char[] str1 = text1.toCharArray();
        char[] str2 = text2.toCharArray();
        int[][] dp = new int[str1.length][str2.length];
        dp[0][0] = str1[0] == str2[0] ? 1 : 0;
        for (int i = 1; i < str1.length; i++) {
            dp[i][0] = dp[i - 1][0] == 1 ? 1 : (str1[i] == str2[0] ? 1 : 0);
        }
        for (int j = 1; j < str2.length; j++) {
            dp[0][j] = dp[0][j - 1] == 1 ? 1 : (str1[0] == str2[j] ? 1 : 0);
        }
        for (int i = 1; i < str1.length; i++) {
            for (int j = 1; j < str2.length; j++) {
                dp[i][j] = Math.max(dp[i - 1][j], dp[i][j - 1]);
                if (str1[i] == str2[j]) {
                    dp[i][j] = Math.max(dp[i][j], 1 + dp[i - 1][j - 1]);
                }
            }
        }
        return dp[str1.length - 1][str2.length - 1];
    }

    // 结合空间压缩， 【两个变量替代左上角位置】
    public int longestCommonSubsequencePro(String text1, String text2) {
        char[] str1 = text1.toCharArray();
        char[] str2 = text2.toCharArray();
        int[] dp = new int[str2.length];
        dp[0] = str1[0] == str2[0] ? 1 : 0;
        for (int j = 1; j < str2.length; j++) {
            dp[j] = dp[j - 1] == 1 ? 1 : (str1[0] == str2[j] ? 1 : 0);
        }
        for (int i = 1; i < str1.length; i++) {
            int leftUp = dp[0];
            dp[0] = dp[0] == 1 ? 1 : (str1[i] == str2[0] ? 1 : 0);
            for (int j = 1; j < str2.length; j++) {
                int ans;
                ans = Math.max(dp[j], dp[j - 1]);
                if (str1[i] == str2[j]) {
                    ans = Math.max(ans, 1 + leftUp);
                }
                leftUp = dp[j];
                dp[j] = ans;
            }
        }
        return dp[str2.length - 1];
    }
}
