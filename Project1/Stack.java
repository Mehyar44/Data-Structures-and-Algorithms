public class Stack<T> {
    private LinkedList<T> list;

    public Stack() {
        list = new LinkedList<>();
    }

    public void push(T data) {
        list.add(data);
    }

    public T pop() {
        if (isEmpty())
            throw new IllegalStateException("Stack is empty");
        return list.remove(list.size() - 1);
    }

    public T peek() {
        if (isEmpty())
            throw new IllegalStateException("Stack is empty");
        return list.get(list.size() - 1);
    }

    public boolean isEmpty() {
        return list.isEmpty();
    }

    public int size() {
        return list.size();
    }
}