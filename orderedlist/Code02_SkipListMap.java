package orderedlist;

import java.util.ArrayList;

public class Code02_SkipListMap {


    public static class SkipListNode<K extends Comparable<K>, V> {
        K key;
        V val;
        ArrayList<SkipListNode<K, V>> nextNodes;

        public SkipListNode(K k, V v) {
            key = k;
            val = v;
            nextNodes = new ArrayList<>();
        }

        // 判断 当前 Key 是否小于 otherKey
        public boolean isKeyLess(K otherKey) {
            return otherKey != null && (key == null || key.compareTo(otherKey) < 0);
        }

        // 判断 当前 key 是否 == otherKey
        public boolean isKeyEqual(K otherKey) {
            return (key == null && otherKey == null)
                    || (key != null && otherKey != null && key.compareTo(otherKey) == 0);
        }
    }

    public static class SkipListMap<K extends Comparable<K>, V> {
        private static final double PROBABILITY = 0.5;
        private SkipListNode<K, V> head;
        private int size;
        private int maxLevel; // 增删改查都是从 maxLevel 进行对应位置的查找

        public SkipListMap() {
            head = new SkipListNode<>(null, null);
            head.nextNodes.add(null);     // 添加第0层
            size = 0;
            maxLevel = 0;
        }

        // 最重要的两个基础功能：
        // 本层查找 =》 整棵树的查找
        // 首先说明： 什么叫小？ 即 【next != null && next.isKeyLess(key)】;
        private SkipListNode<K, V> mostRightLessNodeInLevel(K key, SkipListNode<K, V> cur, int level) {
            SkipListNode<K, V> next = cur.nextNodes.get(level);
            while (next != null && next.isKeyLess(key)) {
                cur = next;
                next = cur.nextNodes.get(level);
            }
            return cur;
        }

        // 全局查找，最后跑到 0 层找到 < key 的 最右节点
        private SkipListNode<K, V> mostRightLessNodeInTree(K key) {
            if (key == null) {
                return null;
            }
            int level = maxLevel;
            SkipListNode<K, V> cur = head;
            while (level >= 0) {
                cur = mostRightLessNodeInLevel(key, cur, level);
                level--;
            }
            return cur;
        }

        // 我自己改写的左老师的，不知道理解是否正确 ==》
        // 当查找一个key的节点时，从最高层往下找，如果高层已经找到了，就直接返回。 不用再去第0层找最右<key的，然后查看第0层这个节点的下一个节点是不是。
        private SkipListNode<K, V> getKeyIndex(K key) {
            SkipListNode<K, V> cur = head;
            int level = maxLevel;
            while (level >= 0) {
                cur = mostRightLessNodeInLevel(key, cur, level);
                SkipListNode<K, V> next = cur.nextNodes.get(level);
                if (next != null && next.isKeyEqual(key)) {
                    return next;
                } else {
                    level--;
                }
            }
            return null;
        }


        public boolean containsKey(K key) {
            return getKeyIndex(key) != null;
        }

        // 查
        public V get(K key) {
            SkipListNode<K, V> find = getKeyIndex(key);
            return find == null ? null : find.val;
        }

        // 增 + 改 并在一起
        // 改就是存在的话，直接修改val； 不存在的话，那么就执行添加操作
        public void put(K key, V val) {
            // 先检查是否存在，不存在的话，那么 重新从 maxLevel 开始 依次往下插入
            SkipListNode<K, V> find = getKeyIndex(key);
            if (find != null) {
                find.val = val;
                return;
            } else {
                // 因为要插入新节点 所以size++
                size++;
                // 设置新节点
                SkipListNode<K, V> newNode = new SkipListNode<>(key, val);
                int newNodeLevel = 0;
                while (Math.random() < PROBABILITY) {
                    newNodeLevel++;
                }
                for (int i = 0; i <= newNodeLevel; i++) {
                    newNode.nextNodes.add(null);
                }

                // 有可能 newNodeLevel > maxLevel, 因此要把 head节点的 nextNodes 补齐
                while (newNodeLevel > maxLevel) {
                    head.nextNodes.add(null);
                    maxLevel++;
                }

                // 现在就可以执行插入操作了， 从最高层开始寻找，当 当前层数 level <= newNodeLevel时，即可插入
                int level = maxLevel;
                SkipListNode<K, V> cur = head;
                while (level >= 0) {
                    cur = mostRightLessNodeInLevel(key, cur, level);
                    if (level <= newNodeLevel) {
                        newNode.nextNodes.set(level, cur.nextNodes.get(level));
                        cur.nextNodes.set(level, newNode);
                    }
                    level--;
                }
            }
        }

        public void remove(K key) {
            // 首先要确认 是否存在， 只有存在的情况下， 才会执行 remove
            if (containsKey(key)) {
                size--;
                int level = maxLevel;
                SkipListNode<K, V> cur = head;
                while (level >= 0) {
                    cur = mostRightLessNodeInLevel(key, cur, level);
                    SkipListNode<K, V> next = cur.nextNodes.get(level);
                    if (next != null && next.isKeyEqual(key)) {
                        cur.nextNodes.set(level, next.nextNodes.get(level));
                    }
                    // 要检查 是否 此 level 只剩 head节点，如果是的话，删除 head节点 level层的指针
                    if (cur == head && cur.nextNodes.get(level) == null) {
                        head.nextNodes.remove(level);
                        // 千万不要忘了， maxLevel 也一定要 一并修改
                        maxLevel--; // maxLevel 始终 与 head节点的nextNodes 所拥有的size （即 跳表的链表层数 -> 由head节点发起的链表层数）相同
                    }
                    level--;
                }
            }
        }

        public int size() {
            return size;
        }


        public K firstKey() {
            SkipListNode<K, V> firstKey = head.nextNodes.get(0);
            return firstKey == null ? null : firstKey.key;
        }

        public K lastKey() {
            int level = maxLevel;
            SkipListNode<K, V> cur = head;
            while (level >= 0) {
                while (cur.nextNodes.get(level) != null) {
                    cur = cur.nextNodes.get(level);
                }
                level--;
            }
            return cur.key;
        }

        public K ceilingKey(K key) {
            SkipListNode<K, V> less = mostRightLessNodeInTree(key);
            SkipListNode<K, V> next = less.nextNodes.get(0);
            return next != null ? next.key : null;
        }

        public K floorKey(K key) {
            SkipListNode<K, V> less = mostRightLessNodeInTree(key);
            SkipListNode<K, V> next = less.nextNodes.get(0);
            return next != null && next.key.compareTo(key) == 0 ? next.key : less.key;
        }

    }

//    // 跳表的节点定义
//    public static class SkipListNode<K extends Comparable<K>, V> {
//        public K key;
//        public V val;
//        // 里面存的是链表
//        public ArrayList<SkipListNode<K, V>> nextNodes;
//
//        public SkipListNode(K k, V v) {
//            key = k;
//            val = v;
//            nextNodes = new ArrayList<SkipListNode<K, V>>();
//        }
//
//        // 遍历的时候，如果是往右遍历到的null(next == null), 遍历结束
//        // 头(null), 头节点的null，认为最小
//        // node  -> 头，node(null, "")  node.isKeyLess(!null)  true
//        // node里面的key是否比otherKey小，true，不是false
//        // 功能： 是否 当前 Key 比 otherKey小
//        // 如果 当前为 null 节点， otherKey != null -> true
//        // 如果 otherKey == null , 那么 isKeyLess 结果一定返回 false， 即 当前点 一定不小于
//        public boolean isKeyLess(K otherKey) {
//            //  otherKey == null -> false
//            return otherKey != null && (key == null || key.compareTo(otherKey) < 0);
//        }
//
//        public boolean isKeyEqual(K otherKey) {
//            return (key == null && otherKey == null)
//                    || (key != null && otherKey != null && key.compareTo(otherKey) == 0);
//        }
//
//    }
//
//    public static class SkipListMap<K extends Comparable<K>, V> {
//        // 0.5 的概率向上一层，0.5的概率停在这当前层
//        private static final double PROBABILITY = 0.5; // < 0.5 继续做，>=0.5 停
//        private SkipListNode<K, V> head;
//        private int size;
//        private int maxLevel;
//
//        public SkipListMap() {
//            head = new SkipListNode<K, V>(null, null);
//            head.nextNodes.add(null); // 0
//            size = 0;
//            maxLevel = 0;
//        }
//
//        // 从最高层开始，一路找下去，
//        // 最终，找到第0层的<key的最右的节点
//        private SkipListNode<K, V> mostRightLessNodeInTree(K key) {
//            if (key == null) {
//                return null;
//            }
//            int level = maxLevel;      // 从最高层 每层找到 < k 的 最右节点，然后依次往下层走 重复上述过程
//            SkipListNode<K, V> cur = head;
//            while (level >= 0) { // 从上层跳下层
//                //  cur  level  -> level-1
//                cur = mostRightLessNodeInLevel(key, cur, level);
//                level--;
//            }
//            return cur;
//        }
//
//        // 在level层里，如何往右移动
//        // 现在来到的节点是cur，来到了cur的level层，在level层上，找到<key最后一个节点并返回
//        private SkipListNode<K, V> mostRightLessNodeInLevel(K key,
//                                                            SkipListNode<K, V> cur,
//                                                            int level) {
//            SkipListNode<K, V> next = cur.nextNodes.get(level);
//            while (next != null && next.isKeyLess(key)) {
//                cur = next;
//                next = cur.nextNodes.get(level);
//            }
//            return cur;
//        }
//
//        public boolean containsKey(K key) {
//            if (key == null) {
//                return false;
//            }
//            SkipListNode<K, V> less = mostRightLessNodeInTree(key);
//            SkipListNode<K, V> next = less.nextNodes.get(0);
//            return next != null && next.isKeyEqual(key);
//        }
//
//        // 新增、改value
//        public void put(K key, V value) {
//            if (key == null) {
//                return;
//            }
//            // 0层上，最右一个，< key 的Node -> >key
//            SkipListNode<K, V> less = mostRightLessNodeInTree(key); // 从整棵树中查找 节点（也就是到第0层查找是否存在key这个节点）
//            SkipListNode<K, V> find = less.nextNodes.get(0);
//            if (find != null && find.isKeyEqual(key)) {    // Key 已经存在，找到对应节点，只更新value
//                find.val = value;
//            } else { // find == null   8   7   9           // 否则插入
//                size++;
//                int newNodeLevel = 0;
//                while (Math.random() < PROBABILITY) {      // 0.5的概率加一层，0.5的概率停在当前层
//                    newNodeLevel++;
//                }
//                // newNodeLevel
//                while (newNodeLevel > maxLevel) {
//                    head.nextNodes.add(null);
//                    maxLevel++;
//                }
//                SkipListNode<K, V> newNode = new SkipListNode<K, V>(key, value);
//                for (int i = 0; i <= newNodeLevel; i++) {
//                    newNode.nextNodes.add(null);
//                }
//                // 从上到下 依次每层找到 < k 的最右节点， 然后插入当前元素
//                int level = maxLevel;
//                SkipListNode<K, V> pre = head;
//                while (level >= 0) {
//                    // level 层中，从 pre节点开始找， 找到最右的 < key 的节点
//                    pre = mostRightLessNodeInLevel(key, pre, level);
//                    if (level <= newNodeLevel) {
//                        newNode.nextNodes.set(level, pre.nextNodes.get(level));
//                        pre.nextNodes.set(level, newNode);
//                    }
//                    level--;
//                }
//            }
//        }
//
//        public V get(K key) {
//            if (key == null) {
//                return null;
//            }
//            SkipListNode<K, V> less = mostRightLessNodeInTree(key);
//            SkipListNode<K, V> next = less.nextNodes.get(0);
//            return next != null && next.isKeyEqual(key) ? next.val : null;
//        }
//
//        public void remove(K key) {
//            if (containsKey(key)) {
//                size--;
//                int level = maxLevel;
//                SkipListNode<K, V> pre = head;
//                while (level >= 0) {
//                    pre = mostRightLessNodeInLevel(key, pre, level);
//                    SkipListNode<K, V> next = pre.nextNodes.get(level);
//                    // 1）在这一层中，pre下一个就是key
//                    // 2）在这一层中，pre的下一个key是>要删除key
//                    if (next != null && next.isKeyEqual(key)) {
//                        // free delete node memory -> C++
//                        // level : pre -> next(key) -> ...
//                        pre.nextNodes.set(level, next.nextNodes.get(level));
//                    }
//                    // 在level层只有一个节点了，就是默认节点head
//                    if (level != 0 && pre == head && pre.nextNodes.get(level) == null) {
//                        head.nextNodes.remove(level);
//                        maxLevel--;
//                    }
//                    level--;
//                }
//            }
//        }
//
//        public K firstKey() {
//            return head.nextNodes.get(0) != null ? head.nextNodes.get(0).key : null;
//        }
//
//        public K lastKey() {
//            int level = maxLevel;
//            SkipListNode<K, V> cur = head;
//            while (level >= 0) {
//                SkipListNode<K, V> next = cur.nextNodes.get(level);
//                while (next != null) {
//                    cur = next;
//                    next = cur.nextNodes.get(level);
//                }
//                level--;
//            }
//            return cur.key;
//        }
//
//        public K ceilingKey(K key) {
//            if (key == null) {
//                return null;
//            }
//            SkipListNode<K, V> less = mostRightLessNodeInTree(key);
//            SkipListNode<K, V> next = less.nextNodes.get(0);
//            return next != null ? next.key : null;
//        }
//
//        public K floorKey(K key) {
//            if (key == null) {
//                return null;
//            }
//            SkipListNode<K, V> less = mostRightLessNodeInTree(key);
//            SkipListNode<K, V> next = less.nextNodes.get(0);
//            return next != null && next.isKeyEqual(key) ? next.key : less.key;
//        }
//
//        public int size() {
//            return size;
//        }
//
//    }

    // for test
    public static void printAll(SkipListMap<String, String> obj) {
        for (int i = obj.maxLevel; i >= 0; i--) {
            System.out.print("Level " + i + " : ");
            SkipListNode<String, String> cur = obj.head;
            while (cur.nextNodes.get(i) != null) {
                SkipListNode<String, String> next = cur.nextNodes.get(i);
                System.out.print("(" + next.key + " , " + next.val + ") ");
                cur = next;
            }
            System.out.println();
        }
    }

    public static void main(String[] args) {
        SkipListMap<String, String> test = new SkipListMap<>();
        printAll(test);
        System.out.println("======================");
        test.put("A", "10");
        printAll(test);
        System.out.println("======================");
        test.remove("A");
        printAll(test);
        System.out.println("======================");
        test.put("E", "E");
        test.put("B", "B");
        test.put("A", "A");
        test.put("F", "F");
        test.put("C", "C");
        test.put("D", "D");
        printAll(test);
        System.out.println("======================");
        System.out.println(test.containsKey("B"));
        System.out.println(test.containsKey("Z"));
        System.out.println(test.firstKey());
        System.out.println(test.lastKey());
        System.out.println(test.floorKey("D"));
        System.out.println(test.ceilingKey("D"));
        System.out.println("======================");
        test.remove("D");
        printAll(test);
        System.out.println("======================");
        System.out.println(test.floorKey("D"));
        System.out.println(test.ceilingKey("D"));


    }

}
