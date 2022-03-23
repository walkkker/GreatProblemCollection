package orderedlist;

import java.util.ArrayList;

public class SkipListCode {

    public static class Node<K extends Comparable<K>, V> {
        K key;
        V val;
        // 【错误点】注意这里面要写上 <K, V>
        ArrayList<Node<K, V>> nexts;   // 这就是关键，跳表的关键就是建立随机层数的多层链表

        public Node(K k, V v) {
            key = k;
            val = v;
            nexts = new ArrayList<>();
        }

        // 注意，为了后续的 比较操作（增删改查）都需要多次进行节点比较，所以 在Node类里面，规定isKeyLess与 isKeyEqual这两个函数

        // 1. 如何比较 cur.key < otherKey?
        // 我们说如果 (1) cur == null && otherKey != null (2) cur != null && otherKey != null && cur.key < otherKey )
        // 【注意】 otherKey 为 null 的话，那么otherKey 就 视作 最小值，也就是说 它小于任何值
        public boolean isKeyLess(K otherKey) {
            return (key == null && otherKey != null) || (key != null && otherKey != null && key.compareTo(otherKey) < 0);
        }

        public boolean isKeyEqual(K otherKey) {
            return (key == null && otherKey == null) || (key != null && otherKey != null && key.compareTo(otherKey) == 0);
        }

    }


    // 【错误点】别忘了 put remove 调整size!!!
    // 下面就是 跳跃表的 结构
    public static class SkipList<K extends Comparable<K>, V> {
        // 【错误点】 跳跃表需要多个基本属性
        // 包含， 概率因子 + 头 + size + 以及非常重要的就是记录 当前最大层数的maxLevel（当然这个可以从head.nexts.size() 获取，但是多记一个就是快！）
        private static final double PROBABILITY = 0.5;
        private Node<K, V> head;      // 在 生成SkipList时 初始化 head, K-V 都为 null 的一个节点，表示最左最小节点
        private int size;       // 记录当前 跳表 中包含多少个元素
        private int maxLevel;   // 记录 当前 SkipList 的最高层数是多少 (最低层是第0层)

        public SkipList() {
            head = new Node<K, V>(null, null);    // 头结点设置为 最小值 -> null表示最小
            size = 0;
            maxLevel = 0;
            head.nexts.add(null);   // 【这个很重要】因为maxLevel标记有第0层了，所以第0层上至少要有头节点，不然会报错
        }

        // 【核心】 跳表的逻辑 就是 从最高层开始往右到不能再往右，然后停在当前节点但是层数下一层，再往右到不能再往右，重复，直到第0层。
        // 那么 往右到不能再往右 的标准是什么呢？ 在每一层 到达 < key 的最右， 就是下面的函数
        // 参数： 从cur 节点开始(保证cur != null)， 在level层中， 找到 < K 的最右的节点。 并返回该节点
        private Node<K, V> mostRightInLevel(Node<K, V> cur, int level, K key) {
            // 右边没数了 || 右边的key 大于 key
//            while (!(cur.nexts.get(level) == null || cur.nexts.get(level).key.compareTo(key) > 0)) {
            Node<K, V> next = cur.nexts.get(level);
            while (next != null && next.key.compareTo(key) < 0) {
                cur = next;
                next = cur.nexts.get(level);
            }
            return cur;
        }

        // 【核心函数】整棵树寻找 <= key 的最右节点。 结合每层寻找的函数，最终找到 第0层上 < key 的最右节点（全局的floorKey）
        // 步骤，从最高层head节点开始找，每层找 floor，然后降一层继续往右找，最终到第0层，找到全局的 floorKey
        private Node<K, V> mostRightInTree(K key) {
            // 从最高层开始找
            Node<K, V> cur = head;
            int level = maxLevel;
            while (level >= 0) {  // [level，0]
                cur = mostRightInLevel(cur, level, key);
                level--;
            }
            return cur;
        }

        public V put(K key, V val) {
            if (key == null) {
                throw new RuntimeException("Invalid Parameter");
            }
            Node<K, V> preNode = mostRightInTree(key);
            Node<K, V> next = preNode.nexts.get(0);
            if (next != null && next.key.compareTo(key) == 0) {
                V preVal = next.val;
                next.val = val;
                return preVal;
            } else {
                // 【错误点】别忘了 ++size !!!
                size++;
                Node<K, V> newNode = new Node<>(key, val);
                int newLevel = 0;
                // 0.5的概率加一层，0.5的概率停在当前层
                while (Math.random() < PROBABILITY) {
                    newLevel++;
                }

                // 先要建立出来数组的位置,先 占位
                for (int i = 0; i <= newLevel; i++) {
                    newNode.nexts.add(null);
                }

                // 判断是否 head 要增加新层
                while (maxLevel < newLevel) {
                    head.nexts.add(null);
                    maxLevel++;
                }

                // 开始从最高层 依次向下向右找，注意 if(level <= newLevel),那么就可以插入了
                int level = maxLevel;
                Node<K, V> cur = head;
                while (level >= 0) {
                    cur = mostRightInLevel(cur, level, key);
                    if (level <= newLevel) {
                        newNode.nexts.set(level, cur.nexts.get(level));
                        cur.nexts.set(level, newNode);
                    }
                    // 【错误点】别漏掉了，最后要把 level--
                    level--;
                }
                return null;
            }
        }


        public V remove(K key) {
            if (key == null) {
                throw new RuntimeException("Invalid Parameter");
            }
            Node<K, V> cur = mostRightInTree(key);
            Node<K, V> next = cur.nexts.get(0);
            if (next != null && next.key.compareTo(key) == 0) {
                // 【错误点】节点存在，要删除了，记得 size--!!!
                size--;
                // exist, can be removed
                // 【注意】 删除注意一点就行， 如果 删除节点后， 最高层除了head之外没有别的节点，那么要将这些只有head的层全部删除。
                V ans = next.val;
                cur = head;
                int level = maxLevel;  // 常规，每次都从 最高层（maxLevel）+ 头节点（head）开始，
                while (level >= 0) {
                    cur = mostRightInLevel(cur, level, key);
                    next = cur.nexts.get(level);
                    if (next != null && next.key.compareTo(key) == 0) {
                        cur.nexts.set(level, next.nexts.get(level));
                    }

                    // 检查是否要删除该层, 【注意】要留住第0层
                    if (level != 0 && head.nexts.get(level) == null) {
                        head.nexts.remove(level);
                        // 【错误点】删除高层，不要忘了 maxLevel--
                        maxLevel--;
                    }
                    level--;
                }
                return ans;
            } else {
                return null;
            }
        }

        public V get(K key) {
            if (key == null) {
                throw new RuntimeException("Invalid");
            }
            Node<K, V> node = mostRightInTree(key);
            Node<K, V> next = node.nexts.get(0);
            if (next != null && next.key.compareTo(key) == 0) {
                return next.val;
            } else {
                return null;
            }
        }

        public K firstKey() {
            return head.nexts.get(0).key;
        }

        public K lastKey() {
            Node<K, V> cur = head;
            int level = maxLevel;
            // 不断 找向最右侧，next==null
            while (level >= 0) {
                Node<K, V> next = cur.nexts.get(level);
                while (next != null) {
                    cur = next;
                    next = cur.nexts.get(level);
                }
                level--;
            }
            return cur.key;
        }


        public K ceilingKey(K key) {
            Node<K, V> node = mostRightInTree(key);
            Node<K, V> next = node.nexts.get(0);
            if (next == null) {
                return null;
            } else {
                return next.key;
            }
        }


        // <= key 的最大
        public K floorKey(K key) {
            Node<K, V> node = mostRightInTree(key);
            Node<K, V> next = node.nexts.get(0);    // 【错误点】不要老写 node.next!!! 这是多层链表，下一级节点存在链表里面，你要告诉我你要哪一层的下一个！！！
            if (next != null && next.key.compareTo(key) == 0) {
                return next.key;
            } else {
                return node.key;
            }
        }

        public int size() {
            return size;
        }

        public boolean containsKey(K key) {
            Node<K, V> node = mostRightInTree(key);
            if (node.nexts.get(0) != null && node.nexts.get(0).key.compareTo(key) == 0) {
                return true;
            } else {
                return false;
            }
        }


    }
}
