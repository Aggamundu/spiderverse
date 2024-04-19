package spiderman;

public class DimensionNode {
private Data data;
private DimensionNode next;

public DimensionNode(Data data, DimensionNode next){
this.data = data;
this.next = next;
}
public Data getData() {
return data;
}
public void setData(Data data){
this.data = data;
}
public DimensionNode getNextDimensionNode() {
return next;
}
public void setDimensionNode (DimensionNode next){
this.next = next;
}

}
