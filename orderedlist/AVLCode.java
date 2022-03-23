package orderedlist;

/**
 * （1）创建AVL树节点
 * （2）创建AVL树
 * （3）AVL树就是BST加上了调平操作 -> 是为了保证 左右子树规模相似 =》 从而使得 数据分布规律不受用户输入规律 的影响， 从而提高性能
 * （4）AVL树的 平衡因子是 高度  =》 AVL树 是 强平衡搜索二叉树
 * （5）AVL树 要求 每一个节点的 左右子树高度差 不超过1
 * 【注意】 这类自己实现的结构都是 带有一个size属性， 所以在 Put新元素和remove元素时，一定要注意 添加 size++, size-- 的语句， 切记！
 * 【错误点】 put方法中，检查当前搜索到的最近index节点是否与Key值相等时，一定要 先判断index==null
 * 【错误点】 同样的，get()方法也是一样，判空，否则会 抛出 空指针异常的问题。 这类空指针异常问题一定要注意！！ 所有题全部注意！
 * 【错误点】maintain() 函数 是可能对 cur==null时进行 调平衡操作的（删除节点左右为null时，就会检查null节点），因为要获取L，R的h，所以 一开始一定要检测cur是否为null， 否则抛异常。
 *
 */
public class AVLCode {

    public static class AVLNode<K extends Comparable<K>, V> {
        K key;
        V val;
        AVLNode<K, V> left;
        AVLNode<K, V> right;
        int h;       // AVL树的【平衡因子】 【高度】。 平衡因子存储在AVL节点中

        public AVLNode(K k, V v) {
            key = k;
            val = v;
            left = null;
            right = null;
            h = 0;
        }
    }

    public static class AVL<K extends Comparable<K>, V> {
        // 两个 属性: 头结点 + size
        AVLNode<K, V> root;
        int size;

        public AVL() {
            root = null;
            size = 0;
        }

        // 左旋 + 右旋, 都是针对父节点（当前结点）旋转而言的
        // 【旋转函数要返回 头结点】旋转要返回头结点 =》 因为旋转要换头，所以返回 这棵树的头结点
        // 【注意】旋转完之后，记得要更新 孩子节点改变的节点的 高度h
        public AVLNode<K, V> leftRotate(AVLNode<K, V> cur) {
            AVLNode<K, V> right = cur.right;
            cur.right = right.left;
            right.left = cur;
            // 记得更新 AVLNode 的平衡因子 h高度
            // 先更新 cur,在更新 cur的父
            // 【错误点！！】高度是 两颗子树的 **最大值** ， + 1
            cur.h = Math.max((cur.left != null ? cur.left.h : 0), (cur.right == null ? 0 : cur.right.h)) + 1;
            right.h = Math.max(right.left == null ? 0 : right.left.h, right.right == null ? 0 : right.right.h) + 1;
            return right;
        }

        public AVLNode<K, V> rightRotate(AVLNode<K, V> cur) {
            AVLNode<K, V> left = cur.left;
            cur.left = left.right;
            left.right = cur;
            cur.h = Math.max((cur.left != null ? cur.left.h : 0), (cur.right == null ? 0 : cur.right.h)) + 1;
            left.h = Math.max(left.left == null ? 0 : left.left.h, left.right == null ? 0 : left.right.h) + 1;
            return left;
        }

        // 【真实用在维护节点的平衡性】
        // 四种平衡： LL LR RL RR
        public AVLNode<K, V> maintain(AVLNode<K, V> cur) {
            // 【错误点】 一定要注意 判断 cur == null 的情况！！！
            if (cur == null) {
                return null;
            }
            // maintain函数主要用来判断 LL LR RL RR违规并对其进行调整平衡的操作
            int L = cur.left == null ? 0 : cur.left.h;
            int R = cur.right == null ? 0 : cur.right.h;
            if (Math.abs(L - R) > 1) {   // 条件从==2变成 > 1 好一些
                if (L > R) {
                    // L > R，只可能是 LL / LR 型违规
                    int LL = cur.left.left == null ? 0 : cur.left.left.h;
                    int LR = cur.left.right == null ? 0 : cur.left.right.h;
                    if (LL >= LR) {   // LL 或者 LL+LR型，都只需要 右旋一次
                        cur = rightRotate(cur);
                    } else {          // 否则就是 LR型，那么要先 局部左旋，大整体右旋
                        cur.left = leftRotate(cur.left);
                        cur = rightRotate(cur);
                    }
                } else {
                    int RR = cur.right.right == null ? 0 : cur.right.right.h;
                    int RL = cur.right.left == null ? 0 : cur.right.left.h;
                    if (RR >= RL) {
                        cur = leftRotate(cur);
                    } else {
                        cur.right = rightRotate(cur.right);
                        cur = leftRotate(cur);
                    }
                }
            }
            return cur;
        }


        // 这是一个递归
        // 返回添加后的头部, 上游保证了 进来的节点 AVL树中不存在
        private AVLNode<K, V> add(AVLNode<K, V> cur, K key, V val) {
            if (cur == null) {
                return new AVLNode(key, val);
            }
            if (key.compareTo(cur.key) < 0) {
                cur.left = add(cur.left, key, val);
            } else {    // > 0
                cur.right = add(cur.right, key, val);
            }
            // 【错误点】千万别忘了，最后要更新高度！！ 左边加完了/右边加完了，最后不要忘了更新高度
            cur.h = Math.max(cur.left == null ? 0 : cur.left.h, cur.right == null ? 0 : cur.right.h) + 1;

            cur = maintain(cur);   // 【易错点】全部调整完了之后，不要忘了 调整平衡， 返回新的头部接住！
            return cur;
        }


        // 删除一个节点，返回新的头结点。  上游保证 当前key一定存在在AVL树中。
        // 【难点】注意，BST删除节点有四种情况，双null,一个为null，两个都不为null（找后继节点）
        // 并且，从删除节点到跟，沿途所有节点都要检查 maintain 调平衡
        private AVLNode<K, V> delete(AVLNode<K, V> cur, K key) {
            if (key.compareTo(cur.key) < 0) {
                cur.left = delete(cur.left, key);
            } else if (key.compareTo(cur.key) > 0) {
                cur.right = delete(cur.right, key);
            } else {  // 当前结点就是要删除的节点, BST四种情况的删除
                if (cur.left == null && cur.right == null) {
                    cur = null;
                } else if (cur.left == null) {
                    cur = cur.right;
                } else if (cur.right == null) {
                    cur = cur.left;
                } else {
                    // cur.left != null && cur.right != null   ==>  寻找后继节点（右子树的最左节点）
                    AVLNode<K, V> des = cur.right;
                    while (des.left != null) {
                        des = des.left;
                    }
                    // 此时 des 就是cur的后继结点
                    // 【关键一句】在右子树上递归调用 delete 删除 des
                    cur.right = delete(cur.right, des.key);

                    // 将 des替换cur
                    des.left = cur.left;
                    des.right = cur.right;
                    cur = des;
                }
            }

            // 别忘了最后 一定要更新当前 cur 的高度
            // 所有情况最后都要更新 cur.h， 所以最后 统一 抽离到 最外层写
            if (cur != null) {
                cur.h = Math.max(cur.left != null ? cur.left.h : 0, cur.right != null ? cur.right.h : 0) + 1;
            }

            // BST 收拾好以后， 在当前节点执行 调平操作
            cur = maintain(cur);
            return cur;
        }

        // 【重要 + 错误点】注意 如何得到 距离最近的节点 while循环中每次先记录当前结点就可以
        // 很重要的函数private 寻找key对应的节点
        // 在当前节点上查询是否为key，迭代就可以实现
        // 【小难】返回值：寻找离 key 最近的 AVL节点， 存在两种可能返回 （存在key时是key节点，不存在key时就是最近节点）
        private AVLNode<K, V> findNearIndex(K key) {
            AVLNode<K, V> cur = root;
            AVLNode<K, V> ans = cur;
            while (cur != null) {
                ans = cur;
                if (key.compareTo(cur.key) == 0) {
                    break;
                } else if (key.compareTo(cur.key) < 0) {
                    cur = cur.left;
                } else {
                    cur = cur.right;
                }
            }
            return ans;
        }

        // 下面是对外接口 put 和 remove, containsKey
        public boolean containsKey(K key) {
            AVLNode<K, V> index = findNearIndex(key);
            if (index.key.compareTo(key) == 0) {
                return true;
            } else {
                return false;
            }
        }

        public void put(K key, V val) {
            // 【重要】 null类型无法比较大小，所以 抛出异常，系统中也是这么实现的
            if (key == null) {
                throw new NullPointerException();
            }
            AVLNode<K, V> index = findNearIndex(key);
            // 元素已经存在，那么直接赋值即可。 单纯改值的话，就没有必要调平操作了。
            // 【错误点】关键是，只是改变节点内部值的话，就不更改size了
            // 【错误点】if 条件中 一定要保证 index节点不能为null，如果index为null的话，说明此时root==null，那么要走 add那个分支，不然会报 空指针异常
            if (index != null && key.compareTo(index.key) == 0) {
                index.val = val;
            } else {
                root = add(root, key, val);
                // 【错误点】 千万不要忘了 size++
                size++;
            }
        }

        public void remove(K key) {
            if (containsKey(key)) {
                root = delete(root, key);
                // 【错误点】千万不要忘了 size--!
                size--;
            }
        }

        public V get(K key) {
            AVLNode<K, V> index = findNearIndex(key);
            if (index != null && key.compareTo(index.key) == 0) {
                return index.val;
            } else {
                return null;
            }
        }


        // 【易错点】 找到最后一个 <= 的最大
        // 【注意】 是 cur.key <= key 时，才记录。 也就是说，寻找的是 cur中的 <= K 的最大值对应节点
        private AVLNode<K, V> findLastNoMoreIndex(K key) {
            AVLNode<K ,V> cur = root;
//            AVLNode<K, V> ans = cur;   // 【错误点】
            AVLNode<K, V> ans = null;
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
        private AVLNode<K, V> findLastNoSmallIndex(K key) {
            AVLNode<K, V> cur = root;
            AVLNode<K, V> ans = null;  // 【错误点】 ans默认值要为null，因为是用来记录 可用答案的，当没有答案时，就是null
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
            AVLNode<K, V> cur = root;
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
            AVLNode<K, V> cur = root;
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
            AVLNode<K, V> index = findLastNoSmallIndex(key);
            // 【错误点】有可能返回Null,没有满足要求的答案时
            return index == null ? null : index.key;
        }

        public K floorKey(K key) {
            if (root == null) {
                return null;
            }
            AVLNode<K, V> index = findLastNoMoreIndex(key);
            return index == null ? null : index.key;
        }

        public int size() {
            return size;
        }

    }

}
