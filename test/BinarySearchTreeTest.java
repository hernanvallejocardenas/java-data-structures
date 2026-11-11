import java.util.Arrays;
import java.util.List;

/**
 * Tests for BinarySearchTree. Run through TestRunner.
 */
public class BinarySearchTreeTest {

    public static void testInsertAndSearch() {
        BinarySearchTree<Integer> tree = new BinarySearchTree<>();
        tree.insert(10);
        tree.insert(5);
        tree.insert(15);
        Assert.equals(10, tree.search(10), "search root");
        Assert.equals(5, tree.search(5), "search left child");
        Assert.equals(15, tree.search(15), "search right child");
        Assert.isNull(tree.search(7), "search missing value");
        Assert.isTrue(tree.contains(10), "contains present value");
        Assert.isFalse(tree.contains(7), "contains missing value");
        Assert.equals(3, tree.size(), "size after 3 inserts");
    }

    public static void testDuplicateInsert() {
        BinarySearchTree<Integer> tree = new BinarySearchTree<>();
        Assert.isTrue(tree.insert(10), "first insert returns true");
        Assert.isFalse(tree.insert(10), "duplicate insert returns false");
        Assert.equals(1, tree.size(), "size unchanged after duplicate");
        Assert.equals(Arrays.asList(10), tree.inorder(), "inorder after duplicate");
    }

    public static void testInorder() {
        BinarySearchTree<Integer> tree = new BinarySearchTree<>();
        for (int value : new int[]{10, 5, 15, 3, 7, 12, 20}) {
            tree.insert(value);
        }
        List<Integer> expected = Arrays.asList(3, 5, 7, 10, 12, 15, 20);
        Assert.equals(expected, tree.inorder(), "inorder is sorted ascending");
    }

    public static void testDeleteLeaf() {
        BinarySearchTree<Integer> tree = new BinarySearchTree<>();
        for (int value : new int[]{10, 5, 15, 3}) {
            tree.insert(value);
        }
        Assert.isTrue(tree.delete(3), "delete leaf returns true");
        Assert.equals(Arrays.asList(5, 10, 15), tree.inorder(), "inorder after leaf delete");
        Assert.equals(3, tree.size(), "size after leaf delete");
        Assert.isFalse(tree.contains(3), "deleted leaf is gone");
    }

    public static void testDeleteOneChild() {
        BinarySearchTree<Integer> tree = new BinarySearchTree<>();
        for (int value : new int[]{10, 5, 15, 3}) {
            tree.insert(value);
        }
        // Node 5 has only a left child (3).
        Assert.isTrue(tree.delete(5), "delete one-child node returns true");
        Assert.equals(Arrays.asList(3, 10, 15), tree.inorder(), "inorder after one-child delete");
        Assert.equals(3, tree.size(), "size after one-child delete");
        Assert.isFalse(tree.contains(5), "deleted node is gone");
        Assert.isTrue(tree.contains(3), "child of deleted node is still present");
    }

    public static void testDeleteTwoChildren() {
        BinarySearchTree<Integer> tree = new BinarySearchTree<>();
        for (int value : new int[]{10, 5, 15, 3, 7, 12, 20}) {
            tree.insert(value);
        }
        // Node 5 has two children (3 and 7).
        Assert.isTrue(tree.delete(5), "delete two-children node returns true");
        Assert.equals(Arrays.asList(3, 7, 10, 12, 15, 20), tree.inorder(),
                "inorder after deleting 5");
        Assert.equals(6, tree.size(), "size after deleting 5");
        // Now delete the root, which also has two children.
        Assert.isTrue(tree.delete(10), "delete root returns true");
        Assert.equals(Arrays.asList(3, 7, 12, 15, 20), tree.inorder(),
                "inorder after deleting root");
        Assert.equals(5, tree.size(), "size after deleting root");
        Assert.isFalse(tree.contains(10), "deleted root is gone");
        Assert.isTrue(tree.contains(12), "successor is still present");
    }

    public static void testDeleteMissing() {
        BinarySearchTree<Integer> tree = new BinarySearchTree<>();
        for (int value : new int[]{10, 5, 15}) {
            tree.insert(value);
        }
        Assert.isFalse(tree.delete(7), "delete missing value returns false");
        Assert.equals(Arrays.asList(5, 10, 15), tree.inorder(), "tree unchanged after failed delete");
        Assert.equals(3, tree.size(), "size unchanged after failed delete");
    }

    public static void testHeight() {
        BinarySearchTree<Integer> empty = new BinarySearchTree<>();
        Assert.equals(-1, empty.height(), "height of empty tree");
        BinarySearchTree<Integer> single = new BinarySearchTree<>();
        single.insert(10);
        Assert.equals(0, single.height(), "height of single node");
        BinarySearchTree<Integer> balanced = new BinarySearchTree<>();
        for (int value : new int[]{10, 5, 15, 3, 7, 12, 20}) {
            balanced.insert(value);
        }
        Assert.equals(2, balanced.height(), "height of balanced tree");
        BinarySearchTree<Integer> degenerate = new BinarySearchTree<>();
        for (int value : new int[]{1, 2, 3, 4, 5}) {
            degenerate.insert(value);
        }
        Assert.equals(4, degenerate.height(), "height of degenerate tree (sorted insert)");
    }

    public static void testEmptyTree() {
        BinarySearchTree<Integer> tree = new BinarySearchTree<>();
        Assert.equals(0, tree.size(), "empty size");
        Assert.isTrue(tree.isEmpty(), "isEmpty");
        Assert.equals(-1, tree.height(), "empty height");
        Assert.equals(Arrays.asList(), tree.inorder(), "empty inorder");
        Assert.isNull(tree.search(10), "search empty tree");
        Assert.isFalse(tree.delete(10), "delete from empty tree");
        Assert.expectThrows(NullPointerException.class, () -> tree.insert(null), "insert null");
    }
}
