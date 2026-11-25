
import java.util.*;

public class StarGraph {

    private Map<String, List<String>> adjacencyList;

    public StarGraph() {
        adjacencyList = new LinkedHashMap<>();
    }

    public void addStar(String name) {
        adjacencyList.putIfAbsent(name, new ArrayList<>());
    }

    public void addEdge(String star1, String star2) {
        addStar(star1);
        addStar(star2);

        if (!adjacencyList.get(star1).contains(star2)) {
            adjacencyList.get(star1).add(star2);
        }

        if (!adjacencyList.get(star2).contains(star1)) {
            adjacencyList.get(star2).add(star1);
        }
    }

    public void displayGraph() {
        for (String star : adjacencyList.keySet()) {
            System.out.println(
                star + " -> " + adjacencyList.get(star)
            );
        }
    }

    public List<String> dfs(String start) {
        List<String> result = new ArrayList<>();

        if (!adjacencyList.containsKey(start)) {
            return result;
        }

        Set<String> visited = new HashSet<>();
        Stack<String> stack = new Stack<>();

        stack.push(start);

        while (!stack.isEmpty()) {
            String current = stack.pop();

            if (!visited.contains(current)) {
                visited.add(current);
                result.add(current);

                List<String> neighbours =
                    adjacencyList.get(current);

                for (int i = neighbours.size() - 1; i >= 0; i--) {
                    String neighbour = neighbours.get(i);

                    if (!visited.contains(neighbour)) {
                        stack.push(neighbour);
                    }
                }
            }
        }

        return result;
    }
}
