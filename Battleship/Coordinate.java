public class Coordinate {
  
  private int x;
  private int y;
  private int probability;
  
  public Coordinate(int x, int y) {
    this.x = x;
    this.y = y;
    probability = 1;
  }
  
  public int getProb () {
    return probability;
  }
  
  public void incProb () {
    probability++;
  }
  
  public int getX () {
    return x;
  }
  
  public int getY () {
    return y;
  }
}