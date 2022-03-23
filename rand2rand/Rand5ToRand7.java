package rand2rand;

public class Rand5ToRand7 {

    // [1,5]随机
    public static int rand5()  {
        return (int) (Math.random() * 5) + 1;
    }


    // 只能使用 rand5, 实现 [1,7]随机
    public static int rand7() {

        // 首先将 rand5 变成 01等概率发生器， 12->0, 45->1, 3重做
        // 【核心】有了01等概率发生器之后， 1 - 7 ==》 0 - 6  ==> 3位二进制 表示范围 [0, 7]  ==>  只做 0-6 等概率发生器
        int ans = 0;
        do {
              ans = (f() << 2) + (f() << 1) + f();
        } while (ans == 7);
        return ans + 1;   // 最后返回值 + 1 即可
    }

    // 首先将 rand5 变成 01等概率发生器， 12->0, 45->1, 3重做
    public static int f() {
        int ans = 0;
        do{
            ans = rand5();
        } while (ans == 3);
        return ans < 3 ? 0 : 1;
    }
}
