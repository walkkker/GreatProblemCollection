package topInterviewHardQuestions;


public class Code04_FindKthMinNum {


    // 最后结合 【一二阶段模型】 套入 lc 习题，实现最优解 O(log (min{M, N}))
    // 结合本题的数据情况，千万不要忘了 -> nums1 nums2 长度为0的情况  -> 需要单独讨论
    public static double findMedianSortedArrays(int[] nums1, int[] nums2) {
        int allLen = nums1.length + nums2.length;
        boolean even = (allLen & 1) == 0;
        // 偶数 取 上中和下钟/2， 奇数直接取中间值
        // 分情况讨论是否 存在 数组长度为0的情况
        if (nums1.length > 0 && nums2.length > 0) {
            if (even) {
                return ((double) (findKthNum(nums1, nums2, allLen / 2)
                        + findKthNum(nums1, nums2, (allLen / 2) + 1))) / 2D;
            } else {
                // size / 2 向上取整
                return findKthNum(nums1, nums2, (allLen + 1) / 2);
            }
        } else if (nums1.length > 0) {  // 【错误点】size/2 得到的是 下中位数的下标
            int l1 = nums1.length;
            if (even) {
                return ((double) (nums1[l1 / 2] + nums1[l1 / 2 - 1])) / 2;
            } else {
                // 只有一个数组，返回下标的时候 不需要向上取整
                return nums1[l1 / 2];
            }
        } else if (nums2.length > 0) {
            int l2 = nums2.length;
            if (even) {
                return ((double) (nums2[l2 / 2] + nums2[l2 / 2 - 1])) / 2;
            } else {
                // 只有一个数组，返回下标的时候 不需要向上取整
                return nums2[l2 / 2];
            }
        } else {  // 千万别忘了这个，两个数组都为len==0的情况
            return 0;
        }


    }


    // 【第二阶段】在两个长度不相等的有序数组中，找到第Kth的数值
    // 使用 l = 17, s = 10 举例子
    public static int findKthNum(int[] arr1, int[] arr2, int kth) {
        // 重定位 -> 长短数组
        int[] longs = arr1.length > arr2.length ? arr1 : arr2;
        int[] shorts = longs == arr1 ? arr2 : arr1;
        int l = longs.length;
        int s = shorts.length;
        // 基于【第一阶段】 -> 分为三种情况讨论
        // 1 <= K <= s
        // s< K <= l
        // s < K <= 【整体长度】
        if (kth <= s) {        // 注意这里不要写 arr1 ,arr2了，要改成 longs + shorts
            return getUpMedian(longs, 0, kth - 1, shorts, 0, kth - 1);
        }
        if (kth > l) {
            if (longs[kth - s - 1] >= shorts[s - 1]) {
                return longs[kth - s - 1];
            }
            if (shorts[kth - l - 1] >= longs[l - 1]) {
                return shorts[kth - l - 1];
            }
            // 手动淘汰掉 上面两个位置后， 计算结果才可以正确
            return getUpMedian(longs, kth - s, l - 1, shorts, kth - l, s - 1);
        }
        // 最后一中情况，就是 s < k <= l
        // 单独检验 longs[kth - s - 1], 从而获得 相同长度数组
        if (longs[kth - s - 1] > shorts[s - 1]) {
            return longs[kth - s - 1];
        }
        return getUpMedian(longs, kth - s, kth - 1, shorts, 0, s - 1);
    }

    // 【第一阶段】 -> 找到 相同长度的两个有序数组的 第中间小的数字（偶数就取上中位数，奇数就取中位数），返回对应的数值
    // (首 + 尾) / 2  == 首 + （尾 - 首） / 2， 取得的是 【上中位数】（奇数的中位数，偶数的上中位数）
    public static int getUpMedian(int[] A, int sA, int eA, int[] B, int sB, int eB) {
        // 先设定中位数的位置
        int midA = 0;
        int midB = 0;

        // 因为 递归过程中 [s1,e1] == [s2,e2]，所以 只要while(s1 < e1)，那么就说明数组中元素>=2，此时递归
        // 当 s1 == e1时，元素个数相同都为1。此时寻找 第一小，也就是 Math.min（）
        while (sA < eA) {
            midA = sA + (eA - sA) / 2;
            midB = sB + (eB - sB) / 2;
            // 奇数偶数情况下，中位数相同 处理结果相同
            if (A[midA] == B[midB]) {
                return A[midA];
            }

            // 奇数情况下
            if (((eA - sA + 1) & 1) == 1) {
                if (A[midA] > B[midB]) {
                    if (B[midB] >= A[midA - 1]) {
                        return B[midB];
                    } else {
                        eA = midA - 1;
                        sB = midB + 1;
                    }
                } else {
                    if (A[midA] >= B[midB - 1]) {
                        return A[midA];
                    } else {
                        eB = midB - 1;
                        sA = midA + 1;
                    }
                }
            } else {   //偶数情况下, 这里不可以直接复制上面，只可以内部复制！
                if (A[midA] > B[midB]) {
                    eA = midA;
                    sB = midB + 1;
                } else {
                    eB = midB;
                    sA = midA + 1;
                }
            }
        }
        return Math.min(A[sA], B[sB]);
    }

    public static void main(String[] args) {
        int[] nums1 = {1, 2};
        int[] nums2 = {3, 4};
        findMedianSortedArrays(nums1, nums2);
    }

}
