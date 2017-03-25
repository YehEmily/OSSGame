import java.util.*;

// Create printer class(?)
// Alternatively try printing just function calls brute-force

public class AIPlayer extends Player {
  
  private Move bestOffMove;
  private Move bestDefMove;
  private float bestOffScore;
  private float bestDefScore;
  
//  private int[][] moves = {{2,2}, {1,1}, {1,3}, {3,1}, {3,3},
//    {1,2}, {2,1}, {2,3}, {3,2}};
  
  public AIPlayer () {
    super("Computer");
    bestOffMove = new Move(-1, -1);
    bestDefMove = new Move(-1, -1);
    bestDefScore = 0;
    bestOffScore = 0;
  }
  
  public AIPlayer(String name) {
    super(name);
    bestOffMove = new Move(-1, -1);
    bestDefMove = new Move(-1, -1);
    bestDefScore = 0;
    bestOffScore = 0;
  }
  
  // Not in use atm
//  public String getMove (Board board) {
//    for (int i = 0; i < moves.length; ++i) {
//      int move_x = moves[i][0];
//      int move_y = moves[i][1];
//      if (board.getBoard()[move_x-1][move_y-1]==0) {
//        getInformedMove(board);
//        return move_x + "," + move_y;
//      }
//    }
//    System.out.println("No more moves. Game over?");
//    return null;
//  }
  
  public Move getMMMove (Board board) {
    findMMValue(board, "Computer", 1);
    if ((-1 * bestDefScore) > bestOffScore) {
      System.out.println("Def: " + bestDefScore);
      Move finalMove = bestDefMove;
      bestOffMove = new Move(-1, -1);
      bestDefMove = new Move(-1, -1);
      bestDefScore = 0;
      bestOffScore = 0;
      return finalMove;
    } else {
      System.out.println("Off: " + bestOffScore);
      Move finalMove = bestOffMove;
      bestOffMove = new Move(-1, -1);
      bestDefMove = new Move(-1, -1);
      bestDefScore = 0;
      bestOffScore = 0;
      return finalMove;
    }
  }
  
  public float findMMValue (Board b, String currentPlayer, int depth) {
    LinkedList<Move> moves = b.getAvailableMoves();
    float currentScore = 0;
    float bestScore = 0;
    
    if (moves.isEmpty() || isGameOver(b)) {
      bestScore = calculateSum(b) / depth;
//      System.out.println(bestScore);
      
    } else {
      while (!moves.isEmpty()) {
        Move m = moves.remove();
        
        if (currentPlayer.equals("Computer")) { // Computer's turn
          b.addMark(m.getFirst(), m.getSecond(), false);
          currentScore = findMMValue(b, "Player", depth+1);
          if (currentScore > bestOffScore) {
            bestOffScore = currentScore;
            bestOffMove = m;
            bestScore = currentScore;
          }
          b.removeMark(m.getFirst(), m.getSecond()); // clean up board
          
        } else { // Player's turn
          b.addMark(m.getFirst(), m.getSecond(), true);
          currentScore = findMMValue(b, "Computer", depth+1);
          if (currentScore < bestDefScore) {
            bestDefScore = currentScore;
            bestDefMove = m;
            bestScore = currentScore;
          }
          b.removeMark(m.getFirst(), m.getSecond()); // clean up board
        }
      }
    }
    return bestScore;
  }
  
  
  public Move getNextMove (Board currentBoard) {
//    System.out.println(getMMMove (currentBoard));
    return getMMMove (currentBoard);
  }
  
  public Move getInformedMove(Board currentBoard) {
    Hashtable<Move, Integer> moveSums = new Hashtable<Move, Integer>();
    LinkedList<Move> moves = currentBoard.getAvailableMoves();
    while (!moves.isEmpty()) {
      Move move = moves.remove();
      moveSums.put(move, calculateSum(currentBoard));//, move));
    }
//    System.out.println(moveSums);
    return findMaxMove(moveSums);
  }
  
  private Move findMaxMove (Hashtable<Move,Integer> moves) {
    int maxVal = -100;
    Move maxMove = null;
    for (Map.Entry<Move,Integer> entry : moves.entrySet()) {
      if (entry.getValue() > maxVal) {
        maxVal = entry.getValue();
        maxMove = entry.getKey();
      }
    }
    return maxMove;
  }
  
  // Return +100 for each 3-in-a-row
  // Return +10 for each 2-in-a-row-with-one-empty-cell
  // Return +1 for each 1-with-two-empty-cells
  // Return -(value) for player (following rules above)?
  // Return 0 otherwise (empty lines or lines with both computer and player)
  private int calculateSum (Board board) {
    int sum = 0;
//    board.addMark(proposedMove.getFirst(), proposedMove.getSecond(), false);
    int[][] b = board.getBoard();
    
    sum += calculateSumForPlayer(b, 2);
    sum -= calculateSumForPlayer(b, 1);
    
//    board.removeMark(proposedMove.getFirst(), proposedMove.getSecond());
    return sum;
  }
  
  private int calculateSumForPlayer (int[][] b, int x) {
    // x is player; computer = 2, player = 1
    int sum = 0;
    
    // Check diagonals
    if ((b[0][0]==x) && (b[1][1]==x) && (b[2][2]==x)) {
      sum += 1000;
    } else if ((b[0][2]==x) && (b[1][1]==x) && (b[2][0]==x)) {
      sum += 1000;
    }
    
    else if (((b[0][0]==x) && (b[1][1]==x) && (b[2][2]==0)) ||
             ((b[0][0]==x) && (b[1][1]==0) && (b[2][2]==x)) ||
             ((b[0][0]==0) && (b[1][1]==x) && (b[2][2]==x)))
      sum += 10;
    else if (((b[0][2]==x) && (b[1][1]==x) && (b[2][0]==0)) ||
             ((b[0][2]==x) && (b[1][1]==0) && (b[2][0]==x)) ||
             ((b[0][2]==0) && (b[1][1]==x) && (b[2][0]==x)))
      sum += 10;
    
    else if (((b[0][0]==x) && (b[1][1]==0) && (b[2][2]==0)) ||
             ((b[0][0]==0) && (b[1][1]==x) && (b[2][2]==0)) ||
             ((b[0][0]==0) && (b[1][1]==0) && (b[2][2]==x)))
      sum += 1;
    else if (((b[0][2]==x) && (b[1][1]==0) && (b[2][0]==0)) ||
             ((b[0][2]==0) && (b[1][1]==x) && (b[2][0]==0)) ||
             ((b[0][2]==0) && (b[1][1]==0) && (b[2][0]==x)))
      sum += 1;
    
    // Check each row and column
    for (int i = 0; i < 3; ++i) {
      if ((b[i][0]==x) && (b[i][1]==x) && (b[i][2]==x)) {
        sum += 1000;
      } if ((b[0][i]==x) && (b[1][i]==x) && (b[2][i]==x)) {
        sum += 1000;
      }
      
      if (((b[i][0]==x) && (b[i][1]==x) && (b[i][2]==0)) ||
          ((b[i][0]==x) && (b[i][1]==0) && (b[i][2]==x)) ||
          ((b[i][0]==0) && (b[i][1]==x) && (b[i][2]==x)))
        sum += 10;
      if (((b[0][i]==x) && (b[1][i]==x) && (b[2][i]==0)) ||
          ((b[0][i]==x) && (b[1][i]==0) && (b[2][i]==x)) ||
          ((b[0][i]==0) && (b[1][i]==x) && (b[2][i]==x)))
        sum += 10;
      
      if (((b[i][0]==x) && (b[i][1]==0) && (b[i][2]==0)) ||
          ((b[i][0]==0) && (b[i][1]==x) && (b[i][2]==0)) ||
          ((b[i][0]==0) && (b[i][1]==0) && (b[i][2]==x)))
        sum += 1;
      if (((b[0][i]==x) && (b[1][i]==0) && (b[2][i]==0)) ||
          ((b[0][i]==0) && (b[1][i]==x) && (b[2][i]==0)) ||
          ((b[0][i]==0) && (b[1][i]==0) && (b[2][i]==x)))
        sum += 1;
    }
    return sum;
  }
  
  private boolean isGameOver (Board board) {
    try {
      for (int i = 0; i < 3; ++i) {
        if ((board.getBoard()[i][0]!=0) &&
            (board.getBoard()[i][0]==board.getBoard()[i][1]) &&
            (board.getBoard()[i][1]==board.getBoard()[i][2])) {
          return true;
        } else if ((board.getBoard()[0][i]!=0) &&
                   (board.getBoard()[0][i]==board.getBoard()[1][i]) &&
                   (board.getBoard()[1][i]==board.getBoard()[2][i])) {
          return true;
        }
      }
      if ((board.getBoard()[0][0]!=0) && 
          (board.getBoard()[0][0]==board.getBoard()[1][1]) &&
          (board.getBoard()[1][1]==board.getBoard()[2][2])) {
        return true;
      } else if ((board.getBoard()[2][0]!=0) &&
                 (board.getBoard()[2][0]==board.getBoard()[1][1]) &&
                 (board.getBoard()[1][1]==board.getBoard()[0][2])) {
        return true;
      }
      return false;
    } catch (NullPointerException ex) {
      return false;
    }
  }
}