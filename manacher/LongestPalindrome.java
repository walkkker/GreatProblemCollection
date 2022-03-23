package manacher;

public class LongestPalindrome {
    public String longestPalindrome(String s) {
        if (s == null || s.length() == 0) {
            return null;
        }
        // 【STEP1】 首先对原始字符串做一定的加工，使其变为 每个char 的左右两边都加上 "#"，从而可以正确的求出回文长度
        char[] origin = s.toCharArray();
        char[] str = manacherString(origin);
        // 【STEP2】 创建对应的 回文半径数组
        int[] pArr = new int[str.length];
        // 【STEP3】 创建 最右回文边界R 以及 获得最右回文边界时的 中心点的下标C
        int C = -1;
        // 讲述中：R代表最右的扩成功的位置
        // coding：最右的扩成功位置的，再下一个位置
        int R = -1;
        // 因为目标是求 最长的回文字串的长度， 设一个max记录每一次得到的 pArr【i】 中的最大值，即与最大回文子串长度有关
        int max = Integer.MIN_VALUE;
        int ans = -1;
        // 【Step Final】最重要的一步，开始遍历 new char arr，然后根据规则 对每一个位置 求出对应的 回文半径
        for (int i = 0; i < str.length; i++) {
            // 首先设置 至少的回文半径
            pArr[i] = i < R ? Math.min(R - i, pArr[2 * C - i]) : 1;

            // 至此，对于 节点 i , 回文区域为 (i - pArr[i], i + pArr[i]), 注意此处是开区间，不包含
            // 而不包含的位置，就成为了 我们检测 边界是否char 相等的位置
            while (i - pArr[i] > -1 && i + pArr[i] < pArr.length) {
                if (str[i - pArr[i]] == str[i + pArr[i]]) {
                    pArr[i]++;
                } else {
                    break;
                }
            }

            // 退出时，i 节点的 回文半径pArr[i]已经确定好了，接下来 检查是否更新 回文右边界R
            if (i + pArr[i] > R) {
                R = i + pArr[i];
                C = i;
            }

            // 此时， 可以单独处理 问题所需的答案
            if (max < pArr[i]) {
                max = pArr[i];
                ans = i;
            }
        }
        // i/2 就是 i在原始字符串中的位置
        // pArr[i] - 1， 就是 对应的回文子串长度
        // 所以， (i - r, i + r)  ==> [i - r + 1, i + r - 1]
        int start = (ans - max + 1) / 2;
        int count = max - 1;

        return String.valueOf(origin, start, count);
    }

    // 处理串中 i/2 就是 i在原始字符串中的位置
    public static char[] manacherString(char[] old) {
        char[] ans = new char[2 * old.length + 1];
        for (int i = 0; i < ans.length; i++) {
            ans[i] = (i & 1) == 0 ? '#' : old[i / 2];
        }
        return ans;
    }

}
