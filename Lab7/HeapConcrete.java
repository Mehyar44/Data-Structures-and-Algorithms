public class HeapConcrete<T extends Comparable<T>> implements HeapAbstract<T> {
    private T[] heap;      // Array to store heap elements
    private int size;      // Current number of elements in heap
    private int capacity;  // Maximum capacity before expanding

    // Constructor with custom capacity
    @SuppressWarnings("unchecked")
    public HeapConcrete(int capacity) {
        this.capacity = capacity;
        this.heap = (T[]) new Comparable[capacity]; // Create array
        this.size = 0;
    }

    // Default constructor with capacity 10
    public HeapConcrete() {
        this(10);
    }

    // Constructor to build heap from existing array (heapify)
    @SuppressWarnings("unchecked")
    public HeapConcrete(T[] items) {
        this.capacity = items.length;
        this.size = items.length;
        this.heap = (T[]) new Comparable[capacity];
        System.arraycopy(items, 0, heap, 0, size); // Copy items into heap
        heapify(); // Rearrange to satisfy min-heap property
    }

    // Return current size of heap
    public int size() {
        return size;
    }

    // Check if heap is empty
    public boolean isEmpty() {
        return size == 0;
    }

    // Insert a new element into the heap
    public void insert(T item) {
        if (size == capacity) expand(); // Expand array if full
        heap[size] = item;             // Add item at the end
        bubbleUp(size);                // Restore heap property
        size++;
    }

    // Remove and return the smallest element
    public T deleteMin() {
        if (isEmpty()) throw new IllegalStateException("Heap is empty"); // Error handling
        T min = heap[0];             // Smallest element is at the root
        heap[0] = heap[--size];      // Replace root with last element
        heap[size] = null;           // Avoid memory leak
        bubbleDown(0);               // Restore heap property
        return min;
    }

    // Print the heap contents
    public void printHeap() {
        for (int i = 0; i < size; i++) System.out.print(heap[i] + " ");
        System.out.println();
    }

    // Expand the array when full
    @SuppressWarnings("unchecked")
    private void expand() {
        capacity *= 2;
        T[] newHeap = (T[]) new Comparable[capacity];
        System.arraycopy(heap, 0, newHeap, 0, size);
        heap = newHeap;
    }

    // Restore heap property after insertion
    private void bubbleUp(int i) {
        int parent = (i - 1) / 2;
        while (i > 0 && heap[i].compareTo(heap[parent]) < 0) {
            swap(i, parent);
            i = parent;
            parent = (i - 1) / 2;
        }
    }

    // Restore heap property after deletion
    private void bubbleDown(int i) {
        int left, right, smallest;
        while (true) {
            left = 2 * i + 1;
            right = 2 * i + 2;
            smallest = i;

            if (left < size && heap[left].compareTo(heap[smallest]) < 0) smallest = left;
            if (right < size && heap[right].compareTo(heap[smallest]) < 0) smallest = right;

            if (smallest == i) break; // Heap property satisfied
            swap(i, smallest);
            i = smallest;
        }
    }

    // Convert an array into a heap
    private void heapify() {
        for (int i = size / 2 - 1; i >= 0; i--) bubbleDown(i);
    }

    // Swap two elements in the heap
    private void swap(int a, int b) {
        T temp = heap[a];
        heap[a] = heap[b];
        heap[b] = temp;
    }
}