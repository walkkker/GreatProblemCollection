package TopInterviewClassicQuestions;

import linkedlist.ListNode;

public class DeleteDuplicateII {

    // 第一种： 迭代
    class Solution {
        // 看有效节点的下一个节点，看下一个节点是否是重复节点，如果是的话，删除与其相关的【所有】的重复的节点
        public ListNode deleteDuplicates(ListNode head) {
            ListNode dummy = new ListNode(0);
            dummy.next = head;
            ListNode pre = dummy;   // pre始终指向有效链表的末尾

            while (pre != null) {
                // 这个过程是在看是否cur节点是 非重复的节点
                ListNode cur = pre.next;

                // 此处分析就是关键了
                if (cur != null && cur.next != null && cur.next.val == cur.val) {
                    // cur.val 是重复值了，那就 记录重复的值
                    int dup = cur.val;     // 满足if条件，cur.val就是一个重复值了,那么我们就要跳过它，并删除 带有cur.val的节点， 正巧我们有pre作为有效节点的最后一个
                    while (cur != null && cur.val == dup) {
                        cur = cur.next;
                    }

                    // 退出时，节点==null || 节点.val ！= dup
                    pre.next = cur;   // 但是注意，此时不能确定新节点是否有效，所以只是单纯删除重复的dup节点
                    // 新的 pre.next会在 下一次循环中检查
                } else {
                    // else 中就是pre.next 不是一个重复节点的条件了
                    // 不管cur==null || cur.next == null || cur.next.val != cur.val
                    // 都代表 cur是一个非重复节点
                    pre = cur;   // 等同于 pre = pre.next
                }
            }
            return dummy.next;
        }
    }

    // 递归
    class Solution2 {
        public ListNode deleteDuplicates(ListNode head) {
            return process(head, null);
        }

        // 返回无重复元素的链表头节点
        public ListNode process(ListNode cur, ListNode preNode) {
            if (cur == null) {
                return null;
            }

            // 分解子问题
            // 一个就是 当前节点是重复的，一个就是 当前节点不是重复的

            // 重复： 如果与前面相同 || 后面相同，说明当前值是一个重复值，就要从后面的链表返回新的头节点
            if ((preNode != null && preNode.val == cur.val) || (cur.next != null && cur.next.val == cur.val)) {
                return process(cur.next, cur);
            } else {
                // cur 不是 不重复节点
                cur.next = process(cur.next, cur);
                return cur;
            }
        }
    }
}
