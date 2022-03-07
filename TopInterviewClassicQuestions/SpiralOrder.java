package TopInterviewClassicQuestions;

import java.util.*;

public class SpiralOrder {
    class Solution {
        public List<Integer> spiralOrder(int[][] matrix) {
            int r1 = 0;
            int c1 = 0;
            int r2 = matrix.length - 1;
            int c2 = matrix[0].length - 1;
            List<Integer> ans = new ArrayList<>();
            while (r1 <= r2 && c1 <= c2) {
                printSingleCircle(matrix, r1, c1, r2, c2, ans);
                r1 += 1;
                c1 += 1;
                r2 -= 1;
                c2 -= 1;
            }
            return ans;
        }

        public void printSingleCircle(int[][] m, int r1, int c1, int r2, int c2, List<Integer> ans) {
            int rows = r2 - r1 + 1;
            int cols = c2 - c1 + 1;
            // 分四种情况进行输出: 只有一个点， 单行（单向）， 单列（单向），多行多列（分为上下左右输出）
            if (rows == 1 && cols == 1) {
                ans.add(m[r1][c1]);
            } else if (rows == 1) {
                for (int i = c1; i <= c2; i++) {
                    ans.add(m[r1][i]);
                }
            } else if (cols == 1) {
                for (int i = r1; i <= r2; i++) {
                    ans.add(m[i][c1]);
                }
            } else {
                // 此时 行列数 都大于2， 分为 上 右 下 左输出即可
                for (int j = c1; j < c2; j++) {
                    ans.add(m[r1][j]);
                }
                for (int i = r1; i < r2; i++) {
                    ans.add(m[i][c2]);
                }
                for (int j = c2; j > c1; j--) {
                    ans.add(m[r2][j]);
                }
                for (int i = r2; i > r1; i--) {
                    ans.add(m[i][c1]);
                }
            }
        }
    }
}
