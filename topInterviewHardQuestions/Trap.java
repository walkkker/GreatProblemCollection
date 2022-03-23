package topInterviewHardQuestions;

public class Trap {
    // 【步骤】 就是 计算 最大值较小一侧指针的柱子，然后移动指针 + 更新该区间最大值。
    class Solution {
        // 哪一侧的最大值 较小， 就去结算哪一侧并移动指针 -> 这保证了 每个柱子都是以某一侧较小的最大值进行结算，这也就符合了 接雨水的特性（木桶原理 ->　两侧较低者为准）。
        //　说白了，我只需要知道 每一根柱子 哪一侧的最大值较小，那么那个较小值与柱子的高度之差）就是 在这个柱子上所能够 承接的 水量。
        public int trap(int[] height) {
            if (height.length <= 2) {
                return 0;
            }
            int N = height.length;
            // 去除两端位置 开始的 双端双指针
            int L = 1;
            int R = N - 2;
            // 初始化 左右两侧最大值
            int lmax = height[0];
            int rmax = height[N - 1];
            int ans = 0;
            // 边界 L == R 时，也要做计算该柱子的盛水量（与二分法边界要求相同，与缺失的第一个正数不同，因为 missingPositive 是两个区间，区间布满时就退出循环）
            while (L <= R) {
                if (lmax < rmax) {
                    int tmp = lmax - height[L];
                    ans += tmp >= 0 ? tmp : 0;
                    lmax = Math.max(lmax, height[L]);
                    L++;
                } else {
                    int tmp = rmax - height[R];
                    ans += tmp >= 0 ? tmp : 0;   // 注意 负数 的话，返回 0
                    rmax = Math.max(rmax, height[R]);
                    R--;
                }
            }
            return ans;
        }
    }
}
