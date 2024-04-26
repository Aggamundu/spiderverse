package spiderman;
import java.util.*;
/**
 * Steps to implement this class main method:
 * 
 * Step 1:
 * DimensionInputFile name is passed through the command line as args[0]
 * Read from the DimensionsInputFile with the format:
 * 1. The first line with three numbers:
 *      i.    a (int): number of dimensions in the graph
 *      ii.   b (int): the initial size of the cluster table prior to rehashing
 *      iii.  c (double): the capacity(threshold) used to rehash the cluster table 
 * 2. a lines, each with:
 *      i.    The dimension number (int)
 *      ii.   The number of canon events for the dimension (int)
 *      iii.  The dimension weight (int)
 * 
 * Step 2:
 * SpiderverseInputFile name is passed through the command line as args[1]
 * Read from the SpiderverseInputFile with the format:
 * 1. d (int): number of people in the file
 * 2. d lines, each with:
 *      i.    The dimension they are currently at (int)
 *      ii.   The name of the person (String)
 *      iii.  The dimensional signature of the person (int)
 * 
 * Step 3:
 * SpotInputFile name is passed through the command line as args[2]
 * Read from the SpotInputFile with the format:
 * Two integers (line seperated)
 *      i.    Line one: The starting dimension of Spot (int)
 *      ii.   Line two: The dimension Spot wants to go to (int)
 * 
 * Step 4:
 * TrackSpotOutputFile name is passed in through the command line as args[3]
 * Output to TrackSpotOutputFile with the format:
 * 1. One line, listing the dimenstional number of each dimension Spot has visited (space separated)
 * 
 * @author Seth Kelley
 */

 //create hashmap for vertexes and indices
 //Then use DFS
public class TrackSpot {
    
    public static void main(String[] args) {

        if ( args.length < 4 ) {
            StdOut.println(
                "Execute: java -cp bin spiderman.TrackSpot <dimension INput file> <spiderverse INput file> <spot INput file> <trackspot OUTput file>");
                return;
        }

        //create adjList
        Collider collider = new Collider();
        LinkedList<Integer>[] adjList = collider.createList(args[0]);
        StdIn.setFile(args[2]);
        int start = StdIn.readInt();
        int targetValue = StdIn.readInt();
        HashMap<Integer, Integer> vertexIndices = new HashMap<>();
        int[] vertexValues = new int[adjList.length];
        for(int i = 0; i<vertexValues.length;i++){
            vertexValues[i] = adjList[i].getFirst();
        }
        for(int i = 0;i<vertexValues.length;i++){
            vertexIndices.put(vertexValues[i],i);
        }
        boolean[] visited = new boolean[adjList.length]; //iterate thru until you find the correct starting dim value
        int startIndex = vertexIndices.get(start);
        StdOut.setFile(args[3]);
        dfs(adjList, startIndex, visited, targetValue, vertexValues,vertexIndices);
            
        
    }
        public static boolean dfs(LinkedList<Integer>[] adjList, int vertex, boolean[] visited, int targetValue,
            int[] vertexValues, HashMap<Integer,Integer> map) {
            visited[vertex] = true;
            StdOut.print(vertexValues[vertex] + " ");
            if (vertexValues[vertex] == targetValue) {
                return true; // Return true if target value is found
            }
            for (int neighbor : adjList[vertex]) {
                int index = map.get(neighbor);
                if (!visited[index]) {
                    if (dfs(adjList, index, visited, targetValue, vertexValues, map)) {
                        return true; // Stop traversal if target value is found
                    }
                }
            }
            return false;
        }
        
}


