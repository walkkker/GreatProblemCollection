package TopInterviewClassicQuestions;

public class MissingNumber {
    /** 这种题的话就是考察 方法的多样性了
     1. 排序，然后 [0, n -1]遍历数组，哪个数字不存在，就是那个数字。 如果都存在，那么返回n。
     2. （good）数学 0-n 累加和 - nums[]累加和
     3.  哈希集合。 将nums中的元素都放进去，然后 遍历[0,n]，哪个元素不在里面，就是那个元素
     4. (good) 位运算的方法也很聪明 -> 利用 a ^ a = 0 的原理。 xor变量为0（0^a=a），遍历数组nums,全部异或进去。 然后 遍历[0,n]，再次遍历异或，此时相同元素结果全为0，最终只剩 没有出现在数组中的那个元素。

     */
    class Solution {
        public int missingNumber1(int[] nums) {
            int xor = 0;
            int n = nums.length;
            for (int i = 0; i < n; i++) {
                xor ^= nums[i];
            }
            for (int i = 0; i <= n; i++) {
                xor ^= i;
            }
            return xor;
        }

        public int missingNumber(int[] nums) {
            int n = nums.length;
            int sum = 0;
            for (int i = 0; i < nums.length; i++) {
                sum += nums[i];
            }
            int allSum = (n * (n + 1)) / 2;
            return allSum - sum;
        }
    }
}
