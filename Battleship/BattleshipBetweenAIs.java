import java.io.*;

public class BattleshipBetweenAIs {
  
  private AIPlayer ai1;
  private AIPlayerRandomShots ai2;
  
  private boolean isAI1Turn, isGameOver;
  private Board aiBoard; // for ai1
  
  public BattleshipBetweenAIs () {
    ai1 = new AIPlayer("AI 1");
    ai2 = new AIPlayerRandomShots("AI 2");
    isAI1Turn = true;
    isGameOver = false;
    aiBoard = new Board();
    play();
  }
  
  public void play () {
    int count = 0;
    placeAIShipsFromInput("ships.txt");
    placeAIShips();
    while (!isGameOver) {
      count++;
      while (isAI1Turn) { // AI 1's turn
        System.out.println("It's AI 1's turn!");
        String nextShot = ai1.getNextPDFShot_Improved(ai2.getBoard());
        boolean isHit = ai2.getBoard().isHit(nextShot); // Check if hit
        if (isHit) {
          aiBoard.addAction(nextShot, 7);
          System.out.println("A hit!");
        } else {
          aiBoard.addAction(nextShot, -1);
          System.out.println("A miss!");
        }
        isAI1Turn = false;
      }
      System.out.println("***** AI 1'S BOARD *****");
      System.out.println(ai1.getBoard().toHiddenString());
      if (ai1.getBoard().isGameOver()) isGameOver = true;
      
      while (!isAI1Turn) { // AI 2's turn
        System.out.println("It's AI 2's turn!");
        String nextShot = ai2.getNextShot();
        while (!ai1.getBoard().isValidShot(nextShot)) {
          nextShot = ai2.getNextShot();
        }
        boolean isHit = ai1.getBoard().isHit(nextShot); // Check if hit
        if (isHit) {
          System.out.println("A hit!");
        } else {
          System.out.println("A miss!");
        }
        isAI1Turn = true;
      }
      System.out.println("***** AI 2'S BOARD *****");
      System.out.println(ai2.getBoard().toHiddenString());
      if (isGameOver = ai2.getBoard().isGameOver()) isGameOver = true;
    }
    System.out.println("Congratulations, game over!");
    System.out.println("Game finished in " + count + " moves!");
    if (ai2.getBoard().isGameOver()) System.out.println("The winner is AI 1, the PDF algorithm!");
    if (ai1.getBoard().isGameOver()) System.out.println("The winner is AI 2, the searching/hunting algorithm!");
  }
  
  public void placeAIShips () {
    Board b = ai1.getBoard();
    String[] ships = ai1.getShips();
    int[] ship_lengths = {5, 4, 3, 3, 2};
    for (int i = 0; i < ships.length; ++i) {
      String ship = ships[i];
      b.placeShip(convertCoord(ship)[0], convertCoord(ship)[1], ship_lengths[i]); 
    }
  }
  
  public String[] convertCoord (String c) {
    String[] result = new String[2];
    result[0] = c.split(",")[0];
    result[1] = c.split(",")[1];
    return result;
  }
  
  public String placeAIShipsFromInput (String fileName) {
    Board b = ai2.getBoard();
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
  
  public static void main (String[] args) {
    new BattleshipBetweenAIs();
  }
}