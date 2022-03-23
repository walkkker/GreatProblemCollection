package coinchange;

public class CoinChange1 {
    /**
     之前直接一维的 思路不是很对，误打误撞对了而已。

     本题依旧是一个 二维DP问题。
     从左往右尝试模型，那么自然想到 dp[i][j]
     -> i 表示 从 index往后 coins中可选的面额
     -> j 表示 index.. 所要满足的rest

     从左往右尝试 -> 一个面额一个面额的走， index及其往后 负责 j的rest

     【注意】 对于i号面额， 可以选择【0】1 2 3 4， 就是说 也可以不选

     */
    class Solution {
        public int coinChange1(int[] coins, int amount) {
            int N = coins.length;
            int[][] dp = new int[N + 1][amount + 1];
            dp[N][0] = 0;

            // 【错误点】 N行 后面 要设置为 无效值，因为没有面额的情况下 凑不齐 任何amount
            for (int j = 1; j <= amount; j++) {
                dp[N][j] = Integer.MAX_VALUE;   // 设置成最大值的话，后面计算min时候就会消去影响。 【不对】 因为 有 + 1 操作，所以会导致结果变成最小值，所以完全不对！ 必须单独讨论。 【记住】任何题中，一旦设置了 无效值，一定要 每个答案 都单独 检验
                // 【错误点】有无效值 一定要，必须！ 单独讨论
            }

            for (int i = N - 1; i >= 0; i--) {
                for (int j = 1; j <= amount; j++) {


                    if (j - coins[i] >= 0 && dp[i][j - coins[i]] != Integer.MAX_VALUE) {
                        // 因为有 +1 操作，所以要 单独讨论
                        dp[i][j] = Math.min(dp[i][j - coins[i]] + 1, dp[i + 1][j]);
                    } else {
                        dp[i][j] = dp[i + 1][j];
                    }
                }
            }
            // 【错误点】 最后要注意 -> 如果无法组成，返回 -1.
            return dp[0][amount] == Integer.MAX_VALUE ? -1 : dp[0][amount];
        }

        // 压缩空间版本
        // 这种 只依赖 左和下的，空间压缩 把 0 列放到二维遍历中后，直接删除 第一个[]，就可以。 不需要别的操作。
        public int coinChange(int[] coins, int amount) {
            int N = coins.length;
            int[] dp = new int[amount + 1];
            dp[0] = 0;

            for (int j = 1; j <= amount; j++) {
                dp[j] = Integer.MAX_VALUE;
            }

            for (int i = N - 1; i >= 0; i--) {
                for (int j = 1; j <= amount; j++) {
                    if (j - coins[i] >= 0 && dp[j - coins[i]] != Integer.MAX_VALUE) {
                        // 因为有 +1 操作，所以要 单独讨论
                        dp[j] = Math.min(dp[j - coins[i]] + 1, dp[j]);
                    }
                }
            }
            // 【错误点】 最后要注意 -> 如果无法组成，返回 -1.
            return dp[amount] == Integer.MAX_VALUE ? -1 : dp[amount];
        }
    }


}
