package TopInterviewClassicQuestions;

public class SearchInRotateArr {

    class Solution {
        // 这道题的核心就是 无论怎么旋转，至少有一半是有序的
        public int search(int[] nums, int target) {
            int L = 0;
            int R = nums.length - 1;
            while (L <= R) {
                int mid = L + ((R - L) >> 1);
                if (nums[mid] == target) {
                    return mid;
                }
                if (nums[mid] > nums[L]) {   // left part in order
                    if (target < nums[mid] && target >= nums[L]) {   // 注意 <= 号
                        R = mid - 1;
                    } else {
                        L = mid + 1;
                    }
                } else if (nums[mid] < nums[L]) {
                    if (target > nums[mid] && target <= nums[R]) {
                        L = mid + 1;
                    } else {
                        R = mid - 1;
                    }
                } else {   // 【错误点！！！】这个情况千万不能漏掉，很致命 L==MID
                    L++;    // l = MID + 1
                }
            }
            return -1;
        }
    }

    class Solution2 {
        public int search(int[] nums, int target) {
            int L = 0;
            int R = nums.length - 1;
            while (L <= R) {
                int mid = (L + R) / 2;
                if (nums[mid] == target) {
                    return mid;
                }
                if (nums[L] == nums[mid]) {
                    L = L + 1;
                } else if (nums[L] < nums[mid]) { // the left is in order
                    if (target < nums[mid] && target >= nums[L]) { // 这里一开始忘了 = 的情况， 画出界限的话应该是 [nums[L], nums[mid])的话，去左侧；否则去右侧
                        R = mid - 1;
                    } else {
                        L = mid + 1;
                    }
                } else {  // the right is in order
                    if (target > nums[mid] && target <= nums[R]) { // (nums[mid], nums[R]]
                        L = mid + 1;
                    } else {
                        R = mid - 1;
                    }
                }
            }
            return -1;
        }
    }
}
