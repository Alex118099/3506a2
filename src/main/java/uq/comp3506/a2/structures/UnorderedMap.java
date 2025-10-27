// @edu:student-assignment

package uq.comp3506.a2.structures;

import java.util.ArrayList;
import java.util.LinkedList;

/**
 * Supplied by the COMP3506/7505 teaching team, Semester 2, 2025.
 * <p>
 * NOTE: You should go and carefully read the documentation provided in the
 * MapInterface.java file - this explains some of the required functionality.
 */
public class UnorderedMap<K, V> implements MapInterface<K, V> {

    /**
     * Inner class to store key-value pairs (entries)
     */
    private class MapEntry {
        K key;
        V value;
        
        MapEntry(K key, V value) {
            this.key = key;
            this.value = value;
        }
    }

    /**
     * Member variables to track data, size, capacity, etc.
     */
    private ArrayList<LinkedList<MapEntry>> buckets;
    private int size;
    private int capacity;
    private static final int DEFAULT_CAPACITY = 16;
    private static final double LOAD_FACTOR = 0.75;

    /**
     * Constructs an empty UnorderedMap
     */
    public UnorderedMap() {
        this.capacity = DEFAULT_CAPACITY;
        this.size = 0;
        this.buckets = new ArrayList<>(capacity);
        
        // Initialize all buckets with empty LinkedLists
        for (int i = 0; i < capacity; i++) {
            buckets.add(new LinkedList<>());
        }
    }

    /**
     * Hash function to convert key to bucket index
     * @param key the key to hash
     * @return the bucket index
     */
    private int hash(K key) {
        if (key == null) {
            return 0; // Handle null keys by placing them in bucket 0
        }
        // Use Math.abs to ensure non-negative index
        // Use Math.floorMod to handle negative hashCodes properly
        return Math.floorMod(key.hashCode(), capacity);
    }

    /**
     * Resizes the hash table when load factor is exceeded
     */
    private void resize() {
        // Save old buckets
        ArrayList<LinkedList<MapEntry>> oldBuckets = buckets;
        
        // Double the capacity
        capacity = capacity * 2;
        size = 0;
        
        // Create new buckets
        buckets = new ArrayList<>(capacity);
        for (int i = 0; i < capacity; i++) {
            buckets.add(new LinkedList<>());
        }
        
        // Rehash all entries from old buckets
        for (LinkedList<MapEntry> bucket : oldBuckets) {
            for (MapEntry entry : bucket) {
                put(entry.key, entry.value);
            }
        }
    }

    /**
     * returns the size of the structure in terms of pairs
     * @return the number of kv pairs stored
     */
    @Override
    public int size() {
        return size;
    }

    /**
     * helper to indicate if the structure is empty or not
     * @return true if the map contains no key-value pairs, false otherwise.
     */
    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * Clears all elements from the map. That means, after calling clear(),
     * the return of size() should be 0, and the data structure should appear
     * to be "empty".
     */
    @Override
    public void clear() {
        // Clear all buckets
        for (LinkedList<MapEntry> bucket : buckets) {
            bucket.clear();
        }
        size = 0;
    }

    /**
     * Associates the specified value with the specified key in this map.
     * If the map previously contained a mapping for the key, the old value
     * is replaced by the specified value.
     *
     * @param key   the key with which the specified value is to be associated
     * @param value the payload data value to be associated with the specified key
     * @return the previous value associated with key, or null if there was no such key
     */
    @Override
    public V put(K key, V value) {
        // Check if we need to resize
        if (size >= capacity * LOAD_FACTOR) {
            resize();
        }
        
        // Get the bucket for this key
        int index = hash(key);
        LinkedList<MapEntry> bucket = buckets.get(index);
        
        // Check if key already exists in the bucket
        for (MapEntry entry : bucket) {
            if (key == null ? entry.key == null : key.equals(entry.key)) {
                // Key exists, update value and return old value
                V oldValue = entry.value;
                entry.value = value;
                return oldValue;
            }
        }
        
        // Key doesn't exist, add new entry
        bucket.add(new MapEntry(key, value));
        size++;
        return null;
    }

    /**
     * Looks up the specified key in this map, returning its associated value
     * if such key exists.
     *
     * @param key the key with which the specified value is to be associated
     * @return the value associated with key, or null if there was no such key
     */
    @Override
    public V get(K key) {
        // Get the bucket for this key
        int index = hash(key);
        LinkedList<MapEntry> bucket = buckets.get(index);
        
        // Search for the key in the bucket
        for (MapEntry entry : bucket) {
            if (key == null ? entry.key == null : key.equals(entry.key)) {
                return entry.value;
            }
        }
        
        // Key not found
        return null;
    }

    /**
     * Looks up the specified key in this map, and removes the key-value pair
     * if the key exists.
     *
     * @param key the key with which the specified value is to be associated
     * @return the value associated with key, or null if there was no such key
     */
    @Override
    public V remove(K key) {
        // Get the bucket for this key
        int index = hash(key);
        LinkedList<MapEntry> bucket = buckets.get(index);
        
        // Search for the key in the bucket
        for (MapEntry entry : bucket) {
            if (key == null ? entry.key == null : key.equals(entry.key)) {
                // Found the key, remove it and return its value
                V value = entry.value;
                bucket.remove(entry);
                size--;
                return value;
            }
        }
        
        // Key not found
        return null;
    } 

}
