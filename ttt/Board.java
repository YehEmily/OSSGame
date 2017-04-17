package ttt;

import java.util.*;

public class Board {
  
  private int[][] board;
  
  /**
   * Board constructor (default)
   */
  public Board () {
    board = new int[3][3];
  }
  
  /**
   * Board constructor
   * @param  current state of board
   */
  public Board(int[][] board) {
    this.board = board;
  }
  
  /**
   * addMark: Adds a mark to the board
   * 
   * @param  the mark's first coordinate
   * @param  the mark's second coordinate
   * @param  whether it's the player's turn or not
   */
  public void addMark (int a, int b, boolean isPlayerTurn) {
    if (isPlayerTurn) {
      board[a][b] = 1;
    } else {
      board[a][b] = 2;
    }
  }
  
  /**
   * removeMark: Removes a mark from the board
   * 
   * @param  the mark's first coordinate
   * @param  the mark's second coordinate
   */
  public void removeMark (int a, int b) {
    board[a][b] = 0;
  }
  
  /**
   * getBoard: Public getter method for board
   */
  public int[][] getBoard () {
    return board;
  }
  
  /**
   * getAvailableMoves: Returns all the available moves on the board
   */
  public LinkedList<Move> getAvailableMoves () {
    LinkedList<Move> moves = new LinkedList<Move>();
    for (int i = 0; i < 3; ++i) {
      for (int j = 0; j < 3; ++j) {
        if (board[i][j]==0) {
          if (i != j) { // Don't add doubles yet
            moves.add(new Move(i, j));
          }
        }
        if (board[j][i]==0) {
          if (i != j) { // Don't add doubles yet
            moves.add(new Move(j, i));
          }
        }
      }
    }
    
    if (board[0][0] == 0) {
      moves.add(new Move(0,0));
    }
    if (board[1][1] == 0) {
      moves.add(new Move(1,1));
    }
    if (board[2][2] == 0) {
      moves.add(new Move(2,2));
    }
    
    return moves;
  }
  
  public String toString () {
    String s = "  1 2 3\n";
    for (int i = 0; i < 3; ++i) {
      s += (i+1) + " ";
      for (int j = 0; j < 3; ++j) {
        if (board[i][j] == 0) {
          s += "  ";
        } else if (board[i][j] == 1) { // player is 1; X
          s += "X ";
        } else if (board[i][j] == 2) { // computer is 2; O
          s += "O ";
        } else {
          s += board[i][j] + " ";
        }
      }
      s += "\n";
    }
    return s.substring(0, s.length()-2);
  }
}