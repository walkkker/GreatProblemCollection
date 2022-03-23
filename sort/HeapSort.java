package sort;

public class HeapSort {
    public int[] sortArray(int[] nums) {
        // O(N)
        for (int i = nums.length - 1; i >= 0; i--) {
            heapify(nums, i, nums.length);
        }

        // O（N * log N）, 这个是排序部分，其实就是 pop操作
        int size = nums.length;
        while (size > 0) {
            swap(nums, 0, --size);
            heapify(nums, 0, size);
        }
        return nums;
    }

    // 大根堆， 只需要 堆的向下调整
    public void heapify(int[] heap, int index, int size) {
        // father: i; left: 2*i + 1; right: 2*i + 2
        int left = 2 * index + 1;
        while (left < size) {
            int largest = left + 1 < size ? (heap[left] >= heap[left + 1] ? left : left + 1) : left;  // 【错误点1， 这个地方别写错了】
            largest = heap[index] >= heap[largest] ? index : largest;
            if (largest == index) {
                break;
            }
            swap(heap, index, largest);
            index = largest;
            left = 2 * index + 1;
        }
    }

    // 堆的 向上调整 和 向下调整，都需要用到 swap
    public void swap(int[] arr, int a, int b) {
        int tmp = arr[a];
        arr[a] = arr[b];
        arr[b] = tmp;
    }
}
