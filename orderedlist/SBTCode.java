package orderedlist;

/**
 *     重点就是定义 SBTNode， 因为 SBTNode里面就定义了 size，所以不需要重复 在SBTMap中定义size了
 *     SBT不同于 AVL，特别注意 size 这个平衡因子， 的变化。
 *     每个SBTNode的 size 表明了 该节点为子树所包含的size,所以当该子树 下面 要增加节点的时候，该节点的size要++
 *     【注意】一定是 BST 全部处理完毕了，才调平衡（调平衡自动保持原来的BST特性）
 *     【与AVL树的不同点】在走add和delete分支的时候，沿途所有的节点的 size都要++ / --
 */
public class SBTCode {


    // 以当前结点为头的整棵子树所包含的节点个数
    public static class SBTNode<K extends Comparable<K>, V> {
        K key;
        V val;
        SBTNode<K, V> left;
        SBTNode<K, V> right;
        int size;    // (1)平衡因子 （2）add与delete方法里面对size的调整与AVL树的h不同

        public SBTNode(K k, V v) {
            key = k;
            val = v;
            left = null;
            right = null;
            size = 1;
        }
    }

    public static class SBTTreeMap<K extends Comparable<K>, V> {
        SBTNode<K, V> root;
        // 不同于 AVL树，不需要 记录int size, 因为 头结点的size就是 整棵树的总结点数量（如果需要记录重复节点，就在Node声明中 增加 int all字段，调整平衡必须是 不重复意义下的size）

        // 旋转永远是 哪个节点要转， 就传入哪个节点
        public SBTNode<K, V> leftRotate(SBTNode<K, V> cur) {
            SBTNode<K, V> right = cur.right;
            cur.right = right.left;
            right.left = cur;
            // 要更新对应节点的size
            // size = l.size + r.size + 1;
            right.size = cur.size;
            cur.size = (cur.left == null ? 0 : cur.left.size) + (cur.right == null ? 0 : cur.right.size) + 1;
            return right;
        }

        public SBTNode<K, V> rightRotate(SBTNode<K, V> cur) {
            SBTNode<K, V> left = cur.left;
            cur.left = left.right;
            left.right = cur;
            left.size = cur.size;
            cur.size = (cur.left == null ? 0 : cur.left.size) + (cur.right == null ? 0 : cur.right.size) + 1;
            return left;
        }

        public SBTNode<K ,V> maintain(SBTNode<K, V> cur) {
            if (cur == null) {        // 一定要加上 null 条件判断
                return null;
            }
            int L = cur.left == null ? 0 : cur.left.size;
            int R = cur.right == null ? 0 : cur.right.size;
            int LL = cur.left != null && cur.left.left != null ? cur.left.left.size : 0;
            int LR = cur.left != null && cur.left.right != null ? cur.left.right.size : 0;
            int RR = cur.right != null && cur.right.right != null ? cur.right.right.size : 0;
            int RL = cur.right != null && cur.right.left != null ? cur.right.left.size : 0;
            // 比，怎么比？ 侄子 和 叔叔 比
            // 【注意】先写 LL 在写LR， 先写RR，再写RL。   因为第一个满足就不会去看下一个了，这样就实现了LL,LR同时违规时，只执行LL
            if (LL > R) {
                cur = rightRotate(cur);  // 【注意】SBT中 孩子节点改变的节点，要递归调用 maintain， 注意接收返回的头
                // cur cur.right 两个节点 孩子变了。 先调平孩子，在调平父
                cur.right = maintain(cur.right);
                cur = maintain(cur);
            } else if (LR > R) {
                cur.left = leftRotate(cur.left);
                cur = rightRotate(cur);   // LR RL 型违规中，三者的孩子都是全变的
                cur.left = maintain(cur.left);
                cur.right = maintain(cur.right);
                cur = maintain(cur);
            } else if (RR > L) {
                cur = leftRotate(cur);
                cur.left = maintain(cur.left);
                cur = maintain(cur);
            } else if (RL > L) {
                cur.right = rightRotate(cur.right);
                cur = leftRotate(cur);
                cur.left = maintain(cur.left);
                cur.right = maintain(cur.right);
                cur = maintain(cur);
            }
            // maintain 中 不用管size， 左旋右旋中 已经调好了 size. 直接返回新的cur就可以了
            return cur;
        }

        // 这个add 一定确认 SBT中没有 key这个选项，一定是新的
        private SBTNode<K, V> add(SBTNode<K, V> cur, K key, V val) {
            if (cur == null) {
                return new SBTNode(key, val);
            }
            // 一直滑
            cur.size++;
            if (key.compareTo(cur.key) < 0) {
                cur.left = add(cur.left, key, val);
            } else {
                cur.right = add(cur.right, key, val);
            }
            // AVL树的这里 最后要 整合 h
            // SBT 的 size 一开始就已经 调整好了，所以不用整合了。 最后不要忘了 调平衡
            return maintain(cur);
        }

        // 一定确定 这个 key 是存在的
        private SBTNode<K, V> delete(SBTNode<K, V> cur, K key) {
            // 一开始就可以将 cur.size--
            cur.size--;
            if (key.compareTo(cur.key) < 0) {
                cur.left = delete(cur.left, key);
            } else if (key.compareTo(cur.key) > 0) {
                cur.right = delete(cur.right, key);
            } else {
                if (cur.left == null && cur.right == null) {
                    cur = null;
                } else if (cur.left == null) {
                    cur = cur.right;
                } else if (cur.right == null) {
                    cur = cur.left;
                } else {
                    // 寻找 右子树的 最左节点， BST的后继结点 来替代删除节点
                    SBTNode<K, V> des = cur.right;
                    while (des.left != null) {
                        des = des.left;
                    }
                    cur.right = delete(cur.right, des.key);
                    des.left = cur.left;
                    des.right = cur.right;
                    des.size = cur.size;
                    cur = des;
                }
            }
            // cur = maintain(cur);  可选可不选
            return cur;
        }

        // 上游保证 key != null
        private SBTNode<K, V> findNearNode(K key) {
            if (root == null) {
                return null;
            }
            SBTNode<K, V> cur = root;
            SBTNode<K, V> ans = cur;
            while (cur != null) {
                ans = cur;
                if (key.compareTo(cur.key) < 0) {
                    cur = cur.left;
                } else if (key.compareTo(cur.key) > 0) {
                    cur = cur.right;
                } else {
                    break;
                }
            }
            return ans;
        }

        public void put(K key, V val) {
            if (key == null) {
                throw new RuntimeException("Invalid Parameter");
            }
            SBTNode<K, V> node = findNearNode(key);
            if (node != null && node.key.compareTo(key) == 0) {
                node.val = val;
            } else {
                root = add(root, key, val);
            }
        }

        public boolean containsKey(K key) {
            if (key == null) {
                throw new RuntimeException("Invalid Parameter");
            }
            SBTNode<K, V> node = findNearNode(key);
            // 一定要注意 先决条件 node != null
            if (node != null && key.compareTo(node.key) == 0) {
                return true;
            } else {
                return false;
            }
        }

        public V remove(K key) {
            if (key == null) {
                throw new RuntimeException("Invalid Parameter");
            }
            SBTNode<K, V> node = findNearNode(key);
            if (node != null && node.key.compareTo(key) == 0) {
                V ans = node.val;
                root = delete(root, key);
                return ans;
            } else {
                return null;
            }
        }

        public V get(K key) {
            if (key == null) {
                throw new RuntimeException("Invalid Parameter");
            }
            SBTNode<K, V> node = findNearNode(key);
            if (node != null && node.key.compareTo(key) == 0) {
                V ans = node.val;
                return ans;
            } else {
                return null;
            }
        }



        // 【易错点】 找到最后一个 <= 的最大
        // 【注意】 是 cur.key <= key 时，才记录。 也就是说，寻找的是 cur中的 <= K 的最大值对应节点
        private SBTNode<K, V> findLastNoMoreIndex(K key) {
            SBTNode<K ,V> cur = root;
            SBTNode<K, V> ans = null;   // 【错误点】以前是cur，想想为什么错！ 有些时候没有答案就改返回null的
            while (cur != null) {
                if (key.compareTo(cur.key) == 0) {
                    ans = cur;
                    break;
                } else if (key.compareTo(cur.key) < 0) {
                    cur = cur.left;
                } else {      // key.compareTo(cur.key) > 0
                    ans = cur;
                    cur = cur.right;
                }
            }
            return ans;
        }

        // 注意点与上述函数相同     >= k 最小的, 大的cur记录， 小的cur不记录
        private SBTNode<K, V> findLastNoSmallIndex(K key) {
            SBTNode<K, V> cur = root;
            SBTNode<K, V> ans = null;   // 【注意】这个ans 初始值设为null，如果找不到 >= 最小，那就返回null。 如果=cur的话，会错的
            while (cur != null) {
                if (key.compareTo(cur.key) > 0) {
                    cur = cur.right;
                } else if (key.compareTo(cur.key) < 0) {
                    ans = cur;
                    cur = cur.left;
                } else {
                    ans = cur;
                    break;
                }
            }
            return ans;
        }


        public K firstKey() {
            SBTNode<K, V> cur = root;
            if (cur == null) {
                return null;
            } else {
                while (cur.left != null) {
                    cur = cur.left;
                }
            }
            return cur.key;
        }

        public K lastKey() {
            SBTNode<K, V> cur = root;
            if (cur == null) {
                return null;
            } else {
                while (cur.right != null) {
                    cur = cur.right;
                }
            }
            return cur.key;
        }

        //  >= k 的 最小值key
        public K ceilingKey(K key) {
            if (root == null) {
                return null;
            }
            SBTNode<K, V> index = findLastNoSmallIndex(key);
            return index == null ? null : index.key;
        }

        public K floorKey(K key) {
            if (root == null) {
                return null;
            }
            SBTNode<K, V> index = findLastNoMoreIndex(key);
            return index == null ? null : index.key;
        }

        public int size() {
            if (root == null) {
                return 0;
            } else {
                return root.size;
            }
        }

        // 很大的特点: 可以找到对应的 index； 注意 因为每个节点都有size， 注意节点从【0】开始
        // 递归函数
        // 传入的参数是 相对于当前节点为头的整棵子树 的相对下标. 所以 需要两个参数，一个是当前节点，一个是请求的相对下标
        private SBTNode<K, V> getIndexNode(SBTNode<K, V> cur, int index) {
            SBTNode<K, V> ans = cur;
            // 左子树的大小 就是 当前节点的 相对下标
            int curIndex = cur.left == null ? 0 : cur.left.size;
            if (index == curIndex) {
                return cur;
            } else if (index < curIndex) {
                return getIndexNode(cur.left, index);
            } else {
                // 去右边的话，要进行 下标换算
                // 那就要将 index 减去 【左边+当前位置的 节点个数】 -》 curIndex + 1
                return getIndexNode(cur.right, index - curIndex - 1);
            }
        }

        public K getIndexKey(int index) {
            if (root == null) {
                return null;
            }
            if (index < 0 || (root != null && index >= root.size)) {
                throw new RuntimeException("Invalid Parameter");
            }
            SBTNode<K, V> ans = getIndexNode(root, index);
            return ans.key;
        }

        public V getIndexValue(int index) {
            if (root == null) {
                return null;
            }
            if (index < 0 || (root != null && index >= root.size)) {
                throw new RuntimeException("Invalid Parameter");
            }
            SBTNode<K, V> ans = getIndexNode(root, index);
            return ans.val;
        }

    }



    // for test
    public static void printAll(SBTNode<String, Integer> head) {
        System.out.println("Binary Tree:");
        printInOrder(head, 0, "H", 17);
        System.out.println();
    }

    // for test
    public static void printInOrder(SBTNode<String, Integer> head, int height, String to, int len) {
        if (head == null) {
            return;
        }
        printInOrder(head.right, height + 1, "v", len);
        String val = to + "(" + head.key + "," + head.val + ")" + to;
        int lenM = val.length();
        int lenL = (len - lenM) / 2;
        int lenR = len - lenM - lenL;
        val = getSpace(lenL) + val + getSpace(lenR);
        System.out.println(getSpace(height * len) + val);
        printInOrder(head.left, height + 1, "^", len);
    }

    // for test
    public static String getSpace(int num) {
        String space = " ";
        StringBuffer buf = new StringBuffer("");
        for (int i = 0; i < num; i++) {
            buf.append(space);
        }
        return buf.toString();
    }

    public static void main(String[] args) {
        SBTTreeMap<String, Integer> sbt = new SBTTreeMap<String, Integer>();
        sbt.put("d", 4);
        sbt.put("c", 3);
        sbt.put("a", 1);
        sbt.put("b", 2);
        // sbt.put("e", 5);
        sbt.put("g", 7);
        sbt.put("f", 6);
        sbt.put("h", 8);
        sbt.put("i", 9);
        sbt.put("a", 111);
        System.out.println(sbt.get("a"));
        sbt.put("a", 1);
        System.out.println(sbt.get("a"));
        for (int i = 0; i < sbt.size(); i++) {
            System.out.println(sbt.getIndexKey(i) + " , " + sbt.getIndexValue(i));
        }
        printAll(sbt.root);
        System.out.println(sbt.firstKey());
        System.out.println(sbt.lastKey());
        System.out.println(sbt.floorKey("g"));
        System.out.println(sbt.ceilingKey("g"));
        System.out.println(sbt.floorKey("e"));
        System.out.println(sbt.ceilingKey("e"));
        System.out.println(sbt.floorKey(""));
        System.out.println(sbt.ceilingKey(""));
        System.out.println(sbt.floorKey("j"));
        System.out.println(sbt.ceilingKey("j"));
        sbt.remove("d");
        printAll(sbt.root);
        sbt.remove("f");
        printAll(sbt.root);

    }


}
