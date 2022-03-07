package TopInterviewClassicQuestions;

import java.util.*;

public class MergeIntervals {

    // 不需要使用 并查集
    // 解法： 贪心 -> 先排序，然后如果当前元素开头小于等于目前区间结尾，那么就扩充结尾；否则开辟新的区间
    // 【1】先排序。 排序规则：按照 startPoint 从小到大排序，第二维不需要关心。 使用两个变量s,e记录当前统计的区间 首尾
    // 【2】开始遍历： 如果 当前元素i的startPoint 在 当前e前，那么合并；否则输出然后重置
    // 实现 空间复杂度 O（1） -> 在原数组上记录答案
    class Solution {
        public int[][] merge(int[][] intervals) {
            Arrays.sort(intervals, (a, b) -> a[0] - b[0]);
            int s = intervals[0][0];
            int e = intervals[0][1];
            int size = 0;
            for (int i = 1; i < intervals.length; i++) {
                if (intervals[i][0] <= e) {
                    e = Math.max(e, intervals[i][1]);
                } else {
                    intervals[size][0] = s;
                    intervals[size++][1] = e;
                    s = intervals[i][0];
                    e = intervals[i][1];
                }
            }
            // 千万别忘了 最后一个还没有处理
            intervals[size][0] = s;
            intervals[size++][1] = e;
            return Arrays.copyOf(intervals, size);
        }
    }
}
