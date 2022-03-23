package linkedListSort;

import linkedlist.ListNode;

import java.util.*;

public class LinkedListMergeSort {
    // 数组的 归并排序  -> 非递归方法
    // （1）链表merge 过程 一定要保证 左侧结尾与右侧结尾都指向null
    // （2）函数分层次 -> hthtn, merge + main
    class Solution {
        public ListNode sortList(ListNode head) {
            if (head == null) {
                return null;
            }
            ListNode dummy = new ListNode(0);
            dummy.next = head;
            // 首先要计算长度， 步长每次*2， 但要 小于 N
            int N = 0;
            while (head != null) {
                N++;
                head = head.next;
            }

            for (int len = 1; len < N; len <<= 1) {
                ListNode pre = dummy;
                ListNode teamFirst = dummy.next;
                while (teamFirst != null) {
                    ListNode[] hthtn = hthtn(teamFirst, len);
                    ListNode[] mhmt = merge(hthtn[0], hthtn[1], hthtn[2], hthtn[3]);
                    // 合并完成之后 会有 【新的头部】，所以要pre连起来
                    pre.next = mhmt[0];
                    pre = mhmt[1];

                    teamFirst = hthtn[4];
                }
            }
            return dummy.next;
        }

        // 输入当前 第一个节点 和 合并的步长len
        // 返回 左区间头尾 右区间头尾 以及下一个节点
        public ListNode[] hthtn(ListNode teamFirst, int len) {
            ListNode ls = teamFirst;
            ListNode le = teamFirst;
            ListNode rs = null;
            ListNode re = null;
            ListNode next = null;
            int count = 0;
            ListNode cur = teamFirst;
            while (cur != null) {
                count++;  // 这句执行完，我就知道 当前节点是第几个了 （从1开始计数）
                if (count <= len) {
                    le = cur;
                } else {
                    if (count == len + 1) {
                        rs = cur;
                    }
                    re = cur;
                }

                if (count == (len << 1)) {
                    break;
                }
                cur = cur.next;  // 链表遍历千万别忘了最后这一步
            }

            // 要将左区间 和 右区间 的结尾置空  必须 ==> 防止 merge完出现错误链接
            le.next = null;
            if (re != null) {   // 【错误点 细心！】只有 re存在时，才会存在next，不然为null
                next = re.next;
                re.next = null;
            }
            return new ListNode[]{ls, le, rs, re, next};
        }

        // 返回 merge 的 头 和 merge 的尾
        public ListNode[] merge(ListNode ls, ListNode le, ListNode rs, ListNode re) {
            if (rs == null) {
                return new ListNode[]{ls, le};
            }
            ListNode dummy = new ListNode(0);
            ListNode pre = dummy;
            while (ls != null && rs != null) {
                if (ls.val < rs.val) {
                    pre.next = ls;
                    ls = ls.next;
                } else {
                    pre.next = rs;
                    rs = rs.next;
                }
                pre = pre.next;
            }
            if (ls != null) {
                pre.next = ls;
            }
            if (rs != null) {
                pre.next = rs;
            }
            while (pre.next != null) {
                pre = pre.next;
            }
            return new ListNode[]{dummy.next, pre};
        }
    }
}
