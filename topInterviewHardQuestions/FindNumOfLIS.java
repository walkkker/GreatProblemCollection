package topInterviewHardQuestions;

import java.util.ArrayList;
import java.util.TreeMap;

public class FindNumOfLIS {
    // 要结合 treeMap 做
// 然后里面的内容值 对应 >= 进行个体累加   每一个值a 记录的个数表示 >= a的个数
    class Solution {
        public int findNumberOfLIS(int[] nums) {
            ArrayList<TreeMap<Integer, Integer>> dp = new ArrayList<>();
            for (int i = 0; i < nums.length; i++) {
                int pos = search(dp, nums[i]);
                int cnt = 0;  // 统计num组成的LS数量

                // Step1: 首先更新cnt
                if (pos == 0) {
                    cnt = 1;
                } else {  // 只要不是第一个，一定有前面的treemap
                    TreeMap<Integer, Integer> preTp = dp.get(pos - 1);
                    // firstKey 包含着所有值
                    cnt = preTp.get(preTp.firstKey()) - (preTp.ceilingKey(nums[i]) == null ? 0 : preTp.get(preTp.ceilingKey(nums[i])));
                }

                // Step2: 更新完num的cnt，将它插入到treemap中
                if (pos == dp.size()) {
                    // 说明我要新建啦
                    dp.add(new TreeMap<>());
                    dp.get(pos).put(nums[i], cnt);
                } else {
                    // 插入到已经有的 treemap中，那么此时就要累加之前的。因为加入已存在的，一定比当前treemap中存在的元素都小
                    TreeMap<Integer, Integer> curTp = dp.get(pos);
                    curTp.put(nums[i], cnt + curTp.get(curTp.firstKey())); // 放的时候要累加
                }
            }
            // 全部插入完了之后，就可以直接查询最后一个treemap中总体的数量了。
            return dp.get(dp.size() - 1).firstEntry().getValue();


        }

        // >= num 的最小值 (所在的 treemap),返回下标 - ceiling
        // <= num的最右+1， 和>=num的最左，没有区别
        public int search(ArrayList<TreeMap<Integer, Integer>> dp, int num) {
            int L = 0;
            int R = dp.size() - 1;
            int ans = dp.size();   // 注意这里的默认值 -> 如果找不到的话，那么就说明当前[L, R]中，没有元素是>=num的，那么我们认为 最右就是 size这个虚拟位置
            while (L <= R) {
                int mid = L + (R - L) / 2;
                if (dp.get(mid).firstKey() >= num) {
                    ans = mid;
                    R = mid - 1;
                } else {
                    L = mid + 1;
                }
            }
            return ans;
        }
    }
}
