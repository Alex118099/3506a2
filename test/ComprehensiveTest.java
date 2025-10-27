import uq.comp3506.a2.structures.Heap;
import uq.comp3506.a2.structures.Entry;

public class ComprehensiveTest {
    public static void main(String[] args) {
        try {
            test1_EmptyHeap();
            test2_SingleElement();
            test3_MultipleElements();
            test4_PriorityOrder();
            System.out.println("\n=== ALL TESTS PASSED ===");
        } catch (Exception e) {
            System.err.println("TEST FAILED: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    static void test1_EmptyHeap() {
        System.out.println("Test 1: Empty Heap");
        Heap<Integer, String> heap = new Heap<>();
        assert heap.isEmpty() : "New heap should be empty";
        assert heap.size() == 0 : "New heap size should be 0";
        assert heap.peekMin() == null : "peekMin on empty heap should return null";
        assert heap.removeMin() == null : "removeMin on empty heap should return null";
        System.out.println("  PASSED\n");
    }
    
    static void test2_SingleElement() {
        System.out.println("Test 2: Single Element");
        Heap<Integer, String> heap = new Heap<>();
        heap.insert(42, "FortyTwo");
        assert heap.size() == 1 : "Size should be 1 after insert";
        assert !heap.isEmpty() : "Heap should not be empty";
        
        Entry<Integer, String> peek = heap.peekMin();
        assert peek != null : "peekMin should not return null";
        assert peek.getKey() == 42 : "peekMin key should be 42";
        assert heap.size() == 1 : "Size should still be 1 after peek";
        
        Entry<Integer, String> removed = heap.removeMin();
        assert removed != null : "removeMin should not return null";
        assert removed.getKey() == 42 : "Removed key should be 42";
        assert heap.size() == 0 : "Size should be 0 after removing only element";
        assert heap.isEmpty() : "Heap should be empty after removing only element";
        System.out.println("  PASSED\n");
    }
    
    static void test3_MultipleElements() {
        System.out.println("Test 3: Multiple Elements");
        Heap<Integer, String> heap = new Heap<>();
        
        heap.insert(5, "Five");
        heap.insert(3, "Three");
        heap.insert(7, "Seven");
        heap.insert(1, "One");
        heap.insert(9, "Nine");
        
        assert heap.size() == 5 : "Size should be 5";
        
        Entry<Integer, String> min = heap.peekMin();
        assert min.getKey() == 1 : "Min should be 1";
        
        System.out.println("  PASSED\n");
    }
    
    static void test4_PriorityOrder() {
        System.out.println("Test 4: Priority Order");
        Heap<Integer, String> heap = new Heap<>();
        
        heap.insert(5, "Five");
        heap.insert(3, "Three");
        heap.insert(7, "Seven");
        heap.insert(1, "One");
        heap.insert(9, "Nine");
        
        int[] expected = {1, 3, 5, 7, 9};
        int index = 0;
        
        while (!heap.isEmpty()) {
            Entry<Integer, String> e = heap.removeMin();
            assert e.getKey() == expected[index] : 
                "Expected " + expected[index] + " but got " + e.getKey();
            index++;
        }
        
        assert heap.isEmpty() : "Heap should be empty after removing all";
        assert heap.size() == 0 : "Size should be 0 after removing all";
        System.out.println("  PASSED\n");
    }
}

