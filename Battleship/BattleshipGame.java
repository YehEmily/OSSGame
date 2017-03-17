import java.util.*;

public class BattleshipGame {
  
//  private Board board;
  private AIPlayer ai;
  private Player p;
  private boolean isPlayerTurn;
  
  public BattleshipGame () {
//    board = new Board();
    ai = new AIPlayer("AI");
    System.out.println("What's your name?");
    Scanner s = new Scanner(System.in);
    p = new Player(s.next());
    s.close();
    isPlayerTurn = true;
    play();
  }
  
  public void play () {
    placeUserShips();
    placeAIShips();
    
    while (isPlayerTurn) {
    }
  }
  
  public String[] convertCoord (String c) {
    String[] result = new String[2];
    result[0] = c.split(",")[0];
    result[1] = c.split(",")[1];
    return result;
  }
  
  public void placeAIShips () {
    Board b = ai.getBoard();
    String[] ships = ai.getShips();
    int[] ship_lengths = {5, 4, 3, 3, 2};
    for (int i = 0; i < ships.length; ++i) {
      String ship = ships[i];
      b.placeShip(convertCoord(ship)[0], convertCoord(ship)[1], ship_lengths[i]); 
    }
  }
  
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