import java.util.Arrays;

public class Main {
	public static void main(String[] args) {

		// Testing LinkedList
		LinkedList<Integer> linkedList = new LinkedList<>();

		// Adding elements to the list
		linkedList.add(1);   // Add at end
		linkedList.add(2);
		linkedList.add(3);
		linkedList.addFirst(0); // Add at beginning
		linkedList.addLast(4);  // Add at end
		System.out.println("List after adds: " + Arrays.toString(linkedList.toArray()));

		// Peek first and last elements without removing
		System.out.println("Peek First: " + linkedList.peekFirst()); // Should print 0
		System.out.println("Peek Last: " + linkedList.peekLast());   // Should print 4

		// Remove specific element by value
		linkedList.remove((Integer) 2);
		System.out.println("After removing 2: " + Arrays.toString(linkedList.toArray()));

		// Remove first and last elements
		linkedList.pollFirst();
		linkedList.pollLast();
		System.out.println("After polling first and last: " + Arrays.toString(linkedList.toArray()));

		// Checking contains, indexOf, and size
		System.out.println("Contains 3? " + linkedList.contains(3));
		System.out.println("Index of 3: " + linkedList.indexOf(3));
		System.out.println("Size: " + linkedList.size());

	  // Testing ArrayList (Extra Credit)
		ArrayList<String> arrayList = new ArrayList<>();

		// Adding elements
		arrayList.add("A");
		arrayList.add("B");
		arrayList.add("C");
		arrayList.add(1, "X"); // Add at index 1
		System.out.println("ArrayList after adds: " + Arrays.toString(arrayList.toArray()));

		// Remove by value
		arrayList.remove("B");
		System.out.println("After removing B: " + Arrays.toString(arrayList.toArray()));

		// Replace element at index
		arrayList.set(0, "Z");
		System.out.println("After set(0, Z): " + Arrays.toString(arrayList.toArray()));

		// Checking contains, indexOf, size, and capacity
		System.out.println("Contains C? " + arrayList.contains("C"));
		System.out.println("Index of C: " + arrayList.indexOf("C"));
		System.out.println("Size: " + arrayList.size());
		System.out.println("Capacity: " + arrayList.getCapacity());
	}
}
