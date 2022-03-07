package TopInterviewClassicQuestions;

public class CountAndSay {

    // 这道题没啥但是不好想，就是多看看，熟悉思路， 可以稍微背一下思路
    class Solution {
        // 这道题就是让你写一个递归  -> countAndSay 就是这个递归函数
        // 从 f(n - 1)中获取 n-1结果， 然后 对结果进行计数和输出即可
        public String countAndSay(int n) {
            if (n == 1) {
                return "1";
            }
            char[] last = countAndSay(n - 1).toCharArray();
            StringBuilder builder = new StringBuilder();  // 这个就是答案了
            int times = 1;  // 初始化 第一个 字符的次数
            for (int i = 1; i < last.length; i++) {
                if (last[i] == last[i - 1]) {
                    times++;
                } else {
                    builder.append(String.valueOf(times));
                    builder.append(String.valueOf(last[i - 1]));  // 注意，因为要加进字符串里面，所以要使用 String.valueOf()
                    times = 1;
                }
            }
            // 别忘了，最后一个类别的元素 还没有加进 builder
            builder.append(String.valueOf(times));
            builder.append(String.valueOf(last[last.length - 1]));
            return builder.toString();
        }
    }
}
