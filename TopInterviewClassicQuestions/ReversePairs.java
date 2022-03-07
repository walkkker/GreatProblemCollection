package TopInterviewClassicQuestions;

import linkedlist.ListNode;


// 有一个大错误点！！！ 一定要注意
public class ReversePairs {

    public ListNode swapPairs(ListNode head) {
        if (head == null) {
            return null;
        }

        ListNode dummy = new ListNode(0); // 存在换头操作，用dummy
        dummy.next = head;   // 这两句永远在的，切记创建完dummy后 要next连起来head
        ListNode lastEnd = dummy;
        ListNode start = dummy.next;
        while (start != null) {
            if (start.next == null) {
                break;
            } else {
                ListNode end = start.next;
                reverseList(start, end);
                lastEnd.next = end;
                lastEnd = start;
                start = start.next;
            }
        }
        return dummy.next;
    }

    public void reverseList(ListNode start, ListNode end) {
        ListNode pre = null;
        ListNode cur = start;
        ListNode endNext = end.next;
        // 这里错啦！！！！！
        // 【打错误点！】要只逆转 start -> end！！！ 这样写，会把所有的节点全部逆转的！！！！
        // while (cur != null) {
        while (cur != endNext) {
            ListNode next = cur.next;
            cur.next = pre;
            pre = cur;
            cur = next;
        }
        start.next = endNext;    // 这句很重要
    }

}
