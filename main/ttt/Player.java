package ttt;

public class Player {
  
  private String name;
  private boolean isTurn;
  private int score;

  public Player (String name) {
    this.name = name;
    this.score = 0;
    this.isTurn = false;
  }
  
  public String getName () {
    return name;
  }
  
  public boolean isTurn () {
    return isTurn;
  }
  
  public void setTurn (boolean isTurn) {
    this.isTurn = isTurn;
  }
  
  public int getScore () {
    return score;
  }
  
  public void incScore () {
    score += 1;
  }
  
  public String toString () {
    String s = name + " has a score of " + score + ".";
    return s;
  }
  
  public static void main (String[] args) {
    Player test = new Player("Emily");
    System.out.println(test);
  }
}