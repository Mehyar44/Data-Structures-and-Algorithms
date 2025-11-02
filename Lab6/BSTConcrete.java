import java.util.NoSuchElementException;
import java.util.*;

public class BSTConcrete<Key extends Comparable<Key>, Value> extends BSTAbstract<Key, Value> implements Iterable<Key> {

    //  Helper Access Methods
    private static class NodeRef<Key extends Comparable<Key>, Value> {
        BSTAbstract<Key,Value>.Node node;
        NodeRef(BSTAbstract<Key,Value>.Node n) { node = n; }
    }

    private Node getRoot() {
        try {
            var f = BSTAbstract.class.getDeclaredField("root");
            f.setAccessible(true);
            return (Node)f.get(this);
        } catch(Exception e) { throw new RuntimeException(e); }
    }

    private void setRoot(Node n) {
        try {
            var f = BSTAbstract.class.getDeclaredField("root");
            f.setAccessible(true);
            f.set(this,n);
        } catch(Exception e) { throw new RuntimeException(e); }
    }

    private Key key(Node n) { try {
        var f = n.getClass().getDeclaredField("key"); f.setAccessible(true); return (Key)f.get(n);
    } catch(Exception e){throw new RuntimeException(e);} }

    private Value val(Node n) { try {
        var f = n.getClass().getDeclaredField("val"); f.setAccessible(true); return (Value)f.get(n);
    } catch(Exception e){throw new RuntimeException(e);} }

    private void setVal(Node n, Value v) { try {
        var f = n.getClass().getDeclaredField("val"); f.setAccessible(true); f.set(n,v);
    } catch(Exception e){throw new RuntimeException(e);} }

    private Node left(Node n) { try {
        var f = n.getClass().getDeclaredField("left"); f.setAccessible(true); return (Node)f.get(n);
    } catch(Exception e){throw new RuntimeException(e);} }

    private Node right(Node n) { try {
        var f = n.getClass().getDeclaredField("right"); f.setAccessible(true); return (Node)f.get(n);
    } catch(Exception e){throw new RuntimeException(e);} }

    private void setLeft(Node n, Node v) { try {
        var f = n.getClass().getDeclaredField("left"); f.setAccessible(true); f.set(n,v);
    } catch(Exception e){throw new RuntimeException(e);} }

    private void setRight(Node n, Node v) { try {
        var f = n.getClass().getDeclaredField("right"); f.setAccessible(true); f.set(n,v);
    } catch(Exception e){throw new RuntimeException(e);} }

    private int sizeField(Node n) { try {
        var f = n.getClass().getDeclaredField("size"); f.setAccessible(true); return (int)f.get(n);
    } catch(Exception e){throw new RuntimeException(e);} }

    private void setSize(Node n, int v) { try {
        var f = n.getClass().getDeclaredField("size"); f.setAccessible(true); f.set(n,v);
    } catch(Exception e){throw new RuntimeException(e);} }

    private Node newNode(Key k, Value v) {
        try {
            var c = BSTAbstract.class.getDeclaredClasses()[0];
            var ctor = c.getDeclaredConstructor(BSTAbstract.class);
            ctor.setAccessible(true);
            Node n = (Node)ctor.newInstance(this);
            setVal(n,v);
            var fk = n.getClass().getDeclaredField("key"); fk.setAccessible(true); fk.set(n,k);
            var fs = n.getClass().getDeclaredField("size"); fs.setAccessible(true); fs.set(n,1);
            return n;
        } catch(Exception e){throw new RuntimeException(e);}
    }

    // BST Methods

    public boolean isEmpty() { return getRoot()==null; }

    public int size() { return size(getRoot()); }

    private int size(Node x) { return x==null?0:sizeField(x); }

    public boolean contains(Key key){
        if(key==null) throw new IllegalArgumentException("null");
        return get(key)!=null;
    }

    public Value get(Key key){
        if(key==null) throw new IllegalArgumentException("null");
        Node x = getRoot();
        while(x!=null){
            int cmp = key.compareTo(key(x));
            if(cmp<0) x = left(x);
            else if(cmp>0) x = right(x);
            else return val(x);
        }
        return null;
    }

    public void put(Key key, Value val){
        if(key==null) throw new IllegalArgumentException("null");
        if(val==null){ delete(key); return; }
        setRoot(put(getRoot(),key,val));
    }

    private Node put(Node x, Key k, Value v){
        if(x==null) return newNode(k,v);
        int cmp = k.compareTo(key(x));
        if(cmp<0) setLeft(x, put(left(x),k,v));
        else if(cmp>0) setRight(x, put(right(x),k,v));
        else setVal(x,v);
        setSize(x,1+size(left(x))+size(right(x)));
        return x;
    }

    public void deleteMin(){
        if(isEmpty()) throw new NoSuchElementException();
        setRoot(deleteMin(getRoot()));
    }

    private Node deleteMin(Node x){
        if(left(x)==null) return right(x);
        setLeft(x, deleteMin(left(x)));
        setSize(x,1+size(left(x))+size(right(x)));
        return x;
    }

    public void deleteMax(){
        if(isEmpty()) throw new NoSuchElementException();
        setRoot(deleteMax(getRoot()));
    }

    private Node deleteMax(Node x){
        if(right(x)==null) return left(x);
        setRight(x, deleteMax(right(x)));
        setSize(x,1+size(left(x))+size(right(x)));
        return x;
    }

    public void delete(Key k){
        if(k==null) throw new IllegalArgumentException("null");
        setRoot(delete(getRoot(),k));
    }

    private Node delete(Node x, Key k){
        if(x==null) return null;
        int cmp = k.compareTo(key(x));
        if(cmp<0) setLeft(x, delete(left(x),k));
        else if(cmp>0) setRight(x, delete(right(x),k));
        else {
            if(right(x)==null) return left(x);
            if(left(x)==null) return right(x);
            Node t = x;
            x = min(right(t));
            setRight(x, deleteMin(right(t)));
            setLeft(x, left(t));
        }
        setSize(x,1+size(left(x))+size(right(x)));
        return x;
    }

    private Node min(Node x){
        while(left(x)!=null) x = left(x);
        return x;
    }

    public int height(){ return height(getRoot()); }

    private int height(Node x){
        if(x==null) return -1;
        return 1+Math.max(height(left(x)),height(right(x)));
    }

    public Iterable<Key> keys(){
        List<Key> list = new ArrayList<>();
        inorder(getRoot(),list);
        return list;
    }

    private void inorder(Node x, List<Key> list){
        if(x==null) return;
        inorder(left(x),list);
        list.add(key(x));
        inorder(right(x),list);
    }

    public Iterable<Key> levelOrder(){
        List<Key> list = new ArrayList<>();
        if(isEmpty()) return list;
        Queue<Node> q = new LinkedList<>();
        q.add(getRoot());
        while(!q.isEmpty()){
            Node n = q.poll();
            list.add(key(n));
            if(left(n)!=null) q.add(left(n));
            if(right(n)!=null) q.add(right(n));
        }
        return list;
    }

    public Iterator<Key> iterator(){ return keys().iterator(); }
}