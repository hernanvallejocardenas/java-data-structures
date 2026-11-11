# Java Data Structures

Three classic data structures implemented from scratch in Java, with a
test suite and a small command-line demo. Standard library only; no build
tools, no external dependencies, no JUnit. Requires a JDK (javac and java).

## The three structures

### HashMap — `src/HashMap.java`

A hash table with separate chaining and dynamic resizing. Each bucket
holds a singly linked list of entries. When the number of entries exceeds
capacity times the load factor (0.75), the table doubles and every entry
is rehashed. Keys must be non-null; values may be null.

Operations: `put`, `get`, `remove`, `containsKey`, `size`, `isEmpty`,
`keys`, `capacity`.

Time complexity (average case): `put`, `get`, `remove`, `containsKey` are
O(1). Worst case (all keys in one bucket) is O(n). Resizing is O(n),
amortized O(1) per insertion.

### BinarySearchTree — `src/BinarySearchTree.java`

A binary search tree storing distinct values in sorted order. Every
node's left subtree holds smaller values and its right subtree holds
larger ones, so an inorder traversal returns the values in ascending
order. Duplicate inserts are rejected (set semantics). Deletion handles
all three cases: leaf, one child, and two children (replaced by the
inorder successor).

Operations: `insert`, `search`, `contains`, `delete`, `inorder`,
`height`, `size`, `isEmpty`.

Time complexity (average case, roughly balanced tree): `insert`, `search`,
`contains`, `delete` are O(log n). Worst case (values inserted in sorted
order, a degenerate tree) is O(n). `inorder` and `height` are O(n).
Height is measured in edges: an empty tree has height -1, a single node
has height 0.

### MinHeap — `src/MinHeap.java`

A min-heap (priority queue) stored as an array in level order. Every
parent is less than or equal to its children, so the smallest element is
always at the root. Insertion sifts the new element up; removing the
minimum moves the last element to the root and sifts it down. Building a
heap from an array (`heapify`) is O(n) by sifting down from the last
parent instead of inserting one element at a time.

Operations: `insert`, `extractMin`, `peek`, `heapify`, `size`, `isEmpty`.

Time complexity: `insert` and `extractMin` are O(log n), `peek` is O(1),
`heapify` is O(n), `size` and `isEmpty` are O(1).

## Compile and run the tests

```
javac -d out src/*.java test/*.java
java -cp out TestRunner
```

25 tests, all passing. Coverage: insert, search, and delete for each
structure; empty maps, trees, and heaps; single elements; duplicates;
resize and rehash of the hash table; BST ordering and all three delete
cases; heap ordering, heapify from an array, and a 500-element heap that
forces the backing array to grow.

## Run the demo

```
java -cp out Demo
```

The demo has two parts. First, it counts word frequencies in a sample
sentence with `HashMap`, then prints each word and its count in
alphabetical order by walking a `BinarySearchTree` inorder. Second, it
runs a small task scheduler with `MinHeap`, printing five tasks in
priority order.

## Example usages

Count word frequencies:

```java
HashMap<String, Integer> counts = new HashMap<>();
for (String word : text.toLowerCase().split("[^a-z]+")) {
    Integer count = counts.get(word);
    counts.put(word, count == null ? 1 : count + 1);
}
```

List values in sorted order:

```java
BinarySearchTree<Integer> tree = new BinarySearchTree<>();
tree.insert(10);
tree.insert(5);
tree.insert(15);
List<Integer> sorted = tree.inorder();   // [5, 10, 15]
```

Build a priority queue from an array in O(n):

```java
MinHeap<Integer> heap = MinHeap.heapify(new Integer[]{9, 4, 7, 1, 5});
int next = heap.extractMin();   // 1
```

## Notes

- Java standard library only; no external dependencies.
- Requires Java 8 or later (tested with OpenJDK 21).
- The `out/` directory holds compiled classes and is a build artifact;
  it is not part of the source.
