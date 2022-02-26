package sellstock;

public class StockCollection {

    public static int maxProfit(int[] prices) {
        int ans = Integer.MIN_VALUE;  // 计算的时候， 最小值的选取包含当前值。  这样子，如果i最小，那么ans = 0； 否则，ans = arr[i] - min
        int min = Integer.MAX_VALUE;
        for (int i = 0; i < prices.length; i++) {
            min = Math.min(min, prices[i]);
            ans = Math.max(ans, prices[i] - min);
        }
        return ans;
    }

    // II
    public int maxProfitII1(int[] prices) {
        int N = prices.length;
        int ans = 0;
        for (int i = 1; i < N; i++) {
            ans += Math.max(prices[i] - prices[i - 1], 0);
        }
        return ans;
    }


    public int maxProfitII2(int[] prices) {
        int N = prices.length;
        int[] dp = new int[N];
        for (int i = 1; i < N; i++) {
            dp[i] = dp[i - 1] + Math.max(-prices[i - 1], -prices[i]) + prices[i];
        }
        return dp[N - 1];
    }


    // 第三版
    public int maxProfitIII(int[] prices) {
        // 以每一天为第二次卖出的结尾 求最大值
        // 初始化第一天的情况
        int N = prices.length;
        int min = prices[0];
        int ans = 0;
        int onceMax = 0; // 第一次交易的max
        int onceMinusBuyMax = -prices[0]; // 这世最关键的变量，每一个位置前面部分，最大第一次交易 - 后面最小的买入价
        for (int i = 1; i < N; i++) {
            // 计算当前位置作为第二次卖出时刻时的最大利润 （ans记录遍历每个位置时的最大值）
            ans = Math.max(ans, onceMinusBuyMax + prices[i]);
            // 还需要更新其他值，为后续的 位置计算做准备
            min = Math.min(min, prices[i]);
            onceMax = Math.max(onceMax, prices[i] - min);
            onceMinusBuyMax = Math.max(onceMinusBuyMax, onceMax - prices[i]); // [1]onceMax更新了 【2】onceMax没更新，但是 当前这个点可以作为第二次交易的最佳买入点
        }
        return ans;
    }


    // 第四版
    public int maxProfitIV(int k, int[] prices) {
        int N = prices.length;
        if (N < 2) {
            return 0;
        }
        int[][] dp = new int[N][k + 1];
        for (int j = 1; j <= k; j++) {
            int bestBuy = -prices[0];
            for (int i = 1; i < N; i++) {
                bestBuy = Math.max(bestBuy, dp[i][j - 1] - prices[i]);
                dp[i][j] = Math.max(bestBuy + prices[i], dp[i - 1][j]);
            }
        }
        return dp[N - 1][k];
    }

    // V
    public int maxProfitV1(int[] prices) {

        int N = prices.length;
        if (N < 2) {
            return 0;
        }
        int[] buy = new int[N];
        int[] sell = new int[N];
        // buy[i] = Math.max(buy[i - 1], sell[i - 2] - prices[i])
        // sell[i] = Math.max(sell[i - 1], buy[i] + prices[i])
        buy[1] = Math.max(-prices[0], -prices[1]);
        sell[1] = Math.max(0, prices[1] - prices[0]);
        for (int i = 2; i < N; i++) {
            buy[i] = Math.max(buy[i - 1], sell[i - 2] - prices[i]);
            sell[i] = Math.max(sell[i - 1], buy[i] + prices[i]);
        }
        return sell[N - 1];
    }

    public int maxProfitV2(int[] prices) {

        int N = prices.length;
        if (N < 2) {
            return 0;
        }
        // buy[i] = Math.max(buy[i - 1], sell[i - 2] - prices[i])
        // sell[i] = Math.max(sell[i - 1], buy[i] + prices[i])
        int buyPre = Math.max(-prices[0], -prices[1]);
        int sellPre = Math.max(0, prices[1] - prices[0]);
        int sellPrePre = 0;
        for (int i = 2; i < N; i++) {
            buyPre = Math.max(buyPre, sellPrePre - prices[i]);
            sellPrePre = sellPre;
            sellPre = Math.max(sellPre, buyPre + prices[i]);
        }
        return sellPre;
    }



    // VI
    public int maxProfitVI(int[] prices, int fee) {
        int N = prices.length;
        if (N < 2) {
            return 0;
        }
        // buy[i] = Math.max(buy[i - 1], sell[i - 1] - prices[i])
        // sell[i] = Math.max(sell[i - 1], buy[i - 1] + prices[i])
        int buy = -prices[0] - fee;
        int buyPre = 0;
        int sell = 0;
        for (int i = 1; i < N; i++) {
            buyPre = buy;
            buy = Math.max(buy, sell - prices[i] - fee);
            sell = Math.max(sell, buyPre + prices[i]);
        }
        return sell;
    }


}
