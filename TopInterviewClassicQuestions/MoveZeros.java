package TopInterviewClassicQuestions;

public class MoveZeros {

    // 双指针 + swap
// 遇到 0 位置就跳过，遇到非0元素 就与有效区的下一个位置进行交换
// 【核心】L对应 有效区， R对应搜索指针。 当R元素满足要求时，那么就交换到有效边界下一个位置，然后有效边界++。  当R元素不满足条件时，就直接跳过（不进入有效区）。就是这个思想， 最后就会 分为 有效区和无效区。
    class Solution {
        public void moveZeroes(int[] nums) {
            int L = -1;  // 有效区的右边界 （闭区间）
            for (int R = 0; R < nums.length; R++) { // 遍历的当前位置
                if (nums[R] != 0) {
                    swap(nums, ++L, R);    // 如果R与L之间有空间，那么一定是0
                }
            }
        }

        public void swap(int[] arr, int i, int j) {
            int tmp = arr[i];
            arr[i] = arr[j];
            arr[j] = tmp;
        }
    }
}
