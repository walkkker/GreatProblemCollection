package unionfindset;

import java.util.*;

public class UnionFind1 {

    public static class Node<V>{
        V val;

        public Node(V v) {
            val = v;
        }
    }

    public static class UnionFind<V> {
        HashMap<V, Node<V>> nodes;
        HashMap<Node<V>, Node<V>> parentMap;
        HashMap<Node<V>, Integer> sizeMap;

        public UnionFind(List<V> values) {
            nodes = new HashMap<>();
            parentMap = new HashMap<>();;
            sizeMap = new HashMap<>();;
            for (V val : values) {
                Node<V> newNode = new Node<>(val);
                nodes.put(val, newNode);
                parentMap.put(newNode, newNode);
                sizeMap.put(newNode, 1);
            }
        }

        private Node findParent(Node<V> cur) {
            Stack<Node<V>> stack = new Stack<>();
            while (cur != parentMap.get(cur)) {
                stack.push(cur);
                cur = parentMap.get(cur);
            }
            while (!stack.isEmpty()) {
                parentMap.put(stack.pop(), cur);
            }
            return cur;
        }

        public boolean isSameSet(V v1, V v2) {
            Node<V> node1 = nodes.get(v1);
            Node<V> node2 = nodes.get(v2);
            return parentMap.get(node1) == parentMap.get(node2);
        }

        public void union(V v1, V v2) {
            Node<V> node1 = nodes.get(v1);
            Node<V> node2 = nodes.get(v2);
            // 注意 ， 是findParent!!
//            Node<V> p1 = parentMap.get(node1);
//            Node<V> p2 = parentMap.get(node2);
            Node<V> p1 = findParent(node1);
            Node<V> p2 = findParent(node2);
            if (p1 != p2) {
                // 依据size 重定位
                int aSize = sizeMap.get(p1);
                int bSize = sizeMap.get(p2);
                Node<V> big = aSize > bSize ? p1 : p2;
                Node<V> small = big == p1 ? p2 : p1;
                parentMap.put(small, big);
                // 或者直接写 aSize + bSize
                sizeMap.put(big, sizeMap.get(big) + sizeMap.get(small));
                sizeMap.remove(small); // sizeMap始终只保留 代表节点的集合的size
            }
        }

        public int sets() {
            return sizeMap.size();
        }

    }
}
