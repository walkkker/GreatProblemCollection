package topInterviewHardQuestions;

public class FindKthNumberInDictionaryOrder {
    class Solution {
        public int findKthNumber(int n, int k) {
            // 因为要找寻第k个，那么 如果将整棵树看作是一个大数组的话，那么 寻找第k个就是 寻找下标为 k - 1的元素。
            k--;

            // 范围是 [1, n]，所以头结点就是 1
            int cur = 1;

            // 当前子树节点数量size > index, 我就向下找 （因为当前节点是第一个数，所以k--）
            // 当前子树 size <= index, 往右找 （跨过整棵树，所以 k - getSteps(cur)）
            // 注意 下标的变更

            // 不断的变更，最终 将下标变成0位置，就是 要找的元素了
            while (k > 0) {
                int steps = (int) getSteps(cur, n);   // 【注意】转回 int
                if (k < steps) {   // 左侧的范围就是 [0, steps - 1]，所以 k < steps 时，往下找； 否则往右找！
                    cur = cur * 10;   // 往下找，来到下一层的第一个节点 cur * 10
                    k--;              // 在当前节点，找 k-- 的下标 （因为头结点是第一个，所以要减去）

                } else {
                    cur++;    // 往右找，那么来到 同一层的 右侧节点 cur = cur + 1
                    k = k - steps;   // 在该节点找 哪一个 下标？  因为跳过了 之前的整棵树，所以 减去 所有的 节点数
                }
                // 然后就会 循环回去，如果此时 k == 0 了， 那么说明 当前来到的节点就是第0下标的节点，也就是我们要找的节点！，退出循环，返回答案即可
            }

            return cur;


        }


        // 注意转成 long 类型的 n， 防止 first*10 之后 溢出
        // 功能： 来到当前节点 cur, 返回 以 cur 为头的子树 的 总节点数
        // n 一定要有，n卡着 last的最大可能值
        public int getSteps(int cur, long n) {
            long first = cur;
            long last = cur;
            int steps = 0;
            while (first <= n) {   // first<=n 说明当前层，一定有数。 但是last可能越界，所以min
                steps += Math.min(last, n) - first + 1;
                first *= 10;           // 继续变成最左节点， 那就是 后面填个 0
                last = last * 10 + 9;  // 继续变成 最右节点， 那就是 后面填个 9
            }
            return steps;
        }
    }
}
