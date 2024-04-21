package spiderman;
public class Person {
  private int dimension;
  private String name;
  private int id;

  public Person(int dimension, String name, int id){
    this.name = name;
    this.dimension = dimension;
    this.id = id;
  }

  public int getDimension(){
    return dimension;
  }
}
