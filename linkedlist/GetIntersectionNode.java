package linkedlist;

public class GetIntersectionNode {
    public ListNode getIntersectionNode(ListNode headA, ListNode headB) {
        if (headA == null || headB == null) {
            return null;
        }
        int aLen = 1;
        int bLen = 1;
        ListNode cur1 = headA;
        ListNode cur2 = headB;
        while (cur1.next != null) {
            cur1 = cur1.next;
            aLen++;
        }
        while (cur2.next != null) {
            cur2 = cur2.next;
            bLen++;
        }
        if (cur1 != cur2) {
            return null;
        }
        int n = Math.abs(aLen - bLen);
        // long -> cur1; short -> cur2, 重定位
        cur1 = aLen > bLen ? headA : headB;
        cur2 = cur1 == headA ? headB : headA;
        while (n-- > 0) {   // 长的 先跳n次
            cur1 = cur1.next;
        }
        while (cur1 != cur2) {
            cur1 = cur1.next;
            cur2 = cur2.next;
        }
        return cur1;
    }
}
