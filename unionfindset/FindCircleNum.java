package unionfindset;

import java.util.LinkedList;
import java.util.Queue;

public class FindCircleNum {
    // 1. 并查集
    class Solution {
        // 看上半区就可以了
        // n*n， 所以 n 个集合
        // 根据上半区进行 连通分量的合并
        public int findCircleNum(int[][] isConnected) {
            int N = isConnected.length;
            UnionFind uf = new UnionFind(N);
            for (int i = 0; i < N; i++) {
                for (int j = i + 1; j < N; j++) {
                    if (isConnected[i][j] == 1) {
                        uf.union(i, j);
                    }
                }
            }
            return uf.sets();
        }

        public class UnionFind {
            int[] parent;
            int[] sizeMap;
            int[] stack;
            int size;

            public UnionFind(int n) {
                parent = new int[n];
                sizeMap = new int[n];
                stack = new int[n];
                size = n;
                for (int i = 0; i < n; i++) {
                    parent[i] = i;
                    sizeMap[i] = 1;
                }
            }

            private int findParent(int cur) {
                int si = 0;
                while (cur != parent[cur]) {
                    stack[si++] = cur;
                    cur = parent[cur];
                }
                while (si != 0) {
                    parent[stack[--si]] = cur;
                }
                return cur;
            }

            public void union(int a, int b) {
                int pa = findParent(a);
                int pb = findParent(b);
                if (pa != pb) {
                    int aSize = sizeMap[pa];
                    int bSize = sizeMap[pb];
                    int big = aSize > bSize ? pa : pb;
                    int small = big == pa ? pb : pa;
                    parent[small] = big;
                    sizeMap[big] = aSize + bSize;
                    size--;
                }
            }

            public int sets() {
                return size;
            }
        }
    }

    // 2.DFS
    class Solution2 {
        // DFS
        public int findCircleNum(int[][] isConnected) {
            int N = isConnected.length;
            // 【核心】(1)标记是否已经被访问 （2）这个 visited 也是防止 走回头路，导致 无限递归
            boolean[] visited = new boolean[N];
            int ans = 0;
            // 每计算一次连通分量， ans + 1
            for (int i = 0; i < N; i++) {
                if (!visited[i]) {
                    dfs(i, isConnected, visited);
                    ans++;
                }
            }
            return ans;
        }

        // 当前在 i 位置，将与i 相连的 城市 组成一个连通分量
        public void dfs(int i, int[][] isConnected, boolean[] visited) {
            visited[i] = true;
            int N = isConnected.length;
            // 【错误点】下面的for 以及 if 必须这样写
            // （1）因为要来回连通，所以每一行都要从0开始遍历
            // （2）因为要防止无线递归（回头路），所以 要 !visited[j] 才可以对j再做dfs
            for (int j = 0; j < N; j++) {
                if (isConnected[i][j] == 1 && !visited[j]) {
                    dfs(j, isConnected, visited);
                }
            }
        }
    }

    // 3. BFS
    class Solution3 {
        public int findCircleNum(int[][] isConnected) {
            int N = isConnected.length;
            boolean[] visited = new boolean[N];
            Queue<Integer> queue = new LinkedList<>();
            int ans = 0;
            for (int i = 0; i < N; i++) {
                if (!visited[i]) {
                    queue.add(i);
                    while (!queue.isEmpty()) {
                        int cur = queue.poll();
                        visited[cur] = true;
                        for (int j = 0; j < N; j++) {
                            // 【错误点】注意是 [cur][j]，当前节点的邻居
                            if (isConnected[cur][j] == 1 && !visited[j]) {
                                queue.add(j);
                            }
                        }
                    }
                    ans++;
                }
            }
            return ans;
        }
    }


}
