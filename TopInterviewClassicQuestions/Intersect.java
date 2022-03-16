package TopInterviewClassicQuestions;

import java.util.*;

public class Intersect {

    /**
     1. 哈希表法

     2. 排序 + 双指针

     */

    // 哈希表法
    class Solution1 {
        public int[] intersect(int[] nums1, int[] nums2) {
            HashMap<Integer, Integer> map = new HashMap<>();
            // 将较短的数组 放入 HashMap中
            int[] longer = nums1.length > nums2.length ? nums1 : nums2;
            int[] shorter = longer == nums1 ? nums2 : nums1;
            for (int i = 0; i < shorter.length; i++) {
                if (!map.containsKey(shorter[i])) {
                    map.put(shorter[i], 0);
                }
                map.put(shorter[i], map.get(shorter[i]) + 1);
            }

            int[] ans = new int[shorter.length];
            int index = 0;

            for (int i = 0; i < longer.length; i++) {
                if (map.containsKey(longer[i]) && (map.get(longer[i]) > 0)) {
                    ans[index++] = longer[i];
                    map.put(longer[i], map.get(longer[i]) - 1);
                }
            }
            return Arrays.copyOfRange(ans, 0, index);
        }
    }

    // 排序+双指针
    /**
     1. 哈希表法

     2. 排序 + 双指针

     */
    class Solution2 {
        public int[] intersect(int[] nums1, int[] nums2) {
            Arrays.sort(nums1);
            Arrays.sort(nums2);
            int p1 = 0;
            int p2 = 0;
            int minLen = nums1.length < nums2.length ? nums1.length : nums2.length;
            int[] ans = new int[minLen];
            int index = 0;
            while (p1 != nums1.length && p2 != nums2.length) {
                if (nums1[p1] == nums2[p2]) {
                    ans[index++] = nums1[p1];
                    // 【错误点】别漏掉 p1 p2 一起向右移动
                    p1++;
                    p2++;
                } else {
                    if (nums1[p1] < nums2[p2]) {    // 谁小 谁右移
                        p1++;
                    } else {
                        p2++;
                    }
                }
            }
            return Arrays.copyOfRange(ans, 0, index);
        }
    }
}
