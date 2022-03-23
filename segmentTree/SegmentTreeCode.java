package segmentTree;

import java.util.Arrays;

public class SegmentTreeCode {

    public static class SegmentTree {
        int MAXN;
        int[] arr;    // 转化为 下标从1开头的数组
        int[] sum;    // 维护的 线段树节点的 区间和 （也可以来记录 max,min等 O（1）时间就可以从左右孩子整合的信息）
        int[] lazy;   // 懒增加标记
        boolean[] isUpdate; // 标记当前节点的 更新 是否激活
        int[] change; // 如果当前的 isUpdate[i]==true，那么change[i]就是懒更新的值

        public SegmentTree(int[] origin) {
            // 【注意】MAXN = origin.length + 1  [0, N]， 因为所有都要右移一位，0位置弃而不用，所以MAXN要在 origin.length + 1
            // MAXN = origin.length + 1;
            // arr = new int[MAXN];
            MAXN = origin.length;
            sum = new int[MAXN << 2];
            lazy = new int[MAXN << 2];
            isUpdate = new boolean[MAXN << 2];
            change = new int[MAXN << 2];
//            for (int i = 1; i < MAXN; i++) {
//                arr[i] = origin[i - 1];
//            }
            arr = origin;
        }

        // 入参： 父节点下标  => 功能：自动计算左右孩子下标，整合左右信息赋值回来 sum[父节点]）
        public void pushUp(int i) {
            // 愚蠢的错误
//            sum[i] = sum[i * 2 + 1] + sum[i + 2 + 2];
            sum[i] = sum[i * 2 + 1] + sum[i * 2 + 2];
        }

        // 入参： 父节点下标, 左边节点个数，右边节点个数  =》 功能： 将父节点的懒标记 下发到 左右孩子， 下发后清空父节点懒标记
        public void pushDown(int i, int ln, int rn) {
            // 【注意】一定要先 检查update， 然后再 下发 lazy
            int left = i * 2 + 1;
            int right = i * 2 + 2;
            if (isUpdate[i]) {
                change[left] = change[i];
                change[right] = change[i];
                isUpdate[left] = true;
                isUpdate[right] = true;
                // 【注意】因为更新下发，左右孩子的 lazy 就要清0
                lazy[left] = 0;
                lazy[right] = 0;
                // 【错误点】进行了懒标记的更新，千万不要忘了 随时更新 维护的区间和 =》 搞了半天就是为了 维护区间和这个东西
                sum[left] = change[left] * ln;
                sum[right] = change[right] * rn;
                isUpdate[i] = false;
            }
            // 更新下发完成后，再看 是否还有 懒增加 要下发
            if (lazy[i] != 0) {
                lazy[left] += lazy[i];
                lazy[right] += lazy[i];
                // 【注意】懒标记一旦更新（不管是 懒更新 还是 懒增加），都要将 当前节点 对应的 sum（维护区间和）进行更新。
                sum[left] += lazy[i] * ln;
                sum[right] += lazy[i] * rn;
                lazy[i] = 0;
            }
        }

        // rt 节点下标 + L,R 对应范围   ==》 使得 下标 与 对应的范围 解耦，可以自主组合
        // 建立 rt 节点 的 sum值
        public void build(int rt, int L, int R) {
            if (L == R) {
                sum[rt] = arr[L];
                // 【错误点！！为什么老犯错，走神不理解原理？】base case 加 return 啊！！！
                return;
            }
            // 分解子问题， 先 得到左右孩子的节点sum， 然后 pushUp 得到当前节点sum
            int M = (L + R) / 2;
            build(rt * 2 + 1, L, M);
            build(rt * 2 + 2, M + 1, R);
            pushUp(rt);
        }

        public void build() {
            build(0, 0, arr.length - 1);
        }


        public void add(int L, int R, int C) {
            add(L, R, C, 0, 0, arr.length - 1);
        }

        // 递归函数
        // 入参: 用户请求[L, R]增加C，   程序 在 rt节点进行检查（rt节点 对应 [l, r]范围）
        public void add(int L, int R, int C, int rt, int l, int r) {
            if (l >= L && r <= R) {
                lazy[rt] += C;
                sum[rt] += C * (r - l + 1);
                // 【错误点】 base case 一定要返回！！！
                return;
            }
            // 否则，如果 当前 [l,r]不全部包含在 [L,R]内，那么要看下，是否要去 左右孩子节点进行 懒增加 的更新
            // 因为当前懒不住了，所以 先下发懒标记，然后更新 左右孩子
            int mid = (l + r) / 2;
            pushDown(rt, mid - l + 1, r - mid);
            // 如果 L 压到了 左区间的 范围，那么就要去 左区间 更新了
            if (L <= mid) {
                add(L, R, C, rt * 2 + 1, l, mid);
            }
            // 如果 R 压到了右区间的范围，那么就要去右区间更新了
            if (R > mid) {
                add(L, R, C, rt * 2 + 2, mid + 1, r);
            }
            // 左右更新完了之后，别忘了 更新 rt 的 sum
            pushUp(rt);
        }

        // 递归： 更新当前 rt 这个节点的 sum和相关信息
        public void update(int L, int R, int C, int rt, int l, int r) {
            if (l >= L && r <= R) {
                change[rt] = C;
                isUpdate[rt] = true;
                lazy[rt] = 0;      // 注意这一个步骤， 当有更新来时，之前的懒增加标记 清0，因为 更新就是重新来过，之前全部算了
                sum[rt] = C * (r - l + 1);
                // 【错误点】 base case 做完一定要 返回！！！
                return;
            }
            // 否则，当前 懒不住了 。 因为 用户请求 需要获得 下面孩子的信息，而更新信息在我这一层被懒住了，孩子们并不知道，所以当现在要去问孩子们的时候，我需要先把我这里懒标记的信息 交给孩子们。
            int mid = (l + r) / 2;
            pushDown(rt, mid - l + 1, r - mid);
            if (L <= mid) {
                // 错误点 写的不认真！
                update(L, R, C, rt * 2 + 1, l, mid);
            }
            if (R > mid) {
                update(L, R, C, rt * 2 + 2, mid + 1, r);
            }
            // 两个孩子更新完成了， 更新我自己
            pushUp(rt);
        }

        public void update(int L, int R, int C) {
            update(L, R, C, 0, 0, arr.length - 1);
        }

        // 【注意】因为 累加和 可能很大，所以 使用 long类型 返回比较好
        // 注意 query, add, update 里面，当要去往 孩子节点 更新，增加，查询时， 都需要将懒标记下发
        // 返回 L,R 区间的 区间和
        public long query(int L, int R, int rt, int l, int r) {
            if (l >= L && r <= R) {
                return sum[rt];
            }
            long ans = 0;
            int mid = (l + r) / 2;
            // 【错误点】 孩子节点还没有获得新的信息。 所以要先下发，再去获取。
            pushDown(rt, mid - l + 1, r - mid);
            // pushDown完了之后，就可以向左孩子拿信息，向右孩子拿信息了
            if (L <= mid) {
                ans += query(L, R, rt * 2 + 1, l, mid);
            }
            if (R > mid) {
                ans += query(L, R, rt * 2 + 2, mid + 1, r);
            }
            return ans;
        }

        public long query(int L, int R) {
            return query(L, R, 0, 0, arr.length - 1);
        }
    }


    public static class Right {
        public int[] arr;

        public Right(int[] origin) {
            arr = origin;
        }

        public void update(int L, int R, int C) {
            for (int i = L; i <= R; i++) {
                arr[i] = C;
            }
        }

        public void add(int L, int R, int C) {
            for (int i = L; i <= R; i++) {
                arr[i] += C;
            }
        }

        public long query(int L, int R) {
            long ans = 0;
            for (int i = L; i <= R; i++) {
                ans += arr[i];
            }
            return ans;
        }

    }



    public static int[] genarateRandomArray(int len, int max) {
        int size = (int) (Math.random() * len) + 1;
        int[] origin = new int[size];
        for (int i = 0; i < size; i++) {
            origin[i] = (int) (Math.random() * max) - (int) (Math.random() * max);
        }
        return origin;
    }

    public static boolean test() {
        int len = 100;
        int max = 1000;
        int testTimes = 5000;
        int addOrUpdateTimes = 1000;
        int queryTimes = 500;
        for (int i = 0; i < testTimes; i++) {
            int[] origin = genarateRandomArray(len, max);
            SegmentTree seg = new SegmentTree(origin);
            int N = origin.length;
            seg.build();
            Right rig = new Right(origin);
            for (int j = 0; j < addOrUpdateTimes; j++) {
                int num1 = (int) (Math.random() * N);
                int num2 = (int) (Math.random() * N);
                int L = Math.min(num1, num2);
                int R = Math.max(num1, num2);
                int C = (int) (Math.random() * max) - (int) (Math.random() * max);
                if (Math.random() < 0.5) {
                    seg.add(L, R, C);
                    rig.add(L, R, C);
                } else {
                    seg.update(L, R, C);
                    rig.update(L, R, C);
                }
            }
            for (int k = 0; k < queryTimes; k++) {
                int num1 = (int) (Math.random() * N);
                int num2 = (int) (Math.random() * N);
                int L = Math.min(num1, num2);
                int R = Math.max(num1, num2);
                long ans1 = seg.query(L, R);
                long ans2 = rig.query(L, R);
                if (ans1 != ans2) {
                    return false;
                }
            }
        }
        return true;
    }

    public static void main(String[] args) {
        int[] origin = { 2, 1, 1, 2, 3, 4, 5 };
        SegmentTree seg = new SegmentTree(origin);
        int L = 2; // 操作区间的开始位置 -> 可变
        int R = 5; // 操作区间的结束位置 -> 可变
        int C = 4; // 要加的数字或者要更新的数字 -> 可变
        // 区间生成，必须在[S,N]整个范围上build
        seg.build();
        // 区间修改，可以改变L、R和C的值，其他值不可改变
        seg.add(L, R, C);
        // 区间更新，可以改变L、R和C的值，其他值不可改变
        seg.update(L, R, C);
        // 区间查询，可以改变L和R的值，其他值不可改变
        long sum = seg.query(L, R);
        System.out.println(sum);

        System.out.println("对数器测试开始...");
        System.out.println("测试结果 : " + (test() ? "通过" : "未通过"));

    }

}
