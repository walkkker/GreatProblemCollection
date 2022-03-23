package topInterviewHardQuestions;

public class WiggleSortII {

    /**
     这题挺好的，就是挺麻烦的
     O（N）时间 + O（1）空间
     前提条件：
     【1】快排 topK（k为中位数） -> 分成 小 中 大
     【2】完美洗牌模型

     */
    class Solution {
        public void wiggleSort(int[] nums) {
            if (nums == null || nums.length < 2) {
                return;
            }

            int N = nums.length;
            // 【1】首先用 快排的topK 方法 选取 中间/上中 位置的 数字 来做划分， 将nums 分成 【小 中 大】 三部分。
            getkthNum(nums, (N + 1)/ 2);
            // 【2】判断 奇偶数 -> 进行不同的操作
            // 【错误点！！】 位运算一定要加括号！！ 因为优先级别非常低！！
            if ((N & 1) == 1) {   // 奇数
                // 奇数的话 要变成 l0l1l2l3 r1r2r3 -> l0 + (r1l1r2l2r3l3)直接完美洗牌搞定 小大小大..
                shuffle(nums, 1, nums.length - 1);  // 【强调】完美洗牌shuffle 只能用于偶数
            } else {
                // 偶数形式下， 直接转完就是 大小大小大小 -> 整体逆序就可以
                shuffle(nums, 0, N - 1);
                reverse(nums, 0, N - 1);
            }
        }

        // Step3： 最终的完美洗牌函数 返回 r1l1r2l2r3l3r4l4\
        // 【注意】 因为主函数 会调用 不同LR位置的 shuffle，所以直接给 L,R参数
        // 一定要注意： shuffle 保证了是偶数，所以一定可以 从 2开始（并且使用的长度是偶数），1用不到
        // O(N) O(1) 完美洗牌 r1l1r2l2r3l3
        // 参数只需要 int[] nums
        // 用到的定理 -> 3^k - 1 的length， （起始点从1开始算的话）1,3...3^(k - 1) 是环的入口点
        // 对每个点进行 下标连续推 就可以了
        public void shuffle(int[] nums, int L, int R) {
            // 根据那个数论结论， 将 数组 拆成 3^k - 1的子数组，然后分段处理就可以了
            while (R - L + 1 > 0) {
                int len = R - L + 1;
                // base 从三开始算就可以，因为最小是2 （3^1 - 1）
                int base = 3;   // 实际要 使用的分段长度应该为 base - 1， 这里 base == 3^k
                int k = 1;
                // 找到 3^k - 1 <= len 的最大k ==> 3^k <= len + 1
                while (base <= (len + 1) / 3) {
                    base *= 3;
                    k++;
                }

                // 找到k了
                // 【错误点】对应的 块 的长度 base - 1，千万别忘了长度是 base - 1
                // 【Step】rotate， 先固定 （base - 1） / 2 长度的左半去，然后把右对等长度换过来，跟谁换呀？ 跟 剩下的左半区换
                int half = (base - 1) / 2;
                int mid = (L + R) / 2;                  // 上中点
                rotate(nums, L + half, mid, mid + half);  // -【错误点】mid + half 算错了。 这个 换左右 别混了
                // 交换了之后， 就可以 洗 当前这一部分的牌啦
                circles(nums, L, base - 1, k);
                // 下一部分了
                L = L + base - 1;
            }
        }

        // STEP1 函数， 给定 start ，给定len（当前这一块的长度），给定k，我把你这个区域内调整好 -> 完美洗牌顺序
        // 下标连续推
        // 参数说明： len是因为要做 下标变换（从哪里跳到哪里）， k是为了标记出 每一个入环节点的位置（1开头的）
        public void circles(int[] nums, int start, int len, int k) {
            // 给了我们一个k， 环的数量就是 k 个 (1(3^0),3...3^(k - 1))   [0, k - 1]
            // 所以只需要运行 k - 1 次, 每次 将 环里面的数字变换就可以了
            int trigger = 1;   // 3 ^ 0  , 将 trigger看作是入环节点
            while (k-- > 0) {
                // 【难点】这里要做下标换算. 得到的 cur是相对于 Len + 起始点为1的位置
                // 我们要换算到 起始点为start的位置
                // 其实f() = 1 如何变成 start ； 然后 f(cur)就求出来了 数组对应下标位置
                // 所以求得 cur -> cur - 1 + start
                // 先把当前的值记录下来
                int preNum =  nums[trigger - 1 + start];

                int cur = modifyIndex(trigger, len);  // 通过 trigger 去找 当前数字 该去的位置 + 初始化cur
                // 下标连续推 -> 当前的 trigger - 1 + start 循环结束后单独更新
                while (cur != trigger) {
                    // 里面做什么呢？ 把这一条路上的节点依次更新
                    // 加一个tmp就可以 两个值互换啦
                    int tmp = nums[cur - 1 + start];
                    nums[cur - 1 + start] = preNum;
                    preNum = tmp;
                    // 【错误点】不要忘了 更新cur 到 下一个该去的位置
                    cur = modifyIndex(cur, len); // modifyIndex一定要有len，因为依据 一半的大小会对左右分区进行不同的映射
                }
                nums[cur - 1 + start] = preNum;
                // 至此，完成一组的 位置调整

                // post处理， *3 处理. 变成新的入环节点
                trigger *= 3;
            }
        }


        // 从 1 开始算
        // 在这个偶数长度里面，返回 完美洗牌的 对应位置
        // 左半区变成了 2,4,6   右边区变成了 1,3,5
        public int modifyIndex(int i, int len) {
            int half = len / 2;
            if (i <= half) {  // 左半区
                return 2 * i;
            } else {
                return (i - half) * 2 - 1;  // i - half 就是 右半区从1开始的位置
            }
        }



        // Step2 函数 -> 左部分右部分交换函数
        // [L, M]  [M + 1, R] 交换  —> 三逆序
        public void rotate(int[] nums, int L, int M, int R) {
            reverse(nums, L, M);
            reverse(nums, M + 1, R);
            reverse(nums, L, R);
        }

        // 逆序函数，很多地方用到了，不只分支函数里哦
        public void reverse(int[] nums, int L, int R) {
            while (L <= R) {
                // 就是 L 与 R 位置交换
                int tmp = nums[L];
                nums[L] = nums[R];
                nums[R] = tmp;
                L++;
                R--;
            }
        }

        // 【Step4】 TopK 问题， 分成 【小 中 大】 三部分
        // 最小第k个数字
        public int getkthNum(int[] nums, int k) {
            k = k - 1;   // 第 k 小 的数字 对应 k - 1 位置
            int L = 0;
            int R = nums.length - 1;
            while (L <= R) {
                int pivot = nums[L + (int) (Math.random() * (R - L + 1))];
                int[] range = partition(nums, L, R, pivot);
                if (k < range[0]) {
                    R = range[0] - 1;
                } else if (k > range[1]) {
                    L = range[1] + 1;
                } else {
                    return nums[k];
                }
            }
            return -1;
        }

        public int[] partition(int[] nums, int L, int R, int pivot) {
            int less = L - 1;
            int more = R + 1;
            int index = L;
            while (index < more) {
                if (nums[index] == pivot) {
                    index++;
                } else if (nums[index] < pivot) {
                    swap(nums, ++less, index++);
                } else {
                    swap(nums, index, --more);
                }
            }
            return new int[]{less + 1, more - 1};
        }

        public void swap(int[] nums, int i, int j) {
            int tmp = nums[i];
            nums[i] = nums[j];
            nums[j] = tmp;
        }

    }

}
