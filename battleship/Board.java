package battleship;

import java.util.*;

public class Board {
  
  private char[] rowNames = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J'};
  private int[][] board;
  private LinkedList<String[]> ships;
  private LinkedList<Integer[]> shipsCount;
  
  /**
   * Constructor
   */
  public Board () {
    board = new int[10][10];
    ships = new LinkedList<String[]>();
    shipsCount = new LinkedList<Integer[]>();
  }
  
  /**
   * placeShip: Places a ship based on coordinate, direction, and length.
   * 
   * @param   starting coordinate of ship
   * @param   direction ship faces
   * @param   length of ship
   */
  public void placeShip (String c, String d, int length) {
    int[] result = convertCoord(c);
    int row = result[0]; int column = result[1];
    String direction = d.toUpperCase();
    String[] coords = new String[length];
    Integer[] marks = new Integer[length];
    
    if ("VERTICAL".equals(direction)) { // Keep column fixed
      for (int i = row; i < (row+length); ++i) {
        board[i][column] = 5;
        coords[i-row] = "" + rowNames[i] + column;
        marks[i-row] = 1;
      }
    } else { // Keep row fixed
      for (int i = column; i < (column+length); ++i) {
        board[row][i] = 5;
        coords[i-column] = "" + rowNames[row] + i;
        marks[i-column] = 1;
      }
    }
    ships.add(coords);
    shipsCount.add(marks);
  }
  
  /**
   * Ships getter method.
   */
  public LinkedList<String[]> getShips () {
    return ships;
  }
  
  /**
   * isHit: Determines whether a given coordinate is a hit, and also
   * adds the hit to the board.
   * 
   * @param   coordinate of query
   * @return  whether coordinate landed on a ship
   */
  public boolean isHit (String coordinate) {
    int[] coords = convertCoord(coordinate);
    if (board[coords[0]][coords[1]] == 5) {
      board[coords[0]][coords[1]] = 7;
      for (int i = 0; i < ships.size(); ++i) {
        for (int j = 0; j < ships.get(i).length; ++j) {
          if (ships.get(i)[j].equals("" + rowNames[coords[0]] + coords[1])) {
            shipsCount.get(i)[j] = -1;
          }
        }
      }
      return true;
    } else if (board[coords[0]][coords[1]] == 0) {
      board[coords[0]][coords[1]] = -1;
    }
    return false;
  }
  
  /**
   * isShipSunk: Determines whether a given ship has been sunk. If so,
   * this method also marks the ship as "sunk" on the board.
   * 
   * @param   size of ship to be checked
   * @return  whether ship has been sunk
   */
  public boolean isShipSunk (int shipSize) {
    int index = returnShipIndex(shipSize);
    if (index != -1) {
      for (int i = 0; i < shipsCount.get(index).length; ++i) {
        if (shipsCount.get(index)[i] != -1) {
          return false;
        }
      }
      markSunkShips(shipSize);
      return true;
    }
    return false;
  }
  
  /**
   * isValidShot: Determines whether a given shot is valid.
   * 
   * @param   coordinate to be checked
   * @return  whether coordinate is valid/empty
   */
  public boolean isValidShot (String coordinate) {
    int[] coords = convertCoord(coordinate);
    return ((board[coords[0]][coords[1]] != 9) &&
            (board[coords[0]][coords[1]] != 7) &&
            (board[coords[0]][coords[1]] != -1));
  }
  
  /**
   * isGameOver: Determines from the board whether the game is over.
   */
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
  
  /**
   * addAction: Only used by AIPlayers. Adds an action to their copy of the board
   * for future reference.
   * 
   * @param   coordinate of action
   * @param   value of action
   */
  public void addAction (String c, int action) { // for AI
    int[] coords = convertCoord(c);
    board[coords[0]][coords[1]] = action;
  }
  
  /**
   * Board getter method.
   */
  public int[][] getBoard () {
    return this.board;
  }
  
  /**
   * Prints board with ships concealed.
   */
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
        } else if (board[i][j] == 9) {
          s += "H ";
        } else {
          s += "- ";
        }
      }
      s += "\n";
    }
    return s;
  }
  
  /**
   * Prints board as is.
   */
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
  
  /**
   * convertCoord: Converts a given coordinate to an integer array.
   * 
   * @param   coordinate to be converted
   * @return  integer array of coordinate values
   */
  private int[] convertCoord (String c) {
    int[] result = new int[2];
    result[0] = findRow(c.charAt(0));
    result[1] = Integer.parseInt(c.substring(1, c.length()));
    return result;
  }
  
  /**
   * markSunkShips: Marks sunk ships on the board.
   */
  private void markSunkShips (int shipSize) {
    int index = returnShipIndex(shipSize);
    String[] shipSunk = ships.get(index);
    for (int i = 0; i < shipSunk.length; ++i) {
      String coord = shipSunk[i];
      int[] coords = convertCoord(coord);
      board[coords[0]][coords[1]] = 9; // Signifies sunk
    }
  }
  
  /**
   * returnShipIndex: Returns index of ship in ships or shipsCount linked lists.
   * 
   * @param   size of ship
   * @return  integer index of ship
   */
  private int returnShipIndex (int shipSize) {
    int index = -1;
    switch (shipSize) {
      case 5: index = 0;
      break;
      case 4: index = 1;
      break;
      case 3: index = 2;
      break;
      case 33: index = 3; // No good way to represent the second 3-long ship
      break;
      case 2: index = 4;
      break;
      default: System.out.println("Invalid ship size");
      break;
    }
    return index;
  }
  
  /**
   * Finds row of a given row name (A-J).
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
  
  /**
   * Cleans up the board as it's represented as a string.
   */
  private String cleanUpString (String s) {
    String newString1 = s.replaceAll(" 0", " -"); // Undiscovered
    String newString2 = newString1.replaceAll(" 5", " S"); // Ships
    String newString3 = newString2.replaceAll(" -1", " O"); // Misses
    String newString4 = newString3.replaceAll(" 7", " X"); // Hits
    String newString5 = newString4.replaceAll(" 9", " H");
    String newString6 = newString5.replaceAll("_", "");
    String finalString = newString6.substring(0, newString6.length()-2);
    return finalString;
  }
}