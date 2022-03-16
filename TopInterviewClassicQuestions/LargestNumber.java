package TopInterviewClassicQuestions;

import java.util.Arrays;
import java.util.Comparator;

public class LargestNumber {

    // 贪心 -> 本质上 就是 “最小字典序的拼接问题”，只不过现在要求 拼接后大的前缀 排在前面
// 将数组中的数字转化为字符串，同样的方式做 就可以了
    class Solution {
        public String largestNumber(int[] nums) {
            // STEP1 将数组元素全部转成 字符串
            int N = nums.length;
            String[] strs = new String[N];
            for (int i = 0; i < N; i++) {
                strs[i] = String.valueOf(nums[i]);
            }
            Arrays.sort(strs, new MyComparator());
            // 排序完成之后，拼接起来就可以了
            StringBuilder builder = new StringBuilder();
            for (String str : strs) {
                builder.append(str);
            }
            String ans = builder.toString();

            // 【错误点】缺少对 [0,0]这种数组的处理，注意处理0字符
            // 其他情况下，用一下贪心即可： 如果数组中 存在 0 和 非0元素，那么0一定会排序在 非0元素后面。 所以 我们只需要 特殊处理 数组中元素 全为0 的情况， 防止 “0000”这种出现（改成“0”）就可以了。
            if (ans.charAt(0) == '0') {
                return "0";
            } else {
                return ans;
            }
        }

        // 注意 字符串的字典序 比较 要使用 内置方法 compareTo(), 拼接后大的 排序在前
        public class MyComparator implements Comparator<String> {
            public int compare(String o1, String o2) {
                return (o2 + o1).compareTo(o1 + o2);
            }
        }
    }
}
