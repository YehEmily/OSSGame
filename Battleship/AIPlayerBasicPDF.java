public class AIPlayerBasicPDF extends AIPlayerObject {
  
  private String[] ships; // Pre-loaded ship locations
  private int[][] probs; // Probabilities of each square on board
  private String[] rows; // Names of rows
  
  /**
   * Constructor
   * @param   name of AI
   */
  public AIPlayerBasicPDF (String name) {
    super(name);
    ships = new String[] {"A1,VERTICAL", "C5,HORIZONTAL", "F9,VERTICAL",
      "H5,HORIZONTAL", "J0,HORIZONTAL"};
    rows = new String[] {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J"};
    probs = new int[10][10];
  }
  
  /**
   * getNextShot: Main method to find next shot. Based on PDF algorithm.
   * 
   * @param   current state of board
   * @return  next shot (string)
   */
  @Override
  public String getNextShot (Board board) {
    if (!board.isShipSunk(5)) {
      calcProbs(board, 5);
    } else if (!board.isShipSunk(4)) {
      calcProbs(board, 4);
    } else if (!board.isShipSunk(3)) {
      calcProbs(board, 3);
    } else if (!board.isShipSunk(33)) {
      calcProbs(board, 3);
    } else if (!board.isShipSunk(2)) {
      calcProbs(board, 2);
    }
    
    ruleOutShotsAndMisses(board);
    
    int[] bestMove = findBestMove();
    
    String bm = bestMove[0] + "," + bestMove[1];
    probs = new int[10][10];
    return convertPairToCoord(bm);
  }
  
  /**
   * Ships getter method.
   */
  public String[] getShips () {
    return ships;
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
  
  protected int[] convertCoord (String s) {
    return null;
  }
  
  protected int findRow (char c) {
    return 0;
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
  
}