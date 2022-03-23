package graph;

import java.util.*;

public class Prim {

    // 两个版本： 第一个是 面向对象的版本， 第二个是 邻接矩阵的版本

    // V1 -> 面向对象版本
    public static Set<Edge> primMST(Graph graph) {
        PriorityQueue<Edge> heap = new PriorityQueue<>(new EdgeComparator());
        Set<Node> visited = new HashSet<>();
        Set<Edge> ans = new HashSet<>();
        for (Node node : graph.nodes.values()) {
            if (!visited.contains(node)) {
                for (Edge edge : node.edges) {
                    heap.add(edge);
                }
                visited.add(node);
                while (!heap.isEmpty()) {
                    Edge curEdge = heap.poll();
                    if (!visited.contains(curEdge.to)) {
                        Node toNode = curEdge.to;
                        ans.add(curEdge);
                        visited.add(toNode);
                        // 别忘了解锁了新的点，要解锁新的边
                        for (Edge edge : toNode.edges) {
                            heap.add(edge);
                        }
                    }
                }
            }
            break;
        }
        return ans;
    }


    public static class EdgeComparator implements Comparator<Edge> {
        public int compare(Edge o1, Edge o2) {
            return o1.weight - o2.weight;
        }
    }




    // V2 -> 邻接矩阵版本
    // 返回 sum ，最小生成树的路径和,如果是系统最大值代表无路
    public static int primMST2(int[][] graph) {
        int N = graph.length;
        int[] distance = new int[N];
        boolean[] visited = new boolean[N];
        int sum = 0;

        // 从0号点 出发
        visited[0] = true;
        for (int i = 0; i < N; i++) {
            distance[i] = graph[0][i];
        }

        //总共要解锁 size 个点，还剩 size - 1个点没解锁
        for (int i = 1; i < N; i++) {
            int minPath = Integer.MAX_VALUE;
            int minIndex = -1;
            for (int j = 0; j < N; j++) {
                if (distance[j] < minPath && !visited[j]) {
                    minPath = distance[j];
                    minIndex = j;
                }
            }

            // 【错误点】漏掉了
            // 提前终止判断， 所有节点已经全部被解锁
            if (minIndex == -1) {   // 全部节点被解锁，minIndex不会被更新，-1表示节点全部解锁，则直接返回答案
                return sum;
            }


            sum += minPath;
            visited[minIndex] = true;

            for (int j = 0; j < N; j++) {
                // 【错误点】条件上要加上 !visited[j]
                if (!visited[j] && graph[minIndex][j] < distance[j]) {
                    distance[j] = graph[minIndex][j];
                }
            }
        }
        return sum;
    }
}
