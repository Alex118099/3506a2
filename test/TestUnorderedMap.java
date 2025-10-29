/**
 * Supplied by the COMP3506/7505 teaching team, Semester 2, 2025.
 */

import uq.comp3506.a2.structures.UnorderedMap;

public class TestUnorderedMap {

    public static void main(String[] args) {
        System.out.println("Testing the UnorderedMap Class...");
     
        UnorderedMap<Integer, String> map = new UnorderedMap<>();
        
        // 1. Test basic operations
        System.out.println("Test Case 1: Basic put, get, and size");
        map.put(1, "One");
        map.put(2, "Two");
        map.put(3, "Three");
        System.out.println("Size after 3 inserts: " + map.size());
        System.out.println("Get key 2: " + map.get(2));
        System.out.println("Get non-existent key 99: " + map.get(99));

        // 2. Test update operation
        System.out.println("\nTest Case 2: Update existing key");
        String oldValue = map.put(2, "NewTwo");
        System.out.println("Old value for key 2: " + oldValue);
        System.out.println("New value for key 2: " + map.get(2));
        System.out.println("Size should stay 3: " + map.size());

        // 3. Test remove operation
        System.out.println("\nTest Case 3: Remove operations");
        String removed = map.remove(3);
        System.out.println("Removed value: " + removed);
        System.out.println("Size after removal: " + map.size());
        System.out.println("Try to get removed key 3: " + map.get(3));
        System.out.println("Remove non-existent key: " + map.remove(99));

        // 4. Test edge cases
        System.out.println("\nTest Case 4: Edge cases");
        UnorderedMap<Integer, String> emptyMap = new UnorderedMap<>();
        System.out.println("Is new map empty? " + emptyMap.isEmpty());
        System.out.println("Remove from empty map: " + emptyMap.remove(1));
        System.out.println("Get from empty map: " + emptyMap.get(1));

        // 5. Test clear and resize (by adding many elements)
        System.out.println("\nTest Case 5: Clear and resize behavior");
        UnorderedMap<Integer, String> largeMap = new UnorderedMap<>();
        for (int i = 0; i < 20; i++) {
            largeMap.put(i, "Value" + i);
        }
        System.out.println("Size after 20 inserts: " + largeMap.size());
        System.out.println("Get key 15: " + largeMap.get(15));
        largeMap.clear();
        System.out.println("Size after clear: " + largeMap.size());
        System.out.println("Is map empty after clear? " + largeMap.isEmpty());
    }
}
