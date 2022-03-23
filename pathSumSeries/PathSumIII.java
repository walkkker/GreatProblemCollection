package pathSumSeries;

import BTBasic.TreeNode;

import java.util.HashMap;

public class PathSumIII {
    /**
     【核心】在二叉树上 玩 DFS 的 前缀和
     （1）先想一个问题，给你一个数组，问你 这个数组中有多少个 子数组 累加和==100？ =》 子数组累加和问题
     - 前缀和数组 + 求每个i位置为结尾的 子数组有几个

     - 哈希Map记录 前缀和数组

     总结解法：
     DFS(先序遍历就是DFS)，让 HashMap 随着 DFS 更新前缀和
     - 加一个节点时，将该前缀和 添加在 HashMap中， 并记录频次
     - 回退一个节点时，将该节点对应的前缀和次数-1，如果减到0了，则删除该entry

     */
    class Solution {
        public int pathSum(TreeNode root, int targetSum) {
            if (root == null) {
                return 0;
            }

            HashMap<Integer, Integer> map = new HashMap<>();  // 前缀和，次数
            map.put(0, 1);   // 前缀和不要忽略这个东西
            return process(root, 0, targetSum, map);

        }

        // 返回 当前结点出发 满足targetSum的路径数目
        // 【错误点】将当前preSum放入HashMap中的操作 要在 检查当前 preSum - target 是否存在于 HashMap 之【后】！。
        // 为什么呢？ 检查是否存在 preSum - targetSum 在map中，这是一定能检查出来的。
        // 然而，如果在后的话，举一个返例， targetSum == 0; 在后面防止检查ans会无论如何都可以产生。 因为 任何一个前缀和-自己，但是此时是没有元素的，所以是错的
        // 【总结】 处理结果操作，要放在 当前 preSum 放入哈希表 之前！！！ 防止preSum - preSum == 0 满足要求时刻（但此时无元素，所以会算错）
        // 【总结】那么简单想，就是 当前来到我了，我看看我之前有没有可以的，处理完成之后，我把我当前的preSum也加进去map，给后面的数字用。
        public int process(TreeNode cur, int preSum, int targetSum, HashMap<Integer, Integer> map) {
            if (cur == null) {
                return 0;
            }
            preSum += cur.val;
            // 必须放在前面 -> 先计算 当前 满足条件的前缀和数量
            int ans = 0;
            if (map.containsKey(preSum - targetSum)) {
                ans += map.get(preSum - targetSum);
            }

            // 在后面 要往下继续 dfs的时候， 要把当前的 preSum加进去
            if (!map.containsKey(preSum)) {
                map.put(preSum, 0);
            }
            map.put(preSum, map.get(preSum) + 1);

            // 右面自然就会用到前面的前缀和啦。   注意 ans 在这里要加起来，获得后面的。
            // 每个节点都算出自己的，然后将孩子的也加起来。 那么最终头结点就是所有的了。
            ans += process(cur.left, preSum, targetSum, map);
            ans += process(cur.right, preSum, targetSum, map);

            // 要回去了，所以 首先将自己的频次-1，其次看看 如果为0了，就删除就行了。
            map.put(preSum, map.get(preSum) - 1);
            if (map.get(preSum) == 0) {
                map.remove(preSum);
            }
            // 返回统计的 自己 和 后面节点中 所有的计算满足targetSum的数量
            return ans;
        }
    }
}
