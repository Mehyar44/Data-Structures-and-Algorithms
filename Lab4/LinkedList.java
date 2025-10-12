import java.util.Collection;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class LinkedList<E> implements List<E> {

	private Node<E> head; // Reference to first node
	private Node<E> tail; // Reference to last node
	private int size;       // Number of elements in the list

	// Constructor initializes empty list
	public LinkedList() {
		head = null;
		tail = null;
		size = 0;
	}

	// List Common Methods
	@Override
	public boolean add(E e) {
		addLast(e); // Add element to the end of the list
		return true;
	}

	@Override
	public void add(int index, E element) {
		if (index < 0 || index > size)
			throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);

		if (index == 0)
			addFirst(element);
		
		else if (index == size) 
					addLast(element);
		else {
			Node<E> current = getNode(index);
			Node<E> newNode = new Node<>(element, current.prev(), current);
			current.prev().setNext(newNode);
			current.setPrev(newNode);
			size++;
		}
	}

	@Override
	public boolean addAll(Collection<? extends E> c) {
		for (E e : c) 
			addLast(e);
		return !c.isEmpty();
	}

	@Override
	public boolean addAll(int index, Collection<? extends E> c) {
		if (index < 0 || index > size)
			throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
		
		if (c.isEmpty()) return false;
			int i = index;
		
		for (E e : c) 
			add(i++, e);
		return true;
	}

	@Override
	public void clear() {
		head = tail = null; // Remove all references
		size = 0;
	}

	@Override
	public boolean contains(Object o) {
		return indexOf(o) != -1; // True if element found
	}

	@Override
	public boolean containsAll(Collection<?> c) {
		for (Object o : c)
			if (!contains(o)) 
				return false;
		return true;
	}

	@Override
	public boolean equals(Object o) {
		if (o == this)
			return true;

		if (!(o instanceof List)) 
			return false;

		List<?> other = (List<?>) o;

		if (size != other.size()) 
			return false;

		Iterator<E> it1 = iterator();
		Iterator<?> it2 = other.iterator();
		
		while (it1.hasNext()) {
			E e1 = it1.next();
			Object e2 = it2.next();
			if (e1 == null ? e2 != null : !e1.equals(e2)) 
				return false;
		}
		
		return true;
	}

	@Override
	public E get(int index) {
		return getNode(index).element();
	}

	@Override
	public int indexOf(Object o) {
		Node<E> current = head;
		for (int i = 0; i < size; i++) {
			if ((o == null && current.element() == null) || (o != null && o.equals(current.element()))) return i;
		current = current.next();
		}
		
		return -1;
	}

	@Override
	public boolean isEmpty() {
		return size == 0;
	}

	@Override
	public Iterator<E> iterator() {
		return new Iterator<E>() {
		private Node<E> current = head;
			public boolean hasNext() { return current != null; }
			public E next() {
				if (!hasNext()) throw new NoSuchElementException();
				E e = current.element();
				current = current.next();
				return e;
			}
		};
	}

	@Override
	public E remove(int index) {
		Node<E> current = getNode(index);
		E value = current.element();
		if (current.prev() != null) current.prev().setNext(current.next());
		else head = current.next();
		if (current.next() != null) current.next().setPrev(current.prev());
		else tail = current.prev();
		size--;
		return value;
	}

	@Override
	public boolean remove(Object o) {
		Node<E> current = head;
		while (current != null) {
			if ((o == null && current.element() == null) || (o != null && o.equals(current.element()))) {
				if (current.prev() != null) current.prev().setNext(current.next());
				else head = current.next();
				if (current.next() != null) current.next().setPrev(current.prev());
				else tail = current.prev();
				size--;
				return true;
			}
			current = current.next();
		}
		return false;
	}

	@Override
	public boolean removeAll(Collection<?> c) {
		boolean modified = false;
		for (Object o : c) {
			while (remove(o)) modified = true;
		}
		return modified;
	}

	@Override
	public E set(int index, E element) {
		Node<E> node = getNode(index);
		return node.setElement(element);
	}

	@Override
	public int size() {
		return size;
	}

	@Override
	public List<E> subList(int fromIndex, int toIndex) {
		if (fromIndex < 0 || toIndex > size || fromIndex > toIndex)
			throw new IndexOutOfBoundsException();
		LinkedList<E> sub = new LinkedList<>();
		Node<E> current = getNode(fromIndex);
		for (int i = fromIndex; i < toIndex; i++) {
			sub.addLast(current.element());
			current = current.next();
		}
		return sub;
	}

	@Override
	public Object[] toArray() {
		Object[] arr = new Object[size];
		Node<E> current = head;
		for (int i = 0; i < size; i++) {
			arr[i] = current.element();
			current = current.next();
		}
		return arr;
	}

	// LinkedList Specific Methods
	// Add element at beginning
	public void addFirst(E e) {
		Node<E> newNode = new Node<>(e, null, head);
		if (head != null) head.setPrev(newNode);
		head = newNode;
		if (tail == null) tail = head;
		size++;
	}

	// Add element at end
	public void addLast(E e) {
		Node<E> newNode = new Node<>(e, tail, null);
		if (tail != null) tail.setNext(newNode);
		tail = newNode;
		if (head == null) head = tail;
		size++;
	}

	// Peek first element
	public E peekFirst() {
		return head != null ? head.element() : null;
	}

	// Peek last element
	public E peekLast() {
		return tail != null ? tail.element() : null;
	}

	// Remove and return first element
	public E pollFirst() {
		if (head == null) return null;
		E val = head.element();
		head = head.next();
		if (head != null) head.setPrev(null);
		else tail = null;
		size--;
		return val;
	}

	// Remove and return last element
	public E pollLast() {
		if (tail == null) return null;
		E val = tail.element();
		tail = tail.prev();
		if (tail != null) tail.setNext(null);
		else head = null;
		size--;
		return val;
	}

	// Helper Method
	// Get node at specific index efficiently
	private Node<E> getNode(int index) {
		if (index < 0 || index >= size) throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
		Node<E> current;
		if (index < size / 2) {
			current = head;
			for (int i = 0; i < index; i++) current = current.next();
		} else {
			current = tail;
			for (int i = size - 1; i > index; i--) current = current.prev();
		}
		return current;
	}
}
