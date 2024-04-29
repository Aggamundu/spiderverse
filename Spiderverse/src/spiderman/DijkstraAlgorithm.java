package spiderman;
import java.util.*;

public class DijkstraAlgorithm {
    static class Edge {
        int destination;
        int weight;

        public Edge(int destination, int weight) {
            this.destination = destination;
            this.weight = weight;
        }
    }

    static class Graph {
        int vertices;
        List<List<Edge>> adjacencyList;

        public Graph(int vertices) {
            this.vertices = vertices;
            adjacencyList = new ArrayList<>(vertices);
            for (int i = 0; i < vertices; i++) {
                adjacencyList.add(new ArrayList<>());
            }
        }

        public void addEdge(int source, int destination, int weight) {
            adjacencyList.get(source).add(new Edge(destination, weight));
            adjacencyList.get(destination).add(new Edge(source, weight)); // For undirected graph
        }

        public Map<Integer, Integer> dijkstra(int source) {
            Map<Integer, Integer> distances = new HashMap<>();
            PriorityQueue<Node> fringe = new PriorityQueue<>(Comparator.comparingInt(node -> node.distance));
            boolean[] visited = new boolean[vertices];

            for (int i = 0; i < vertices; i++) {
                distances.put(i, Integer.MAX_VALUE);
            }
            distances.put(source, 0);
            fringe.offer(new Node(source, 0));

            while (!fringe.isEmpty()) {
                int currentVertex = fringe.poll().vertex;

                if (visited[currentVertex]) {
                    continue;
                }
                visited[currentVertex] = true;

                for (Edge neighbor : adjacencyList.get(currentVertex)) {
                    int distance = distances.get(currentVertex) + neighbor.weight;
                    if (distance < distances.get(neighbor.destination)) {
                        distances.put(neighbor.destination, distance);
                        fringe.offer(new Node(neighbor.destination, distance));
                    }
                }
            }

            return distances;
        }

        static class Node {
            int vertex;
            int distance;

            public Node(int vertex, int distance) {
                this.vertex = vertex;
                this.distance = distance;
            }
        }
    }

    public static void main(String[] args) {
        int vertices = 5;
        Graph graph = new Graph(vertices);
        graph.addEdge(0, 1, 4);
        graph.addEdge(0, 2, 2);
        graph.addEdge(1, 2, 5);
        graph.addEdge(1, 3, 10);
        graph.addEdge(2, 3, 3);
        graph.addEdge(2, 4, 2);
        graph.addEdge(3, 4, 4);

        int source = 0;
        Map<Integer, Integer> shortestDistances = graph.dijkstra(source);

        System.out.println("Shortest distances from source vertex " + source + ": " + shortestDistances);
    }
}

