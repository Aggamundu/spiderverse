package spiderman;

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
 * 
 * Step 2:
 * ClusterOutputFile name is passed in through the command line as args[1]
 * Output to ClusterOutputFile with the format:
 * 1. n lines, listing all of the dimension numbers connected to 
 *    that dimension in order (space separated)
 *    n is the size of the cluster table.
 * 
 * @author Seth Kelley
 */

public class Clusters {
    public static void main(String[] args) {

        if ( args.length < 2 ) {
            StdOut.println(
                "Execute: java -cp bin spiderman.Clusters <dimension INput file> <collider OUTput file>");
                return;
        }
       StdIn.setFile(args[0]);
        int a  = StdIn.readInt();//number of dimensions
        int TableSize = StdIn.readInt();//initial size
        double c = StdIn.readDouble();//table threshold
        DimensionNode[] clusters = new DimensionNode[TableSize]; //create hashtable
        double dimCount = 0; //number of added dimensions
        for (int i = 0; i <a; i++){ //for loop to go thru every dimension
            Data newData = new Data(StdIn.readInt(), StdIn.readInt(), StdIn.readInt()); //create data object
            index(clusters, newData, clusters.length);//call index method to place a DimensionNode in the hashtable
            dimCount++;
            double calc = dimCount/clusters.length;
            if(calc>=c){
                DimensionNode[] newClusters = resize(clusters);
                for(DimensionNode ptr: clusters){
                    while(ptr!=null){
                        index(newClusters,ptr.getData(),newClusters.length);
                        ptr = ptr.getNextDimensionNode();
                    }
                }
                for(int j = 0; j<clusters.length; j++){
                    DimensionNode ptr = clusters[j];
                    while(ptr!=null){
                        index(newClusters,ptr.getData(),newClusters.length);
                        ptr = ptr.getNextDimensionNode();
                    }
                }
                clusters = newClusters;
            }
        }
        connectClusters(clusters);


        for(int i = 0;i<clusters.length;i++){
            if(i==0){
                DimensionNode ptr = clusters[i];
                while(ptr.getNextDimensionNode()!=null){
                    ptr = ptr.getNextDimensionNode();
                }
                ptr.setNextDimensionNode(clusters[clusters.length-1]);
                ptr = ptr.getNextDimensionNode();
                ptr.setNextDimensionNode(clusters[clusters.length-2]);
            }
            else if(i==1){
                DimensionNode ptr = clusters[i];
                while(ptr.getNextDimensionNode()!=null){
                    ptr = ptr.getNextDimensionNode();
                }
                ptr.setNextDimensionNode(clusters[i].getPrevDimensionNode());
                ptr = ptr.getNextDimensionNode();
                ptr.setNextDimensionNode(clusters[clusters.length-1]);
            }
            else {
                DimensionNode one = clusters[i].getPrevDimensionNode();
                DimensionNode two = clusters[i].getPrevDimensionNode().getPrevDimensionNode();
                DimensionNode ptr = clusters[i];
                while(ptr.getNextDimensionNode()!=null){
                    ptr = ptr.getNextDimensionNode();
                }
                ptr.setNextDimensionNode(one);
                ptr = ptr.getNextDimensionNode();
                ptr.setNextDimensionNode(two);
            }
            
        }
        StdOut.setFile(args[1]);
        for(int z = 0; z<clusters.length;z++){
            DimensionNode ptr = clusters[z];
            while(ptr!=null){
                StdOut.print((ptr.getData().getNumber())+" ");
                ptr = ptr.getNextDimensionNode();
                
            }
            System.out.println();
        }
        
        
    }

    public static void connectClusters(DimensionNode[] arr){
        for(int i = 1;i<arr.length;i++){
            arr[i].setPrevDimensionNode(arr[i-1]);
        }
    }
    public static double countClusters(DimensionNode[] arr){
        int count = 0;
        for(int i = 0;i<arr.length;i++){
            if(arr[i]!=null){
                count++;
            }
        }
        return count;
            
    }
    public static DimensionNode[] resize(DimensionNode[] arr){
        int a = (arr.length * 2);
        return new DimensionNode[a];
    }

    public static void index(DimensionNode[] cluster, Data newData, int TableSize){
        int index = newData.getNumber()%TableSize;//hash function
        DimensionNode insert = new DimensionNode(newData,null,null);
            if (cluster[index] == null){
                    cluster[index] = insert;
            }
            else {
            insert.setNextDimensionNode(insert);
            cluster[index] = insert;
            }
    }
}
