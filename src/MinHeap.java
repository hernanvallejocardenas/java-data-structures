import java.util.Arrays;

/**
 * A min-heap (priority queue) implemented from scratch over an array.
 *
 * Heap rule: every parent is less than or equal to its children, so the
 * smallest element is always at the root (index 0). Children of the
 * element at index i sit at 2i + 1 and 2i + 2; the parent of i is (i - 1) / 2.
 *
 * Two operations keep the rule true:
 *   sift up: after inserting at the end, swap the new element with its
 *     parent while it is smaller than the parent.
 *   sift down: after removing the root, move the last element to the root
 *     and swap it with its smaller child while it is larger than that child.
 *
 * Time complexity:
 *   insert, extractMin, peek: O(log n) for insert/extractMin, O(1) for peek
 *   heapify (building a heap from an array): O(n), not O(n log n), because
 *     most nodes are near the leaves and sift down only a short distance
 *   size, isEmpty: O(1)
 *
 * Elements must be non-null and comparable. Smaller means higher priority.
 */
public class MinHeap<T extends Comparable<T>> {

    /** Default array capacity. */
    private static final int DEFAULT_CAPACITY = 16;

    /** The heap, stored as an array in level order. */
    private T[] heap;

    /** Number of elements stored. */
    private int size;

    /** Creates an empty heap. */
    @SuppressWarnings("unchecked")
    public MinHeap() {
        heap = (T[]) new Comparable[DEFAULT_CAPACITY];
        size = 0;
    }

    /**
     * Builds a heap from the given array in O(n) time (heapify).
     * The input array is copied, so later changes to it do not affect
     * the heap. Elements must be non-null.
     */
    @SuppressWarnings("unchecked")
    public MinHeap(T[] items) {
        if (items == null) {
            throw new NullPointerException("items must not be null");
        }
        int capacity = Math.max(DEFAULT_CAPACITY, items.length);
        heap = (T[]) new Comparable[capacity];
        System.arraycopy(items, 0, heap, 0, items.length);
        size = items.length;
        // Bottom-up heapify: sift down every non-leaf node, starting
        // from the last parent. Leaves already satisfy the heap rule.
        for (int i = size / 2 - 1; i >= 0; i--) {
            siftDown(i);
        }
    }

    /**
     * Builds a heap from the given array in O(n) time and returns it.
     * Same as the array constructor; provided as a named factory so the
     * heapify operation is visible in the API.
     */
    public static <T extends Comparable<T>> MinHeap<T> heapify(T[] items) {
        return new MinHeap<>(items);
    }

    /** Number of elements in the heap. O(1). */
    public int size() {
        return size;
    }

    /** True when the heap holds no elements. O(1). */
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * Adds an element, placing it at the end and sifting it up to its
     * correct position. O(log n).
     */
    public void insert(T value) {
        if (value == null) {
            throw new NullPointerException("null values are not supported");
        }
        if (size == heap.length) {
            grow();
        }
        heap[size] = value;
        siftUp(size);
        size++;
    }

    /**
     * Returns the smallest element without removing it, or null when
     * the heap is empty. O(1).
     */
    public T peek() {
        return size == 0 ? null : heap[0];
    }

    /**
     * Removes and returns the smallest element, or null when the heap
     * is empty. The last element moves to the root and sifts down.
     * O(log n).
     */
    public T extractMin() {
        if (size == 0) {
            return null;
        }
        T min = heap[0];
        size--;
        heap[0] = heap[size];
        heap[size] = null;   // avoid holding a reference to the removed element
        if (size > 0) {
            siftDown(0);
        }
        return min;
    }

    /** Moves the element at index i up until the heap rule holds. */
    private void siftUp(int i) {
        while (i > 0) {
            int parent = (i - 1) / 2;
            if (heap[i].compareTo(heap[parent]) >= 0) {
                break;
            }
            swap(i, parent);
            i = parent;
        }
    }

    /** Moves the element at index i down until the heap rule holds. */
    private void siftDown(int i) {
        while (true) {
            int left = 2 * i + 1;
            int right = 2 * i + 2;
            int smallest = i;
            if (left < size && heap[left].compareTo(heap[smallest]) < 0) {
                smallest = left;
            }
            if (right < size && heap[right].compareTo(heap[smallest]) < 0) {
                smallest = right;
            }
            if (smallest == i) {
                break;
            }
            swap(i, smallest);
            i = smallest;
        }
    }

    private void swap(int i, int j) {
        T temp = heap[i];
        heap[i] = heap[j];
        heap[j] = temp;
    }

    /** Doubles the backing array when it fills up. */
    private void grow() {
        heap = Arrays.copyOf(heap, heap.length * 2);
    }
}
