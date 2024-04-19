package spiderman;

public class DimensionNode {
private Data data;
private DimensionNode next;
private DimensionNode prev;

public DimensionNode(Data data, DimensionNode next, DimensionNode prev){
this.data = data;
this.next = next;
this.prev = prev;
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
public void setNextDimensionNode (DimensionNode next){
this.next = next;
}
public DimensionNode getPrevDimensionNode(){
  return prev;
}
public void setPrevDimensionNode(DimensionNode prev){
this.prev = prev;
}

}
