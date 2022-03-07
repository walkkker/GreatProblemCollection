package TopInterviewClassicQuestions;

import java.util.*;

public class Subsets {
    // 递归中 的 base case 不要忘记写return !!!!! 每次必须检查！！！！
// 每个位置 要/不要
    class Solution {
        public List<List<Integer>> subsets(int[] nums) {
            List<List<Integer>> ans = new ArrayList<>();
            List<Integer> path = new ArrayList<>();
            process(0, nums, path, ans);
            return ans;
        }

        public void process(int index, int[] nums, List<Integer> path, List<List<Integer>> ans) {
            if (index == nums.length) {
                ans.add(new ArrayList<>(path));  // 会创建一个 新的 ArrayList
                return;  // 【错误点！！！】 又忘了 写 return!!!!!!!!!
            }
            // 要带 回溯
            // 不要 / 要 =》 两种选择
            process(index + 1, nums, path, ans);

            // 要 的时候要带回溯， 不然走另一个分支的时候 会携带之前分支的信息
            path.add(nums[index]);
            process(index + 1, nums, path, ans);
            path.remove(path.size() - 1);   // 就是这一步是 【回溯】，就是还原现场
        }
    }
}
