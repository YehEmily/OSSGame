import java.util.*;

public class AIPlayer extends Player {
  
  private String[] ships;
//  private LinkedList<String> hits;
  private Queue<String> queue;
//  private boolean isCarrierFound, isBattleshipFound, isCruiserFound,
//    isSubmarineFound, isDestroyerFound;
  
  public AIPlayer (String name) {
    super(name);
    ships = new String[] {"A1,VERTICAL", "C5,HORIZONTAL", "F9,VERTICAL",
      "H5,HORIZONTAL", "J0,HORIZONTAL"};
//    hits = new LinkedList<String>();
    queue = new LinkedList<String>();
//    isCarrierFound = false;
//    isBattleshipFound = false;
//    isCruiserFound = false;
//    isSubmarineFound = false;
//    isDestroyerFound = false;
  }
  
  public String[] getShips () {
    return ships;
  }
  
  public String getNextShot () {
    String result = "";
    String[] rows = new String[] {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J"};
    if (!queue.isEmpty()) { // A hit was made
      String c = queue.remove();
      result = c;
    } else {
      Random r = new Random();
      int row = r.nextInt(10);
      int col = r.nextInt(10);
      result = rows[row] + col;
    }
    return result;
  }
  
  public void addHit (String coordinate) {
//    hits.add(coordinate);
    LinkedList<String> candidates = getAdjCoords(coordinate);
    while (!candidates.isEmpty()) {
      queue.add(candidates.remove());
    }
  }
  
  private LinkedList<String> getAdjCoords (String coordinate) {
    LinkedList<String> result = new LinkedList<String>();
    String[] rows = new String[] {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J"};
    int row = findRow(coordinate.charAt(0));
    int col = Integer.parseInt(coordinate.substring(1, coordinate.length()));
    if ((row != 0) && (row != 9)) {
      if ((col != 0) && (col != 9)) {
        String r1 = "" + (rows[row]) + (col+1);
        String r2 = "" + (rows[row]) + (col-1);
        String r3 = "" + (rows[row+1]) + (col);
        String r4 = "" + (rows[row-1]) + (col);
        result.add(r1); result.add(r2); result.add(r3); result.add(r4);
      }
      
      else if (col == 0) {
        String r1 = "" + (rows[row]) + (col+1);
        String r2 = "" + (rows[row+1]) + (col);
        String r3 = "" + (rows[row-1]) + (col);
        result.add(r1); result.add(r2); result.add(r3);
      }
      
      else if (col == 9) {
        String r1 = "" + (rows[row]) + (col-1);
        String r2 = "" + (rows[row+1]) + (col);
        String r3 = "" + (rows[row-1]) + (col);
        result.add(r1); result.add(r2); result.add(r3);
      }
    }
    
    else if (row == 0) {
      if ((col != 0) && (col != 9)) {
        String r1 = "" + (rows[row]) + (col+1);
        String r2 = "" + (rows[row]) + (col-1);
        String r3 = "" + (rows[row+1]) + (col);
        result.add(r1); result.add(r2); result.add(r3);
      }
      
      else if (col == 0) {
        String r1 = "" + (rows[row]) + (col+1);
        String r2 = "" + (rows[row+1]) + (col);
        result.add(r1); result.add(r2);
      }
      
      else if (col == 9) {
        String r1 = "" + (rows[row]) + (col-1);
        String r2 = "" + (rows[row+1]) + (col);
        result.add(r1); result.add(r2);
      }
    }
    
    else if (row == 9) {
      if ((col != 0) && (col != 9)) {
        String r1 = "" + (rows[row]) + (col+1);
        String r2 = "" + (rows[row]) + (col-1);
        String r3 = "" + (rows[row-1]) + (col);
        result.add(r1); result.add(r2); result.add(r3);
      }
      
      else if (col == 0) {
        String r1 = "" + (rows[row]) + (col+1);
        String r2 = "" + (rows[row-1]) + (col);
        result.add(r1); result.add(r2);
      }
      
      else if (col == 9) {
        String r1 = "" + (rows[row]) + (col-1);
        String r2 = "" + (rows[row-1]) + (col);
        result.add(r1); result.add(r2);
      }
    }
    System.out.println(result);
    return result;
  }
  
  private int findRow (char c) {
    char[] rowNames = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J'};
    int count = -1;
    for (int i = 0; i < rowNames.length; ++i) {
      if (rowNames[i] == c) {
        return count + 1;
      }
      count++;
    }
    return -1;
  }
  
  public static void main (String[] args) {
    AIPlayer a = new AIPlayer("Computer");
    System.out.println(a.getNextShot());
  }
}