package TopInterviewClassicQuestions;

import java.util.*;

public class YangHuiSanJiao {
    // 三角 I
    class Solution {
        // 除了第0行外 -> 每一行 第一个位置 和 最后一个位置 肯定都是1
        // 中间位置， i , 等同于 上一行中 arr[i] + arr[i - 1]
        // 每下一行，个数+1
        // 说白了，依赖于 上和左上
        public List<List<Integer>> generate(int numRows) {
            List<List<Integer>> ans = new ArrayList<>();
            for (int i = 0; i < numRows; i++) {
                ans.add(new ArrayList<>());
                ans.get(i).add(1);
            }

            // i 从 1 开始
            for (int i = 1; i < numRows; i++) {   // i位置的 list 长度为 i + 1
                for (int j = 1; j < i; j++) {
                    ans.get(i).add(ans.get(i - 1).get(j) + ans.get(i - 1).get(j - 1));
                }
                ans.get(i).add(1);
            }
            return ans;
        }
    }

    // 三角 II
    // 额外空间复杂度 为 O（1）的做法  ==》 直接求 rowIndex 行
// 【核心】 1行开始 ， 中间位置 i 都依赖与 上方 + 左上 的值
// 类似于 DP 的 空间压缩   ->　所以　要　从右往左　更新
// 要注意的是， 开头1，结尾再加个1， 只能从 [1,..]的   +  [1,...]才可以才可以随便向上依赖
// 【！！】所以要注意的是，最后的 for循环 从1开始
    class Solution2 {
        public List<Integer> getRow(int rowIndex) {
            List<Integer> ans = new ArrayList<>();
            ans.add(1);  // 0 行
            for (int i = 1; i <= rowIndex; i++) {   // i 行 长度 i + 1
                for (int j = i - 1; j >= 1; j--) {   // 去除开头和尾巴的位置 存在依赖
                    ans.set(j, ans.get(j) + ans.get(j - 1));
                }
                ans.add(1);
            }
            return ans;
        }
    }

}
