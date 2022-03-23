package graph;

import java.util.*;

public class Kruskal {
    // 直白一点就是 把所有的 边集 收集在一个 小根堆
    // 然后 小根堆依次弹出最小边，  如果当前弹出的最小边 连接的两个Node不是一个连通变量，那么 选中该边，并union这两个Node
    // 否则的话，忽略该边。
    public class UnionFind {
        private HashMap<Node, Node> parentMap;
        private HashMap<Node, Integer> sizeMap;

        public UnionFind(Collection<Node> nodes) {
            parentMap = new HashMap<>();
            sizeMap = new HashMap<>();
            for (Node node : nodes) {
                parentMap.put(node, node);
                sizeMap.put(node, 1);
            }
        }


        // 经测试， 用 Stack类 会比 数组快，在这个哈希表实现的UF中
        public Node findParent(Node cur) {
            Node[] stack = new Node[parentMap.size()];
            int size = 0;
            while (cur != parentMap.get(cur)) {
                stack[size++] = cur;
                cur = parentMap.get(cur);
            }
            while (size != 0) {
                parentMap.put(stack[--size], cur);
            }
            return cur;
        }

        public boolean isSameSet(Node n1, Node n2) {
            return findParent(n1) == findParent(n2);
        }

        public void union(Node n1, Node n2) {
            // 注意！ 错了，是找 parent!!!
//            Node p1 = parentMap.get(n1);
//            Node p2 = parentMap.get(n2);
            Node p1 = findParent(n1);
            Node p2 = findParent(n2);
            if (p1 != p2) {
                int aSize = sizeMap.get(p1);
                int bSize = sizeMap.get(p2);
                Node big = aSize > bSize ? p1 : p2;
                Node small = big == p1 ? p2 : p1;
                parentMap.put(small, big);
                sizeMap.put(big, aSize + bSize);
                sizeMap.remove(small);
            }
        }

        public int sets() {
            return sizeMap.size();
        }
    }

    public static class EdgeComparator implements Comparator<Edge> {
        public int compare(Edge o1, Edge o2) {
            return o1.weight - o2.weight;
        }
    }

    public Set<Edge> kruskalMST(Graph graph) {
        UnionFind uf = new UnionFind(graph.nodes.values());

        PriorityQueue<Edge> heap = new PriorityQueue<>(new EdgeComparator());

        Set<Edge> ans = new HashSet<>();

        // 边集全部加入 heap
        for (Edge edge : graph.edges) {
            heap.add(edge);
        }

        while (!heap.isEmpty()) {
            Edge edge = heap.poll();
            if (!uf.isSameSet(edge.from, edge.to)) {
                ans.add(edge);
                uf.union(edge.from, edge.to);
            }
        }
        return ans;
    }

}
