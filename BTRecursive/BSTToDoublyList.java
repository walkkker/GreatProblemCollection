package BTRecursive;




public class BSTToDoublyList {

    public Node treeToDoublyList(Node root) {
        if (root == null) {
            return null;
        }

        Info ans = process(root);
        Node head = ans.head;
        Node tail = ans.tail;
        head.left = tail;
        tail.right = head;
        return head;
    }

    public class Info {
        Node head;
        Node tail;

        public Info(Node h, Node t) {
            head = h;
            tail = t;
        }
    }

    public Info process(Node cur) {
        if (cur == null) {
            return new Info(null, null);
        }
        Info left = process(cur.left);
        Info right = process(cur.right);

        Node head = cur;
        Node tail = cur;

        // 【错误点1】少连了 cur -> left.tail。 summary: 双向链表的连接和删除一定要特别谨慎。两个节点都要进行操作。  不像单链表只需要操作一个节点就可以。
        if (left.tail != null) {
            left.tail.right = cur;
            cur.left = left.tail;
            head = left.head;
        }

        if (right.head != null) {
            cur.right = right.head;
            right.head.left = cur;
            tail = right.tail;
        }

        return new Info(head, tail);
    }

}
