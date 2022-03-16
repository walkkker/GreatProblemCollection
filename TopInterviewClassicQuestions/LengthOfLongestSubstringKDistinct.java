package TopInterviewClassicQuestions;

public class LengthOfLongestSubstringKDistinct {

    // s 是 输入的字符串， k是 规定的不能超过不同的字符种类数量
    public int lengthOfLongestSubstringKDistinct(String s, int k) {
        if (s == null || s.length() == 0 || k < 1) {
            return 0;
        }

        char[] chs = s.toCharArray();
        int[] count = new int[256];
        int numDiff = 0;
        int ans = 0;
        int R = 0;      // [L, R)
        // 同端双指针 -> 使用 for循环
        for (int L = 0; L < chs.length; L++) {
            // 找到第一个R不能够到的地方
            // 【错误点】 注意，这里是R指针，所以比较时 访问的是 cnt[chs[R] - 'a']
            while (R < chs.length && (numDiff < k || (numDiff == k && count[chs[R] - 'a'] > 0))) {
                numDiff += count[chs[R] - 'a'] == 0 ? 1 : 0;
                count[chs[R] - 'a']++;
                R++;   // 【滑动窗口】 中 一定不要忘了这一句
            }

            ans = Math.max(ans, R - L);

            // 【错误点续】这里是L指针移动，所以才是chs[L] - ‘a’
            if (count[chs[L] - 'a'] == 1) {
                numDiff--;
            }
            count[chs[L] - 'a']--;
        }
        return ans;
    }
}
