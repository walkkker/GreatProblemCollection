package TopInterviewClassicQuestions;

public class MyPow {
    class Solution {
        // 【1】pow 功能函数的实现
        // 【2】针对负数的特定处理 （非MIN_VALUE,转正值） + （MIN_VALUE 特殊处理）
        public double myPow(double x, int n) {

            int pow = n == Integer.MIN_VALUE ? n + 1 : n;   // 此时，任何数字都可以转正数啦！
            pow = pow >= 0 ? pow : -pow;

            double ans = 1D;
            double t = x;
            while (pow != 0) {
                if ((pow & 1) == 1) {
                    ans *= t;
                }
                pow >>= 1;
                t *= t;
            }

            // 此时开始处理 如果是 Integer.MIN_VALUE的情况， 此时依然假设还是正数次幂，所以补上一个*x
            if (n == Integer.MIN_VALUE) {
                ans *= x;
            }

            // 此时根据正负数 返回对应结果
            return n >= 0 ? ans : (1D / ans);
        }
    }
}
