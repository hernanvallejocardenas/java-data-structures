/**
 * Command-line demo for the three data structures.
 *
 * Section 1 counts word frequencies with HashMap, then prints each word
 * and its count in alphabetical order by walking the BinarySearchTree
 * inorder and looking the count up in the HashMap.
 *
 * Section 2 runs a small task scheduler with MinHeap: tasks with a lower
 * priority number run first.
 *
 * Run: java -cp out Demo
 */
public class Demo {

    /** A task with a priority. Lower number means it runs sooner. */
    private static class Task implements Comparable<Task> {
        private final String name;
        private final int priority;

        Task(String name, int priority) {
            this.name = name;
            this.priority = priority;
        }

        @Override
        public int compareTo(Task other) {
            return Integer.compare(this.priority, other.priority);
        }

        @Override
        public String toString() {
            return name + " (priority " + priority + ")";
        }
    }

    public static void main(String[] args) {
        String text = "the quick brown fox jumps over the lazy dog "
                + "the dog barks at the fox the fox runs and the dog chases the fox";

        // --- Section 1: word frequencies with HashMap + BinarySearchTree ---
        HashMap<String, Integer> counts = new HashMap<>();
        for (String word : text.toLowerCase().split("[^a-z]+")) {
            if (word.isEmpty()) {
                continue;
            }
            Integer count = counts.get(word);
            counts.put(word, count == null ? 1 : count + 1);
        }

        BinarySearchTree<String> sorted = new BinarySearchTree<>();
        for (String word : counts.keys()) {
            sorted.insert(word);
        }

        System.out.println("Word frequencies (alphabetical):");
        for (String word : sorted.inorder()) {
            System.out.println("  " + word + ": " + counts.get(word));
        }

        String topWord = null;
        int topCount = 0;
        for (String word : counts.keys()) {
            if (counts.get(word) > topCount) {
                topWord = word;
                topCount = counts.get(word);
            }
        }
        System.out.println("Most frequent word: " + topWord + " (" + topCount + " times)");
        System.out.println("Distinct words: " + counts.size());

        // --- Section 2: task scheduler with MinHeap ---
        MinHeap<Task> queue = new MinHeap<>();
        queue.insert(new Task("Fix login bug", 1));
        queue.insert(new Task("Review pull request", 2));
        queue.insert(new Task("Update README", 3));
        queue.insert(new Task("Refactor tests", 4));
        queue.insert(new Task("Plan next sprint", 5));

        System.out.println();
        System.out.println("Tasks in priority order:");
        while (!queue.isEmpty()) {
            System.out.println("  " + queue.extractMin());
        }
    }
}
