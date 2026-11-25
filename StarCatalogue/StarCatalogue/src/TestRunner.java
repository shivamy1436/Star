
import java.util.*;

public class TestRunner {

    private static int passed = 0;
    private static int failed = 0;
    private static int testNumber = 0;

    private static void check(boolean condition, String message) {
        testNumber++;

        if (condition) {
            passed++;
            System.out.println("TEST " + testNumber +
                " PASSED: " + message);
        } else {
            failed++;
            System.out.println("TEST " + testNumber +
                " FAILED: " + message);
        }
    }

    public static void main(String[] args) {

        System.out.println("================================");
        System.out.println(" STAR CATALOGUE TEST RUNNER");
        System.out.println("================================");

        Star s1 = new Star("Sirius", -1.46, 10, 20);
        Star s2 = new Star("Vega", 0.03, 30, 40);
        Star s3 = new Star("Polaris", 2.0, 50, 60);

        // PART 1: STAR CLASS (Tests 1-4)

        check(s1.getName().equals("Sirius"), "Star name");
        check(s1.getMagnitude() == -1.46, "Star magnitude");
        check(s1.getX() == 10, "Star X coordinate");
        check(s1.getY() == 20, "Star Y coordinate");

        // PART 2: HASH TABLE (Tests 5-12)

        StarHashTable hash = new StarHashTable(5);

        hash.insert(s1);
        check(hash.search("Sirius") == s1, "Hash insert and search");

        hash.insert(s2);
        check(hash.search("Vega") == s2, "Second hash insertion");

        check(hash.search("Unknown") == null, "Missing star search");

        check(hash.getSize() == 2, "Hash table size");

        check(hash.search("sirius") == s1, "Case insensitive search");

        Star updated = new Star("Sirius", 1.0, 11, 21);
        hash.insert(updated);

        check(hash.search("Sirius") == updated, "Hash update");

        check(hash.getSize() == 2, "No duplicate on update");

        check(hash.delete("Vega"), "Hash delete");

        // PART 3: AVL TREE (Tests 13-19)

        AVLTree avl = new AVLTree();

        avl.insert(s1);
        check(avl.search("Sirius") == s1, "AVL insert and search");

        avl.insert(s2);
        check(avl.search("Vega") == s2, "AVL second insertion");

        avl.insert(s3);
        check(avl.search("Polaris") == s3, "AVL third insertion");

        check(avl.search("Unknown") == null, "AVL missing search");

        AVLTree ll = new AVLTree();
        ll.insert(new Star("C", 1, 0, 0));
        ll.insert(new Star("B", 1, 0, 0));
        ll.insert(new Star("A", 1, 0, 0));

        check(ll.search("A") != null, "LL rotation");

        AVLTree rr = new AVLTree();
        rr.insert(new Star("A", 1, 0, 0));
        rr.insert(new Star("B", 1, 0, 0));
        rr.insert(new Star("C", 1, 0, 0));

        check(rr.search("C") != null, "RR rotation");

        AVLTree lr = new AVLTree();
        lr.insert(new Star("C", 1, 0, 0));
        lr.insert(new Star("A", 1, 0, 0));
        lr.insert(new Star("B", 1, 0, 0));

        check(lr.search("B") != null, "LR rotation");

        // PART 4: GRAPH (Tests 20-24)

        StarGraph graph = new StarGraph();

        graph.addStar("Sirius");
        graph.addStar("Vega");
        graph.addStar("Polaris");

        graph.addEdge("Sirius", "Vega");
        graph.addEdge("Vega", "Polaris");

        check(graph.dfs("Sirius").size() == 3,
            "DFS visits connected stars");

        check(graph.dfs("Sirius").contains("Vega"),
            "DFS finds Vega");

        check(graph.dfs("Sirius").contains("Polaris"),
            "DFS finds Polaris");

        check(graph.dfs("Unknown").isEmpty(),
            "DFS with invalid starting star");

        graph.addEdge("Sirius", "Vega");

        check(graph.dfs("Sirius").size() == 3,
            "Duplicate edge does not duplicate traversal");

        // PART 5: SLIDING WINDOW (Tests 25-33)

        double[] readings = {10, 8, 3, 2, 9, 10};

        SlidingWindowDipDetector.Result result =
            SlidingWindowDipDetector.findLowestAverageWindow(
                readings, 2
            );

        check(result.startIndex == 2, "Lowest window start index");

        check(result.endIndex == 3, "Lowest window end index");

        check(result.average == 2.5, "Lowest window average");

        double[] values = {5, 5, 5, 5};

        SlidingWindowDipDetector.Result equalResult =
            SlidingWindowDipDetector.findLowestAverageWindow(
                values, 2
            );

        check(equalResult.average == 5.0,
            "Equal readings");

        check(equalResult.startIndex == 0,
            "First equal window selected");

        SlidingWindowDipDetector.Result singleResult =
            SlidingWindowDipDetector.findLowestAverageWindow(
                new double[]{7, 3, 9}, 1
            );

        check(singleResult.average == 3.0,
            "Window size one");

        SlidingWindowDipDetector.Result fullResult =
            SlidingWindowDipDetector.findLowestAverageWindow(
                new double[]{2, 4, 6}, 3
            );

        check(fullResult.average == 4.0,
            "Window size equal to array length");

        boolean invalidCaught = false;

        try {
            SlidingWindowDipDetector.findLowestAverageWindow(
                readings, 0
            );
        } catch (IllegalArgumentException e) {
            invalidCaught = true;
        }

        check(invalidCaught, "Invalid window size zero");

        boolean largeCaught = false;

        try {
            SlidingWindowDipDetector.findLowestAverageWindow(
                readings, 10
            );
        } catch (IllegalArgumentException e) {
            largeCaught = true;
        }

        check(largeCaught, "Window larger than array");

        boolean nullCaught = false;

        try {
            SlidingWindowDipDetector.findLowestAverageWindow(
                null, 2
            );
        } catch (IllegalArgumentException e) {
            nullCaught = true;
        }

        check(nullCaught, "Null array validation");

        // FINAL REPORT

        System.out.println("\n================================");
        System.out.println("       TEST SUMMARY");
        System.out.println("================================");

        System.out.println("Total Tests : " + testNumber);
        System.out.println("Passed      : " + passed);
        System.out.println("Failed      : " + failed);

        if (failed == 0 && testNumber == 33) {
            System.out.println("\nALL 33 TESTS PASSED!");
        } else {
            System.out.println("\nSOME TESTS FAILED!");
        }
    }
}
