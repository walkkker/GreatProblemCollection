package sort;

public class MergeSort {

    public int[] sortArray(int[] nums) {
        process(nums, 0, nums.length - 1);
        return nums;
    }

    public void process(int[] arr, int L, int R) {
        if (L == R) {
            return;
        }
        int M = L + (R - L) / 2;
        process(arr, L, M);
        process(arr, M + 1, R);
        merge(arr, L, M, R);
    }


    public void merge(int[] arr, int L, int M, int R) {
        int cur1 = L;
        int cur2 = M + 1;
        int index = 0;
        int[] help = new int[R - L + 1];
        while (cur1 <= M && cur2 <= R) {
            help[index++] = arr[cur1] <= arr[cur2] ? arr[cur1++] : arr[cur2++];
        }
        while (cur1 <= M) {
            help[index++] = arr[cur1++];
        }
        while (cur2 <= R) {
            help[index++] = arr[cur2++];
        }
        for (index = 0; index < help.length; index++) {
            arr[L + index] = help[index];
        }
    }



    // 非递归
    class Solution {
        public int[] sortArray(int[] nums) {
            mergeSort(nums);
            return nums;
        }

        public void mergeSort(int[] nums) {
            int N = nums.length;
            int step = 1;
            while (step < N) {
                int L = 0;
                // [f, M]   [M + 1, R]
                int M = L + step - 1;
                while (M + 1 < N) {   // 【重点】只关心 是否存在 右组
                    int R = Math.min(M + step, N - 1);
                    merge(nums, L, M, R);
                    L = R + 1;
                    M = L + step - 1;
                }
                // 防止溢出， 所以提前 break
                if (step > N / 2) {
                    break;
                }
                step <<= 1;
            }
        }


        public void merge(int[] nums, int L, int M, int R) {
            int p1 = L;
            int p2 = M + 1;
            int[] help = new int[R - L + 1];
            int index = 0;
            while (p1 <= M && p2 <= R) {
                if (nums[p1] <= nums[p2]) {
                    help[index++] = nums[p1++];
                } else {
                    help[index++] = nums[p2++];
                }
            }
            while (p1 <= M) {
                help[index++] = nums[p1++];
            }
            while (p2 <= R) {
                help[index++] = nums[p2++];
            }

            for (index = 0; index < help.length; index++) {
                nums[L + index] = help[index];
            }
        }
    }
}
