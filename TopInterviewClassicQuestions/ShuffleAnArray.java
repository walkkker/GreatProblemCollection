package TopInterviewClassicQuestions;

public class ShuffleAnArray {
    /**
     0-n-1上做一个随机，把它交换到最后一个位置。
     然后 0-n-2做一个随机，把它交换到n-2位置上去。
     循环。

     【原理，核心】假设总共有5个位置，对于每个数字而言
     第一个位置的概率： 1/5
     第二个位置的概率: 4/5(没有在第一个位置) * 1/4（剩下四个中，选择了 剩下四个位置中的第一个位置） = 1/5
     第三个位置的概率： 4/5 * 3/4 * 1/3 = 1/5

     所以，对于任意一个数字而言，他去到每一个位置的概率 都是 相同的，从而就实现了 等概率打乱数组。
     */
    class Solution {
        int[] origin;
        int[] shuffle;
        int N;


        public Solution(int[] nums) {
            origin = nums;
            N = nums.length;
            shuffle = new int[N];
            // 初始化 shuffle数组
            for (int i = 0; i < N; i++) {
                shuffle[i] = nums[i];
            }
        }

        public int[] reset() {
            return origin;
        }

        public int[] shuffle() {
            // 从后往前遍历，每一个位置 选取 当前范围内的一个随机位置交换到当前位置上
            for (int i = N - 1; i >= 0; i--) {
                // 每一个位置 应该选择的范围 在 [0, i]
                int selected = (int) (Math.random() * (i + 1));
                swap(shuffle, selected, i);
            }
            return shuffle;
        }

        private void swap(int[] arr, int i, int j) {
            int tmp = arr[i];
            arr[i] = arr[j];
            arr[j] = tmp;
        }
    }

/**
 * Your Solution object will be instantiated and called as such:
 * Solution obj = new Solution(nums);
 * int[] param_1 = obj.reset();
 * int[] param_2 = obj.shuffle();
 */
}
