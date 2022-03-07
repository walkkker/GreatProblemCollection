package reverseIntegerSeries;

public class MyAtoi {

    public int myAtoi(String s) {
        char[] str = s.toCharArray();
        boolean positive = true;
        boolean num = false;
        int ans = 0;
        for (char ch : str) {
            if (num == false) {
                if (ch == ' ') {
                    continue;
                } else if (ch == '-') {
                    positive = false;
                    num = true;
                } else if (ch == '+') {
                    num = true;
                } else if (ch >= '0' && ch <= '9') {
                    ans -= ch - '0';
                    num = true;
                }
                // else if (ch > '9' || ch < '0') {  // 【错误点】这个条件一定要放到最后边，不然 '[-+ ]都会直接被当作错误字符 终止for loop'
                // 修改了 不上面写法了。 除了上述几种 if 情况外， else就行
                else {
                    break;
                }
            } else {
                if (ch < '0' || ch > '9') {
                    break;
                }
                int tmp = ch - '0';
                // 【错误点，越界条件没想明白】【注意我们操作的 是 负数】
                if (Integer.MIN_VALUE / 10 > ans || (Integer.MIN_VALUE / 10 == ans && Integer.MIN_VALUE % 10  > -tmp)) {
                    ans = Integer.MIN_VALUE;
                    break;
                }
                ans = ans * 10 - tmp;
            }
        }

        return positive ? (ans == Integer.MIN_VALUE ? Integer.MAX_VALUE : Math.abs(ans)) : ans;
    }
}
