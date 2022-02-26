package jumpGame;

import java.util.*;

public class FrogCanCross {
    class Solution {
        HashMap<Integer, Integer> map = new HashMap<>();
        Boolean[][] dp;  // 官方题解很聪明一点，因为boolean无法标记为cache区域。 所以对于boolean类型，可以使用 Boolean[][] 来创建 dp表，这样 null 就可以作为无效值了
        public boolean canCross(int[] stones) {
            for (int i = 0; i < stones.length; i++) {
                map.put(stones[i], i);
            }
            int N = stones.length;
            dp = new Boolean[N][N];  // index和lastDist 的 范围都是 [0, N - 1]
            return process(0, 0, stones);
        }

        // 很关键的一个点就是 lastDist的最大值  -> 因为一开始是1， 每次最多加1，那么数组最后一个元素为N-1的话，lastDist最大为N-1， 具体一点就是 最差情况下【到第 i个位置的时候， lastDistance最大值 为 i】
        public boolean process(int index, int lastDist, int[] s) {

            if (dp[index][lastDist] != null) {
                return dp[index][lastDist];
            }

            if (index == s.length  - 1) {
                return true;
            }
            boolean ans = false;
            for (int i = -1; i <= 1; i++) {
                int jumpDist = lastDist + i;
                if (jumpDist <= 0) {      // 必须往前跳 | 也可以作为大的if 框在 for循环下面
                    continue;
                }
                int next = s[index] + jumpDist;
                if (map.containsKey(next)) {
                    ans |= process(map.get(next), lastDist + i, s);
                    if (ans) {      // 这就是一个小剪枝，但是速度提升了一半，看起来对于常数时间还是很重要的
                        break;
                    }
                }
            }
            dp[index][lastDist] = ans;
            return ans;
        }
    }
}
