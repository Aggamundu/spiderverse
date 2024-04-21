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
 * ColliderOutputFile name is passed in through the command line as args[2]
 * Output to ColliderOutputFile with the format:
 * 1. e lines, each with a different dimension number, then listing
 *       all of the dimension numbers connected to that dimension (space separated)
 * 
 * @author Seth Kelley
 */

public class Collider {

    public static void main(String[] args) {

        if ( args.length < 3 ) {
            StdOut.println(
                "Execute: java -cp bin spiderman.Collider <dimension INput file> <spiderverse INput file> <collider OUTput file>");
                return;
        }
        LinkedList<Integer>[] adjList = createList(args[0]);
        printList(args[2],adjList);
        

        
/*
        ArrayList<LinkedList<Person>> arr = insertPerson(args[1]);
        StdOut.setFile("test.out");
        for(int i = 0;i<arr.size();i++){
            for (Person love:arr.get(i)){
                StdOut.println(love.getName());
            }
            
        } */
        
    }
    public static void printList(String fileThree, LinkedList<Integer>[] adjList){
                StdOut.setFile(fileThree);
        for(int f = 0; f<adjList.length;f++){
            for (int num : adjList[f]){
                StdOut.print(num + " ");
           }
            StdOut.println(); 
        } 
    }

    public static LinkedList<Integer>[] createList(String fileOne){
        Clusters clustersObj = new Clusters();
        DimensionNode[] clusters = clustersObj.createTable(fileOne);
        StdIn.setFile(fileOne);
        LinkedList<Integer>[] adjList = new LinkedList[StdIn.readInt()];
        StdIn.readInt();
        StdIn.readInt();
        //create adjacency list
        for(int i = 0; i<adjList.length;i++){
                adjList[i] = new LinkedList<>();
            } 
        
        for (int i = 0; i<adjList.length;i++){//set vertexes of adjList
            adjList[i].add(StdIn.readInt());
            StdIn.readInt();
            StdIn.readInt();
        }
        for(int z = 0; z<clusters.length;z++){
            DimensionNode ptr = clusters[z];
            while(ptr.getNextDimensionNode()!=null){
                for(int k = 0;k<adjList.length;k++){//forward
                    if(adjList[k].getFirst()==clusters[z].getData().getNumber()){
                        adjList[k].add(ptr.getNextDimensionNode().getData().getNumber());
                        break;
                    }
                }
                for(int j = 0;j<adjList.length;j++){ //reverse
                    if(adjList[j].getFirst()==ptr.getNextDimensionNode().getData().getNumber()){
                        adjList[j].add(clusters[z].getData().getNumber());
                        break;
                    }
                }

                ptr = ptr.getNextDimensionNode();
            }
        }
        return adjList;
    }

    public static ArrayList<LinkedList<Person>> insertPerson(String file){
        StdIn.setFile(file);
        ArrayList<LinkedList<Person>> arr = new ArrayList<>();
        LinkedList<Person> list1 = new LinkedList<>();
        int num = StdIn.readInt();
        arr.add(list1);
        int dimension0 = StdIn.readInt();
            StdIn.readChar();
            String name0 = StdIn.readString();
            int id0 = StdIn.readInt();
            Person person0 = new Person(dimension0,name0,id0);
        arr.get(0).add(person0);
        for(int i = 0; i<num-1;i++){
            boolean found = false;
            int dimension = StdIn.readInt();
            StdIn.readChar();
            String name = StdIn.readString();
            int id = StdIn.readInt();
            Person person = new Person(dimension,name,id);
            
            for(int j = 0; j<arr.size();j++){
                
                if(arr.get(j).getFirst().getDimension()==person.getDimension()){
                    arr.get(j).add(person);
                    found = true;
                }
            }
                if(!found){
                    LinkedList<Person> emptyList = new LinkedList<>();
                    emptyList.add(person);
                    arr.add(emptyList);
                }
        }
        return arr;
    }
}