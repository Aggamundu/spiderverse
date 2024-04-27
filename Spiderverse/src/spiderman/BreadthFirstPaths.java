package spiderman;

import java.util.*;

public class BreadthFirstPaths {
    private boolean[] marked;
    private int[] edgeTo;

    public BreadthFirstPaths() {
        // Constructor
    }

    public void bfs(LinkedList<Integer>[] adjList, int source, int destination, HashMap<Integer,Integer> Map) {
        marked = new boolean[adjList.length];
        edgeTo = new int[adjList.length];
        Queue<Integer> queue = new LinkedList<>();
        // Perform BFS
        queue.offer(source);
        marked[source] = true;

        while (!queue.isEmpty()) {
            int vertex = queue.remove();
            if (vertex == destination) {
                break; // Reached destination, stop BFS
            }
            for (int neighbor : adjList[vertex]) {
                int index = Map.get(neighbor);
                if (!marked[index]) {
                    marked[index] = true;
                    edgeTo[index] = vertex;
                    queue.add(index);
                }
            }
        }

        // Print the shortest path
        printShortestPath(source, destination, adjList);
    }

    private void printShortestPath(int source, int destination, LinkedList<Integer>[] adjList) {
        if (!hasPathTo(destination)) {
            System.out.println("No path exists from " + source + " to " + destination);
            return;
        }

        Stack<Integer> path = new Stack<>();
        for (int vertex = destination; vertex != source; vertex = edgeTo[vertex]) {
            path.push(vertex);
        }
        path.push(source);

        
        while (!path.isEmpty()) {
            StdOut.print(adjList[path.pop()].getFirst() + " ");
        }
        StdOut.println();
    }

    private boolean hasPathTo(int destination) {
        return marked[destination];
    }
}
