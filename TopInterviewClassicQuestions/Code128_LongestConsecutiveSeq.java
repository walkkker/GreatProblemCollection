package TopInterviewClassicQuestions;

import java.util.*;

public class Code128_LongestConsecutiveSeq {

    public static int longestConsecutive(int[] nums) {
        HashMap<Integer, Integer> map = new HashMap<>();
        int ans = 0;
        for (int num : nums) {
            if (!map.containsKey(num)) {
                map.put(num, 1);   // 这一句绝对不能少， 用来防止重复元素进入
                int preLen = map.containsKey(num - 1) ? map.get(num - 1) : 0;
                int postLen = map.containsKey(num + 1) ? map.get(num + 1) : 0;
                int all = 1 + preLen + postLen;
                map.put(num - preLen, all);
                map.put(num + postLen, all);
                ans = Math.max(ans, all);
            }
        }
        return ans;
    }

}
