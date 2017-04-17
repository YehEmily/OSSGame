import java.util.*;

public class AIPlayer extends AIPlayerObject {
  
  private String[] ships; // Pre-loaded ship locations
  private int[][] probs; // Probabilities of each square on board
  private String[] rows; // Names of rows
  private int largestShipNow;
  
  /**
   * Constructor
   * @param   name of AI
   */
  public AIPlayer (String name) {
    super(name);
    ships = new String[] {"A1,VERTICAL", "C5,HORIZONTAL", "F9,VERTICAL",
      "H5,HORIZONTAL", "J0,HORIZONTAL"};
    rows = new String[] {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J"};
    probs = new int[10][10];
    largestShipNow = 5;
  }
  
  /**
   * getNextShot: Main method to find next shot. Based on *improved* PDF algorithm, in which
   * probabilities are derived from (at most) 5 different PDFs to represent the 5 different
   * ships (albeit only 4 different ship sizes).
   * 
   * @param   current state of board
   * @return  next shot (string)
   */
  public String getNextShot (Board board) {
    int[][] b2 = new int[10][10];
    int[][] b3 = new int[10][10];
    int[][] b4 = new int[10][10];
    int[][] b5 = new int[10][10];
    int[][] b33 = new int[10][10];
    
    if (!board.isShipSunk(5)) {
      calcProbs(board, 5);
      b5 = copyProbs();
      probs = new int[10][10];
    } else {
      largestShipNow = 4;
    }
    if (!board.isShipSunk(4)) {
      calcProbs(board, 4);
      b4 = copyProbs();
      probs = new int[10][10];
    } else {
      largestShipNow = 3;
    }
    if (!board.isShipSunk(3)) {
      calcProbs(board, 3);
      b3 = copyProbs();
      probs = new int[10][10];
    } else {
      if (board.isShipSunk(33)) {
        largestShipNow = 2;
      }
    }
    if (!board.isShipSunk(33)) {
      calcProbs(board, 3);
      b33 = copyProbs();
      probs = new int[10][10];
    } else {
      if (board.isShipSunk(3)) {
        largestShipNow = 2;
      }
    }
    if (!board.isShipSunk(2)) {
      calcProbs(board, 2);
      b2 = copyProbs();
      probs = new int[10][10];
    }
    
    probs = sumProbs(b5, b4, b3, b2, b33);
    
    increaseLinearHitProbability(board);
    
    ruleOutShotsAndMisses(board);
    
    int[] bestMove = findBestMove();
    
    String bm = bestMove[0] + "," + bestMove[1];
    probs = new int[10][10];
    return convertPairToCoord(bm);
  }
  
  /**
   * findBestMove: Finds best move based on PDF algorithm.
   * 
   * @return  best move in the form of an integer array
   */
  private int[] findBestMove () {
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
    return bestMoveSoFar;
  }
  
  /**
   * Copies probability board to another board.
   */
  private int[][] copyProbs () {
    int[][] copy = new int[10][10];
    for (int i = 0; i < 10; ++i) {
      for (int j = 0; j < 10; ++j) {
        copy[i][j] = probs[i][j];
      }
    }
    return copy;
  }
  
  /**
   * Ships getter method.
   */
  public String[] getShips () {
    return ships;
  }
  
  /**
   * calcProbs: Calculates probability of finding a given ship on each coordinate
   * on the board.
   * 
   * @param   current state of board
   * @param   size of ship to be searched for
   */
  private void calcProbs (Board board, int shipSize) {
    int[][] b = board.getBoard();
    
    for (int i = 0; i < 10; ++i) {
      for (int j = 0; j < 10; ++j) {
        
        if ((i + shipSize) < 11) { // VERTICAL SECTIONS
          if (!foundActionInRange(b, i, i+shipSize, j, j, -1)) { // if no misses
            
            if (foundActionInRange(b, i, i+shipSize, j, j, 7)) { // if a hit
              for (int k = i; k < (i + shipSize); ++k) {
                probs[k][j] += 2; // More likely near hit
              }
            } else if (foundActionInRange(b, i, i+shipSize, j, j, 9)) { // If SUNK
              for (int k = i; k < (i + shipSize); ++k) {
                probs[k][j] = 0; // Can't exist in sunk space
              }
            } else { // If undiscovered
              for (int k = i; k < (i + shipSize); ++k) {
                probs[k][j] += 1;
              }
            }
          } else { // if miss in column
            for (int k = i; k < (i + shipSize); ++k) {
              probs[k][j] = 0; // there's 0% chance of ship being in column
            }
          }
        }
        
        if ((j + shipSize) < 11) { // HORIZONTAL SECTIONS
          if (!foundActionInRange(b, i, i, j, j+shipSize, -1)) { // if no misses
            if (foundActionInRange(b, i, i, j, j+shipSize, 7)) { // if a hit
              for (int k = j; k < (j + shipSize); ++k) {
                probs[i][k] += 2; // More likely near hit
              }
            } else if (foundActionInRange(b, i, i, j, j+shipSize, 9)) { // If undiscovered
              for (int k = j; k < (j + shipSize); ++k) {
                probs[i][k] = 0; // Still likely in empty spaces
              }
            } else {
              for (int k = j; k < (j + shipSize); ++k) {
                probs[i][k] += 1; // Ship cannot exist in sunk space
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
  
  /**
   * ruleOutShotsAndMisses: Removes probabilities of making another shot on a
   * coordinate that has already been marked as a hit or a miss.
   * 
   * @param   current state of board
   */
  private void ruleOutShotsAndMisses (Board board) {
    int[][] b = board.getBoard();
    for (int i = 0; i < 10; ++i) {
      for (int j = 0; j < 10; ++j) {
        if ((b[i][j] == 7) || (b[i][j] == -1) || (b[i][j] == 9)) {
          probs[i][j] = 0;
        }
        if ((b[j][i] == 7) || (b[j][i] == -1) || (b[j][i] == 9)) {
          probs[j][i] = 0;
        }
      }
    }
  }
  
  /**
   * convertPairToCoord: Converts a pair (X,X) to a coordinate (X0).
   * 
   * @param   input pair as a string
   * @return  coordinate as a string
   */
  private String convertPairToCoord (String s) {
    String[] pieces = s.split(",");
    String row = rows[Integer.parseInt(pieces[0])];
    return row + pieces[1];
  }
  
  /**
   * foundActionInRange: Determines whether there is a hit or a miss in the given
   * range of rows and columns.
   * 
   * @param   board represented as integer array
   * @param   row 1
   * @param   row 2
   * @param   column 1
   * @param   column 2
   * @param   hit (x=7) or miss (x=-1)
   * @return  true if found hit or miss, false otherwise
   */
  private boolean foundActionInRange (int[][] b, int r1, int r2,
                                      int c1, int c2, int x) {
    for (int i = r1; i < r2; ++i) {
      for (int j = c1; j < c2; ++j) {
        if (b[i][j] == x) { // x = 7 if hit, -1 if miss, 0 if undiscovered, 9 if sunk
          return true;
        }
      }
    }
    return false;
  }
  
  /**
   * sumProbs: Adds up probabilities of finding 5-long, 4-long, etc. ships to create
   * a more cohesive PDF.
   * 
   * @param   probabilities of finding each ship (x5 integer arrays)
   * @return  final probability distribution as an integer array
   */
  private int[][] sumProbs (int[][] b5, int[][] b4, int[][] b3, int[][] b2, int[][] b33) {
    int[][] results = new int[10][10];
    for (int i = 0; i < 10; ++i) {
      for (int j = 0; j < 10; ++j) {
        results[i][j] = b5[i][j] + b4[i][j] + b3[i][j] + b2[i][j] + b33[i][j];
      }
    }
    return results;
  }
  
  /**
   * getHits: Finds all the hits on the board and returns them in a linked list.
   * 
   * @param   current state of board
   * @result  linked list of coordinates of hits
   */
  private LinkedList<String> getHits (Board board) {
    LinkedList<String> results = new LinkedList<String>();
    int[][] b = board.getBoard();
    for (int i = 0; i < 10; ++i) {
      for (int j = 0; j < 10; ++j) {
        if (b[i][j] == 7) {
          String s = rows[i] + j;
          results.add(s);
        }
      }
    }
    return results;
  }
  
  /**
   * increaseLinearHitProbability: Increases the probability of shooting at a
   * coordinate that is adjacent to a known hit.
   * 
   * @param   current state of board
   */
  private void increaseLinearHitProbability (Board b) {
    LinkedList<String> nextHits = new LinkedList<String>();
    LinkedList<String> hits = getHits(b);
    for (int i = 0; i < hits.size(); ++i) {
      for (int j = i+1; j < hits.size(); ++j) {
        if (maybeColinear(hits.get(i), hits.get(j))) {
          nextHits.addAll(getNeighbors(hits.get(i), hits.get(j)));
        }
      }
    }
    for (int i = 0; i < nextHits.size(); ++i) {
      int[] coords = convertCoord(nextHits.get(i));
      probs[coords[0]][coords[1]] += 100;
    }
  }
  
  /**
   * maybeColinear: Determines whether two coordinates are colinear.
   * 
   * @param   two coordinates to compare
   * @return  whether the two coordinates are colinear
   */
  private boolean maybeColinear (String s1, String s2) {
    int[] c1 = convertCoord(s1);
    int[] c2 = convertCoord(s2);
    return (((c1[0] == c2[0]) && (Math.abs(c1[1] - c2[1]) <= largestShipNow)) ||
            ((c1[1] == c2[1]) && (Math.abs(c1[0] - c2[0]) <= largestShipNow)));
  }
  
  /**
   * getNeighbors: Finds coordinates between and adjacent to two given coordinates.
   * 
   * @param   two coordinates as endpoints
   * @return  linked list of neighboring coordinates
   */
  private LinkedList<String> getNeighbors (String s1, String s2) {
    LinkedList<String> neighbors = new LinkedList<String>();
    int[] c1 = convertCoord(s1);
    int[] c2 = convertCoord(s2);
    int index1 = 0;
    int index2 = 0;
    
    if ((c1[0] == c2[0]) && (Math.abs(c1[1] - c2[1]) <= largestShipNow)) {
      for (int i = c1[1]+1; i < c2[1]; ++i) { // Add inbetweens
        String s = "" + rows[c1[0]] + i;
        neighbors.add(s);
      }
      
      if (c1[1] < c2[1]) {
        index1 = c1[1];
        index2 = c2[1];
      } else {
        index1 = c2[1];
        index2 = c1[1];
      }
      
      if (index1 > 0) {
        neighbors.add("" + rows[c1[0]] + (index1-1));
      }
      if (index2 < 10) {
        neighbors.add("" + rows[c1[0]] + (index2+1));
      }
      
    } else if ((c1[1] == c2[1]) && (Math.abs(c1[0] - c2[0]) <= largestShipNow)) {
      for (int i = c1[0]+1; i < c2[0]; ++i) {
        String s = "" + rows[i] + c1[1];
        neighbors.add(s);
      }
      
      if (c1[0] < c2[0]) {
        index1 = c1[0];
        index2 = c2[0];
      } else {
        index1 = c2[0];
        index2 = c1[0];
      }
      
      if (index1 > 0) {
        neighbors.add("" + rows[index1-1] + c1[1]);
      }
      if (index2 < 10) {
        neighbors.add("" + rows[index2+1] + c1[1]);
      }
    }
    
    return neighbors;
  }
  
//  /**
//   * testProbs: Used to check probability distribution within probs array.
//   */
//  private void testProbs () {
//    String s = "";
//    for (int i = 0; i < 10; ++i) {
//      for (int j = 0; j < 10; ++j) {
//        s += probs[i][j] + " ";
//      }
//      s += "\n";
//    }
//    System.out.println(s);
//  }
  
  /**
   * convertCoord: Converts a given coordinate to an integer array.
   */
  protected int[] convertCoord (String c) {
    int[] result = new int[2];
    result[0] = findRow(c.charAt(0));
    result[1] = Integer.parseInt(c.substring(1, c.length()));
    return result;
  }
  
  /**
   * findRow: Finds the row number of a given row name.
   */
  protected int findRow (char c) {
    int count = -1;
    for (int i = 0; i < rows.length; ++i) {
      if (rows[i].charAt(0) == c) {
        return count + 1;
      }
      count++;
    }
    return -1;
  }
}