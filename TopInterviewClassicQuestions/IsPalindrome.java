package TopInterviewClassicQuestions;

public class IsPalindrome {
    class Solution {
        // 整体字符串是否是 回文串 -> 首尾双指针就可以
        // 【注意点】包含数字和字母， 其中 对于字母，大小写忽略
        // Manacher 是关于字符串匹配算法的，找到 str 中的 最长回文子串（本质是求出每个位置对应的回文子串的长度）
        // 【错误点】（1）没有注意到可以忽略大小写
        // 【错误点】（2）没有注意到， 回文串随意，但是只考虑 字母+数字
        // 【核心】函数设计能力  ==》因为每次都对 L,R两个位置的 字符检验，检验成功之后还要再次分清楚 字母 和 数字 ==》 所以 专门设计 isNum isLetter!!! 不然要写很多繁琐代码
        public boolean isPalindrome(String s) {
            if (s.equals("")) {
                return true;
            }
            char[] str = s.toCharArray();
            int L = 0;
            int R = str.length - 1;
            while (L <= R) {   // 双指针嘛，相等时 也要检验，所以也在条件之内
                if (!isValid(str[L])) {
                    L++;
                } else if (!isValid(str[R])) {
                    R--;    // 【错误点】 注意首尾双指针 R要--
                } else {
                    if (!isEqual(str[L++], str[R--])) {   //【注意】验证有效之后，要 移动指针的
                        return false;
                    }
                }
            }
            return true;
        }

        // 针对 大小写 忽略的 验证函数
        // 【错误点】比如 0P ==》 (char)('0' + 32)  ==> 'P'
        // 说白了 ==》分类讨论　一定要细致　＝》　规定明确，只有当　字母比较时，相差32或相等才可以。 这个条件不能够 跟数字通用，不然就会出现上述问题。
        // 代码留在这里，反思问题  ==》条件写分类清楚
        // public boolean isEqual(char a, char b) {
        //     if (a == b || Math.abs(a - b) == 32) {
        //         return true;
        //     } else {
        //         return false;
        //     }
        // }


        // 【错误点】 改正后的 isEqual，还是不对， 逻辑混乱
        // public boolean isEqual(char a, char b) {
        //     if (a >= '0' && a <= '9' && b >= '0' && b <= '9') {
        //         return a == b ? true : false;
        //     } else {
        //         // 因为 进来比较的 都已经经过了 isValid() 函数的检验，所以一定是 字母和数字了
        //         // 此时可以大胆写 字母的逻辑了  ==》 想错了！！ else的话，还包含了 字母 与 数字。 && 关系，一个不通过就算false。
        //         return a == b || Math.abs(a - b) == 32 ? true : false;
        //     }
        // }

        public boolean isEqual(char a, char b) {
            if (isNumber(a) && isNumber(b)) {
                return a == b ? true : false;
            }
            // 不能直接使用else ，因为那样包含 一个字母一个数字情况， 而数字可以与大写字母相差32，就会导致错误。 必须严格规定 a,b都是 LETTER
            if (isLetter(a) && isLetter(b)) {
                return a == b || (Math.abs(a - b) == 32) ? true : false;
            }
            return false;
        }

        // 注意不要写错，这里用了c，那么里面都要使用c，不要不小心忘了变成了a
        public boolean isValid(char c) {
            if (isNumber(c) || isLetter(c)) {
                return true;
            } else {
                return false;
            }
        }


        // 下面两个是关键！！
        public boolean isNumber(char a) {
            return a >= '0' && a <= '9' ? true : false;
        }

        public boolean isLetter(char a) {
            return (a >= 'a' && a <= 'z') || (a >= 'A' && a <= 'Z') ? true : false;
        }

    }
}
