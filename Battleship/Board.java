public class Board {
  
  private char[] rowNames = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J'};
  private int[][] board;
  
  public Board () {
    board = new int[10][10];
  }
  
  public void placeShip (String c, String d, int length) {
    int[] result = convertCoord(c);
    int row = result[0]; int column = result[1];
    String direction = d.toUpperCase();
    
    if (direction.equals("VERTICAL")) { // Keep column fixed
      for (int i = row; i < (row+length); ++i) {
        board[i][column] = 5;
      }
    } else { // Keep row fixed
      for (int i = column; i < (column+length); ++i) {
        board[row][i] = 5;
      }
    }
  }
  
  public boolean isHit (String coordinate) {
    int[] coords = convertCoord(coordinate);
    if (board[coords[0]][coords[1]] == 5) {
      board[coords[0]][coords[1]] = 7;
      return true;
    } else {
      board[coords[0]][coords[1]] = -1;
      return false;
    }
  }
  
  public boolean isValidShot (String coordinate) {
    int[] coords = convertCoord(coordinate);
    if ((board[coords[0]][coords[1]] != -1) &&
        (board[coords[0]][coords[1]] != 7)) {
      return true;
    }
    return false;
  }
  
  public boolean isGameOver () {
    for (int i = 0; i < 10; ++i) {
      for (int j = 0; j < 10; ++j) {
        if (board[i][j] == 5) {
          return false; // Game isn't over if 1+ intact ship piece(s) present
        }
      }
    }
    return true;
  }
  
  public String toHiddenString () {
    String s = "  ";
    for (int i = 0; i < 10; ++i) { // Print first row
      s += i + " ";
    }
    
    s += "\n";
    for (int i = 0; i < 10; ++i) {
      s += rowNames[i] + " ";
      for (int j = 0; j < 10; ++j) { // Print subsequent rows
        if (board[i][j] == 7) {
          s += "X ";
        } else if (board[i][j] == -1) {
          s += "O ";
        } else {
          s += "- ";
        }
      }
      s += "\n";
    }
    return s;
  }
  
  public String toString () {
    String s = "  ";
    for (int i = 0; i < 10; ++i) { // Print first row
      s += "_" + (i) + " ";
    }
    
    s += "\n";
    for (int i = 0; i < 10; ++i) {
      s += rowNames[i] + " ";
      for (int j = 0; j < 10; ++j) { // Print subsequent rows
        s += board[i][j] + " ";
      }
      s += "\n";
    }
    return cleanUpString(s);
  }
  
  public int[] convertCoord (String c) {
    int[] result = new int[2];
    result[0] = findRow(c.charAt(0));
    result[1] = Integer.parseInt(c.substring(1, c.length()));
    return result;
  }
  
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
  
  private String cleanUpString (String s) {
    String newString1 = s.replaceAll(" 0", " -"); // Undiscovered
    String newString2 = newString1.replaceAll(" 5", " S"); // Ships
    String newString3 = newString2.replaceAll(" -1", " O"); // Misses
    String newString4 = newString3.replaceAll(" 7", " X"); // Hits
    String newString5 = newString4.replaceAll("_", "");
    String finalString = newString5.substring(0, newString5.length()-2);
    return finalString;
  }
  
  public static void main (String[] args) {
    Board b = new Board();
    System.out.println(b);
    System.out.println(b.toHiddenString());
  }
}