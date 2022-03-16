package TopInterviewClassicQuestions;

import java.util.*;

public class Calculator {

    // 计算器II
    // + - * / 的计算器
// 双向队列 实现就可以了
    class Solution1 {
        public int calculate(String s) {
            Deque<String> deque = new LinkedList<>();
            // 每一步计算数字，遇到 符号时， 先压数字，再压符号
            char[] chs = s.toCharArray();
            int curNum = 0;
            // 对字符串的处理就是一步一步走，然后分类讨论的去 分别处理不同的字符
            for (int i = 0; i < chs.length; i++) {
                if (chs[i] == ' ') {   // 【注意】字符用==，并且是''， 字符串是“”，并且用 .equals()
                    continue;
                } else if (chs[i] >= '0' && chs[i] <= '9') {
                    curNum = curNum * 10 + chs[i] - '0';
                } else {   // 这个时候就是 +-*/ 了
                    // 遇到符号， 先 压 cur 再 压 op
                    addNum(deque, curNum);
                    deque.addLast(String.valueOf(chs[i]));   // 【错误点】所有的 字符char/数字int 进入deque都要转类型
                    // 别忘了，将curNum 压入之后，就要清0， 重新计算 后面的数字
                    curNum = 0;
                }
            }
            // 【错误点】
            // 正确的表达式，最后一定还有一个 num，但是最后不跟符号。 所以循环结束完毕之后，要手动 将 最后计算的 curNum 加到 deque中
            addNum(deque, curNum);
            return getResult(deque);
        }

        public void addNum(Deque<String> deque, int num) {
            // 如果 deque 为空， 直接压
            // 如果 deque 的 peekLast() 不为 * || /，那么 说明上一个op是 + || -，此时也可以直接压 （注意 字符串比较 要用函数equals()）
            // 所以上述两个情况都不满足的情况下，才 弹出 op 和 preNum， 与 num 计算，最后压入新的 num
            if (!deque.isEmpty() && (deque.peekLast().equals("*") || deque.peekLast().equals("/"))) {  // 【错误点】调用函数要加括号！！
                String op = deque.pollLast();
                int preNum = Integer.parseInt(deque.pollLast());
                // 此时要更新num, 要将 preNum op Num 合并
                num = op.equals("*") ? (preNum * num) : (preNum / num);
            }
            deque.addLast(String.valueOf(num));   // 最后都要压入新的num 【错误点】int别忘了转Stirng
        }

        // 调用这个函数时，表示入队已经全部完成了，此时 队列中仅有 + -
        public int getResult(Deque<String> deque) {
            int res = 0;
            boolean isAdd = true;
            while (!deque.isEmpty()) {
                String cur = deque.pollFirst();
                if (cur.equals("+")) {
                    isAdd = true;
                } else if (cur.equals("-")) {
                    isAdd = false;
                } else {
                    int num = Integer.parseInt(cur);
                    res = isAdd ? res + num : res - num;
                }
            }
            // 因为是一个正确的表达式，所以最后一个位置 一定是一个数字
            return res;
        }
    }

    // 基本计算器I -> 带括号
    class Solution {
        public int calculate(String s) {
            return process(s.toCharArray(), 0)[0];   // 注意返回的是一个数组
        }

        // 很重要的就是： arr[0]: ans（这块区域的值）; arr[1]: ")"的位置  / N（这个不用关心）
        // 参数是： 给一个char[], i表示从什么地方开始
        // 一个 process 处理一个 括号内的数字 / 整个区域
        public int[] process(char[] chs, int i) {
            Deque<String> deque = new LinkedList<>();
            int curNum = 0;
            while (i < chs.length && chs[i] != ')') {
                if (chs[i] == ' ') {
                    i++;
                } else if (chs[i] >= '0' && chs[i] <= '9') {
                    curNum = curNum * 10 + chs[i] - '0';
                    i++;
                } else if (chs[i] == '+' || chs[i] == '-') {  // 遇到符号才压栈/队列（遇到符号说明 当前数字计算完毕，可以压栈咯，并且 要开始计算下一个数字了，所以要清零啦）
                    deque.addLast(String.valueOf(curNum));     // String类型的 deque，注意压的时候 转换类型
                    curNum = 0;
                    deque.addLast(String.valueOf(chs[i]));
                    i++;
                } else {    // 遇上了 左括号(  , 这里面很细，要注意
                    int[] info = process(chs, i + 1);
                    curNum = info[0];
                    i = info[1] + 1;   // 传回来的是右括号的位置，所以下一个位置要跳到 右括号 的下一个位置
                }
            }
            // 【注意】 退出循环时候，最后还有一个数字，没有压入
            deque.addLast(String.valueOf(curNum));
            return new int[]{getNum(deque), i};  // 假设它是) 遇到停下来的，传回右括号所在的位置
        }

        public int getNum(Deque<String> deque) {
            int res = 0;
            boolean isAdd = true;   // 初始化 符号位
            while (!deque.isEmpty()) {  // 符号位，数位，符号位，数位
                String cur = deque.pollFirst();
                if (cur.equals("+")) {
                    isAdd = true;
                } else if (cur.equals("-")) {
                    isAdd = false;
                } else {
                    int num = Integer.parseInt(cur);
                    res = isAdd ? res + num : res - num;
                }
            }
            return res;
        }
    }
}
