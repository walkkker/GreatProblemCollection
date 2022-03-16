package TopInterviewClassicQuestions;

import java.util.*;

public class FourSumCount {

    /**
     【核心】 n^4 拆为两组 n^2， 分治。

     a + b = - (c + d)

     a + b 之和 记录在 map中作词频统计。
     c + d 去寻找map中 -sum数量加起来就可以

     a,b 组成一张表map
     然后枚举 c,d 的 和， 去表中查询map中 【-sum】 的数量
     */
    class Solution {
        public int fourSumCount(int[] nums1, int[] nums2, int[] nums3, int[] nums4) {
            HashMap<Integer, Integer> map = new HashMap<>();
            for (int i = 0; i < nums1.length; i++) {
                for (int j = 0; j < nums2.length; j++) {
                    int sum = nums1[i] + nums2[j];
                    if (!map.containsKey(sum)) {
                        map.put(sum, 0);
                    }
                    map.put(sum, map.get(sum) + 1);
                }
            }

            int ans = 0;

            for (int i = 0; i < nums3.length; i++) {
                for (int j = 0; j < nums4.length; j++) {
                    int sum = nums3[i] + nums4[j];
                    if (map.containsKey(-sum)) {
                        ans += map.get(-sum);
                    }
                }
            }
            return ans;
        }
    }
}
