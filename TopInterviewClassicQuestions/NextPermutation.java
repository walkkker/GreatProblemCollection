package TopInterviewClassicQuestions;

public class NextPermutation {

    class Solution {
        public void nextPermutation(int[] nums) {
            // S1: 为使变化最小，所以从右往左找 第一个 较小值 （这种情况下，右侧全降序说明是最大的，下一个排列 就要 进位然后变成最小了）
            int smaller = -1;   // 默认值 是 -1， 如果找不到，那就 -1 表示当前是 最大值
            int N = nums.length;
            for (int i = N - 2; i >= 0; i--) {
                if (nums[i] < nums[i + 1]) {
                    smaller = i;   // 从右往左寻找到 第一个 下坡
                    break;
                }
            }

            if (smaller == -1) {   // 从右往左全上坡，那么smaller==-1,此时是 最大排列
                reverse(nums, 0, N - 1);
            } else {
                // 否则的话， 从右往左找 第一个 > smaller 的 【较大值】
                // 这个较大值 与 较小值 交换就可以了， 就表示 原本【较小值】上的进位
                // 进位后，右区间要变成最小 （恰好，交换完成之后，右区间保持降序）

                int bigger = N;

                // 【错误点】注意区间表示，要在右侧区间找 右侧区间为 [smaller + 1, N - 1]
                for (int i = N - 1; i > smaller; i--) {
                    // 【错误点】 注意 smaller 标识的是 下标， 要进行 nums[smaller]的映射来找到 较大值
                    if (nums[i] > nums[smaller]) {
                        bigger = i;    // 找到从右往左 右侧区间内 第一个>【较小值】的【较大值，】，该值也是 要交换过去的 进位值
                        break;
                    }
                }

                swap(nums, smaller, bigger);   // 进位完成

                // 右侧区间 逆转 -> 变成升序
                // 右侧区间范围 -> [smaller + 1, N - 1]
                reverse(nums, smaller + 1, N - 1);

            }

        }

        public void swap(int[] arr, int i, int j) {
            int tmp = arr[i];
            arr[i] = arr[j];
            arr[j] = tmp;
        }

        public void reverse(int[] arr, int L, int R) {
            while (L < R) {
                swap(arr, L++, R--);
            }
        }
    }

}
