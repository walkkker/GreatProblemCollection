package TopInterviewClassicQuestions;

public class NumSquares {

    /**
     （1）数学定理法：
     一个数学定理可以帮助解决本题：「四平方和定理」。

     四平方和定理证明了任意一个正整数都可以被表示为至多四个正整数的平方和。这给出了本题的答案的上界。

     */

    // 利用四平方和定理
    class Solution {
        public int numSquares1(int n) {
            // 把 4 除干净
            while (n % 4 == 0) {
                n /= 4;
            }

            // 是 4 的情况
            if (n % 8 == 7) {
                return 4;
            }

            // 处理 1 和 2 的情况。 如果 1 2 4 都不中，那么就返回3

            // 【注意】， Math 的数学库 函数 返回值 都是 double pow(), random()
            int a =  (int) Math.sqrt(n);    // square root
            if (a * a == n) {
                return 1;
            }

            // 测试 是否会是 2 的情况, 遍历 [1, 根号n]， 计算 rest. 看 rest是不是1个数字的完全平方数
            for (int i = 1; i * i <= n; i++) {
                int rest = n - i * i;
                int b = (int) Math.sqrt(rest);
                if (b * b == rest) {
                    return 2;
                }
            }

            return 3;
        }

        // 动态规划方法2
        // dp[i] 表示 i 被拆分成 完全平方数 的最小数量
        public int numSquares(int n) {
            if (n == 0) {   // 特殊处理一下，因为 dp[0]作为边界条件，要设置成0才成立dp状态方程
                return 1;
            }
            int[] dp = new int[n + 1];
            dp[0] = 0;
            for (int i = 1; i <= n; i++) {
                int min = Integer.MAX_VALUE;
                for (int j = 1; j * j <= i; j++) {
                    min = Math.min(min, dp[i - j * j]);   // 【错误点】这里 i 的意思要明确，就是当前 i 的最小完全平方数和的 个数
                }
                dp[i] = 1 + min;
            }
            return dp[n];
        }
    }
}
