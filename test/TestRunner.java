/**
 * Runs every test in HashMapTest, BinarySearchTreeTest, and MinHeapTest.
 * Each test is a static method; a failure is caught and reported, and
 * the runner exits with a non-zero status when any test fails.
 *
 * Run: java -cp out TestRunner
 */
public class TestRunner {

    private static int passed = 0;
    private static int failed = 0;

    public static void main(String[] args) {
        run("HashMapTest.testPutAndGet", HashMapTest::testPutAndGet);
        run("HashMapTest.testOverwrite", HashMapTest::testOverwrite);
        run("HashMapTest.testContainsKey", HashMapTest::testContainsKey);
        run("HashMapTest.testRemove", HashMapTest::testRemove);
        run("HashMapTest.testResizeAndRehash", HashMapTest::testResizeAndRehash);
        run("HashMapTest.testEmptyMap", HashMapTest::testEmptyMap);
        run("HashMapTest.testNullKeyThrows", HashMapTest::testNullKeyThrows);
        run("HashMapTest.testKeys", HashMapTest::testKeys);

        run("BinarySearchTreeTest.testInsertAndSearch", BinarySearchTreeTest::testInsertAndSearch);
        run("BinarySearchTreeTest.testDuplicateInsert", BinarySearchTreeTest::testDuplicateInsert);
        run("BinarySearchTreeTest.testInorder", BinarySearchTreeTest::testInorder);
        run("BinarySearchTreeTest.testDeleteLeaf", BinarySearchTreeTest::testDeleteLeaf);
        run("BinarySearchTreeTest.testDeleteOneChild", BinarySearchTreeTest::testDeleteOneChild);
        run("BinarySearchTreeTest.testDeleteTwoChildren", BinarySearchTreeTest::testDeleteTwoChildren);
        run("BinarySearchTreeTest.testDeleteMissing", BinarySearchTreeTest::testDeleteMissing);
        run("BinarySearchTreeTest.testHeight", BinarySearchTreeTest::testHeight);
        run("BinarySearchTreeTest.testEmptyTree", BinarySearchTreeTest::testEmptyTree);

        run("MinHeapTest.testInsertAndPeek", MinHeapTest::testInsertAndPeek);
        run("MinHeapTest.testExtractMinOrder", MinHeapTest::testExtractMinOrder);
        run("MinHeapTest.testHeapify", MinHeapTest::testHeapify);
        run("MinHeapTest.testHeapifyEmpty", MinHeapTest::testHeapifyEmpty);
        run("MinHeapTest.testEmptyHeap", MinHeapTest::testEmptyHeap);
        run("MinHeapTest.testDuplicates", MinHeapTest::testDuplicates);
        run("MinHeapTest.testSingleElement", MinHeapTest::testSingleElement);
        run("MinHeapTest.testLargeHeap", MinHeapTest::testLargeHeap);

        System.out.println();
        System.out.println(passed + " passed, " + failed + " failed, "
                + (passed + failed) + " total");
        if (failed > 0) {
            System.out.println("TESTS FAILED");
            System.exit(1);
        }
        System.out.println("ALL TESTS PASSED");
    }

    private static void run(String name, Runnable test) {
        try {
            test.run();
            passed++;
            System.out.println("PASS " + name);
        } catch (AssertionError e) {
            failed++;
            System.out.println("FAIL " + name + " — " + e.getMessage());
        } catch (Throwable t) {
            failed++;
            System.out.println("ERROR " + name + " — " + t);
        }
    }
}
