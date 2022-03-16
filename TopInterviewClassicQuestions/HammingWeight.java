package TopInterviewClassicQuestions;

public class HammingWeight {
    public class Solution {
        // you need to treat n as an unsigned value
        // 方法1： 每次去掉最后一个1 （a & -a），然后计数次数就可以
        public int hammingWeight1(int n) {
            int count = 0;
            while (n != 0) {
                count++;
                int rightOne = n & (-n);
                n -= rightOne;
            }
            return count;
        }

        // 方法2 （进阶版）， 不需要 循环
        // 使用 规定位数上 二进制上的 值 来对应 十进制的值来表示 该规定位数上有多少个1
        // 一开始 从2位表示1的数量 -> 4 -> 8 -> 16 -> 32 。 到达 32位表示1的数量，就是返回值了。
        // 以 求2位表示1的数量为例（假设n=10）， 需要的 二进制数是 01， 然后(n & 01) + ((n >> 1) & 01), 得到的 二进制结果 就是 这两位上1的数量。 以此类推
        // 1+1合2， 2+2合4
        public int hammingWeight(int n) {
            n = (n & 0x55555555) + ((n >>> 1) & 0x55555555);
            n = (n & 0x33333333) + ((n >>> 2) & 0x33333333);
            n = (n & 0x0f0f0f0f) + ((n >>> 4) & 0x0f0f0f0f);
            n = (n & 0x00ff00ff) + ((n >>> 8) & 0x00ff00ff);
            n = (n & 0x0000ffff) + ((n >>> 16) & 0x0000ffff);
            return n;
        }
    }
}
