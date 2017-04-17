package ttt;

import java.util.*;

public class AIPlayer extends Player {
  
  private Move bestOffMove;
  private Move bestDefMove;
  private float bestOffScore;
  private float bestDefScore;
  
  /**
   * AIPlayer constructor (default)
   */
  public AIPlayer () {
    super("Computer");
    bestOffMove = new Move(-1, -1);
    bestDefMove = new Move(-1, -1);
    bestDefScore = 0;
    bestOffScore = 0;
  }
  
  /**
   * AIPlayer constructor
   * @param  name of AIPlayer
   */
  public AIPlayer(String name) {
    super(name);
    bestOffMove = new Move(-1, -1);
    bestDefMove = new Move(-1, -1);
    bestDefScore = 0;
    bestOffScore = 0;
  }
  
  /**
   * getNextMove: Returns the best next move, according to the minimax algorithm.
   * 
   * @param   current gameboard
   * @return  next move that minimizes maximum loss
   */
  public Move getNextMove (Board board) {
    findMMValue(board, "Computer", 1);
    if ((-1 * bestDefScore) > bestOffScore) { // if defensive score > offensive
      Move finalMove = bestDefMove;
      bestOffMove = new Move(-1, -1); // reset offensive move
      bestDefMove = new Move(-1, -1); // reset defensive move
      bestDefScore = 0; // reset defensive score
      bestOffScore = 0; // reset offensive score
      return finalMove;
    } else { // if offensive score > defensive
      Move finalMove = bestOffMove;
      bestOffMove = new Move(-1, -1); // reset offensive move
      bestDefMove = new Move(-1, -1); // reset defensive move
      bestDefScore = 0; // reset defensive score
      bestOffScore = 0; // reset offensive score
      return finalMove;
    }
  }
  
  /**
   * findMMValue: Recursively calculates the values of moves in order to find the best one,
   * according to the minimax algorithm.
   * 
   * @param   current gameboard
   * @param   name of current player
   * @param   current depth of search tree
   * @return  float value of the best score on current branch of search tree
   */
  public float findMMValue (Board b, String currentPlayer, int depth) {
    LinkedList<Move> moves = b.getAvailableMoves(); // Retrieve available moves on board
    float currentScore = 0; // Initialize current score
    float bestScore = 0; // Initialize best score
    
    if (moves.isEmpty() || isGameOver(b) || depth > 2) { // Check terminal conditions
      bestScore = calculateSum(b)/depth; // If conditions met, calculate score of current board
      
    } else {
      while (!moves.isEmpty()) {
        Move m = moves.remove(); // Retrieve first possible move
        
        if ("Computer".equals(currentPlayer)) { // Computer's turn
          b.addMark(m.getFirst(), m.getSecond(), false); // Temporarily add mark to board
          currentScore = findMMValue(b, "Player", depth+1); // Recurse
          if (currentScore > bestOffScore) {
            bestOffScore = currentScore;
            bestOffMove = m;
            bestScore = currentScore;
          }
          b.removeMark(m.getFirst(), m.getSecond()); // Clean board
          
        } else { // Player's turn
          b.addMark(m.getFirst(), m.getSecond(), true); // Temporarily add mark to board
          currentScore = findMMValue(b, "Computer", depth+1); // Recurse
          if (currentScore < bestDefScore) {
            bestDefScore = currentScore;
            bestDefMove = m;
            bestScore = currentScore;
          }
          b.removeMark(m.getFirst(), m.getSecond()); // Clean board
        }
      }
    }
    return bestScore;
  }
  
  /**
   * calculateSum: Returns score value of board from computer's perspective
   * 
   * @param   current gameboard
   * @return  score value of board
   */
  private int calculateSum (Board board) {
    int sum = 0;
    int[][] b = board.getBoard();
    sum += calculateSumForPlayer(b, 2);
    sum -= calculateSumForPlayer(b, 1);
    return sum;
  }
  
  /**
   * calculateSumForPlayer: Returns score value of board from a given player's perspective,
   * according to scoring rules:
   * +100 for each 3-in-a-row
   * +10 for each 2-in-a-row-with-one-empty-cell
   * +1 for each 1-with-two-empty-cells
   * 0 otherwise (empty lines or lines with both computer and player)
   * 
   * @param   current gameboard
   * @param   current player, where computer=2 and player=1
   * @return  score value for computer
   */
  private int calculateSumForPlayer (int[][] b, int x) {
    int sum = 0;
    
    // Check diagonals
    if ((b[0][0]==x) && (b[1][1]==x) && (b[2][2]==x)) {
      sum += 100;
    } else if ((b[0][2]==x) && (b[1][1]==x) && (b[2][0]==x)) {
      sum += 100;
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
    
    // Check individual rows and columns
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
  
  /**
   * isGameOver: Checks gameboard to see if the game has ended
   * 
   * @param   current gameboard
   * @return  boolean value of whether game is over or not
   */
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