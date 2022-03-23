package slidingWindow;

import java.util.*;

public class MaxSlidingWindow {

// 滑动窗口的 最大最小值 更新结构
// 双端队列
// R先行的 固定窗口大小模板
    class Solution {
        public int[] maxSlidingWindow(int[] nums, int k) {
            Deque<Integer> deque = new LinkedList<>();
            // 会收集 N - k + 1 个答案
            int N = nums.length;
            int[] ans = new int[N - k + 1];
            int index = 0;

            // R先行的 固定窗口大小模板
            for (int R = 0; R < N; R++) {
                // Step1 R右移一位的操作
                while (!deque.isEmpty() && nums[deque.peekLast()] <= nums[R]) {
                    deque.pollLast();
                }
                deque.addLast(R);

                // Step2 检查 LPre
                // 每次要将LPre排出窗口， (LPre, R] == k. 当LPre>=0时，那么就要排出
                int LPre = R - k;
                if ((LPre >= 0) && deque.peekFirst() == LPre) {
                    deque.pollFirst();
                }

                // Step3 固定窗口信息的收集
                // 因为S2已经检查了，当L>=0时，一定保证 固定窗口大小，所以下面只需要检查[0,r]>=k就可以了
                if (R - k + 1 >= 0) {
                    ans[index++] = nums[deque.peekFirst()];
                }
            }
            return ans;
        }
    }





// 滑动窗口的 最大最小值 更新结构
// 双端队列
    class Solution2 {
        public int[] maxSlidingWindow1(int[] nums, int k) {
            Deque<Integer> deque = new LinkedList<>();
            // 会收集 N - k + 1 个答案
            int N = nums.length;
            int[] ans = new int[N - k + 1];
            int index = 0;
            for (int R = 0; R < N; R++) {
                // 滑动窗口  右边界右移 从last入队列， 左边界右移就从first出队列
                // Step1 右移之前，先从 队尾出，然后再压
                while (!deque.isEmpty() && nums[deque.peekLast()] <= nums[R]) {
                    deque.pollLast();
                }
                deque.addLast(R);

                //
                if (((R - k) >= 0) && deque.peekFirst() == (R - k)) {
                    deque.pollFirst();
                }

                // 此时就可以收集了
                if (R - k + 1 >= 0) {   // 【错误点】 注意弹出来的是下标。存到ans里面的是映射出来的nums中的值
                    ans[index++] = nums[deque.peekFirst()];
                }
            }
            return ans;
        }

        // 【错误点】如果是用L,R表示区间的话，R在开区间，会好写正确很多，记住！！！
        public int[] maxSlidingWindow(int[] nums, int k) {
            Deque<Integer> deque = new LinkedList<>();
            int N = nums.length;
            int[] ans = new int[N - k + 1];
            int L = 0;
            int R = 0;   //[L, R)

            while (R < N) {

                // 窗口为建立时R要右移，窗户建立时R和L都要右移。 所以R右移可以提取出来
                while (!deque.isEmpty() && nums[deque.peekLast()] <= nums[R]) {
                    deque.pollLast();
                }
                deque.addLast(R);
                R++;

                // 窗口建立起来时，收集答案 + 左右边界一起右移
                if (R - L == k) {
                    ans[L] = nums[deque.peekFirst()];
                    //L 右移
                    if (deque.peekFirst() == L) {
                        deque.pollFirst();
                    }
                    L++;
                }


            }
            return ans;
        }
    }
}
