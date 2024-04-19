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
        int a  = StdIn.readInt();
        int TableSize = StdIn.readInt();
        double c = StdIn.readDouble();
        DimensionNode[] clusters = new DimensionNode[TableSize];
        double dimCount = 0;
        for (int i = 0; i <a; i++){
            Data newData = new Data(StdIn.readInt(), StdIn.readInt(), StdIn.readInt());
            index(clusters, newData, clusters.length);
            dimCount++;
            if((dimCount/countClusters(clusters))>=c){
                DimensionNode[] newClusters = resize(clusters);
                for(int j = 0; j<clusters.length; j++){
                    DimensionNode ptr = clusters[j];
                    while(ptr!=null){
                        index(newClusters,ptr.getData(),newClusters.length);
                        ptr = ptr.getNextDimensionNode();
                    }
                }
            }
            
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
        int index = newData.getNumber()%TableSize;
            if (cluster[index] == null){
            cluster[index] = new DimensionNode(newData, null);
            }
            else {
            DimensionNode insert = new DimensionNode(newData, null);
            cluster[index] = insert;
            }
    }
}
