public class Move {
  
  private int first, second;
  
  public Move (int a, int b) {
    this.first = a;
    this.second = b;
  }
  
  public int getFirst () {
    return first;
  }
  
  public String toString () {
    return first + "," + second;
  }
  
  public int getSecond () {
    return second;
  }
}