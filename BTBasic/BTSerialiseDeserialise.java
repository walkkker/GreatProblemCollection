package BTBasic;

import java.util.*;

public class BTSerialiseDeserialise {

    // 先序遍历方式实现
    public class Codec {

        // Encodes a tree to a single string.
        // 序列化这边，可以使用 QUEUE，也可以使用List，都只是为了存储遍历到的数值，然后转成StringBuilder
        public String serialize(TreeNode root) {
            List<Integer> list = new ArrayList<>();
            seProcess(root, list);
            StringBuilder str = new StringBuilder();
            for (Integer x : list) {
                str.append(String.valueOf(x));
                str.append(",");
            }
            return str.toString();
        }

        public void seProcess(TreeNode cur, List<Integer> list) {
            if (cur == null) {
                list.add(null);
                return;
            }
            list.add(cur.val);
            seProcess(cur.left, list);
            seProcess(cur.right, list);
        }

        // Decodes your encoded data to tree.
        public TreeNode deserialize(String data) {
            String[] strArr = data.split(",");
            Queue<Integer> queue = new LinkedList<>();
            // 【错误点】注意一下， Integer.valueOf("null") 是 不合法的。所以要特殊情况处理一下
            // Integer.valueOf(null) 是可以的。 但是 "null"这个是个字符串，无法转成 1,2,3,等等
            for (String str : strArr) {
                if (str.equals("null")) {
                    queue.add(null);
                } else {
                    queue.add(Integer.valueOf(str));
                }
            }
            TreeNode root = deProcess(queue);
            return root;
        }

        public TreeNode deProcess(Queue<Integer> queue) {
            Integer curVal = queue.poll();
            if (curVal == null) {
                return null;
            }
            TreeNode curNode = new TreeNode(curVal);
            curNode.left = deProcess(queue);
            curNode.right = deProcess(queue);
            return curNode;
        }
    }
}
