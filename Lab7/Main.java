public class Main {
    public static void main(String[] args) {
        // Test default constructor
        UR_HeapImpl<Integer> heap = new UR_HeapImpl<>();
        System.out.println("Is heap empty? " + heap.isEmpty());

        // Insert elements
        heap.insert(5);
        heap.insert(3);
        heap.insert(8);
        heap.insert(1);
        heap.insert(6);

        System.out.print("Heap after insertions: ");
        heap.printHeap();

        // Delete min
        System.out.println("Deleted min: " + heap.deleteMin());
        System.out.print("Heap after deleteMin: ");
        heap.printHeap();

        // Test heapify with array
        Integer[] arr = {9, 2, 7, 4, 1, 5};
        UR_HeapImpl<Integer> heap2 = new UR_HeapImpl<>(arr);
        System.out.print("Heap created with heapify: ");
        heap2.printHeap();

        // Repeated deleteMin
        while (!heap2.isEmpty()) {
            System.out.print(heap2.deleteMin() + " ");
        }
        System.out.println();
    }
}