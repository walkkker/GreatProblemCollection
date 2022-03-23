package longestIncreasingSubsequence;

import java.util.*;

public class maxEnvelopes {

    public int maxEnvelopes(int[][] envelopes) {
        Arrays.sort(envelopes, new MyComparator());
        int N = envelopes.length;
        int[] order = new int[N];
        int size = 0;
        for (int i = 0; i < N; i++) {
            int pre = bSearch(order, envelopes[i][1], 0, size - 1);
            order[pre + 1] = envelopes[i][1];
            if (pre + 1 == size) {
                size++;
            }
        }
        return size;
    }

    // 这个排列顺序别搞错了
    public class MyComparator implements Comparator<int[]> {
        public int compare(int[] o1, int[] o2) {
            if (o1[0] == o2[0]) {
                return o2[1] - o1[1];   // 【错误点】这里是第二维上逆序，所以应该是 o2[1] - o1[1]
            } else {
                return o1[0] - o2[0];
            }
        }
    }

    // < tar 的 最右元素下标
    public int bSearch(int[] arr, int tar, int L, int R) {
        int ans = -1;
        while (L <= R) {
            int mid = L + (R - L) / 2;
            if (arr[mid] < tar) {
                ans = mid;
                L = mid + 1;
            } else {
                R = mid - 1;
            }
        }
        return ans;
    }
}
