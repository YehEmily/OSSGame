package battleship;

import java.io.*;
import java.util.*;

public class BSGUIGame {
  
  private AIPlayer ai; // Computer instance
  private Player p; // Player instance
  private boolean isPlayerTurn, isGameOver; // Game state booleans
  private Board aiBoard; // AI's reference board
  
  /**
   * Constructor
   */
  public BSGUIGame () {
    ai = new AIPlayer("AI");
    p = new Player("Player 1");
    isPlayerTurn = true; // Player starts
    isGameOver = false;
    aiBoard = new Board();
    play();
  }
  
  /**
   * Main gameplay happens here
   */
  public void play () {
    placeUserShips();
//    placeUserShipsFromInput("ships.txt");
    placeAIShips();
    
    while (!isGameOver) {
      
      while (isPlayerTurn) { // Player's turn
        System.out.println("It's your turn! Enter a coordinate to shoot.");
        String coordinate = userQuery();
        boolean isHit = ai.getBoard().isHit(coordinate); // Check if hit
        if (isHit) {
          System.out.println("Congratulations! It's a hit!");
        } else {
          System.out.println("Drat, looks like it's a miss!");
        }
        isPlayerTurn = false;
      }
      System.out.println("***** AI'S BOARD *****");
      System.out.println(ai.getBoard().toHiddenString());
      isGameOver = ai.getBoard().isGameOver();
      
      while (!isPlayerTurn) { // AI's turn
        System.out.println("It's the AI's turn! Give it a second to pick a coordinate.");
        System.out.println(Arrays.deepToString(p.getBoard().getBoard()));
        String nextShot = ai.getNextShot(p.getBoard());
        boolean isHit = p.getBoard().isHit(nextShot); // Check if hit
        if (isHit) {
          aiBoard.addAction(nextShot, 7);
          System.out.println("Oh! The AI has made a hit!");
        } else {
          System.out.println("The AI missed!");
          aiBoard.addAction(nextShot, -1);
        }
        isPlayerTurn = true;
      }
      System.out.println("***** YOUR BOARD *****");
      System.out.println(p.getBoard());
      isGameOver = p.getBoard().isGameOver();
    }
    
    System.out.println("Congratulations, game over!");
  }
  
  /**
   * userQuery: Asks the user for information.
   * 
   * @return  user input from keyboard
   */
  public String userQuery () {
    Scanner s = new Scanner(System.in);
    String coordinate = s.next();
    s.close();
    return coordinate;
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
        System.out.println("IOException caught.");
        return "";
      }
      } catch (FileNotFoundException ex) {
      System.out.println("File not found!");
      return "";
    }
  }
  
  /**
   * Places user's ships from keyboard
   */
  public void placeUserShips () {
    Board b = p.getBoard(); // retrieve the player's board
    
    System.out.println("Welcome to Battleship! It's time to place your ships.");
    System.out.println("Specify coordinates in the form of COORDINATE,DIRECTION.");
    System.out.println("For example: A4,VERTICAL");
    System.out.println("Ships are not allowed to overlap.");
    System.out.println("Where would you like to place your CARRIER? (5 units long)");
    Scanner s = new Scanner (System.in);
    String carrier = s.next();
    s.close();
    b.placeShip(convertCoord(carrier)[0], convertCoord(carrier)[1], 5);
    System.out.println(b);
    
    System.out.println("Where would you like to place your BATTLESHIP? (4 units long)");
    s = new Scanner (System.in);
    String battleship = s.next();
    s.close();
    b.placeShip(convertCoord(battleship)[0], convertCoord(battleship)[1], 4);
    System.out.println(b);
    
    System.out.println("Where would you like to place your CRUISER? (3 units long)");
    s = new Scanner (System.in);
    String cruiser = s.next();
    s.close();
    b.placeShip(convertCoord(cruiser)[0], convertCoord(cruiser)[1], 3);
    System.out.println(b);
    
    System.out.println("Where would you like to place your SUBMARINE? (3 units long)");
    s = new Scanner (System.in);
    String submarine = s.next();
    s.close();
    b.placeShip(convertCoord(submarine)[0], convertCoord(submarine)[1], 3);
    System.out.println(b);
    
    System.out.println("Where would you like to place your DESTROYER? (2 units long)");
    s = new Scanner (System.in);
    String destroyer = s.next();
    s.close();
    b.placeShip(convertCoord(destroyer)[0], convertCoord(destroyer)[1], 2);
    System.out.println(b);
    
    System.out.println("All ships have been placed! Give AI a moment to place her ships, too...");
  }
  
  public static void main (String[] args) {
    new BattleshipGame();
  }
}