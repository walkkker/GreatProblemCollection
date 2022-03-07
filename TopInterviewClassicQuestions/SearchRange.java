package TopInterviewClassicQuestions;

public class SearchRange {

    public int[] searchRange(int[] nums, int target) {
        if (nums.length == 0) {
            return new int[]{-1, -1};
        }

        // 叫做 leftPre好一些, 多设置一个变量 好写，没必要省！！
        int leftPre = lessMostRight(nums, target);
        int left = leftPre + 1;
        if ((left == nums.length) || nums[left] != target) {  // 【错误点】这句话一定要加，第一个条件，防止 leftPre 在最右侧，这时检测会出现越界
            return new int[]{-1, -1};
        }
        int right = lessMostRight(nums, target + 1);
        // 返回时 注意 不要搞错了
        return new int[]{left, right};
    }

    // 【错误点2】 记得  ans 设置成 -1，当没有满足的条件时，返回-1表示没有值满足条件
    public int lessMostRight(int[] arr, int target) {
        int L = 0;
        int R = arr.length - 1;
        int ans = -1;   // 【错误点】 二分法 默认值为 -1 表示未找到
        while (L <= R) {
            //  【错误点2】int mid = L + (R - L) >> 1;
            // 上边这句是错的，可以将 << >> 看作是  + - 同样的优先级，不加（）就会出错！！ 切记
            int mid = L + (R - L) / 2;
            if (arr[mid] < target) {
                ans = mid;
                L = mid + 1;
            } else {
                R = mid - 1;
            }
        }
        return ans;
    }
}
