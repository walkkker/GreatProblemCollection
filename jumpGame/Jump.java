package jumpGame;

public class Jump {

    // I
    public boolean canJump(int[] nums) {
        int cur = 0;
        int next = 0;
        for (int i = 0; i < nums.length; i++) {
            if (next < i) {      // next 一定是 大于等于 i 的，才能满足继续往前跳的条件
                return false;
            }
            if (i > cur) {
                cur = next;
            }
            next = Math.max(next, i + nums[i]);
        }
        return true;
    }

    // II
    public int jump(int[] nums) {
        int step = 0;
        int cur = 0;
        int next = 0;
        for (int i = 0; i < nums.length; i++) {
            if (i > cur) {
                step++;
                cur = next;
            }
            next = Math.max(next, i + nums[i]);
        }
        return step;
    }

}
