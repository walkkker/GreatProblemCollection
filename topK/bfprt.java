package topK;

public class bfprt {

    // https://leetcode-cn.com/problems/kth-largest-element-in-an-array/

    /**
     * bfprt算法，它的核心 在于 划分值的选取。  通过划分值的选择，使得每次能够淘汰最小确定数量的数据，
     * 从而能够确定 最差
     * 情况下的数据剩余情况，最终能够 通过证明演算的方式求出 最终时间复杂度是线性的O（N）
     */
    class Solution {
        public int findKthLargest(int[] nums, int k) {
            int N = nums.length;
            // N - k
            return bfprt(nums, 0, N - 1, N - k);
        }

        // index就是要寻找的下标 （已经从 k 换算过来了）
        // arr[L..R]  如果排序的话，位于index位置的数，是什么，返回
        public int bfprt(int[] nums, int L, int R, int index) {
            if (L == R) {
                return nums[L];
            }
        /*
        步骤 -> medianOfMedians
        // L...R  每五个数一组
		// 每一个小组内部排好序
		// 小组的中位数组成新数组
		// 这个新数组的中位数返回，作为划分值 -> 在这一步中，求中位数数组的中位数， 又递归调用了bfpft来取得中间位置的数字
         */
            int ans = 0;
            while (L <= R) {
                int pivot = medianOfMedians(nums, L, R);
                int[] range = partition(nums, L, R, pivot);
                if (index < range[0]) {
                    R = range[0] - 1;
                } else if (index > range[1]) {
                    L = range[1] + 1;
                } else {
                    ans = nums[index];
                    break;
                }
            }
            return ans;
        }


        public int[] partition(int[] arr, int L, int R, int pivot) {
            int less = L - 1;
            int more = R + 1;
            int index = L;
            while (index < more) {
                if (arr[index] == pivot) {
                    index++;
                } else if (arr[index] < pivot) {
                    swap(arr, ++less, index++);
                } else {
                    swap(arr, --more, index);
                }
            }
            return new int[]{less + 1, more - 1};
        }

        // arr[L...R]  五个数一组
        // 每个小组内部排序  -> 因为都是 五个数字 一组，所以 每个小组内的时间复杂度 O（1），整体复杂度 O（N）
        // 每个小组中位数领出来，组成marr  -> O(N)
        // marr中的中位数，返回
        public int medianOfMedians(int[] arr, int L, int R) {
            // 【注意】是在 [L, R]区间上做5个一组的排序，然后取出中位数
            int size  = R - L + 1;
            // 首先确定组数，使用 offset 确定是否会有一个不满5个的组
            int offset = size % 5 == 0 ? 0 : 1;
            // 所以就可以得知 组数 -> size / 5 + offset
            int[] mArr = new int[size / 5 + offset];  // 组数
            for (int team = 0; team < mArr.length; team++) {
                // 0 组 1组 2组 ...
                // 0, 5, 10
                int teamFirst = L + team * 5;
                // 输入 L，R 获取其对应的中位数 ==》 将5个一组排序，并获得这一组的中位数
                mArr[team] = getMedian(arr, teamFirst, Math.min(teamFirst + 4, R));
            }
            // mArr填完了，返回其 乱序数组的 中位数，使用bfprt
            return bfprt(mArr, 0, mArr.length - 1, (mArr.length - 1) / 2);
        }

        // 排序 + 返回中位数
        public int getMedian(int[] arr, int L, int R) {
            insertionSort(arr, L, R);
            return arr[(L + R) / 2];
        }

        // 插入排序 是 冒泡选择插入中 常数时间最短的排序
        public void insertionSort(int[] arr, int L, int R) {
            for (int end = L + 1; end <= R; end++) {
                // 【错误点】一定要记住后面是 j--
                for (int j = end - 1; j >= L && arr[j] > arr[j + 1]; j--) {
                    swap(arr, j , j + 1);
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
