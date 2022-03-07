package TopInterviewClassicQuestions;

public class MergeTwoSortedArrs {

    // 就是 单纯的 有序数组 的 merge过程
// 两个指针分别指向两个数组
// 【核心】 从后往前 merge
// 把思路打开： 两个方向都可以 merge
// （1）从前往后merge ， 谁小放谁
// （2）从后往前merge， 谁大放谁
    class Solution {
        public void merge(int[] nums1, int m, int[] nums2, int n) {
            // 令 m 指向 nums1 有效部分的最后一位， n 指向 nums2 有效部分的最后一位
            int index = m + n - 1;
            m--;
            n--;
            while (m >= 0 && n >= 0) {
                nums1[index--] = nums1[m] > nums2[n] ? nums1[m--] : nums2[n--];
            }
            while (m >= 0) {
                nums1[index--] = nums1[m--];
            }
            while (n >= 0) {
                nums1[index--] = nums2[n--];
            }
        }
    }
}
