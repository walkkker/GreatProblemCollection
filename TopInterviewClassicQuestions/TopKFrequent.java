package TopInterviewClassicQuestions;

import java.util.*;

public class TopKFrequent {
    /**
     哈希表词频统计<int num, Node > + 封装Node（num + freq）+  门槛堆
     【难点】在于 封装 num + frep 在一起。从而从门槛堆中 既控制了频次，又能够从最终size中获得对应num的值。

     【步骤】
     使用 HashMap 统计完词频后，遍历每一个values。 放入到门槛堆。
     门槛堆是一个小根堆，
     （1）if heap.size() < targetSize，那么就 入堆
     （2）if size满了 && 当前元素 比 堆顶大的话，那么就要 堆顶出 + 新元素进
     通过上面两步，实现了门槛堆（小根堆），最终 只剩 前 size 个 最大的出现频率 的数字
     PS： 但是你看到了，在门槛堆中，我们比较的是频次，但是最终要拿出来的结果 是 该频次对应的值。 这就使得 同一个对象需要具备两个属性： 【1】频次用来比较 【2】对应的值用来获取结果  ==》 所以要封装成Node实例就可以了
     -----------------------------------------------------------------------

     利用一个堆就可以了 -> 门槛堆
     - 门槛堆
     - 就是 小根堆组织（堆顶就是这个门槛）， 当前的数字 能不能将 门槛干掉 自己进来。（有点类似于 堆实现 的 topK）
     - 先进行 词频统计（词频表）
     - 准备一个小根堆 （次数 少的 放在顶部）
     - 看小根堆 有没有满
     */
    class Solution {
        public int[] topKFrequent(int[] nums, int k) {
            HashMap<Integer, Node> map = new HashMap<>();
            // Step1 统计词频
            for (int i = 0; i < nums.length; i++) {
                if (!map.containsKey(nums[i])) {
                    map.put(nums[i], new Node(nums[i], 0));
                }
                // 【错误点】这里不是 重新赋值。 而是直接访问对应Node，修改Node里面的值
                // map.put(nums[i], map.get(nums[i]).freq + 1);
                map.get(nums[i]).freq++;
            }


            PriorityQueue<Node> minHeap = new PriorityQueue<>(new MyComparator()); // 注意，因为对象不可比较，所以要传入比较器
            // 词频统计完成之后，都封装到了Node里面，此时遍历Node， 使用 map.values()
            for (Node node : map.values()) {
                if (minHeap.size() < k) {                    // 门槛堆未满时，直接加入
                    minHeap.add(node);
                } else {
                    if (node.freq > minHeap.peek().freq) {  // 门槛堆满了，只有当堆顶元素<新元素时，才弹堆顶+加入新元素
                        minHeap.poll();
                        minHeap.add(node);
                    }
                }
            }

            // 最后，将门槛堆中的 node 依次弹出，塞入ans数组结果
            int[] ans = new int[k];
            int index = 0;
            while (!minHeap.isEmpty()) {
                ans[index++] = minHeap.poll().val;
            }
            return ans;
        }


        // 【构建Node数组】 包含val + freq两个属性
        public class Node {
            int val;
            int freq;

            public Node(int v, int f) {
                val = v;
                freq = f;
            }
        }

        public class MyComparator implements Comparator<Node> {
            public int compare(Node o1, Node o2) {
                return o1.freq - o2.freq;
            }
        }
    }

}
