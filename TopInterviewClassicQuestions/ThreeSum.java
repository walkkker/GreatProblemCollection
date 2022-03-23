package TopInterviewClassicQuestions;

import java.util.*;

public class ThreeSum {
    /**
     排序 双指针

     两数之和 -> 三数之和

     去重很精妙 - 看代码

     */
    class Solution {
        public List<List<Integer>> threeSum(int[] nums) {
            Arrays.sort(nums);    // 一定要记得先排序
            int N = nums.length;
            List<List<Integer>> ans = new ArrayList<>();
            for (int i = N - 1; i >= 2; i--) {
                if (i == N - 1 || nums[i] != nums[i + 1]) {             // 去重
                    // 【错误点】 R的位置要填的是 [0, i - 1]
                    List<List<Integer>> cur = findPairs(nums, 0, i - 1, -nums[i]);
                    for (List<Integer> tmp : cur) {
                        tmp.add(nums[i]);
                        ans.add(tmp);
                    }
                }
            }
            return ans;
        }

        // Step1: 先做有序数组的两数之和，再做三数之和 (去重版本)
        public List<List<Integer>> findPairs(int[] nums, int L, int R, int K) {
            List<List<Integer>> ans = new ArrayList<>();
            while (L < R) {  // 【注意】 L != R， 必须 L != R， 才能为 两个数字
                if (nums[L] + nums[R] > K) {
                    R--;
                } else if (nums[L] + nums[R] < K) {
                    L++;
                } else {
                    if (L == 0 || nums[L] != nums[L - 1]) { // 这个条件是用来去重的
                        List<Integer> cur = new ArrayList<>();
                        cur.add(nums[L]);
                        cur.add(nums[R]);
                        ans.add(cur);
                    }
                    L++;
                    R--;
                }
            }
            return ans;
        }
    }


    // 带错误点
    class Solution1 {
        public List<List<Integer>> threeSum(int[] nums) {
            if (nums == null || nums.length < 3) {
                return new ArrayList<>();
            }

            // 【错误点】 一定要 先排序！！ 不然无法做到 两数之和 以及 后续的去重操作，一定要通过排序将这些相同数字 并在一个 group里，相邻。
            Arrays.sort(nums);

            List<List<Integer>> ans = new ArrayList<>();
            int N = nums.length;
            for (int i = N - 1; i >= 2; i--) {
                if (i == N - 1 || nums[i] != nums[i + 1]) {
                    List<List<Integer>> pairs = findPairs(nums, 0, i - 1, -nums[i]);
                    for (List<Integer> pair : pairs) {
                        pair.add(nums[i]);
                        ans.add(pair);
                    }
                }
            }
            return ans;
        }

        public List<List<Integer>> findPairs(int[] nums, int L, int R, int K) {
            List<List<Integer>> ans = new ArrayList<>();
            while (L < R) {
                if (nums[L] + nums[R] < K) {
                    L++;
                } else if (nums[L] + nums[R] > K) {
                    R--;
                } else {
                    // 【注意】 带去重
                    if (L == 0 || nums[L] != nums[L - 1]) {
                        List<Integer> pair = new ArrayList<>();
                        pair.add(nums[L]);
                        pair.add(nums[R]);
                        ans.add(pair);
                    }
                    L++;
                    R--;
                }
            }
            return ans;
        }
    }
}
