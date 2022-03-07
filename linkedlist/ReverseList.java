package linkedlist;

public class ReverseList {

    // UnRecursive
    public ListNode reverseList(ListNode head) {
        // 记录 pre 和 cur的初始化。 要遍历cur， 所以 cur = head。
        ListNode pre = null;
        ListNode cur = head;
        while (cur != null) {
            // Step 1: 记录 下一个节点
            ListNode next = cur.next;
            // Step2: 反转当前结点的next指针
            cur.next = pre;
            // Step 3: pre, cur 通过赋值的方式向前推进
            pre = cur;
            cur = next;
        }
        return pre;
    }

    // Recursive
    public ListNode reverseListRecursive(ListNode head) {
        if (head == null) {
            return null;
        }
        return process(head);
    }

    public ListNode process(ListNode cur) {
        if (cur.next == null) {
            return cur;
        }
        // Step 1 - 先记录next节点，作为子问题的头结点
        ListNode next = cur.next;
        // Step 2 - process(next) 并接住返回的头结点
        ListNode head = process(next);
        // Step 3 - 对cur节点的next指针进行反转（指向null），并且将next节点的next指针指向cur，连起来（本来是指向null的）  ==》 从而就能够 实现最终的链表反转这样的逻辑
        next.next = cur;
        cur.next = null;
        return head;
    }
}
