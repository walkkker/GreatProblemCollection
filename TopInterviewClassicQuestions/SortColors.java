package TopInterviewClassicQuestions;

public class SortColors {

    // 荷兰国旗问题
// 以 1 做 pivot
// 【错题】
    class Solution {
        public void sortColors(int[] nums) {
            int N = nums.length;
            int less = -1;
            int more = N;
            int index = 0;
            int pivot = 1;
            // 【错误点】理解+记忆！【while循环条件 写错了！！！】 不是 less < more。
            // 这是荷兰国旗问题！ 是三段，所以 要是 index < more 就执行
            while (index < more) {
                if (nums[index] == pivot) {
                    index++;
                } else if (nums[index] < pivot) {
                    swap(nums, index++, ++less);
                } else {
                    swap(nums, index, --more);
                }
            }
        }

        public void swap(int[] nums, int i, int j) {
            int tmp = nums[i];
            nums[i] = nums[j];
            nums[j] = tmp;
        }
    }
}
