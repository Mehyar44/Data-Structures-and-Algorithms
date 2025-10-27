import java.util.Iterator;
import java.util.NoSuchElementException;

public class HashTableConcrete<Key, Value> extends HashTableAbstract<Key, Value> implements Iterable<Key> {

    public HashTableConcrete() { this(INIT_CAPACITY); }

    @SuppressWarnings("unchecked")
    public HashTableConcrete(int capacity) {
        m = capacity;
        n = 0;
        keys = (Key[]) new Object[m];
        vals = (Value[]) new Object[m];
        inserts = 0;
        collisions = 0;
    }

    private int hash(Key key) {
        if (key == null) throw new IllegalArgumentException("Key cannot be null");
        return (key.hashCode() & 0x7fffffff) % m;
    }

    private void resize(int capacity) {
        HashTableConcrete<Key, Value> temp = new HashTableConcrete<>(capacity);
        for (int i = 0; i < m; i++) {
            if (keys[i] != null) temp.put(keys[i], vals[i]);
        }
        keys = temp.keys;
        vals = temp.vals;
        m = temp.m;
    }

    @Override
    public void put(Key key, Value val) {
        if (key == null) throw new IllegalArgumentException("Key cannot be null");
        if (val == null) { delete(key); return; }

        if (n >= m / 2) resize(2 * m);

        int i;
        for (i = hash(key); keys[i] != null; i = (i + 1) % m) {
            if (keys[i].equals(key)) { vals[i] = val; return; }
            collisions++;
        }
        keys[i] = key;
        vals[i] = val;
        n++;
        inserts++;
    }

    @Override
    public Value get(Key key) {
        if (key == null) throw new IllegalArgumentException("Key cannot be null");
        for (int i = hash(key); keys[i] != null; i = (i + 1) % m) {
            if (keys[i].equals(key)) return vals[i];
        }
        return null;
    }

    @Override
    public void delete(Key key) {
        if (key == null) throw new IllegalArgumentException("Key cannot be null");
        if (!contains(key)) return;

        int i = hash(key);
        while (!key.equals(keys[i])) i = (i + 1) % m;

        keys[i] = null;
        vals[i] = null;

        i = (i + 1) % m;
        while (keys[i] != null) {
            Key tempKey = keys[i];
            Value tempVal = vals[i];
            keys[i] = null;
            vals[i] = null;
            n--;
            put(tempKey, tempVal);
            i = (i + 1) % m;
        }

        n--;
        if (n > 0 && n <= m / 8) resize(m / 2);
    }

    @Override
    public int size() { return n; }

    @Override
    public boolean isEmpty() { return n == 0; }

    @Override
    public boolean contains(Key key) { return get(key) != null; }

    @Override
    public Iterable<Key> keys() { return this; }

    @Override
    public Iterator<Key> iterator() {
        return new Iterator<>() {
            int i = 0;
            @Override
            public boolean hasNext() {
                while (i < m && keys[i] == null) i++;
                return i < m;
            }
            @Override
            public Key next() {
                if (!hasNext()) throw new NoSuchElementException();
                return keys[i++];
            }
        };
    }
}