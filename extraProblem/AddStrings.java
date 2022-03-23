package extraProblem;

public class AddStrings {

    // 不能转int计算
// 【注意】计算的时候 先算 字符串的高位 （也就是 整数的低位）
    class Solution {
        public String addStrings(String num1, String num2) {
            char[] chs1 = num1.toCharArray();
            char[] chs2 = num2.toCharArray();
            // 重定位 一下 longa shorta   (【错误点】变量名字不能是类型名字！！！ short long 不可以！)
            char[] longa = chs1.length > chs2.length ? chs1 : chs2;
            char[] shorta = longa == chs1 ? chs2 : chs1;
            int carry  = 0;
            StringBuilder builder = new StringBuilder();
            // 注意 从 字符串的高位 （int低位）开始算
            for (int i = longa.length - 1; i >= 0; i--) {
                int a = longa[i] - '0';
                int b = i >= longa.length - shorta.length ? shorta[i - (longa.length - shorta.length)] - '0' : 0;  // 如果 i 在 short范围内，下标变换直接取。不在的话，认为b==0，可以整合代码
                int tmp = a + b + carry;
                builder.append(String.valueOf(tmp % 10));
                carry = tmp / 10;
            }

            if (carry == 1) {
                builder.append(String.valueOf(1));
            }
            return builder.reverse().toString();  // 最后别忘了要reverse

        }

    }
}
