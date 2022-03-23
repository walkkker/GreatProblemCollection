package slidingWindow;

import java.util.Deque;
import java.util.LinkedList;

public class LongestSubarray {
    /**
     时间复杂度 O（N）
     滑动窗口的最大最小值更新结构 + 舍弃可能性！
     只需要返回最长，所以可以舍弃可能性！
     */
    class Solution {
        public int longestSubarray(int[] nums, int limit) {
            if (limit < 0) {   // 绝对值差<0，那肯定没有答案，返回0
                return 0;
            }
            Deque<Integer> qmax = new LinkedList<>();
            Deque<Integer> qmin = new LinkedList<>();
            int ans = 0;
            int R = 0; // [L, R)
            for (int L = 0; L < nums.length; L++) {
                // 【错误点】 少了 R == L, 没有数字的时候.无法从qmax和qmin中弹出元素，此时说明 [L,R)范围内无元素，那么下一个加入下一个元素的话，绝对值是0，肯定是可以加的。所以直接写成L==R的情况下，R<nums.length的话，也可以替代后面的 !qmax.isEmpty ? 时的分支结构（防止出现nums[null]的异常问题）
                // 【核心】因为至少要让窗口内有一个值，所以添加 L==R 时也可以 右扩的条件，注意是 ||. 而且窗口内只有一个数字的时候，一定是满足条件的，所以右扩没有问题。
                while (R < nums.length && (R == L || Math.abs(Math.max(nums[R], nums[qmax.peekFirst()]) - Math.min(nums[qmin.peekFirst()], nums[R])) <= limit)) {

                    // 当前窗口内最大值最小值
                    // qmax 更新， qmin 更新
                    while (!qmax.isEmpty() && nums[qmax.peekLast()] <= nums[R]) {
                        qmax.pollLast();
                    }
                    qmax.addLast(R);
                    while (!qmin.isEmpty() && nums[qmin.peekLast()] >= nums[R]) {
                        qmin.pollLast();
                    }
                    qmin.addLast(R);
                    // 【遗漏！】千万不要忘了最后一步
                    R++;
                }

                if (Math.abs(nums[qmax.peekLast()] - nums[qmin.peekLast()]) <= limit) {
                    ans = Math.max(ans, R - L);
                }

                if (qmin.peekFirst() == L) {
                    qmin.pollFirst();
                }
                if (qmax.peekFirst() == L) {
                    qmax.pollFirst();
                }
                if (R == L) {
                    R++;
                }
            }
            return ans;
        }
    }
}
