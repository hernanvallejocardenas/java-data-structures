import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * A hash table that maps keys to values, implemented from scratch.
 *
 * Collision handling: separate chaining. Each bucket holds a singly linked
 * list of entries whose keys hash to that bucket.
 *
 * Resizing: when the number of entries exceeds capacity * load factor
 * (0.75), the table doubles in size and every entry is rehashed into the
 * new table. Resizing keeps chains short, so the average case stays fast.
 *
 * Time complexity (average case, with a well-distributed hash function):
 *   put, get, remove, containsKey: O(1)
 *   worst case (all keys in one bucket): O(n)
 *   resize: O(n), amortized O(1) per insertion
 *
 * Keys must be non-null. Values may be null.
 */
public class HashMap<K, V> {

    /** Default number of buckets. A power of two, so the index is a bit mask. */
    private static final int DEFAULT_CAPACITY = 16;

    /** Resize when size exceeds capacity * LOAD_FACTOR. */
    private static final double LOAD_FACTOR = 0.75;

    /** One entry in a chain. */
    private static class Node<K, V> {
        final K key;
        V value;
        Node<K, V> next;

        Node(K key, V value, Node<K, V> next) {
            this.key = key;
            this.value = value;
            this.next = next;
        }
    }

    /** The buckets. table[i] is the head of the chain for bucket i. */
    private Node<K, V>[] table;

    /** Number of key-value pairs stored. */
    private int size;

    /** Resize when size exceeds this value. */
    private int threshold;

    /** Creates an empty map with the default capacity (16). */
    @SuppressWarnings("unchecked")
    public HashMap() {
        table = (Node<K, V>[]) new Node[DEFAULT_CAPACITY];
        threshold = (int) (DEFAULT_CAPACITY * LOAD_FACTOR);
    }

    /** Number of key-value pairs in the map. O(1). */
    public int size() {
        return size;
    }

    /** True when the map holds no entries. O(1). */
    public boolean isEmpty() {
        return size == 0;
    }

    /** Number of buckets. Exposed so tests can verify that resizing happened. O(1). */
    public int capacity() {
        return table.length;
    }

    /**
     * Mixes the key's hash code so that poorly distributed hash codes
     * (for example, ones that differ only in high bits) still spread
     * across buckets. This is the same spread used by java.util.HashMap.
     */
    private static int hash(Object key) {
        int h = key.hashCode();
        return h ^ (h >>> 16);
    }

    /** Bucket index for a key. table.length is a power of two, so the mask works. */
    private int indexFor(Object key) {
        return (table.length - 1) & hash(key);
    }

    /**
     * Associates the value with the key. If the key is already present,
     * its value is replaced and the old value is returned. Otherwise the
     * pair is added and null is returned. Average O(1).
     */
    public V put(K key, V value) {
        Objects.requireNonNull(key, "null keys are not supported");
        int index = indexFor(key);
        for (Node<K, V> node = table[index]; node != null; node = node.next) {
            if (node.key.equals(key)) {
                V old = node.value;
                node.value = value;
                return old;
            }
        }
        table[index] = new Node<>(key, value, table[index]);
        size++;
        if (size > threshold) {
            resize();
        }
        return null;
    }

    /**
     * Returns the value for the key, or null when the key is absent.
     * (A null return can also mean the key maps to null; use
     * containsKey to tell the difference.) Average O(1).
     */
    public V get(K key) {
        Objects.requireNonNull(key, "null keys are not supported");
        for (Node<K, V> node = table[indexFor(key)]; node != null; node = node.next) {
            if (node.key.equals(key)) {
                return node.value;
            }
        }
        return null;
    }

    /** True when the map contains the key. Average O(1). */
    public boolean containsKey(K key) {
        Objects.requireNonNull(key, "null keys are not supported");
        for (Node<K, V> node = table[indexFor(key)]; node != null; node = node.next) {
            if (node.key.equals(key)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Removes the key and returns its old value, or null when the key
     * was absent. Average O(1).
     */
    public V remove(K key) {
        Objects.requireNonNull(key, "null keys are not supported");
        int index = indexFor(key);
        Node<K, V> prev = null;
        for (Node<K, V> node = table[index]; node != null; node = node.next) {
            if (node.key.equals(key)) {
                if (prev == null) {
                    table[index] = node.next;   // removed the head of the chain
                } else {
                    prev.next = node.next;      // unlink from the middle or end
                }
                size--;
                return node.value;
            }
            prev = node;
        }
        return null;
    }

    /**
     * Returns all keys in the map, in no particular order.
     * Used by the demo to list the words that were counted. O(n).
     */
    public List<K> keys() {
        List<K> result = new ArrayList<>();
        for (Node<K, V> bucket : table) {
            for (Node<K, V> node = bucket; node != null; node = node.next) {
                result.add(node.key);
            }
        }
        return result;
    }

    /**
     * Doubles the number of buckets and rehashes every entry into the
     * new table. Each entry's bucket can change because the index mask
     * depends on the table length. O(n).
     */
    @SuppressWarnings("unchecked")
    private void resize() {
        Node<K, V>[] oldTable = table;
        table = (Node<K, V>[]) new Node[oldTable.length * 2];
        threshold = (int) (table.length * LOAD_FACTOR);
        for (Node<K, V> bucket : oldTable) {
            for (Node<K, V> node = bucket; node != null; ) {
                Node<K, V> next = node.next;   // save before relinking
                int index = indexFor(node.key);
                node.next = table[index];
                table[index] = node;
                node = next;
            }
        }
    }
}
