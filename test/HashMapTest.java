import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Tests for HashMap. Run through TestRunner.
 */
public class HashMapTest {

    public static void testPutAndGet() {
        HashMap<String, Integer> map = new HashMap<>();
        map.put("apple", 1);
        map.put("banana", 2);
        map.put("cherry", 3);
        Assert.equals(1, map.get("apple"), "get apple");
        Assert.equals(2, map.get("banana"), "get banana");
        Assert.equals(3, map.get("cherry"), "get cherry");
        Assert.isNull(map.get("missing"), "get missing key");
        Assert.equals(3, map.size(), "size after 3 puts");
    }

    public static void testOverwrite() {
        HashMap<String, Integer> map = new HashMap<>();
        Assert.isNull(map.put("apple", 1), "first put returns null");
        Assert.equals(1, map.put("apple", 5), "overwrite returns old value");
        Assert.equals(5, map.get("apple"), "value after overwrite");
        Assert.equals(1, map.size(), "size unchanged after overwrite");
    }

    public static void testContainsKey() {
        HashMap<String, Integer> map = new HashMap<>();
        map.put("apple", 1);
        Assert.isTrue(map.containsKey("apple"), "contains present key");
        Assert.isFalse(map.containsKey("banana"), "contains absent key");
        map.put("banana", 2);
        Assert.isTrue(map.containsKey("banana"), "contains after put");
    }

    public static void testRemove() {
        HashMap<String, Integer> map = new HashMap<>();
        map.put("apple", 1);
        map.put("banana", 2);
        Assert.equals(1, map.remove("apple"), "remove returns old value");
        Assert.isNull(map.get("apple"), "get after remove");
        Assert.isFalse(map.containsKey("apple"), "contains after remove");
        Assert.equals(1, map.size(), "size after remove");
        Assert.isNull(map.remove("apple"), "remove missing key returns null");
        Assert.equals(1, map.size(), "size unchanged after removing missing key");
    }

    public static void testResizeAndRehash() {
        HashMap<String, Integer> map = new HashMap<>();
        int count = 200;
        for (int i = 0; i < count; i++) {
            map.put("key" + i, i);
        }
        Assert.equals(count, map.size(), "size after 200 puts");
        // Starting from capacity 16 with load factor 0.75, 200 entries
        // force five doublings: 16 -> 32 -> 64 -> 128 -> 256 -> 512.
        Assert.equals(512, map.capacity(), "capacity after resizing");
        for (int i = 0; i < count; i++) {
            Assert.equals(i, map.get("key" + i), "get key" + i + " after rehash");
        }
    }

    public static void testEmptyMap() {
        HashMap<String, Integer> map = new HashMap<>();
        Assert.equals(0, map.size(), "empty size");
        Assert.isTrue(map.isEmpty(), "isEmpty");
        Assert.isNull(map.get("apple"), "get from empty map");
        Assert.isFalse(map.containsKey("apple"), "containsKey on empty map");
        Assert.isNull(map.remove("apple"), "remove from empty map");
        Assert.equals(0, map.keys().size(), "keys of empty map");
    }

    public static void testNullKeyThrows() {
        HashMap<String, Integer> map = new HashMap<>();
        Assert.expectThrows(NullPointerException.class, () -> map.put(null, 1), "put null key");
        Assert.expectThrows(NullPointerException.class, () -> map.get(null), "get null key");
        Assert.expectThrows(NullPointerException.class, () -> map.containsKey(null), "containsKey null key");
        Assert.expectThrows(NullPointerException.class, () -> map.remove(null), "remove null key");
    }

    public static void testKeys() {
        HashMap<String, Integer> map = new HashMap<>();
        map.put("apple", 1);
        map.put("banana", 2);
        map.put("cherry", 3);
        List<String> keys = map.keys();
        Assert.equals(3, keys.size(), "keys size");
        Set<String> set = new HashSet<>(keys);
        Assert.isTrue(set.contains("apple"), "keys contain apple");
        Assert.isTrue(set.contains("banana"), "keys contain banana");
        Assert.isTrue(set.contains("cherry"), "keys contain cherry");
    }
}
