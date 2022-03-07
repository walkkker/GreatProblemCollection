package TopInterviewClassicQuestions;

public class WordSearch {
    class Solution {
        public boolean exist(char[][] board, String word) {
            // 每个位置 都要 走一遍
            for (int i = 0; i < board.length; i++) {
                for (int j = 0; j < board[0].length; j++) {
                    boolean ans = process(i, j, 0, board, word);
                    if (ans) {
                        return true;
                    }
                }
            }
            return false;
        }

        // i,j 对应 二维图中的 点
        // pos 对应定位到 word的哪一个位置了，用于判断 当前false,还是 继续向后面去配对
        // process含义： 从(i,j)位置出发，对应pos...的匹配，配对返回true,不配对返回false
        public boolean process(int i, int j, int pos, char[][] board, String word) {
            // 多个 base case, 顺序不能错！
            // 先看 如果当前 pos已经到达length了，说明前面匹配完成，返回true
            if (pos == word.length()) {
                return true;
            }

            // 没有匹配成功时，因为后面要用到了(i, j)去匹配str[pos]，所以检查（i，j）是否不越界
            if (i < 0 || i >= board.length || j < 0 || j >= board[0].length) {
                return false;
            }

            if (board[i][j] == 0) {
                return false;
            }

            if (board[i][j] != word.charAt(pos)) {
                return false;
            }


            // 为了防止走 回头路 ， 使用 【回溯】标记
            char tmp = board[i][j];
            board[i][j] = 0;

            boolean ans = process(i - 1, j, pos + 1, board, word)
                    || process(i + 1, j, pos + 1, board, word)
                    || process(i, j - 1, pos + 1, board, word)
                    || process(i, j + 1, pos + 1, board, word);

            // 千万别忘了， 返回之前， 将原本的值 恢复回去
            board[i][j] = tmp;
            return ans;
        }
    }
}
