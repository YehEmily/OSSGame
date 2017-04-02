import java.util.*;

public class AIPlayer extends Player {
  
  private String[] ships;
  private Queue<String> queue;
  private boolean isCarrierFound, isBattleshipFound, isCruiserFound,
    isSubmarineFound, isDestroyerFound;
  private int[][] probs;
  private String[] rows;
  
  public AIPlayer (String name) {
    super(name);
    ships = new String[] {"A1,VERTICAL", "C5,HORIZONTAL", "F9,VERTICAL",
      "H5,HORIZONTAL", "J0,HORIZONTAL"};
    queue = new LinkedList<String>();
    rows = new String[] {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J"};
    
    probs = new int[10][10];
    isCarrierFound = false;
    isBattleshipFound = false;
    isCruiserFound = false;
    isSubmarineFound = false;
    isDestroyerFound = false;
  }
  
  private void calcProbs (Board board, int shipSize) {
    int[][] b = board.getBoard();
    for (int i = 0; i < 10; ++i) {
      for (int j = 0; j < 10; ++j) {
        
        // Check VERTICAL sections
        if ((i + shipSize) < 11) {
          if (!foundActionInRange(b, i, i+shipSize, j, j, -1)) { // if no misses
            if (foundActionInRange(b, i, i+shipSize, j, j, 7)) { // if a hit
              for (int k = i; k < (i + shipSize); ++k) {
                probs[k][j] += 2; // More likely near hit
              }
            } else {
              for (int k = i; k < (i + shipSize); ++k) {
                probs[k][j] += 1; // Still likely in empty spaces
              }
            }
          } else { // if miss in column
            for (int k = i; k < (i + shipSize); ++k) {
              probs[k][j] = 0; // there's 0% chance of ship being in column
            }
          }
        }
        
        // Check HORIZONTAL sections
        if ((j + shipSize) < 11) {
          if (!foundActionInRange(b, i, i, j, j+shipSize, -1)) { // if no misses
            if (foundActionInRange(b, i, i, j, j+shipSize, 7)) { // if a hit
              for (int k = j; k < (j + shipSize); ++k) {
                probs[i][k] += 2; // More likely near hit
              }
            } else {
              for (int k = j; k < (j + shipSize); ++k) {
                probs[i][k] += 1; // Still likely in empty spaces
              }
            }
          } else { // if miss in row
            for (int k = j; k < (j + shipSize); ++k) {
              probs[i][k] = 0; // there's 0% chance of ship being in row
            }
          }
        }
      }
    }
  }
  
  private void testProbs () {
    String s = "";
    for (int i = 0; i < 10; ++i) {
      for (int j = 0; j < 10; ++j) {
        s += probs[i][j] + " ";
      }
      s += "\n";
    }
    System.out.println(s);
  }
  
  public String getNextPDFShot (Board board) {
    if (!isCarrierFound) calcProbs(board, 5);
    else if (!isBattleshipFound) calcProbs(board, 4);
    else if (!isCruiserFound) calcProbs(board, 3);
    else if (!isSubmarineFound) calcProbs(board, 3);
    else if (!isDestroyerFound) calcProbs(board, 2);
    
    testProbs();
    
    int highestProbSoFar = 0;
    int[] bestMoveSoFar = new int[2];
    for (int i = 0; i < 10; ++i) {
      for (int j = 0; j < 10; ++j) {
        if (probs[i][j] > highestProbSoFar) {
          highestProbSoFar = probs[i][j];
          bestMoveSoFar[0] = i;
          bestMoveSoFar[1] = j;
        }
      }
    }
    String bestMove = bestMoveSoFar[0] + "," + bestMoveSoFar[1];
//    probs = new int[10][10];
    return convertPairToCoord(bestMove);
  }
  
  private String convertPairToCoord (String s) {
    String[] pieces = s.split(",");
    String row = rows[Integer.parseInt(pieces[0])];
    return row + pieces[1];
  }
  
  private boolean foundActionInRange (int[][] b, int r1, int r2,
                                              int c1, int c2, int x) {
    for (int i = r1; i < r2; ++i) {
      for (int j = c1; j < c2; ++j) {
        if (b[i][j] == x) { // x = 5 if hit, or x = -1 if miss
          return true;
        }
      }
    }
    return false;
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