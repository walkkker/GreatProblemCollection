package longestIncreasingSubsequence;

public class LongestIncreasingSubseq {

    public int lengthOfLIS(int[] nums) {
        int N = nums.length;
        int[] order = new int[N];
        int size = 0;    // 可以使用size去标记最后一个位置（开区间）， 也可以使用 最后一个位置end 去标记最后一个位置（闭区间）
        for (int i = 0; i < N; i++) {
            int pre = bSearch(order, nums[i], 0, size - 1);
            order[pre + 1] = nums[i];
            if (pre + 1 == size) {
                size++;
            }
        }
        return size;
    }

    // < tar 的最右元素下标  -> 得到的位置为pre(我们要放入后一个位置)， 然后order[pre + 1] = tar。 并检查是否 size 变大
    public int bSearch(int[] arr, int tar, int L, int R) {
        int ans = -1;
        while (L <= R) {
            int mid = (L + R) / 2;
            if (arr[mid] < tar) {
                ans = mid;
                L = mid + 1;
            } else {
                R = mid - 1;
            }
        }
        return ans;
    }




    // 下面是 使用 end 而不是 size 的版本


    public int lengthOfLIS1(int[] nums) {
        int N = nums.length;
        int[] order = new int[N];
        int end = -1;   // 【更改点1】 值变了
        for (int i = 0; i < N; i++) {
            int pre = bSearch(order, nums[i], 0, end);  // 【更改点2】 L,R 中 R的范围直接对应end
            order[pre + 1] = nums[i];
            if (pre + 1 > end) {    // 【更改点3】 当新的位置 > end 时， end++ OR end = pre + 1;
                end++;
            }
        }
        return end + 1;    // 【更改点4】 数组是从0开始计数的，所以 end 对应的 size就是 【end + 1】
    }

    // < tar 的最右元素下标
    public int bSearch1(int[] arr, int tar, int L, int R) {
        int ans = -1;
        while (L <= R) {
            int mid = (L + R) / 2;
            if (arr[mid] < tar) {
                ans = mid;
                L = mid + 1;
            } else {
                R = mid - 1;
            }
        }
        return ans;
    }

}
