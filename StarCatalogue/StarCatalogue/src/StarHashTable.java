
public class StarHashTable {

    // Node class for Linked List Chaining
    private static class Node {
        Star star;
        Node next;

        Node(Star star) {
            this.star = star;
            this.next = null;
        }
    }

    private Node[] table;
    private int size;

    // Constructor
    public StarHashTable(int capacity) {
        if (capacity <= 0) {
            throw new IllegalArgumentException(
                "Capacity must be greater than zero"
            );
        }

        table = new Node[capacity];
        size = 0;
    }

    // Hash function (Case Insensitive)
    private int hash(String name) {
        return Math.floorMod(
            name.toLowerCase(java.util.Locale.ROOT).hashCode(),
            table.length
        );
    }

    // Insert Star
    public void insert(Star star) {

        if (star == null || star.getName() == null) {
            throw new IllegalArgumentException(
                "Star cannot be null"
            );
        }

        int index = hash(star.getName());

        Node current = table[index];

        // Check duplicate name
        while (current != null) {

            if (current.star.getName().equalsIgnoreCase(
                    star.getName())) {

                current.star = star;
                return;
            }

            current = current.next;
        }

        // Create new node
        Node newNode = new Node(star);

        // Linked List Chaining
        newNode.next = table[index];
        table[index] = newNode;

        size++;
    }

    // Search Star (Case Insensitive)
    public Star search(String name) {

        if (name == null) {
            return null;
        }

        int index = hash(name);

        Node current = table[index];

        while (current != null) {

            if (current.star.getName().equalsIgnoreCase(name)) {
                return current.star;
            }

            current = current.next;
        }

        return null;
    }

    // Delete Star
    public boolean delete(String name) {

        if (name == null) {
            return false;
        }

        int index = hash(name);

        Node current = table[index];
        Node previous = null;

        while (current != null) {

            if (current.star.getName().equalsIgnoreCase(name)) {

                if (previous == null) {
                    table[index] = current.next;
                } else {
                    previous.next = current.next;
                }

                size--;

                return true;
            }

            previous = current;
            current = current.next;
        }

        return false;
    }

    // Display Hash Table
    public void display() {

        for (int i = 0; i < table.length; i++) {

            System.out.print("Bucket " + i + ": ");

            Node current = table[i];

            while (current != null) {

                System.out.print(
                    current.star.getName() + " -> "
                );

                current = current.next;
            }

            System.out.println("NULL");
        }
    }

    // Get Size
    public int getSize() {
        return size;
    }

    // Check Empty
    public boolean isEmpty() {
        return size == 0;
    }
}
