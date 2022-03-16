package TopInterviewClassicQuestions;

import java.util.*;

public class fractionToDecimal {

    // 该题很多 小错误 -> 多练
// 首先确定一个事情。 整数/整数 答案 一定不是 无理数。 只可能是 无限循环小数/有限不循环小数
// 除出整数部分的 余数*10/除数 就是第一位，余数*10/除数 就是下一位
// 还有就是 不要忘了 判断 + -
// 【核心 + 出错点】当 出现 已经出现过的余数时， 那么就说明会出现 循环节了。所以要想办法 存储 出现过的余数及其对应的位数的位置。 说白了，重复的余数出现就是循环节出现的标志
// 由于 余数*10 / 被除数， 根据题目给定限制数据范围，要使用long类型来存储余数才可以
    class Solution {
        public String fractionToDecimal(int numerator, int denominator) {
            // 还需要 提前先加上 被除数是0 的特殊情况
            if (numerator == 0) {
                return "0";
            }
            // 【错误点】必须（除数是负数，但是整数部分是0的情况）所以分开判断 添加 + - 号，后期计算的时候全部转成正数
            StringBuilder builder = new StringBuilder();

            // 说白了，如果符号不同，就 添加 “-”； 相同的话 不需要添加
            boolean isNegetive = (numerator < 0) ^ (denominator < 0);
            if (isNegetive) {
                builder.append("-");
            }
            // 【错误点】此时必须全部转成 正数 计算, 后面都不带符号了，因为一开始已经搞好了
            // 但是一定要注意，先转成 long类型，不然 系统最小值无法转
            // 啊！ 一定要在 Math.abs(denominator)里面先转(long)！！！，不然转完还是int，最小值问题无法解决
            long num = Math.abs( (long) numerator);
            long den = Math.abs((long) denominator);

            long integer = num / den;
            long rem = num % den;

            builder.append(integer);
            if (rem == 0) {
                return builder.toString();
            }

            builder.append(".");



            HashMap<Long, Integer> map = new HashMap<>();   // 检查是否出现 循环节
            while (rem != 0) {
                long curNum = (rem * 10) / den;
                rem = (rem * 10) % den;
                builder.append(curNum);
                if (rem == 0) {
                    break;
                } else {
                    // 注意 是当前的余数， 作用于 下一位
                    // 如果 i 位置第一次出现余数rem，那么后面出现重复余数时，是i+1位置（也就是 builder.size() 开始往后 重复了）
                    if (!map.containsKey(rem)) {
                        map.put(rem, builder.length());   // 【重要！！！】这句非常重要. value存的就是 如果该rem出现重复，那么循环节开始的位置
                    } else {
                        builder.insert(map.get(rem), "(");
                        builder.append(")");
                        break;
                    }
                }
            }
            return builder.toString();
        }
    }
}
