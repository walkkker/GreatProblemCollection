package TopInterviewClassicQuestions;

import java.util.*;

public class DailyTemperatures {

    class Solution {
        public int[] dailyTemperatures(int[] tem) {
            // 难点在于 怎么创建 容器数组
            // ArrayList<Integer>[] stack = new ArrayList[N];
            // 【错误点】 容器型数组别写错了！！！！！ 数组的声明都要加[] ！！！！！！
            // 【注意】 后面那个 千万不要加 <> 泛型符号！
            int N = tem.length;
            List<Integer>[] stack = new List[N];
            int size = 0; // 【错误点】使用数组模拟栈的时候
            int[] ans = new int[N];
            // 弹出来的 index就是 ans[index]直接填的值
            for (int i = 0; i < N; i++) {
                // STEP1 先弹出
                while (size != 0 && tem[stack[size - 1].get(0)] < tem[i]) {
                    List<Integer> popList = stack[--size];
                    for (int popIndex : popList) {
                        ans[popIndex] = i - popIndex;
                    }
                }

                // STEP2 再压栈 -> 压栈分两种情况
                if (size != 0 && tem[stack[size - 1].get(0)] == tem[i]) {
                    stack[size - 1].add(i);
                } else {
                    List<Integer> newList = new ArrayList<>();
                    newList.add(i);
                    stack[size++] = newList;
                }
            }

            // 第二部分 -> 栈内剩余部分出， 无右侧值
            while (size != 0) {
                List<Integer> popList = stack[--size];
                for (int popIndex : popList) {
                    ans[popIndex] = 0;
                }
            }
            return ans;
        }
    }
}
