class Node<E> { // Doubly linked list node
private E e; // Value for this node
private Node<E> n; // Reference to next node in list
private Node<E> p; // Reference to previous node
// Constructors
Node(E it, Node<E> inp, Node<E> inn) { e = it; p = inp; n = inn; }
Node(Node<E> inp, Node<E> inn) { p = inp; n = inn; }
// Get and set methods for the data members
public E element() { return e; } // Return the value
public E setElement(E it) { return e = it; } // Set element value
public Node<E> next() { return n; } // Return next link
public Node<E> setNext(Node<E> nextval) { return n = nextval; } // Set next link
public Node<E> prev() { return p; } // Return prev link
public Node<E> setPrev(Node<E> prevval) { return p = prevval; } // Set prev link
}
