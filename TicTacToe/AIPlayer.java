import java.util.*;

public class AIPlayer extends Player {
  
  private Move bestMove;
//  private Boolean isGameOver;
  
//  private int[][] moves = {{2,2}, {1,1}, {1,3}, {3,1}, {3,3},
//    {1,2}, {2,1}, {2,3}, {3,2}};
  
  public AIPlayer () {
    super("Computer");
//    finalMove = new Move(0,0);
//    isGameOver = false;
  }
  
  public AIPlayer(String name) {
    super(name);
//    finalMove = new Move(0,0);
//    isGameOver = false;
  }
  
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
  
//  public Move getMinimaxMove (Board currentBoard) {
//    if (isGameOver) {
//      return null;
//    } else {
//      LinkedList<Integer> scores = new LinkedList<Integer>();
//      LinkedList<Move> finalMoves = new LinkedList<Move>();
//      LinkedList<Move> moves = currentBoard.getAvailableMoves();
//      int[][] b = currentBoard.getCopyBoard();
//      
//      while (!moves.isEmpty()) {
//        Move move = moves.remove();
//        moveSums.put(move, calculateSum(currentBoard, move));
//        b[move.getFirst()][move.getSecond()] = 2;
//        getMinimaxMove (b);
//      }
//    }
//  }
  
  
  // Generate whole game tree
  // Apply utility function to terminal states
  // Use utility of terminal state to determine utility of upper nodes
  // Continue backing up
  // Make decision
//  private void generateAllGameStates (Board currentBoard) {
//    Hashtable<Move, Integer> moveSums = new Hashtable<Move, Integer>();
//    LinkedList<Move> moves = currentBoard.getAvailableMoves();
//    LinkedList<Board> states = new LinkedList<Board>();
//    int[][] copy = currentBoard.getCopyBoard();
//    while (!moves.isEmpty()) {
//      Move m = moves.remove();
//      Board b = new Board(copy);
//      moveSums.put(m, calculateSum(currentBoard, m));
//      
//    }
//      
//  }
  
  public Move getMMMove (Board board) {
//    System.out.println("B");
    int[] result = findMMValue(board, "Computer");
//    System.out.println("V");
    Move m = new Move(result[1], result[2]);
    System.out.println(result[0]);
    return m;
  }
  
  public int[] findMMValue (Board b, String currentPlayer) {
//    int[][] copyArray = board.getCopyBoard();
//    Board b = new Board(copyArray); // Copy board
    LinkedList<Move> moves = b.getAvailableMoves();
    int currentScore;
    int bestScore = 0;
//    Move bestMove = new Move(2,2);
    
    if (moves.isEmpty()) {
      bestScore = calculateSum(b);
      
    } else {
      while (!moves.isEmpty()) {
        Move m = moves.remove();
        
        if (currentPlayer.equals("Computer")) { // Computer's turn
          b.addMark(m.getFirst(), m.getSecond(), false);
          currentScore = findMMValue(b, "Player")[0];
          if (currentScore > bestScore) {
            bestScore = currentScore;
            bestMove = m;
          }
          b.removeMark(m.getFirst(), m.getSecond());
        } else {
          b.addMark(m.getFirst(), m.getSecond(), true);
          currentScore = findMMValue(b, "Computer")[0];
          if (currentScore < bestScore) {
            bestScore = currentScore;
            bestMove = m;
          }
          b.removeMark(m.getFirst(), m.getSecond());
        }
//        System.out.println(b);
//        b.removeMark(m.getFirst(), m.getSecond());
      }
    }
    int[] i = new int[] {bestScore, bestMove.getFirst(), bestMove.getSecond()};
    System.out.println(i[0]);
    return i;
  }
 
//        
//          int[][] copy = b.getCopyBoard();
//          Board copyBoard = new Board(copy);
//          Move m = moves.remove();
//          copyBoard.addMark(m.getFirst(), m.getSecond(), false);
//          int score = findMMValue(copyBoard, "Player", currentSum, false);
//          if (score > currentSum) currentSum = score;
//        }
//      } else { // Player's turn
//        int bestscore = 1000;
//        while (!moves.isEmpty()) {
//          int[][] copy = b.getCopyBoard();
//          Board copyBoard = new Board(copy);
//          Move m = moves.remove();
//          copyBoard.addMark(m.getFirst(), m.getSecond(), false);
//          int score = findMMValue(copyBoard, "Player", currentSum, false);
//          if (score < currentSum) currentSum = score;
//        }
//      }
//    }
//    return currentSum;
//  }
    
//  private void simulatePlayer (Board b) {
//    LinkedList<Move> moves = b.getAvailableMoves();
//    if (!moves.isEmpty()) {
//      Move m = moves.remove();
//      b.addMark(m.getFirst(), m.getSecond(), true);
//    }
//  }
//  
//  private void simulateAI (Board b) {
//    LinkedList<Move> moves = b.getAvailableMoves();
//    if (!moves.isEmpty()) {
//      Move m = moves.remove();
//      b.addMark(m.getFirst(), m.getSecond(), false);
//    }
//  }
  
  
  public Move getNextMove (Board currentBoard) {
    System.out.println(getMMMove (currentBoard));
    return getMMMove (currentBoard);
    
//    Hashtable<Move, Integer> moveSums = new Hashtable<Move, Integer>();
//    LinkedList<Move> moves = currentBoard.getAvailableMoves();
//    while (!moves.isEmpty()) {
//      Move move = moves.remove();
//      moveSums.put(move, calculateSum(currentBoard, move));
//    }
//    System.out.println(moveSums);
//    return findMaxMove(moveSums);
  }
  
//  private Move findMaxMove (Hashtable<Move,Integer> moves) {
//    int maxVal = -100;
//    Move maxMove = null;
//    for (Map.Entry<Move,Integer> entry : moves.entrySet()) {
//      if (entry.getValue() > maxVal) {
//        maxVal = entry.getValue();
//        maxMove = entry.getKey();
//      }
//    }
//    return maxMove;
//  }
  
  // Return +100 for each 3-in-a-row
  // Return +10 for each 2-in-a-row-with-one-empty-cell
  // Return +1 for each 1-with-two-empty-cells
  // Return -(value) for player (following rules above)?
  // Return 0 otherwise (empty lines or lines with both computer and player)
  private int calculateSum (Board board) {
    int sum = 0;
//    board.addMark(proposedMove.getFirst(), proposedMove.getSecond(), false);
    int[][] b = board.getBoard();
    
    //do something
    sum += calculateSumForPlayer(b, 2);
    sum -= calculateSumForPlayer(b, 1);
    
//    board.removeMark(proposedMove.getFirst(), proposedMove.getSecond());
    return sum;
  }
  
  private int calculateSumForPlayer (int[][] b, int x) {
    int sum = 0;
    
    // Check diagonals
    if ((b[0][0]==x) && (b[1][1]==x) && (b[2][2]==x)) {
      sum += 100;
//      isGameOver = true;
    } else if ((b[0][2]==x) && (b[1][1]==x) && (b[2][0]==x)) {
      sum += 100;
//      isGameOver = true;
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
        sum += 100;
//        isGameOver = true;
      } if ((b[0][i]==x) && (b[1][i]==x) && (b[2][i]==x)) {
        sum += 100;
//        isGameOver = true;
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
}