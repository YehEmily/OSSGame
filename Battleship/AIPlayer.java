public class AIPlayer extends Player {
  
  private String[] ships;
  
  public AIPlayer (String name) {
    super(name);
    ships = new String[] {"A1,VERTICAL", "C5,HORIZONTAL", "F9,VERTICAL",
      "H5,HORIZONTAL", "J0,HORIZONTAL"};
  }
  
  public String[] getShips () {
    return ships;
  }
}