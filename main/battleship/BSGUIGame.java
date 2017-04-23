package battleship;

import java.io.*;
import java.util.*;

public class BSGUIGame {
  
  private AIPlayer ai; // Computer instance
  private Player p; // Player instance
  private boolean isPlayerTurn, isGameOver, playerHitWasMade, computerHitWasMade;
  private Board aiBoard; // AI's reference board
  final static String[] ROWS = {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J"};
  
  /**
   * Constructor
   */
  public BSGUIGame () {
    ai = new AIPlayer("AI");
    p = new Player("Player 1");
    isPlayerTurn = true; // Player starts
    isGameOver = false;
    aiBoard = new Board();
    placeAIShips();
    placeUserShipsFromInput("/home/emily/Desktop/OSSGame/main/battleship/ships.txt");
  }
  
  public boolean isPlayerTurn () {return isPlayerTurn;}
  public boolean isGameOver () {return isGameOver;}
  public String[] getRows () {return ROWS;}
  
  public void userTurn (String move) {
    boolean isHit = ai.getBoard().isHit(move); // Check if hit
    if (isHit) {
      playerHitWasMade = true;
      System.out.println("Congratulations! It's a hit!");
    } else {
      playerHitWasMade = false;
      System.out.println("Drat, looks like it's a miss!");
    }
    isPlayerTurn = false;
    isGameOver = ai.getBoard().isGameOver();
  }
  
  public boolean playerHitWasMade () {
    return playerHitWasMade;
  }
  
  public int[] computerTurn () {
    String nextShot = ai.getNextShot(p.getBoard());
    boolean isHit = p.getBoard().isHit(nextShot); // Check if hit
    if (isHit) {
      aiBoard.addAction(nextShot, 7);
      computerHitWasMade = true;
    } else {
      aiBoard.addAction(nextShot, -1);
      computerHitWasMade = false;
    }
    isPlayerTurn = true;
    isGameOver = p.getBoard().isGameOver();
    return unconvertCoord(nextShot);
  }
  
  public boolean computerHitWasMade () {
    return computerHitWasMade;
  }
  
  /**
   * convertCoord: Converts a given coordinate into a String array
   * 
   * @param   String input
   * @return  String[] output of input delimited by commas
   */
  public String[] convertCoord (String c) {
    String[] result = new String[2];
    result[0] = c.split(",")[0];
    result[1] = c.split(",")[1];
    return result;
  }
  
  private int[] unconvertCoord (String c) {
    int[] result = new int[2];
    String rowName = c.substring(0, 1);
    int index = -1;
    for (int i = 0; i < ROWS.length; ++i) {
      if (ROWS[i].equals(rowName)) index = i;
    }
    result[0] = index; result[1] = Integer.parseInt(c.substring(1));
    return result;
  }
  
  /**
   * Places AI's ships
   */
  public void placeAIShips () {
    Board b = ai.getBoard();
    String[] ships = ai.getShips();
    int[] ship_lengths = {5, 4, 3, 3, 2};
    for (int i = 0; i < ships.length; ++i) {
      String ship = ships[i];
      b.placeShip(convertCoord(ship)[0], convertCoord(ship)[1], ship_lengths[i]);
    }
  }
  
  /**
   * Places user's ships from a text file
   */
  public String placeUserShipsFromInput (String fileName) {
    Board b = p.getBoard();
    int[] ship_sizes = {5, 4, 3, 3, 2};
    int counter = 0;
    try {
      BufferedReader br = new BufferedReader(new FileReader(fileName));
      try {
        StringBuilder sb = new StringBuilder();
        String line = br.readLine();
        
        while (line != null) {
          b.placeShip(convertCoord(line)[0], convertCoord(line)[1], ship_sizes[counter]);
          sb.append(line);
          sb.append(System.lineSeparator());
          line = br.readLine();
          counter++;
        }
        String all = sb.toString();
        br.close();
        return all;
      } catch (IOException ex) {
        System.out.println("IOException caught!");
        return "";
      }
      } catch (FileNotFoundException ex) {
      System.out.println("File not found!");
      return "";
    }
  }
}