package topK;

// 自主实现堆 + 门槛堆的应用
public class HeapMethod {
    class Solution {
        // 桶排序 O(N * log K)
        public int findKthLargest(int[] nums, int k) {
            MyMinHeap heap = new MyMinHeap(k); // 小根堆 =》 门槛堆
            // 没有到门槛的时候，进；  到了门槛的时候检查，是否要将堆顶弹出，新元素进入
            for (int i = 0; i < nums.length; i++) {
                if (heap.size() < k) {
                    heap.add(nums[i]);
                } else {
                    if (heap.peek() < nums[i]) {
                        heap.poll();
                        heap.add(nums[i]);
                    }
                }
            }
            return heap.peek();
        }

        public class MyMinHeap{
            int size;
            int[] heap;

            public MyMinHeap(int cap) {
                heap = new int[cap];
                size = 0;
            }


            private void heapInsert(int index) {    // 当前插入位置是index,沿着index向上做堆的向上调整
                // 会自动终止，因为 (0-1) / 2 == 0
                while (heap[index] < heap[(index - 1) / 2]) {
                    swap(heap, index, (index - 1) / 2);
                    index = (index - 1) / 2;   // 不要忘了 更改index的指向
                }
            }

            private void heapify(int index) {  // 由index往下做 堆的向下调整
                int left = index * 2 + 1;
                while (left < size) {
                    int smallest = left + 1 < size ? (heap[left] < heap[left + 1] ? left : left + 1) : left;
                    smallest = heap[smallest] < heap[index] ? smallest : index;
                    if (smallest == index) {
                        break;
                    }
                    swap(heap, smallest, index);
                    index = smallest;
                    left = index * 2 + 1;
                }
            }

            public void add(int num) {
                heap[size++] = num;
                heapInsert(size - 1);
            }

            public int poll() {
                int ans = heap[0];
                swap(heap, 0, --size);
                heapify(0);
                return ans;
            }

            public int peek() {
                return heap[0];
            }

            public int size() {
                return size;
            }

            private void swap(int[] heap, int i, int j) {
                int tmp = heap[i];
                heap[i] = heap[j];
                heap[j] = tmp;
            }

        }
    }
}
