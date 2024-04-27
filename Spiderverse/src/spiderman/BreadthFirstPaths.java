package spiderman;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

public class BreadthFirstPaths {
    private boolean[] marked;
    private int[] edgeTo;

    public BreadthFirstPaths() {
        // Constructor
    }

    public void bfs(LinkedList<Integer>[] adjList, int source, int destination, HashMap<Integer,Integer> Map, boolean spiderHere) {
        marked = new boolean[adjList.length];
        edgeTo = new int[adjList.length];
        Queue<Integer> queue = new LinkedList<>();
        // Perform BFS
        queue.add(source);
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
        printShortestPath(source,destination , adjList, spiderHere);
        
    }

    private void printShortestPath(int source, int destination, LinkedList<Integer>[] adjList,boolean spiderHere) {//swap queues and stacks
      Stack<Integer> backPath = new Stack<>();
        if (!hasPathTo(destination)) {
            System.out.println("No path exists from " + source + " to " + destination);
            return;
        }
        Queue<Integer> path = new LinkedList<>();
        for (int vertex = destination; vertex != source; vertex = edgeTo[vertex]) {
            path.add(vertex);
            backPath.push(vertex);
        }
        path.add(source);
        backPath.push(source);
        backPath.pop();
        while (!path.isEmpty()) {
            StdOut.print(adjList[path.remove()].getFirst() + " ");
        }
        if(!spiderHere){
          while(!backPath.isEmpty()){
            StdOut.print(adjList[backPath.pop()].getFirst() + " ");
        }
        }
        
        StdOut.println();
    }

    private boolean hasPathTo(int destination) {
        return marked[destination];
    }
}