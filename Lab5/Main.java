public class Main {
    public static void main(String[] args) {
        // Create a hash table
        HashTableConcrete<String, Integer> table = new HashTableConcrete<>();

        // Test insertions
        table.put("Alice", 23);
        table.put("Bob", 31);
        table.put("Charlie", 19);
        table.put("Daisy", 27);
        table.put("Eve", 22); // triggers resize if needed

        // Display table size
        System.out.println("Size after inserts: " + table.size()); // 5

        // Test retrieval
        System.out.println("Get Alice: " + table.get("Alice")); // 23
        System.out.println("Get Bob: " + table.get("Bob")); // 31
        System.out.println("Get NonExistent: " + table.get("Zoe")); // null

        // Test deletion
        table.delete("Bob");
        table.delete("Zoe"); // deleting non-existent key, should be safe
        System.out.println("Contains Bob? " + table.contains("Bob")); // false
        System.out.println("Size after delete: " + table.size()); // 4

        // Test iteration over keys
        System.out.println("Keys in table:");
        for (String key : table.keys()) {
            System.out.println(key);
        }

        // Test updating a value
        table.put("Alice", 99);
        System.out.println("Updated Alice: " + table.get("Alice")); // 99

        // Display inserts and collisions
        System.out.println("Total inserts: " + table.inserts);
        System.out.println("Total collisions: " + table.collisions);

        // Test isEmpty
        System.out.println("Is table empty? " + table.isEmpty()); // false

        // Test deleting all keys
        table.delete("Alice");
        table.delete("Charlie");
        table.delete("Daisy");
        table.delete("Eve");
        System.out.println("Size after deleting all keys: " + table.size()); // 0
        System.out.println("Is table empty now? " + table.isEmpty()); // true
    }
}