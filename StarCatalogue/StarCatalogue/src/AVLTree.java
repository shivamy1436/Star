
public class AVLTree {

    private static class Node {
        Star star;
        Node left, right;
        int height;

        Node(Star star) {
            this.star = star;
            height = 1;
        }
    }

    private Node root;

    private int height(Node node) {
        return node == null ? 0 : node.height;
    }

    private int balance(Node node) {
        return node == null ? 0 :
            height(node.left) - height(node.right);
    }

    private void updateHeight(Node node) {
        node.height = 1 + Math.max(
            height(node.left),
            height(node.right)
        );
    }

    // Right rotation: LL case
    private Node rightRotate(Node y) {
        Node x = y.left;
        Node t = x.right;

        x.right = y;
        y.left = t;

        updateHeight(y);
        updateHeight(x);

        return x;
    }

    // Left rotation: RR case
    private Node leftRotate(Node x) {
        Node y = x.right;
        Node t = y.left;

        y.left = x;
        x.right = t;

        updateHeight(x);
        updateHeight(y);

        return y;
    }

    private Node insert(Node node, Star star) {
        if (node == null) {
            return new Node(star);
        }

        int compare = star.getName().compareToIgnoreCase(
            node.star.getName()
        );

        if (compare < 0) {
            node.left = insert(node.left, star);
        } else if (compare > 0) {
            node.right = insert(node.right, star);
        } else {
            node.star = star;
            return node;
        }

        updateHeight(node);

        int b = balance(node);

        // LL Rotation
        if (b > 1 &&
            star.getName().compareToIgnoreCase(
                node.left.star.getName()) < 0) {
            return rightRotate(node);
        }

        // RR Rotation
        if (b < -1 &&
            star.getName().compareToIgnoreCase(
                node.right.star.getName()) > 0) {
            return leftRotate(node);
        }

        // LR Rotation
        if (b > 1 &&
            star.getName().compareToIgnoreCase(
                node.left.star.getName()) > 0) {
            node.left = leftRotate(node.left);
            return rightRotate(node);
        }

        // RL Rotation
        if (b < -1 &&
            star.getName().compareToIgnoreCase(
                node.right.star.getName()) < 0) {
            node.right = rightRotate(node.right);
            return leftRotate(node);
        }

        return node;
    }

    public void insert(Star star) {
        root = insert(root, star);
    }

    public Star search(String name) {
        Node current = root;

        while (current != null) {
            int compare = name.compareToIgnoreCase(
                current.star.getName()
            );

            if (compare == 0) {
                return current.star;
            }

            current = compare < 0 ?
                current.left : current.right;
        }

        return null;
    }

    public void displayInOrder() {
        displayInOrder(root);
    }

    private void displayInOrder(Node node) {
        if (node != null) {
            displayInOrder(node.left);
            System.out.println(node.star);
            displayInOrder(node.right);
        }
    }
}
