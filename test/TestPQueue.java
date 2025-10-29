/**
 * Supplied by the COMP3506/7505 teaching team, Semester 2, 2025.
 */

import uq.comp3506.a2.structures.Heap;
import uq.comp3506.a2.structures.Entry;

public class TestPQueue {

    public static void main(String[] args) {
        System.out.println("Testing the Heap-based Priority Queue Class...");
     
        Heap<Integer, String> pq = new Heap<>();
        
        // 1. Test basic operations
        System.out.println("Test Case 1: Insert and size");
        pq.insert(5, "Five");
        pq.insert(3, "Three");
        pq.insert(7, "Seven");
        pq.insert(1, "One");
        System.out.println("Size after 4 inserts: " + pq.size());
        System.out.println("Is empty? " + pq.isEmpty());

        // 2. Test peekMin (should not remove)
        System.out.println("\nTest Case 2: PeekMin");
        Entry<Integer, String> min = pq.peekMin();
        System.out.println("Min element (key): " + min.getKey() + ", value: " + min.getValue());
        System.out.println("Size after peek: " + pq.size());

        // 3. Test removeMin
        System.out.println("\nTest Case 3: RemoveMin");
        Entry<Integer, String> removed = pq.removeMin();
        System.out.println("Removed (key): " + removed.getKey() + ", value: " + removed.getValue());
        System.out.println("Size after removal: " + pq.size());
        System.out.println("New min (key): " + pq.peekMin().getKey());

        // 4. Test edge cases - empty heap
        System.out.println("\nTest Case 4: Empty heap operations");
        Heap<Integer, String> emptyHeap = new Heap<>();
        System.out.println("Is empty? " + emptyHeap.isEmpty());
        System.out.println("PeekMin on empty: " + emptyHeap.peekMin());
        System.out.println("RemoveMin on empty: " + emptyHeap.removeMin());

        // 5. Test clear
        System.out.println("\nTest Case 5: Clear operation");
        System.out.println("Size before clear: " + pq.size());
        pq.clear();
        System.out.println("Size after clear: " + pq.size());
        System.out.println("Is empty after clear? " + pq.isEmpty());

        // 6. Test with many elements
        System.out.println("\nTest Case 6: Many elements");
        Heap<Integer, String> largeHeap = new Heap<>();
        for (int i = 10; i >= 1; i--) {
            largeHeap.insert(i, "Value" + i);
        }
        System.out.println("Size after 10 inserts: " + largeHeap.size());
        System.out.println("Min should be 1: " + largeHeap.peekMin().getKey());
        System.out.println("Removing all elements in order:");
        while (!largeHeap.isEmpty()) {
            System.out.print(largeHeap.removeMin().getKey() + " ");
        }
        System.out.println();
    }
}
