package topInterviewHardQuestions;

public class LongestIncreasingPath {
    /**
     dfs + 记忆化搜索 （存在【错误点】）
     记忆化搜索
     在 dfs的时候，做剪枝，保证到达的grid都是可达的。

     【解法步骤】
     1. 每个点做一遍dfs
     2. dfs里面做剪枝，只去 有效&&cur<next的next位置
     3. dfs 返回值 为当前grid为起点 的 最长路径长度
     */
    class Solution {
        public int longestIncreasingPath(int[][] matrix) {
            int[][] dp = new int[matrix.length][matrix[0].length];
            // 由于 任何一个值 不可能为 0 =》 所以0 就代表此处为无效值（也就是 还没有填）
            // 再解释一下，任何一个点，最短的递增长度就是它自己，所以至少为1，所以可以用0代表没有填过值。
            int max = Integer.MIN_VALUE;
            for (int i = 0; i < matrix.length; i++) {
                for (int j = 0; j < matrix[0].length; j++) {
                    max = Math.max(max, process(matrix, i, j, dp));
                }
            }
            return max;
        }

        // 【存在错误点】，注意！
        // dfs 返回值 为当前grid为起点 的 最长递增路径长度
        public int process(int[][] matrix, int i, int j, int[][] dp) {
            // 因为剪枝了，所以当前的位置一定是有效的

            if (dp[i][j] != 0) {
                return dp[i][j];
            }

            int max = Integer.MIN_VALUE;

            if (i - 1 >= 0 && matrix[i - 1][j] > matrix[i][j]) {
                max = Math.max(max, process(matrix, i - 1, j, dp));
            }

            if (i + 1 < matrix.length && matrix[i + 1][j] > matrix[i][j]) {
                max = Math.max(max, process(matrix, i + 1, j, dp));
            }

            if (j - 1 >= 0 && matrix[i][j - 1] > matrix[i][j]) {
                max = Math.max(max, process(matrix, i, j - 1, dp));
            }

            if (j + 1 < matrix[0].length && matrix[i][j + 1] > matrix[i][j]) {
                max = Math.max(max, process(matrix, i, j + 1, dp));
            }
            // 【错误点】有可能四周都无效，那么此时并不能直接返回 max + 1。因为四周都无效的话，当前最长递增长度应该为1.
            int ans = max == Integer.MIN_VALUE ? 1 : max + 1;
            dp[i][j] = ans;
            return ans;
        }
    }
}
