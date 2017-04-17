import java.util.*;

public class AIPlayerRandomShots extends AIPlayerObject {
  
  private Queue<String> queue;
  private LinkedList<String> hits;
  private char[] rowNames;
  
  /**
   * Constructor.
   */
  public AIPlayerRandomShots (String name) {
    super(name);
    queue = new LinkedList<String>();
    hits = new LinkedList<String>();
    rowNames = new String("ABCDEFGHIJ").toCharArray();
  }
  
  /**
   * getNextShot: Randomly generates next shot, unless a hit was landed previously.
   * 
   * @param   current state of board
   * @return  next shot coordinate (string)
   */
  public String getNextShot (Board b) {
    String result = "";
    if (!queue.isEmpty()) { // A hit was made
      String c = queue.remove();
      result = c;
    } else {
      Random r = new Random();
      int row = r.nextInt(10);
      int col = r.nextInt(10);
      result = Character.toString(rowNames[row]) + col;
    }
    return result;
  }
  
  /**
   * addHit: If previous shot was successful, adds its neighbors to queue to try in following moves.
   * 
   * @param   coordinate of hit
   */
  public void addHit (String coordinate) {
    LinkedList<String> candidates = getAdjCoords(coordinate);
    while (!candidates.isEmpty()) {
      queue.add(candidates.remove());
    }
    hits.add(coordinate);
  }
  
  /**
   * getAdjCoords: Returns neighboring coordinates to a given coordinate.
   * 
   * @param   coordinate to find neighbors for
   * @return  linked list of neighboring coordinates
   */
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
    return result;
  }
  
  /**
   * findRow: Finds row number of a given row name.
   */
  private int findRow (char c) {
    
    int count = -1;
    for (int i = 0; i < rowNames.length; ++i) {
      if (rowNames[i] == c) {
        return count + 1;
      }
      count++;
    }
    return -1;
  }
}