package TopInterviewClassicQuestions;

import java.util.*;

public class HappyNum {

    // 简单做法： 判断 入环点， 每一个中间节点 加 set， 一直算下去就知道了
// 但是进一步的话： 有一个结论：
// 【结论】如果是快乐数的话，一定会回到1；如果不是快乐数的话，一定会出现4（出现4的话，一定不是快乐数，会出现循环）
    class Solution {
        // 通用写法
        public boolean isHappy1(int n) {
            HashSet<Integer> set = new HashSet<>();
            while (n != 1) {
                int tmp = 0;
                while (n != 0) {
                    tmp += (n % 10) * (n % 10);
                    n /= 10;
                }
                n = tmp;
                if (set.contains(n)) {
                    return false;
                } else {
                    // 【错误点】不要漏掉这一句
                    set.add(n);
                }
            }
            return true;
        }

        // 取巧写法： 根据结论，中间数字出现1一定就是快乐数，出现4一定就不是快乐数
        // 该方法 就不需要使用 set 检查【环】了
        public boolean isHappy(int n) {
            while (n != 1 && n != 4) {
                int tmp = 0;
                while (n != 0) {
                    tmp += (n % 10) * (n % 10);
                    n /= 10;
                }
                n = tmp;
            }
            return n == 1;
        }
    }
}
