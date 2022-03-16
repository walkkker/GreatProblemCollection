package TopInterviewClassicQuestions;

import linkedlist.ListNode;

public class DeleteDuplicatesI {
    /**
     * Definition for singly-linked list.
     * public class ListNode {
     *     int val;
     *     ListNode next;
     *     ListNode() {}
     *     ListNode(int val) { this.val = val; }
     *     ListNode(int val, ListNode next) { this.val = val; this.next = next; }
     * }
     */
    /**
     快慢指针

     【关键】就是 【链表是排好序的】，所以 同一个值的重复元素都在一起（保证了前面出现的元素后面不会再出现，不会出现111222111）。+ 每个元素只剩一个（而不是删除所有重复元素）

     不排序的话 就需要Set去重了。

     */
    class Solution {
        public ListNode deleteDuplicates(ListNode head) {
            if (head == null || head.next == null) {
                return head;
            }

            ListNode fast = head.next;
            ListNode slow = head;

            while (slow != null) {
                while (fast != null && fast.val == slow.val) {
                    fast = fast.next;
                }
                slow.next = fast;
                slow = slow.next;
            }
            return head;
        }
    }
}
