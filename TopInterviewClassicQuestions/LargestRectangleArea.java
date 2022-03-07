package TopInterviewClassicQuestions;

public class LargestRectangleArea {

    // 注意错误点！！！
    class Solution {
        // 寻找 两端较小的 单调栈 （下边（栈底）小）
        // 单调栈里面存的都是下标（独一无二） -> 比较时候取下标做映射就可以了 （单调栈对应的就是映射的值 的单调性）
        public int largestRectangleArea(int[] heights) {
            int N = heights.length;
            int[] stack = new int[heights.length];
            int size = 0;
            int maxArea = Integer.MIN_VALUE;

            // 注意每一阶段做法基本一致 -> int popIndex, int leftNearIndex 别忘了
            // 【第一阶段】遍历每一个元素，都入栈，处理中间出栈元素
            for (int i = 0; i < heights.length; i++) {
                // 单调递增的单调栈（下边小） -> 压栈 前 判断条件， 先弹出 >=nums[i] 的栈顶

                // todo【很 狠的错误点！！！】 一定要注意，比较时 要换为【映射】，这里字母长，容易乱，切记
                // while (size != 0 && stack[size - 1] >= heights[i]) {
                while (size != 0 && heights[stack[size - 1]] >= heights[i]) {
                    int popIndex = stack[--size];   // 一定要注意，弹出的元素（下标），才是当前要处理的元素
                    int leftNearIndex = size == 0 ? -1 : stack[size - 1];  // 左侧较小值是谁
                    // 右侧较小值下标 就是 待压栈 元素 i
                    // 所以 局部最小范围 (leftNearIndex, i)
                    int width = i - leftNearIndex - 1;
                    maxArea = Math.max(maxArea, width * heights[popIndex]);  // 注意是在处理弹出的 popIndex
                }

                // 前面不合法元素 弹出完毕后，i号元素 压栈
                stack[size++] = i;
            }

            // 第二阶段，元素已经遍历完毕。 此时栈中还有未弹出的元素。这些元素无右侧较小值。所以统一看作是右侧边界为N （与-1一样，表示不存在）
            while (size != 0) {
                int popIndex = stack[--size];
                int leftNearIndex = size == 0 ? -1 : stack[size - 1];
                int width = N - leftNearIndex - 1;
                maxArea = Math.max(maxArea, heights[popIndex] * width);
            }
            return maxArea;
        }
    }



    // 本题是要求 两侧最近最小的值，即当前数作为 局部最小 的范围。
    class Solution1 {
        public int largestRectangleArea(int[] heights) {
            int[] stack = new int[heights.length];
            int index = -1;
            int maxArea = 0;
            int N = heights.length;
            // 入栈
            for (int i = 0; i < N; i++) {
                while (index != -1 && heights[stack[index]] >= heights[i]) {
                    int popIndex = stack[index--];
                    int leftNearIndex = index == -1 ? -1 : stack[index];
                    // (leftNearIndex, i)
                    int len = i - leftNearIndex - 1;
                    maxArea = Math.max(maxArea, len * heights[popIndex]);
                }
                stack[++index] = i;
            }

            // 全部入栈完成之后，栈内元素依次出栈； 这些元素的右边界都为-1/N， 代表 无右侧较小值
            while (index != -1) {
                int popIndex = stack[index--];
                int leftNearIndex = index == -1 ? -1 : stack[index];
                // (leftNearIndex, N - 1]  ==》 arr[popIndex] 为最小值 的最大范围
                int len = N - leftNearIndex - 1;
                maxArea = Math.max(maxArea, len * heights[popIndex]);
            }
            return maxArea;
        }
    }
}
