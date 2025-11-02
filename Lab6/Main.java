import java.util.NoSuchElementException;

public class Main {
    public static void main(String[] args) {
        UR_BSTImpl<Integer, String> bst = new UR_BSTImpl<>();

        // insert some keys
        bst.put(5, "five");
        bst.put(3, "three");
        bst.put(7, "seven");
        bst.put(2, "two");
        bst.put(4, "four");
        bst.put(6, "six");
        bst.put(8, "eight");

        System.out.println("Size: " + bst.size());
        System.out.println("Height (edges): " + bst.height());

        System.out.println("Inorder (keys):");
        for (Integer k : bst.keys()) System.out.print(k + " ");
        System.out.println();

        System.out.println("Level-order (by level):");
        for (Integer k : bst.levelOrder()) System.out.print(k + " ");
        System.out.println();

        System.out.println("Contains 4? " + bst.contains(4));
        System.out.println("Value for 6: " + bst.get(6));

        // deleteMin
        bst.deleteMin();
        System.out.println("After deleteMin(), level-order:");
        for (Integer k : bst.levelOrder()) System.out.print(k + " ");
        System.out.println();

        // deleteMax
        bst.deleteMax();
        System.out.println("After deleteMax(), level-order:");
        for (Integer k : bst.levelOrder()) System.out.print(k + " ");
        System.out.println();

        // delete a middle key
        bst.delete(5);
        System.out.println("After delete(5), inorder:");
        for (Integer k : bst.keys()) System.out.print(k + " ");
        System.out.println();

        // Exception handling demos
        try {
            bst.put(null, "null");
        } catch (IllegalArgumentException e) {
            System.out.println("Caught expected exception for put(null): " + e.getMessage());
        }

        try {
            UR_BSTImpl<Integer, String> empty = new UR_BSTImpl<>();
            empty.deleteMin();
        } catch (NoSuchElementException e) {
            System.out.println("Caught expected exception for deleteMin() on empty: " + e.getMessage());
        }
    }
}