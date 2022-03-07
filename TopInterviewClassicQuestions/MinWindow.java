package TopInterviewClassicQuestions;

public class MinWindow {

// 欠债模型 -> 滑动窗口内部就是 还债的字符 -> R++ 还债，map[str1[R]]--; L++ 还了的收回来，map[str1[L]]++ -> 每次变动后， 注意 all的取值 是否要发生改变（有效欠账就变，无效就不变）
// 同端双指针
// int[256] 模拟 map
// ASCII 码 对应 0-255
// 一个变量all 与 这个 map 联系很紧密
    class Solution {
        public String minWindow(String s, String t) {
            char[] str1 = s.toCharArray();
            char[] str2 = t.toCharArray();

            // 因为 本题 欠账表 的是 字符，它的 ASCII码就是 0-255，所以一个256的数组就可以搞定了
            int[] map = new int[256];
            int all = str2.length;

            // 首先处理 str2，把欠债情况 添加到 map里面去   =》  欠账表建立完成
            for (char ch : str2) {
                map[ch]++;
            }

            // 开始滑动窗口+ 结合 欠账表
            int start = -1;
            int minLen = Integer.MAX_VALUE;
            int R = 0; // [L, R) 经典同端双指针
            // 同端双指针，记录每一个位置作为 L 时候的答案，最终处理 max/min
            for (int L = 0; L < str1.length; L++) {
                // 条件： 什么时候 R 往右推
                // 循环里面： 右移一位 的相关处理
                while ((R < str1.length) && (all > 0)) {
                    map[str1[R]]--;
                    if (map[str1[R]] >= 0) {   // 有效还款条件，all--
                        all--;
                    }
                    R++;
                }

                // 跳出时 给出的就是最小， R刚刚满足条件之一时，会退出循环，

                // STEP2-中间 处理当前窗口结果
                if (all == 0) {
                    if (R - L < minLen) {
                        minLen = R - L;
                        start = L;
                    }
                }


                // 【STEP3】L右移的相关步骤
                map[str1[L]]++;    // map[str1[L]]借走了（或者说 不还了），所以 计数要++
                if (map[str1[L]] > 0) {
                    all++;
                }
                // for 中 post 处 自动末尾 L++，所以上述只需要 处理L右移的相关处理即可
            }

            // 掌握 str1.substring(start, end) 调用方式  [start, end)
            return start == -1 ? "" : s.substring(start, start + minLen);
            // 【错误点】注意是 s的substring方法，是 String 类 的方法
        }
    }
}
