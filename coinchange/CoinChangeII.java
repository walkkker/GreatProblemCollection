package coinchange;

public class CoinChangeII {
    /**
     【错题】
     二维dp

     index amount

     深刻理解从左往右的尝试模型的 直接dp用法，想想背包问题。 dp[i][j] 表示 i及其往后的范围，搞定j这么多的rest剩余数量
     那么 i 的依赖一定就是 i + 1，然后就是 对应 j这个 rest的 状态去憋就行了（比如说j就是剩余量，那么剩余量会减小）

     dp[i][j]表示 index往后的钱选项 ， 搞定 j 这么多rest钱数

     对于 index 的钱数，可以 取 0,1,2,3,4,5,6张

     于是，
     状态转移方程 -> dp[i][j] = dp[j + 1][j - coins[i] * m]   (m >= 0 && j - coins[i] * m >= 0)


     经过观察后发现 斜率优化的方向：

     dp[i][j] = dp[i][j - coins[i]] + dp[i + 1][j]

     */
    class Solution {

        // 斜率优化的 第一版本
        public int change2(int amount, int[] coins) {
            int N = coins.length;
            int[][] dp = new int[N + 1][amount + 1];
            dp[N][0] = 1;
            for (int i = N - 1; i >= 0; i--) {
                dp[i][0] = 1;
            }

            for (int i = N - 1; i >= 0; i--) {
                for (int j = 1; j <= amount; j++) {
                    if (j - coins[i] >= 0) {
                        dp[i][j] = dp[i][j - coins[i]] + dp[i + 1][j];
                    } else {
                        dp[i][j] = dp[i + 1][j];
                    }
                }
            }

            return dp[0][amount];
        }

        // 空间压缩， 基于上面的二维 来压缩
        public int change(int amount, int[] coins) {
            int N = coins.length;
            int[] dp = new int[amount + 1];
            dp[0] = 1;

            for (int i = N - 1; i >= 0; i--) {
                dp[0] = 1;
                for (int j = 1; j <= amount; j++) {
                    if (j - coins[i] >= 0) {
                        dp[j] = dp[j - coins[i]] + dp[j];
                    }
                }
            }

            return dp[amount];
        }
    }
}
