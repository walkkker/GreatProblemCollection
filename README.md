### 跳跃游戏全系列
[1](jumpGame/Jump.java) 跳跃游戏I
- https://leetcode-cn.com/problems/jump-game/


[2](jumpGame/Jump.java) 跳跃游戏II
- https://leetcode-cn.com/problems/jump-game-ii/

[3](jumpGame/FrogCanCross.java) 青蛙过河（跳石子过河）
- https://leetcode-cn.com/problems/frog-jump/
```
1. map反向索引表， <stones[i], i>， 可以快速定位 1. 跳到的位置是否存在 2.存在的话，O(1)返回下标
2. 注意题目说明了， 不回跳 -> 跳跃步数 >= 0
3. 再次根据题目， 跳跃距离的范围 是 [0, N - 1] -> 在 i位置的 lastDistance最大为 i
4. 对于时间测量而言，我测了一下 记忆化搜索不同 cache结构的时间：
    - 多个参数使用String.valueOf连起来 HashMap<String, Boolean> -> 最慢的
    - HashMap<Integer, HashMap<Integer, Boolean>> -> 快 2/3
    - 如果可以使用数组当然好，但是本题需要分析出来 lastDist范围是固定的这件事情，最快 —— 36ms
5. 由于 测出有一条路径能够 最后一个石子 就够了，所以可以进行剪枝
- 事实证明，虽然多了几行代码，但是常数时间快了一倍
```


### 股票买卖全系列
关键点： 从左往右尝试模型 -> 考虑 最后一个位置是否参与 从而划分出两个大类！！！

- [股票I](sellstock/StockCollection.java):
- https://leetcode.com/problems/best-time-to-buy-and-sell-stock/
- 只进行一次交易

- [股票II](sellstock/StockCollection.java):
- https://leetcode.com/problems/best-time-to-buy-and-sell-stock-ii/
- 任意次数
- （首选）方法一： 波峰波谷图，求每一个波峰的高度  -> ans += Math.max(prices[i] - prices[i - 1], 0)
- 方法二： 动态规划 + 斜率优化

- [股票III](sellstock/StockCollection.java):
- https://leetcode.com/problems/best-time-to-buy-and-sell-stock-iii/
- !! 给定次数，两次.  只允许做不超过两次的交易
- 可以不使用动态规划做
- 怎么做呢 - O（N） + O（1）
- （首选）最优解， 很强的一个贪心：假设每一个位置上的数字 是第二次卖出的时刻， 遍历一遍，求最大值
  - 需要设置几个变量
  - 假设每一个位置的前面已经有过一次最好的max并且计算了max-[后续中的最小] ，那么由于我们计算的是当前位置为第二次卖出的情况
  - 所以统计每次的答案即可
  - 具体一点，对于每一个位置，首先计算 
      【1】当前作为第二次卖出的价格 
      【2】到目前为止，最好的第一次卖出的价格 
      【3】到目前为止，最好的卖出 + 最好的买入的价格（也就是 第一次卖出-该卖出点后面的最低买入点 的最佳值）
  - 贪心在 如果后面更新了 onceMax（第一次交易的最大值），那么后续如果出现了能让之前的onceMax-prices[i]变大的数字，那么新的curMax-prices[i]一定是当前最优解
    所以，在最关键的变量 onceMinusBuyMax中，只需要查看是否 当前位置作为买入点，是否比之前的onceMinusBuyMax就好了
- [股票IV](sellstock/StockCollection.java):
- https://leetcode.com/problems/best-time-to-buy-and-sell-stock-iv/
- 固定K次
- 动态规划 + 斜率优化
- E.G. 对于 dp[3][4]， 考虑两个大情况 3位置不参与(dp[2][4]) 和 3位置参与 的情况， 然后在3位置参与情况下细分

- [股票V](sellstock/StockCollection.java):
- https://leetcode.com/problems/best-time-to-buy-and-sell-stock-with-cooldown/
- 与 II 问题一样是无限次的交易，只是添加了 coolDown de 要求
- cooldown： cool down 问题， 卖出时刻之后 必须等待一个时刻，才可以继续开始买卖， 无限次交易
- buy[22] 表示 在 0-22范围内无限次交易下所获得的最优收入 - 综合考虑后的优良买入时机对应价格，   --> 综合起来之后的【最优】 就是 buy[22]
- sell[i] 表示 0-i 范围内无限次交易，最后一次是 卖动作 所能获得的最大收益
- [股票VI](sellstock/StockCollection.java):    
- https://leetcode.com/problems/best-time-to-buy-and-sell-stock-with-transaction-fee/
- 带手续费
- 与问题V相同，方法也相同
- buy[i]0-i范围上，最后一个动作必须是买，的整体最优     sell[i]最后一个动作是卖的最大利润


### 数据结构设计题
[1] 哈希表的设计，实现 setAll() 方法，要求O(1)时间

[2] 已知一个消息流会不断地吐出整数1~N，但不一定按照顺序依次吐出，如果上次打印的序号为i， 那么当i+1出现时
请打印i+1及其之后接收过的并且连续的所有数，直到1~N全部接收并打印完，请设计这种接收并打印的结构

[3](dataStructureDesign/LRU.java) LRU
- https://leetcode-cn.com/problems/lru-cache/
- 具体错误点看代码，注意细节 -> 什么时候需要更新doubleLinkedList (get方法和put方法中 都需要考虑)

[4] LFU

[5] Top K 字符串问题【Hard】
```
Top K Frequent Words II
Implement three methods for Topk Class:
TopK(k). The constructor.
add(word). Add a new word.
topk(). Get the current top k frequent words.
LintCode题目：https://www.lintcode.com/problem/550/
```
- 加强堆 -> 多一张反向索引表 (key -> index)


### 最长递增子序列 - 系列
[1] 最长递增子序列


[2] 俄罗斯套娃问题

### 子数组累加和系列




### 二叉树基础
[1] 二叉树非递归三序遍历

[2] 二叉树的层序遍历

[3] 二叉树的序列化和反序列化



### TopK



### 排序



### 链表类经典题目

[1]
```
给定一棵搜索二叉树头节点，转化成首尾相接的有序双向链表（节点都有两个方向的指针）
```
- 二叉树递归套路！！
- Info 包含 头指针 + 尾指针


### 字符串相关问题

[1] 最长不重复字串
    - hashMap + 一维dp



### 链表排序问题

[1] 链表的插入排序

[2] 链表的快速排序