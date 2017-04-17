package ttt;

public class Move implements Comparable<Move> {
  
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
  
  public int compareTo (Move m) {
    if ((this.first == m.getFirst()) && (this.second == m.getSecond())) {
      return 0;
    } else {
      return 1;
    }
  }
}