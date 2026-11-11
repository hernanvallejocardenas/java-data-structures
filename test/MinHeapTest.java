import java.util.ArrayList;
import java.util.List;

/**
 * Tests for MinHeap. Run through TestRunner.
 */
public class MinHeapTest {

    public static void testInsertAndPeek() {
        MinHeap<Integer> heap = new MinHeap<>();
        heap.insert(5);
        Assert.equals(5, heap.peek(), "peek after first insert");
        heap.insert(3);
        Assert.equals(3, heap.peek(), "peek after smaller insert");
        heap.insert(8);
        Assert.equals(3, heap.peek(), "peek unchanged after larger insert");
        Assert.equals(3, heap.size(), "size after 3 inserts");
        Assert.isFalse(heap.isEmpty(), "isEmpty after inserts");
    }

    public static void testExtractMinOrder() {
        MinHeap<Integer> heap = new MinHeap<>();
        for (int value : new int[]{5, 3, 8, 1, 9, 2}) {
            heap.insert(value);
        }
        List<Integer> extracted = new ArrayList<>();
        while (!heap.isEmpty()) {
            extracted.add(heap.extractMin());
        }
        List<Integer> expected = List.of(1, 2, 3, 5, 8, 9);
        Assert.equals(expected, extracted, "extractMin returns ascending order");
        Assert.equals(0, heap.size(), "size after draining");
        Assert.isTrue(heap.isEmpty(), "isEmpty after draining");
    }

    public static void testHeapify() {
        Integer[] values = {9, 4, 7, 1, 5, 3, 8, 2, 6};
        MinHeap<Integer> heap = MinHeap.heapify(values);
        Assert.equals(1, heap.peek(), "peek after heapify");
        Assert.equals(9, heap.size(), "size after heapify");
        List<Integer> extracted = new ArrayList<>();
        while (!heap.isEmpty()) {
            extracted.add(heap.extractMin());
        }
        Assert.equals(List.of(1, 2, 3, 4, 5, 6, 7, 8, 9), extracted,
                "heapify then extractMin is sorted");
        // The array constructor does the same O(n) build.
        MinHeap<Integer> heap2 = new MinHeap<>(values);
        Assert.equals(1, heap2.peek(), "peek after array constructor");
    }

    public static void testHeapifyEmpty() {
        MinHeap<Integer> heap = MinHeap.heapify(new Integer[0]);
        Assert.equals(0, heap.size(), "heapify empty array size");
        Assert.isTrue(heap.isEmpty(), "heapify empty array isEmpty");
        Assert.isNull(heap.peek(), "peek on heapified empty array");
        Assert.isNull(heap.extractMin(), "extractMin on heapified empty array");
    }

    public static void testEmptyHeap() {
        MinHeap<Integer> heap = new MinHeap<>();
        Assert.equals(0, heap.size(), "empty size");
        Assert.isTrue(heap.isEmpty(), "isEmpty");
        Assert.isNull(heap.peek(), "peek on empty heap");
        Assert.isNull(heap.extractMin(), "extractMin on empty heap");
    }

    public static void testDuplicates() {
        MinHeap<Integer> heap = new MinHeap<>();
        for (int value : new int[]{4, 2, 2, 5, 2}) {
            heap.insert(value);
        }
        List<Integer> extracted = new ArrayList<>();
        while (!heap.isEmpty()) {
            extracted.add(heap.extractMin());
        }
        Assert.equals(List.of(2, 2, 2, 4, 5), extracted, "duplicates extract in order");
    }

    public static void testSingleElement() {
        MinHeap<Integer> heap = new MinHeap<>();
        heap.insert(42);
        Assert.equals(42, heap.peek(), "peek single element");
        Assert.equals(42, heap.extractMin(), "extractMin single element");
        Assert.isTrue(heap.isEmpty(), "empty after extracting single element");
        Assert.isNull(heap.peek(), "peek after draining single element");
    }

    public static void testLargeHeap() {
        MinHeap<Integer> heap = new MinHeap<>();
        // Insert 500 values in reverse order, forcing several array grows.
        for (int i = 500; i >= 1; i--) {
            heap.insert(i);
        }
        Assert.equals(500, heap.size(), "size after 500 inserts");
        Assert.equals(1, heap.peek(), "peek after 500 inserts");
        for (int expected = 1; expected <= 500; expected++) {
            Assert.equals(expected, heap.extractMin(), "extractMin #" + expected);
        }
        Assert.isTrue(heap.isEmpty(), "empty after draining 500 elements");
    }
}
