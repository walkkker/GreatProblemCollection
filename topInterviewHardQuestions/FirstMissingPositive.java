package topInterviewHardQuestions;

public class FirstMissingPositive {

// 核心： 【1】固定位置 arr[0]=1; arr[1]=2，这样规定我们要实现的数组形式
//        【2】由于存在满足和不满足的部分，所以 两端双指针 + 指针模拟 有效区与无效区（注意清晰L， R对应的含义，很重要）
// L ,R 双端双指针
// [0, L]为有效区，每一个位置 i 上的元素为 i + 1
// [R, N - 1]为无效区（垃圾区） -> 排除掉的已经不可能是有效区的元素
// 此处，R还有一个意义，就是 R 代表 目前最大期望的正整数 (因为 R-1 位置为最大期望，对应值就是R)
// 由此，进行分类情况讨论， 元素留在有序区 || 进入垃圾区
    class Solution {
        public int firstMissingPositive(int[] nums) {
            int N = nums.length;
            int L = 0;   // 为了方便coding， [0, L), L表示下一个要检测的位置 == 当前有效区的最大值
            int R = N;    // [R, N - 1]
            // 【错误点1】这个边界条件要抓好  while(L <= R)是不对的
            // 当 L == R时，左右区间就都已经安置完成了。此时要退出，所以只有 L < R 时，才需要运算，不然会出错
            while (L < R) {
                // 如果当前位置就是满足条件，完美，直接L++，下一个位置
                if (nums[L] == L + 1) {
                    L++;
                    // 【重要】三种条件，当前的 nums[L] 要去 垃圾区
                } else if (nums[L] > R || nums[L] < (L + 1) || nums[nums[L] - 1] == nums[L]) {
                    swap(nums, L, --R);
                    // 否则，交换 nums[L] 去到它应该去的位置 nums[L] - 1
                } else {
                    swap(nums, L, nums[L] - 1);
                }
            }
            return R + 1;    // 【错误点2】注意要 R 是 有效区的最大期望值， 所以缺失的第一个正数 需要是 【R + 1】 =》 有效区最大期望值的下一个值就是缺失的第一个值
        }

        public void swap(int[] arr, int i, int j) {
            int tmp = arr[i];
            arr[i] = arr[j];
            arr[j] = tmp;
        }
    }
}
