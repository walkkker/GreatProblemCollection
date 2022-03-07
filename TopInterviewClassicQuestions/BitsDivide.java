package TopInterviewClassicQuestions;

public class BitsDivide {
    class Solution {
        // 本题需要使用位运算 实现 乘法和除法
        // 将 除法 变成 【乘法相加】 （这个乘法可以用 位运算代替）
        // x右移去够y， 防止y左移出现溢出或转负
        // 在所有数值中，只有系统最小值是没有办法转的 =》 系统最小值的绝对值还是自己
        // 所以要分情况讨论
    /*
    假设是 a / b
    1. a 是 MIN_VALUE， b 是 MIN_VALUE  =》 1
    2. a 不是 MIN_VALUE, b是 MIN_VALUE =》 0
    3. a 是 MIN_VALUE, b 不是MIN_VALUE =》 拆分算
    4. a,b 都不是 MIN_VALUE => 直接调用除法函数
     */

        public int divide(int dividend, int divisor) {
            int minVal = Integer.MIN_VALUE;
            if (dividend == minVal && divisor == minVal) {
                return 1;
            } else if (dividend == minVal) {
                if (divisor == -1) {
                    return Integer.MAX_VALUE;
                } else {
                    int tmp = div(dividend  + 1, divisor);   // 这边借了一部分
                    return tmp + div(dividend - multi(tmp, divisor), divisor);  // 这边就要 重新把借的还回去，然后 将两部分 加和
                }
            } else if (divisor == minVal) {   // 除数 > 被除数   ==> 结果为0
                return 0;
            } else {    //  都不为最小值，从而都可以转成正数计算
                return div(dividend, divisor);
            }
        }

        // 除不尽会向下取整 18/7 = 2
        // 这个除法必须是 两个正数的除法  =》 最后再转符号就可以
        // 这个算法必须是正数
        public int div(int a, int b) {
            // 【第一步，先转正数】
            int x = a >= 0 ? a : -a;
            int y = b >= 0 ? b : -b;

            // x 右移 == y 左移
            // 防止 y 越界
            int res = 0;
            for (int i = 30; i >= 0; i--) {    // 因为都是正数，所以只需要右移30位
                if ((x >> i) >= y) {
                    res |= (1 << i);
                    x -= (y << i);
                }
            }

            // res 就是 除法结果 （限制范围，能够转正数）
            // return res; 错了！！ 别忘了 把符号转回去
            return isNeg(a) ^ isNeg(b) ? -res : res;
        }


        public boolean isNeg(int num) {
            return num < 0;
        }

        public int multi(int a, int b) {
            int res = 0;
            while (b != 0) {
                if ((b & 1) == 1) {    // 以后注意 所有有关 位运算的计算 都必须要加上括号 （位运算优先非常低）
                    res += a;
                }
                a <<= 1;
                b >>>= 1;   // 必须无符号右移
            }
            return res;
        }
    }
}
