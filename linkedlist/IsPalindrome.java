package linkedlist;

public class IsPalindrome {

    class Solution {
        public boolean isPalindrome(ListNode head) {
            if (head == null || head.next == null) {
                return true;
            }
            // 【STEP1】 快慢指针 找中点 (初始值都是head)
            ListNode slow = head;
            ListNode fast = head;
            while (fast.next != null && fast.next.next != null) {
                fast = fast.next.next;
                slow = slow.next;
            }
            // 退出时，slow 就是 中 / 上中
            // 【STEP2】 逆序 [slow, 结尾]
            ListNode end = reverse(slow);
            // 【STEP3】 LR两端指针 向中间走，边走边检查
            ListNode L = head;
            ListNode R = end;
            boolean ans = true;
            while (L != null && R != null) {
                if (L.val != R.val) {
                    ans = false;
                    break;
                }
                // 【错误点， 别忘了 移动指针！！！】
                L = L.next;
                R = R.next;
            }

            // 【STEP4】 结束 判断之后，别忘了 要将 后半区逆序回来
            reverse(end);
            return ans;
        }

        // 注意 区间逆转的 话， 要注意 while 循环的 终止条件 + 自动补连到end.next
        // 不过本题不需要，因为本题 是 从 start 逆转到 null的, 这种一定要 返回 新的头结点
        public ListNode reverse(ListNode node) {
            ListNode pre = null;
            ListNode cur = node;
            while (cur != null) {
                ListNode next = cur.next;
                cur.next = pre;
                pre = cur;
                cur = next;
            }
            return pre;
        }
    }
}
