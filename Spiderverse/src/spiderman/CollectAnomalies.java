package spiderman;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
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
 * HubInputFile name is passed through the command line as args[2]
 * Read from the HubInputFile with the format:
 * One integer
 *      i.    The dimensional number of the starting hub (int)
 * 
 * Step 4:
 * CollectedOutputFile name is passed in through the command line as args[3]
 * Output to CollectedOutputFile with the format:
 * 1. e Lines, listing the Name of the anomaly collected with the Spider who
 *    is at the same Dimension (if one exists, space separated) followed by 
 *    the Dimension number for each Dimension in the route (space separated)
 * 
 * @author Seth Kelley
 */

public class CollectAnomalies {
    
    public static void main(String[] args) {

        if ( args.length < 4 ) {
            StdOut.println(
                "Execute: java -cp bin spiderman.CollectAnomalies <dimension INput file> <spiderverse INput file> <hub INput file> <collected OUTput file>");
                return;
        }
        StdIn.setFile(args[2]);
        int hub = StdIn.readInt();
        Collider collider = new Collider();
        ArrayList<LinkedList<Person>> peopleArr = collider.insertPerson(args[1]);
        LinkedList<Integer> [] adjList = collider.createList(args[0]);
        HashMap<Integer, Integer> vertexIndices = new HashMap<>();//value is vertex indices(0-65)
        HashMap<Integer, Integer> dimensionNumbers = new HashMap<>();//value is dim number
        int[] vertexValues = new int[adjList.length];
        BreadthFirstPaths BFP = new BreadthFirstPaths();
        StdOut.setFile(args[3]);
        for(int i = 0; i<vertexValues.length;i++){ //populate vertexIndices hashmap
            vertexValues[i] = adjList[i].getFirst();
        }
        for(int i = 0;i<vertexValues.length;i++){
            vertexIndices.put(vertexValues[i],i);
        }
        for(int i = 0; i<vertexValues.length; i++){  //populate dimensionNumbers hashmap
            dimensionNumbers.put(i, vertexValues[i]);
        }
      /*   for(LinkedList<Person> p :peopleArr){
            for(int i=0;i<p.size();i++){
                System.out.print(p.get(i).getDimension() + " ");
            }
            System.out.println();
        }*/
        //testing arrays

         for(LinkedList<Person> list: peopleArr){
            boolean foundSpider = false;
            Person spiderman = new Person(-1,"",-1);
            for(Person spider:list){
                if(spider.getDimension()==spider.getID()){
                    foundSpider = true;
                    spiderman = spider;
                }
            }
            for(Person person:list){
                if(person.getDimension()!=person.getID()&&person.getDimension()!=hub){
                    
                    if(foundSpider){
                        int startVertex = vertexIndices.get(person.getDimension());
                        int hubIndex = vertexIndices.get(hub);
                        StdOut.print(person.getName() + " " + spiderman.getName()+ " ");
                        BFP.bfs(adjList,hubIndex,startVertex,vertexIndices,foundSpider);

                    }
                    else {
                        int stopVertex = vertexIndices.get(person.getDimension());
                        int hubIndex = vertexIndices.get(hub);
                        StdOut.print(person.getName() + " ");
                        BFP.bfs(adjList,hubIndex,stopVertex,vertexIndices,foundSpider);
                    }
                }
        }
    }
}




    }
    