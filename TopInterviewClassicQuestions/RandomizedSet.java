package TopInterviewClassicQuestions;
import java.util.*;


// 第二版本是分情况讨论， 分情况讨论版本，分情况讨论也没有那么麻烦
public class RandomizedSet {
    /**
     【错误题】错误集中在 remove函数

     与 LC384 很像
     只需要 哈希表就够了
     具体解法：
     1. 两张哈希表：第一张V->index表(insert 和 remove都是对值进行查询，所以一定要有V-index 的映射)，第二张map表 index-> V表 （因为要实现随机返回值）

     2. 注意一个问题，就是 删除时刻的操作。 不能直接删除，因为那样子会留下来一个洞，所以怎么删？ 将删除元素与最后一个元素进行交换，然后再删除。这样就没有洞了。 （那最后一个东西 去 填洞）


     关于 remove的错误：
     面试中新题： 搞了这么多，只给了我一个启发，就是  把情况分清，然后不要嫌麻烦，每个情况下去写，不要混在一起写一个general的方法，会减少很多麻烦
     不过本题呢，只要做到 先改最后删 就可以 （必须，否则就会出现删除值加进来的情况（也就是不能整合 要删除元素就是最后一个元素的问题））
     */

// 【核心】是要保证 indexValMap 中 index的连续性
    class RandomizedSet1 {
        private HashMap<Integer, Integer> valIndexMap;
        private HashMap<Integer, Integer> indexValMap;
        int size;

        public RandomizedSet1() {
            valIndexMap = new HashMap<>();
            indexValMap = new HashMap<>();
            size = 0;
        }

        // 先检查是否存在， 不存在的话 ，插入时候，两张map 要同时插入
        public boolean insert(int val) {
            if (valIndexMap.containsKey(val)) {
                return false;
            }
            valIndexMap.put(val, size);
            indexValMap.put(size, val);
            size++;
            // 【错误点】不要忘了 返回boolean
            return true;
        }

        // 存在删除返回true，不存在返回false
        // 【核心】是要保证 indexValMap 中 index的连续性
        // public boolean remove(int val) {
        //     if (!valIndexMap.containsKey(val)) {
        //         return false;
        //     }
        //     // 先从 valIndexMap中获得remove val的index,然后 在indexValMap中 将最后一个entry的value 写道 remove index的entry 的value中，然后删除最后一个条目即可

        //     // 【错误点】记住，这种东西一定要 改了一张map，另一张map一定要同时改
        //     int removeIndex = valIndexMap.get(val);
        //     int lastIndexVal = indexValMap.get(size - 1);
        //     // Step1: 先删除 remove val
        //     valIndexMap.remove(val);
        //     indexValMap.remove(removeIndex);


        //     // Step2: 处理 最后一个index元素的相关事宜
        //     // 【错误点】不仅包含删除，还包含 在 valIndexMap 的更新
        //     indexValMap.remove(size - 1);

        //     indexValMap.put(removeIndex, lastIndexVal);
        //     valIndexMap.put(lastIndexVal, removeIndex);

        //     size--;
        //     // 【错误点】不要忘了 返回boolean
        //     return true;
        // }
        // 面试中新题： 搞了这么多，只给了我一个启发，就是  把情况分清，然后不要嫌麻烦，每个情况下去写，不要混在一起写一个general的方法，会减少很多麻烦
        // 不过本题呢，只要做到 先改最后删 就可以
        public boolean remove(int val) {
            if (!valIndexMap.containsKey(val)) {
                return false;
            }
            int removedIndex = valIndexMap.get(val);
            int lastIndexVal = indexValMap.get(size - 1);
            valIndexMap.put(lastIndexVal, removedIndex);
            indexValMap.put(removedIndex, lastIndexVal);
            // 一定要 最后才删，因为有 重合的情况
            valIndexMap.remove(val);
            indexValMap.remove(size - 1);
            size--;
            return true;
        }

        public int getRandom() {
            int rand = (int) (Math.random() * size);
            return indexValMap.get(rand);
        }
    }

    /**
     【错误题】错误集中在 remove函数

     与 LC384 很像
     只需要 哈希表就够了
     具体解法：
     1. 两张哈希表：第一张V->index表(insert 和 remove都是对值进行查询，所以一定要有V-index 的映射)，第二张map表 index-> V表 （因为要实现随机返回值）

     2. 注意一个问题，就是 删除时刻的操作。 不能直接删除，因为那样子会留下来一个洞，所以怎么删？ 将删除元素与最后一个元素进行交换，然后再删除。这样就没有洞了。 （那最后一个东西 去 填洞）


     关于 remove的错误：
     面试中新题： 搞了这么多，只给了我一个启发，就是  把情况分清，然后不要嫌麻烦，每个情况下去写，不要混在一起写一个general的方法，会减少很多麻烦
     不过本题呢，只要做到 先改最后删 就可以 （必须，否则就会出现删除值加进来的情况（也就是不能整合 要删除元素就是最后一个元素的问题））
     */

    // 分情况讨论版本，分情况讨论也没有那么麻烦
// 【核心】是要保证 indexValMap 中 index的连续性
    class RandomizedSet2 {
        private HashMap<Integer, Integer> valIndexMap;
        private HashMap<Integer, Integer> indexValMap;
        int size;

        public RandomizedSet2() {
            valIndexMap = new HashMap<>();
            indexValMap = new HashMap<>();
            size = 0;
        }

        // 先检查是否存在， 不存在的话 ，插入时候，两张map 要同时插入
        public boolean insert(int val) {
            if (valIndexMap.containsKey(val)) {
                return false;
            }
            valIndexMap.put(val, size);
            indexValMap.put(size, val);
            size++;
            // 【错误点】不要忘了 返回boolean
            return true;
        }

        // 存在删除返回true，不存在返回false
        // 【核心】是要保证 indexValMap 中 index的连续性
        // public boolean remove(int val) {
        //     if (!valIndexMap.containsKey(val)) {
        //         return false;
        //     }
        //     // 先从 valIndexMap中获得remove val的index,然后 在indexValMap中 将最后一个entry的value 写道 remove index的entry 的value中，然后删除最后一个条目即可

        //     // 【错误点】记住，这种东西一定要 改了一张map，另一张map一定要同时改
        //     int removeIndex = valIndexMap.get(val);
        //     int lastIndexVal = indexValMap.get(size - 1);
        //     // Step1: 先删除 remove val
        //     valIndexMap.remove(val);
        //     indexValMap.remove(removeIndex);


        //     // Step2: 处理 最后一个index元素的相关事宜
        //     // 【错误点】不仅包含删除，还包含 在 valIndexMap 的更新
        //     indexValMap.remove(size - 1);

        //     indexValMap.put(removeIndex, lastIndexVal);
        //     valIndexMap.put(lastIndexVal, removeIndex);

        //     size--;
        //     // 【错误点】不要忘了 返回boolean
        //     return true;
        // }
        // 面试中新题： 搞了这么多，只给了我一个启发，就是  把情况分清，然后不要嫌麻烦，每个情况下去写，不要混在一起写一个general的方法，会减少很多麻烦
        // 分情况讨论也没有那么麻烦！！
        public boolean remove(int val) {
            if (!valIndexMap.containsKey(val)) {
                return false;
            }
            if (valIndexMap.get(val) == size - 1) {
                valIndexMap.remove(val);
                indexValMap.remove(size - 1);
            } else {
                // 不是最后一项，那么就交换然后正常删除就可以

                int removeIndex = valIndexMap.remove(val);
                int lastIndexVal = indexValMap.remove(size - 1);
                valIndexMap.put(lastIndexVal, removeIndex);
                indexValMap.put(removeIndex, lastIndexVal);
            }

            size--;
            return true;
        }

        public int getRandom() {
            int rand = (int) (Math.random() * size);
            return indexValMap.get(rand);
        }
    }

/**
 * Your RandomizedSet object will be instantiated and called as such:
 * RandomizedSet obj = new RandomizedSet();
 * boolean param_1 = obj.insert(val);
 * boolean param_2 = obj.remove(val);
 * int param_3 = obj.getRandom();
 */
}
