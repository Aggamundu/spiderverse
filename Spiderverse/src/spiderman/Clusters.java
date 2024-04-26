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
        String fileOne = args[0];
        String fileTwo = args[1];
        DimensionNode[] arr = createTable(fileOne);
        print(fileTwo,arr);
    }

    public static DimensionNode[] createTable(String fileOne){
        
        StdIn.setFile(fileOne);
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
                clusters = resize(clusters);
            }
        }
        for(int i = 0;i<clusters.length;i++){
            if (i==0){
                DimensionNode ptr = clusters[i];
                
                while(ptr.getNextDimensionNode()!=null){
                    
                ptr = ptr.getNextDimensionNode();
            }
                Data dataOne = new Data(clusters[clusters.length-1].getData().getNumber(),clusters[clusters.length-1].getData().getCanon(),clusters[clusters.length-1].getData().getWeight());
                DimensionNode one = new DimensionNode(dataOne, null);
                Data dataTwo = new Data(clusters[clusters.length-2].getData().getNumber(),clusters[clusters.length-2].getData().getCanon(),clusters[clusters.length-2].getData().getWeight());
                DimensionNode two = new DimensionNode(dataTwo, null);
                ptr.setNextDimensionNode(one);
                ptr = ptr.getNextDimensionNode();
                ptr.setNextDimensionNode(two);
            }
            else if(i==1){
                DimensionNode ptr = clusters[i];
                while(ptr.getNextDimensionNode()!=null){
                ptr = ptr.getNextDimensionNode();
            }
            Data dataOne = new Data(clusters[0].getData().getNumber(),clusters[0].getData().getCanon(),clusters[0].getData().getWeight());
            DimensionNode one = new DimensionNode(dataOne, null);
            Data dataTwo = new Data(clusters[clusters.length-1].getData().getNumber(),clusters[clusters.length-1].getData().getCanon(),clusters[clusters.length-1].getData().getWeight());
            DimensionNode two = new DimensionNode(dataTwo, null);
            ptr.setNextDimensionNode(one);
            ptr = ptr.getNextDimensionNode();
            ptr.setNextDimensionNode(two);
            }
            else {
                DimensionNode ptr = clusters[i];
                while(ptr.getNextDimensionNode()!=null){
                    ptr = ptr.getNextDimensionNode();
                }
                Data dataOne = new Data(clusters[i-1].getData().getNumber(),clusters[i-1].getData().getCanon(),clusters[i-1].getData().getWeight());
                DimensionNode one = new DimensionNode(dataOne, null);
                Data dataTwo = new Data(clusters[i-2].getData().getNumber(),clusters[i-2].getData().getCanon(),clusters[i-2].getData().getWeight());
                DimensionNode two = new DimensionNode(dataTwo, null);
                ptr.setNextDimensionNode(one);
                ptr = ptr.getNextDimensionNode();
                ptr.setNextDimensionNode(two);
            }
            
        }
         
        return clusters;
        
    }

    public static void print(String fileTwo, DimensionNode[] clusters){
        StdOut.setFile(fileTwo);
         for(int z = 0; z<clusters.length;z++){
            DimensionNode ptr = clusters[z];
            while(ptr!=null){
                StdOut.print((ptr.getData().getNumber())+ " ");
                ptr = ptr.getNextDimensionNode();
            }
            StdOut.println();
        }
    }
    
    public static DimensionNode[] resize(DimensionNode[] arr){
        int a = (arr.length * 2);
        DimensionNode[] bigArr = new DimensionNode[a];
        for(DimensionNode node:arr){
            DimensionNode ptr = node;
                while(ptr!=null){
                    index(bigArr,ptr.getData(),bigArr.length);
                    ptr = ptr.getNextDimensionNode();
                }
                
        }
        return bigArr;
        
    }

    public static void index(DimensionNode[] cluster, Data newData, int TableSize){
        int index = newData.getNumber()%TableSize; //hash function
        DimensionNode insert = new DimensionNode(newData,null);
            if (cluster[index] == null){
                cluster[index] = insert;
            }
            else {
            insert.setNextDimensionNode(cluster[index]);
            cluster[index] = insert;
            }
    }
}