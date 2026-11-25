
import java.util.*;

public class Main {

    private static Scanner sc = new Scanner(System.in);

    private static ArrayList<Star> stars = new ArrayList<>();

    private static StarHashTable hashTable =
        new StarHashTable(20);

    private static AVLTree avlTree = new AVLTree();

    private static StarGraph graph = new StarGraph();

    private static void addStar() {
        System.out.print("Enter star name: ");
        String name = sc.nextLine().trim();

        if (name.isEmpty()) {
            System.out.println("Name cannot be empty.");
            return;
        }

        System.out.print("Enter magnitude: ");
        double magnitude = Double.parseDouble(sc.nextLine());

        System.out.print("Enter X coordinate: ");
        double x = Double.parseDouble(sc.nextLine());

        System.out.print("Enter Y coordinate: ");
        double y = Double.parseDouble(sc.nextLine());

        Star star = new Star(name, magnitude, x, y);

        hashTable.insert(star);
        avlTree.insert(star);
        graph.addStar(name);

        boolean replaced = false;

        for (int i = 0; i < stars.size(); i++) {
            if (stars.get(i).getName().equalsIgnoreCase(name)) {
                stars.set(i, star);
                replaced = true;
                break;
            }
        }

        if (!replaced) {
            stars.add(star);
        }

        System.out.println("Star added successfully!");
    }

    private static void displayStars() {
        if (stars.isEmpty()) {
            System.out.println("No stars available.");
            return;
        }

        for (Star star : stars) {
            System.out.println(star);
        }
    }

    private static void searchStar() {
        System.out.print("Enter star name: ");
        String name = sc.nextLine();

        Star result = hashTable.search(name);

        if (result != null) {
            System.out.println("Star Found!");
            System.out.println(result);
        } else {
            System.out.println("Star not found.");
        }
    }

    private static void deleteStar() {
        System.out.print("Enter star name to delete: ");
        String name = sc.nextLine();

        if (hashTable.delete(name)) {
            stars.removeIf(
                star -> star.getName().equalsIgnoreCase(name)
            );

            rebuildAVL();

            System.out.println("Star deleted successfully.");
        } else {
            System.out.println("Star not found.");
        }
    }

    private static void rebuildAVL() {
        avlTree = new AVLTree();

        for (Star star : stars) {
            avlTree.insert(star);
        }
    }

    private static void displayHashTable() {
        hashTable.display();
    }

    private static void displayAVL() {
        avlTree.displayInOrder();
    }

    private static void searchAVL() {
        System.out.print("Enter star name: ");
        String name = sc.nextLine();

        Star result = avlTree.search(name);

        if (result != null) {
            System.out.println(result);
        } else {
            System.out.println("Star not found.");
        }
    }

    private static void connectStars() {
        System.out.print("Enter first star: ");
        String first = sc.nextLine();

        System.out.print("Enter second star: ");
        String second = sc.nextLine();

        if (hashTable.search(first) == null ||
            hashTable.search(second) == null) {
            System.out.println("Both stars must exist first.");
            return;
        }

        graph.addEdge(first, second);
        System.out.println("Stars connected successfully!");
    }

    private static void displayGraph() {
        graph.displayGraph();
    }

    private static void performDFS() {
        System.out.print("Enter starting star: ");
        String start = sc.nextLine();

        List<String> result = graph.dfs(start);

        if (result.isEmpty()) {
            System.out.println("Star not found in graph.");
        } else {
            System.out.println("DFS Traversal: " + result);
        }
    }

    private static void detectDip() {
        if (stars.isEmpty()) {
            System.out.println("Add stars first.");
            return;
        }

        System.out.print("Enter window size: ");
        int windowSize = Integer.parseInt(sc.nextLine());

        double[] readings = new double[stars.size()];

        for (int i = 0; i < stars.size(); i++) {
            readings[i] = stars.get(i).getMagnitude();
        }

        try {
            SlidingWindowDipDetector.Result result =
                SlidingWindowDipDetector.findLowestAverageWindow(
                    readings, windowSize
                );

            System.out.println("Lowest Average Window:");
            System.out.println(result);

            System.out.println("Stars in this window:");

            for (int i = result.startIndex;
                 i <= result.endIndex; i++) {
                System.out.println(stars.get(i));
            }

        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void main(String[] args) {

        while (true) {

            System.out.println("\n================================");
            System.out.println("       STAR CATALOGUE SYSTEM");
            System.out.println("================================");

            System.out.println("1. Add Star");
            System.out.println("2. Display All Stars");
            System.out.println("3. Search Star using Hash Table");
            System.out.println("4. Delete Star");
            System.out.println("5. Display Hash Table");
            System.out.println("6. Display AVL Tree");
            System.out.println("7. Search Star using AVL Tree");
            System.out.println("8. Connect Two Stars");
            System.out.println("9. Display Star Graph");
            System.out.println("10. DFS Traversal");
            System.out.println("11. Sliding Window Dip Detection");
            System.out.println("12. Exit");

            System.out.print("\nEnter your choice: ");

            try {
                int choice = Integer.parseInt(sc.nextLine());

                switch (choice) {
                    case 1:
                        addStar();
                        break;

                    case 2:
                        displayStars();
                        break;

                    case 3:
                        searchStar();
                        break;

                    case 4:
                        deleteStar();
                        break;

                    case 5:
                        displayHashTable();
                        break;

                    case 6:
                        displayAVL();
                        break;

                    case 7:
                        searchAVL();
                        break;

                    case 8:
                        connectStars();
                        break;

                    case 9:
                        displayGraph();
                        break;

                    case 10:
                        performDFS();
                        break;

                    case 11:
                        detectDip();
                        break;

                    case 12:
                        System.out.println("Thank you!");
                        return;

                    default:
                        System.out.println("Invalid choice!");
                }

            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number.");
            }
        }
    }
}
