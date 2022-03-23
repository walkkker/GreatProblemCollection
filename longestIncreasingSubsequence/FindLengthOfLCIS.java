package longestIncreasingSubsequence;

public class FindLengthOfLCIS {

    public int findLengthOfLCIS(int[] nums) {
        // 先模拟 0位置
        int count = 1;
        int max = 1;

        // 从1位置开始，判断 arr[i] 与 arr[i - 1] 的关系
        for (int i = 1; i < nums.length; i++) {
            if (nums[i] > nums[i - 1]) {
                count++;
            } else {
                count = 1;
            }

            max = Math.max(max, count);
        }
        return max;
    }

}
