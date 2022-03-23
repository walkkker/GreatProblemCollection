package dataStructureDesign;

import java.util.*;
public class LRU {

    /**
     核心点：
     （1）Node 设计
     key
     val
     Node prev
     Node next

     (2) DoubleLinkedList设计 （必须头尾双链表，因为 给定任何一个节点，可以直接 删除）
     Node head
     Node tail
     关键的三个函数：
     Node removeHead()
     void addToTail(Node node)
     void moveNodeToTail(Node node) -> 上游保证list中一定存在这个node

     (3) MyCache的设计
     HashMap<Integer, Node> keyNodeMap;
     DoubleLinkedList nodeList;
     int size;
     int cap;

     【注意】
     （1）增加/删除节点的时候， 一定要保证 HashMap, DoubleLinkedList, size 三者同步。
     （2）cache中，put() 方法，存在两个选项：一个是修改已有值（有点类似于get），一个是插入新值，这两个对应不同的逻辑，不要漏掉。


     */

    class LRUCache {

        HashMap<Integer, Node> map;
        DoubleLinkedList doubleList;
        int size;
        int cap;


        public LRUCache(int capacity) {
            map = new HashMap<>();
            doubleList = new DoubleLinkedList();
            size = 0;
            cap = capacity;
        }

        public int get(int key) {
            if (!map.containsKey(key)) {
                return -1;
            } else {
                Node node = map.get(key);
                doubleList.moveNodeToTail(node);
                return node.val;
            }
        }

        public void put(int key, int value) {
            if (map.containsKey(key)) {
                Node node = map.get(key);
                node.val = value;
                doubleList.moveNodeToTail(node);
            } else {
                if (size == cap) {
                    // map 与 doubleList 同步 增删
                    Node node = doubleList.removeHead();
                    map.remove(node.key);
                    size--;
                }
                Node node = new Node(key, value);
                map.put(key, node);
                doubleList.addToTail(node);
                size++;
            }
        }

        public class Node {
            int key;
            int val;
            Node prev;
            Node next;

            public Node(int k, int v) {
                key = k;
                val = v;
            }
        }

        public class DoubleLinkedList{
            Node head;
            Node tail;

            public DoubleLinkedList() {
                head = null;
                tail = null;
            }

            public Node removeHead() {
                Node ans = head;
                if (head == tail) {
                    head = null;
                    tail = null;
                } else {
                    head = head.next;
                    // 双端列表，所以多断开连接
                    head.prev = null;
                }
                ans.next = null;
                return ans;
            }

            public void addToTail(Node node) {
                if (head == null) {
                    head = node;
                    tail = node;
                } else {
                    tail.next = node;
                    node.prev = tail;
                    tail = node;
                }
            }

            public void moveNodeToTail(Node node) {
                // 三种情况
                // 在尾部，在中间，在头部

                // Step1: 把node 从节点删除，取出来
                if (tail == node) {
                    return;
                } else if (head == node) {
                    head = head.next;
                    node.next = null;
                    head.prev = null;
                } else {
                    node.prev.next = node.next;
                    node.next.prev = node.prev;
                    node.next = null;
                    node.prev = null;
                }

                // Step2 加到链表尾部
                addToTail(node);
            }
        }
    }

/**
 * Your LRUCache object will be instantiated and called as such:
 * LRUCache obj = new LRUCache(capacity);
 * int param_1 = obj.get(key);
 * obj.put(key,value);
 */
}
