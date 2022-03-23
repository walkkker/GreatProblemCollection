package TopInterviewClassicQuestions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class PartitionLabels {
    /**
     1. 先搞一张map，记录 每一个字符在字符串中 最靠右的位置
     - 指示当前块的右边界 必须在哪

     2. 每次遍历时候，依据map更新最右边界（R只会往右更新）。 如果遍历位置i 超过R了，那么说明此时L->R是当前的一块，记录

     3. 最后这种方式就是 划块最多的方式。

     */
    class Solution {
        // hashMap
        public List<Integer> partitionLabels1(String s) {
            List<Integer> ans = new ArrayList<>();
            HashMap<Character, Integer> map = new HashMap<>();
            char[] chs = s.toCharArray();
            for (int i = 0; i < chs.length; i++) {
                map.put(chs[i], i);
            }
            int L = 0;
            int R = 0;
            for (int i = 0; i < chs.length; i++) {
                if (i > R) {
                    ans.add(R - L + 1);
                    L = i;
                }
                R = Math.max(R, map.get(chs[i]));
            }

            // 最后不要忘了 加上最后一组
            ans.add(R - L + 1);

            return ans;
        }


        /**
         执行用时： 2 ms , 在所有 Java 提交中击败了 98.47% 的用户
         内存消耗： 40.1 MB , 在所有 Java 提交中击败了 25.06% 的用户
         */
        // 将 HashMap 变成 int[26] 来模拟 map
        public List<Integer> partitionLabels(String s) {
            List<Integer> ans = new ArrayList<>();
            int[] map = new int[26];
            char[] chs = s.toCharArray();
            // 记录 这个字符 所在的 最右侧位置
            for (int i = 0; i < chs.length; i++) {
                map[chs[i] - 'a'] = i;
            }
            int L = 0;
            int R = 0;
            for (int i = 0; i < chs.length; i++) {
                if (i > R) {
                    ans.add(R - L + 1);
                    L = i;
                }
                R = Math.max(R, map[chs[i] - 'a']);
            }

            // 【错误点】 最后不要忘了 加上最后一组
            ans.add(R - L + 1);

            return ans;
        }
    }
}
