import java.util.ArrayList;
import java.util.List;

/**
 * A binary search tree (BST) that stores distinct values in sorted order.
 *
 * Ordering rule: for every node, all values in its left subtree are
 * smaller, and all values in its right subtree are larger. That rule is
 * what makes search, insert, and delete fast, and what makes an inorder
 * traversal return the values in ascending order.
 *
 * Duplicate values are not stored: inserting a value that is already
 * present returns false and leaves the tree unchanged (set semantics).
 *
 * Time complexity (average case, roughly balanced tree):
 *   insert, search, contains, delete: O(log n)
 *   worst case (values inserted in sorted order, a degenerate tree): O(n)
 *   inorder traversal, height: O(n)
 *
 * Values must be non-null and comparable.
 */
public class BinarySearchTree<T extends Comparable<T>> {

    /** One node of the tree. */
    private static class Node<T> {
        T value;
        Node<T> left;
        Node<T> right;

        Node(T value) {
            this.value = value;
        }
    }

    private Node<T> root;

    /** Number of values stored. */
    private int size;

    /** Number of values in the tree. O(1). */
    public int size() {
        return size;
    }

    /** True when the tree holds no values. O(1). */
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * Inserts the value. Returns true when it was added, false when it
     * was already present. Average O(log n), worst O(n).
     */
    public boolean insert(T value) {
        if (value == null) {
            throw new NullPointerException("null values are not supported");
        }
        if (contains(value)) {
            return false;
        }
        root = insert(root, value);
        size++;
        return true;
    }

    /** Recursive insert: walks down comparing, attaches the new node at a leaf. */
    private Node<T> insert(Node<T> node, T value) {
        if (node == null) {
            return new Node<>(value);
        }
        int cmp = value.compareTo(node.value);
        if (cmp < 0) {
            node.left = insert(node.left, value);
        } else if (cmp > 0) {
            node.right = insert(node.right, value);
        }
        return node;
    }

    /**
     * Returns the stored value equal to the argument, or null when the
     * tree does not contain it. Average O(log n), worst O(n).
     */
    public T search(T value) {
        if (value == null) {
            throw new NullPointerException("null values are not supported");
        }
        Node<T> node = root;
        while (node != null) {
            int cmp = value.compareTo(node.value);
            if (cmp == 0) {
                return node.value;
            }
            node = cmp < 0 ? node.left : node.right;
        }
        return null;
    }

    /** True when the tree contains the value. Average O(log n), worst O(n). */
    public boolean contains(T value) {
        return search(value) != null;
    }

    /**
     * Deletes the value. Returns true when it was present and removed,
     * false when it was absent. Average O(log n), worst O(n).
     *
     * Three cases, handled by the recursive helper:
     *   leaf: remove it. One child: replace it with that child.
     *   two children: replace its value with the inorder successor
     *   (the smallest value in the right subtree), then delete the
     *   successor from the right subtree.
     */
    public boolean delete(T value) {
        if (value == null) {
            throw new NullPointerException("null values are not supported");
        }
        if (!contains(value)) {
            return false;
        }
        root = delete(root, value);
        size--;
        return true;
    }

    /** Recursive delete. Returns the new root of the subtree. */
    private Node<T> delete(Node<T> node, T value) {
        if (node == null) {
            return null;
        }
        int cmp = value.compareTo(node.value);
        if (cmp < 0) {
            node.left = delete(node.left, value);
        } else if (cmp > 0) {
            node.right = delete(node.right, value);
        } else {
            if (node.left == null) {
                return node.right;   // leaf, or only a right child
            }
            if (node.right == null) {
                return node.left;    // only a left child
            }
            // Two children: copy the inorder successor's value in,
            // then delete the successor (which has at most one child).
            Node<T> successor = min(node.right);
            node.value = successor.value;
            node.right = delete(node.right, successor.value);
        }
        return node;
    }

    /** Smallest value in the subtree: keep going left. */
    private Node<T> min(Node<T> node) {
        while (node.left != null) {
            node = node.left;
        }
        return node;
    }

    /**
     * Returns all values in ascending order. O(n).
     * This works because an inorder traversal (left, node, right) visits
     * smaller values before larger ones by the BST ordering rule.
     */
    public List<T> inorder() {
        List<T> result = new ArrayList<>();
        inorder(root, result);
        return result;
    }

    private void inorder(Node<T> node, List<T> result) {
        if (node == null) {
            return;
        }
        inorder(node.left, result);
        result.add(node.value);
        inorder(node.right, result);
    }

    /**
     * Height of the tree: the number of edges on the longest path from
     * the root to a leaf. An empty tree has height -1, a single node
     * has height 0. O(n).
     */
    public int height() {
        return height(root);
    }

    private int height(Node<T> node) {
        if (node == null) {
            return -1;
        }
        return 1 + Math.max(height(node.left), height(node.right));
    }
}
