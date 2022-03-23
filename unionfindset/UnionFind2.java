package unionfindset;

public class UnionFind2 {

    // 数组形式实现的并查集要求 输入进来的数字可以区分开来
    class Solution {
        public int numIslands(char[][] grid) {
            int N = grid.length;
            int M = grid[0].length;
            // （i， j）  => i * M + j
            UnionFind uf = new UnionFind(grid);
            for (int i = 0; i < N; i++) {
                for (int j = 0; j < M; j++) {
                    if (i == 0 && j != 0) {
                        if (grid[i][j] == '1' && grid[i][j - 1] == '1') {
                            uf.union(i,j,i,j-1);
                        }
                    } else if (i != 0 && j == 0) {
                        if (grid[i][j] == '1' && grid[i - 1][j] == '1') {
                            uf.union(i,j,i-1,j);
                        }
                    } else if (i != 0 && j != 0) {
                        if (grid[i][j] == '1' && grid[i][j - 1] == '1') {
                            uf.union(i,j,i,j-1);
                        }
                        if (grid[i][j] == '1' && grid[i - 1][j] == '1') {
                            uf.union(i,j,i-1,j);
                        }
                    }
                }
            }
            return uf.sets();
        }


        public class UnionFind {
            int[] parentMap;
            int[] sizeMap;
            int[] stack;
            int col;
            int size;

            public UnionFind(char[][] grid) {
                int N = grid.length;
                int M = grid[0].length;
                col = M;
                parentMap = new int[M * N];
                sizeMap = new int[M * N];
                stack = new int[M * N];
                for (int i = 0; i < N; i++) {
                    for (int j = 0; j < M; j++) {
                        if (grid[i][j] == '1') {
                            int pos = index(i, j);
                            parentMap[pos] = pos;
                            sizeMap[pos] = 1;
                            size++;
                        }
                    }
                }
            }

            private int index(int i, int j) {
                return i * col + j;
            }

            private int findParent(int i) {
                int size = 0;
                while (i != parentMap[i]) {
                    stack[size++] = i;
                    i = parentMap[i];
                }
                while (size != 0) {
                    parentMap[stack[--size]] = i;
                }
                return i;
            }

            public void union(int i1, int j1, int i2, int j2) {
                int pos1 = index(i1, j1);
                int pos2 = index(i2, j2);
                int p1 = findParent(pos1);
                int p2 = findParent(pos2);
                if (p1 != p2) {
                    int size1 = sizeMap[p1];
                    int size2 = sizeMap[p2];
                    int big = size1 > size2 ? p1 : p2;
                    int small = big == p1 ? p2 : p1;
                    parentMap[small] = big;
                    sizeMap[big] = size1 + size2;
                    size--;    // 千万别忘了，要 size--
                }
            }

            public int sets() {
                return size;
            }

            private int index(int i, int j, int M) {
                return i * M + j;
            }
        }
    }
}
