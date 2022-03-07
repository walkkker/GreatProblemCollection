package linkedlist;

public class RemoveNthFromEnd {

    // 第一个： 找到链表中 倒数第N个节点
    public ListNode getKthFromEnd(ListNode head, int k) {
        ListNode fast = head;
        while (--k > 0) {
            if (fast.next == null) {
                return null;
            }
            fast = fast.next;
        }
        ListNode slow = head;
        while (fast.next != null) {
            fast = fast.next;
            slow = slow.next;
        }
        return slow;
    }

    // 【关键】 第二个，删除链表中倒数第N个节点
    public ListNode removeNthFromEnd(ListNode head, int n) {
        ListNode fast = head;
        ListNode slow = head;
        while (--n > 0) {
            if (fast.next == null) {
                return null;
            } else {
                fast = fast.next;
            }
        }
        // 退出时， slow 就对应着 倒数第N个节点

        // 判断是否是 删除头结点
        if (fast.next == null) {
            return head.next;
        }

        // 不是删除头结点
        fast = fast.next;
        // 此时 slow 就对应着 【删除节点的前一个节点了】
        while (fast.next != null) {
            fast = fast.next;
            slow = slow.next;
        }
        // 因为是 【删除节点的前一个节点了】，所以 slow.next != null， 是一定的
        slow.next = slow.next.next;
        return head;
    }

    // 本题的Pro版本，使用dummy node 解除边界的特殊讨论
    public ListNode removeNthFromEndPro(ListNode head, int n) {
        ListNode dummy = new ListNode(0);
        dummy.next = head;
        ListNode fast = dummy;
        ListNode slow = dummy;
        while (n-- > 0) {
            fast = fast.next;
        }
        while (fast.next != null) {
            fast = fast.next;
            slow = slow.next;
        }
        slow.next = slow.next.next;
        return dummy.next;
    }

}
