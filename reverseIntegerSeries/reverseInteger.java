package reverseIntegerSeries;

public class reverseInteger {
    public int reverse(int x) {
        // 使用 负数 来 承接
        // 这类问题时，要注意越界问题，所以首先将Integer.MIN_VALUE / 10 , 和 Integer.MIN_VALUE % 10 记录下来
        // quotient 商
        int minQ = Integer.MIN_VALUE / 10;
        int minR = Integer.MIN_VALUE % 10;
        int ans = 0;
        boolean isPositive = x >= 0 ? true : false;
        x = isPositive ? x :-x;   // 要让x变成一个正数
        while (x > 0) {
            if (ans < minQ || (ans == minQ && x % 10 < minR)) {
                ans = 0;
                break;
            }
            ans = (ans * 10) - (x % 10);
            x /= 10;
        }
        return isPositive ? (ans == Integer.MIN_VALUE ? 0 : Math.abs(ans)) : ans;
    }
}
