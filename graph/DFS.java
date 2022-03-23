package graph;

import java.util.*;

public class DFS {
    public static void DFS(Node start) {
        if (start == null) {
            return;
        }
        Stack<Node> stack = new Stack<>();
        HashSet<Node> set = new HashSet<>();
        // 压栈时打印, 因为可能会很多次弹出
        stack.push(start);
        set.add(start);
        System.out.print(start.val + " ");
        while (!stack.isEmpty()) {
            Node cur = stack.pop();
            for (Node next : cur.nexts) {
                if (!set.contains(next)) {
                    set.add(next);
                    stack.push(cur);
                    stack.push(next);
                    System.out.print(next.val + " ");
                    break;  // 千万别忘了，要dfs了，所以break
                }
            }
        }
    }
}
