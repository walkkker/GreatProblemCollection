package TopInterviewClassicQuestions;

public class GetSum {
    class Solution {
        public int getSum(int a, int b) {
            // 异或就是无进位相加
            // a&b再左移一位 就是 进位信息

            //int sum = 0; // 【错误点】  sum 应该初始值为a，这样当b==0时，直接返回a
            int sum = a;

            // 将b 复用作进位信息，为0时停止
            while (b != 0) {
                sum = a ^ b;
                b = (a & b) << 1;
                a = sum;
            }
            return sum;
        }
    }
}
