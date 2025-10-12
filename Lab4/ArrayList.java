import java.util.Collection;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class ArrayList<E> implements List<E> {

	private Object[] elements; // Underlying array for storage
	private int size;          // Number of elements in the list
	private static final int DEFAULT_CAPACITY = 10;

	// Constructor initializes array with default capacity
	public ArrayList() {
		elements = new Object[DEFAULT_CAPACITY];
		size = 0;
	}
  
	// Common Methods
	@Override
	public boolean add(E e) {
		enseCapacity(size + 1);
		elements[size++] = e;
		return true;
	}

	@Override
	public void add(int index, E element) {
		if (index < 0 || index > size) {
			throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
		}
		enseCapacity(size + 1);
		for (int i = size; i > index; i--) {
			elements[i] = elements[i - 1];
		}
		elements[index] = element;
		size++;
	}

	@Override
	public boolean addAll(Collection<? extends E> c) {
		if (c.isEmpty()) {
			return false;
		}
		enseCapacity(size + c.size());
		for (E e : c) {
			elements[size++] = e;
		}
		return true;
	}

	@Override
	public boolean addAll(int index, Collection<? extends E> c) {
		if (index < 0 || index > size) {
			throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
		}
		if (c.isEmpty()) {
			return false;
		}
		enseCapacity(size + c.size());
		// Shift elements to make room
		for (int i = size - 1; i >= index; i--) {
			elements[i + c.size()] = elements[i];
		}
		int i = index;
		for (E e : c) {
			elements[i++] = e;
		}
		size += c.size();
		return true;
	}

	@Override
	public void clear() {
		for (int i = 0; i < size; i++) {
			elements[i] = null; // Help GC
		}
		size = 0;
	}

	@Override
	public boolean contains(Object o) {
		return indexOf(o) != -1;
	}

	@Override
	public boolean containsAll(Collection<?> c) {
		for (Object o : c) {
			if (!contains(o)) {
				return false;
			}
		}
		return true;
	}

	@Override
	public boolean equals(Object o) {
		if (o == this) {
			return true;
		}
		if (!(o instanceof List)) {
			return false;
		}
		List<?> other = (List<?>) o;
		if (size != other.size()) {
			return false;
		}
		Iterator<E> it1 = iterator();
		Iterator<?> it2 = other.iterator();
		while (it1.hasNext()) {
			E e1 = it1.next();
			Object e2 = it2.next();
			if (e1 == null ? e2 != null : !e1.equals(e2)) {
				return false;
			}
		}
		return true;
	}

	@SuppressWarnings("unchecked")
	@Override
	public E get(int index) {
		if (index < 0 || index >= size) {
			throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
		}
		return (E) elements[index];
	}

	@Override
	public int indexOf(Object o) {
		for (int i = 0; i < size; i++) {
			if ((o == null && elements[i] == null) || (o != null && o.equals(elements[i]))) {
				return i;
			}
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
			private int crentIndex = 0;

			public boolean hasNext() {
				return crentIndex < size;
			}

			@SuppressWarnings("unchecked")
			public E next() {
				if (!hasNext()) {
					throw new NoSuchElementException();
				}
				return (E) elements[crentIndex++];
			}
		};
	}

	@SuppressWarnings("unchecked")
	@Override
	public E remove(int index) {
		if (index < 0 || index >= size) {
			throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
		}
		E removed = (E) elements[index];
		for (int i = index; i < size - 1; i++) {
			elements[i] = elements[i + 1];
		}
		elements[size - 1] = null;
		size--;
		return removed;
	}

	@Override
	public boolean remove(Object o) {
		int index = indexOf(o);
		if (index != -1) {
			remove(index);
			return true;
		}
		return false;
	}

	@Override
	public boolean removeAll(Collection<?> c) {
		boolean modified = false;
		for (Object o : c) {
			while (remove(o)) {
				modified = true;
			}
		}
		return modified;
	}

	@SuppressWarnings("unchecked")
	@Override
	public E set(int index, E element) {
		if (index < 0 || index >= size) {
			throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
		}
		E old = (E) elements[index];
		elements[index] = element;
		return old;
	}

	@Override
	public int size() {
		return size;
	}

	@Override
	public List<E> subList(int fromIndex, int toIndex) {
		if (fromIndex < 0 || toIndex > size || fromIndex > toIndex) {
			throw new IndexOutOfBoundsException();
		}
		ArrayList<E> sub = new ArrayList<>();
		for (int i = fromIndex; i < toIndex; i++) {
			sub.add(get(i));
		}
		return sub;
	}

	@Override
	public Object[] toArray() {
		Object[] arr = new Object[size];
		System.arraycopy(elements, 0, arr, 0, size);
		return arr;
	}

	// Specific Methods
	public void ensureCapacity(int minCapacity) {
		if (minCapacity > elements.length) {
			int newCapacity = Math.max(elements.length * 2, minCapacity);
			Object[] newArr = new Object[newCapacity];
			System.arraycopy(elements, 0, newArr, 0, size);
			elements = newArr;
		}
	}

  public int getCapacity() {
		return elements.length;
	}
}
