abstract public class HashTableAbstract<Key, Value> {
    private static final int INIT_CAPACITY = 5;
    protected int n; // size of the data set
    protected int m; // size of the hash table
    protected Key[] keys;
    Value[] vals;
    int inserts, collisions;

    // Constructors (optional, can be implemented in concrete class)
    // public HashTableAbstract() {}
    // public HashTableAbstract(int cap) {}

    // Abstract methods to implement in concrete class
    abstract public void put(Key key, Value val);
    abstract public Value get(Key key);
    abstract public void delete(Key key);
    abstract public int size();
    abstract public boolean isEmpty();
    abstract public boolean contains(Key key);
    abstract public Iterable<Key> keys();

    // Useful helpers (to be implemented in concrete class)
    // private int hash(Key key);
    // private void resize(int capacity);
}