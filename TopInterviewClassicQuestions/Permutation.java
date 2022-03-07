package TopInterviewClassicQuestions;

import java.util.*;

public class Permutation {

    // 全排列  交换！！！
    class Solution {
        public List<List<Integer>> permute(int[] nums) {
            List<List<Integer>> ans = new ArrayList<>();
            process(0, nums, ans);
            return ans;
        }

        // 【1】DFS 一维数组需要i做位置导向
        // 【2】存路径 使用 int[] path（感觉不太好） / 回溯法，使用
        // 全排列经典题目，注意！！！ 上面想的不对 (回溯法+原数组上交换，省空间)
        // 使用 swap 方法做
        public void process(int index, int[] nums, List<List<Integer>> ans) {
            if (index == nums.length) {
                ans.add(arrToList(nums));
                return;              // 千万别忘了！！！
            }
            // 因为数字都不重复，所以不用担心。依次每个数字做当前的位置
            for (int i = index; i < nums.length; i++) {
                swap(nums, index, i);
                process(index + 1, nums, ans);
                swap(nums, index, i);
            }
        }

        public void swap(int[] arr, int i, int j) {
            int tmp = arr[i];
            arr[i] = arr[j];
            arr[j] = tmp;
        }

        public List<Integer> arrToList(int[] nums) {
            List<Integer> ans = new ArrayList<>();
            for (int num : nums) {
                ans.add(num);
            }
            return ans;
        }
    }
}
