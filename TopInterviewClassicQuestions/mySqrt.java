package TopInterviewClassicQuestions;

// 两种解决方案

public class mySqrt {
// 其实就是使用二分法！！！
// 只是 比较的时候 将 本来的映射方式 从 arr[mid] 变成了 纯纯的 mid
// 详细一点，如果是求 double类型的 小数点后四位的平方根，
// 那么 假设目前在 求小数点的阶段 -> 0.200 那么映射之后就是 3.200 与 10 进行大小比较
// 【错误点！！！】单纯使用 int mid ==> mid * mid 会出现越界
    class Solution {
        public int mySqrt(int x) {
            if (x == 0) {
                return 0;
            }
            // [1, X]
            int L = 1;
            int R = x;
            int ans = 0;
            while (L <= R) {
                int mid = L + (R - L) / 2;
                if ((long) mid * (long) mid <= (long) x) {
                    ans = mid;
                    L = mid + 1;
                } else {
                    R = mid - 1;
                }
            }
            return ans;
        }
    }



// 所以 (1) 可以在 mid * mid 那里 转成 long 类型
// (2) 可以把 相关变量全部转成 long 类型，最后算完之后  转回 int 类型
    class Solution2 {
        public int mySqrt(int x) {
            if (x == 0) {
                return 0;
            }
            // [1, X]
            long L = 1;
            long R = x;
            long ans = 0;
            while (L <= R) {
                long mid = L + (R - L) / 2;
                if (mid * mid <=  x) {
                    ans = mid;
                    L = mid + 1;
                } else {
                    R = mid - 1;
                }
            }
            return (int) ans;
        }
    }
}
