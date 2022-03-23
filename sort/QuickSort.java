package sort;

public class QuickSort {
    // Recursice method
    public int[] sortArray(int[] nums) {
        process(nums, 0, nums.length - 1);
        return nums;
    }

    public void process(int[] arr, int L, int R) {
        if (L >= R) {
            return;
        }
        swap(arr, L + (int) (Math.random() * (R - L + 1)), R);
        int[] equalArea = partition(arr, L, R);
        process(arr, L, equalArea[0] - 1);
        process(arr, equalArea[1] + 1, R);
    }

    public int[] partition(int[] arr, int L, int R) {
        int index = L;
        int less = L - 1;
        int more = R;
        while (index < more) {
            if (arr[index] == arr[R]) {
                index++;
            } else if (arr[index] < arr[R]) {
                swap(arr, ++less, index++);
            } else {
                swap(arr, --more, index);
            }
        }
        swap(arr, R, more++);
        return new int[]{less + 1, more - 1};
    }

    public void swap(int[] arr, int a, int b) {
        int tmp = arr[a];
        arr[a] = arr[b];
        arr[b] = tmp;
    }
}
