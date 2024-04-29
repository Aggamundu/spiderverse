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
      
      
        if (!hasPathTo(destination)) {
            System.out.println("No path exists from " + source + " to " + destination);
            return;
        }

        Stack<Integer> reversePath = new Stack<>(); 
        Queue<Integer> path = new LinkedList<>();
        for (int vertex = destination; vertex != source; vertex = edgeTo[vertex]) {
            path.add(vertex);
            reversePath.add(vertex);
        }
        path.add(source);
        reversePath.push(source);
        if(!spiderHere){
            while (!reversePath.isEmpty()) {//goes back to hub
                if(reversePath.peek()!=destination){
                    StdOut.print(adjList[reversePath.pop()].getFirst() + " ");
                } else{
                    reversePath.pop();
                }
            
            }
        }
            
        
        
        
        while (!path.isEmpty()) {//goes back to hub
            StdOut.print(adjList[path.remove()].getFirst() + " ");
        }
        
        
        StdOut.println();
    }

    private boolean hasPathTo(int destination) {
        return marked[destination];
    }
}