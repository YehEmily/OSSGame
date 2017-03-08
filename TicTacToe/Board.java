import java.util.*;

public class Board {
  
  private int[][] board;
  
  public Board () {
    board = new int[3][3];
  }
  
  public Board(int[][] board) {
    this.board = board;
  }
  
  public void addMark (int a, int b, boolean isPlayerTurn) {
    if (isPlayerTurn) {
      board[a][b] = 1;
    } else {
      board[a][b] = 2;
    }
  }
  
  public void removeMark (int a, int b) {
    board[a][b] = 0;
  }
  
  public int[][] getBoard () {
    return board;
  }
  
  public int[][] getCopyBoard() {
    int[][] copy = new int[3][3];
    for (int i = 0; i < 3; ++i) {
      for (int j = 0; j < 3; ++j) {
        copy[i][j] = board[i][j];
      }
    }
    return copy;
  }
  
  public LinkedList<Move> getAvailableMoves () {
    LinkedList<Move> moves = new LinkedList<Move>();
    for (int i = 0; i < 3; ++i) {
      for (int j = 0; j < 3; ++j) {
        if (board[i][j]==0) {
          moves.add(new Move(i, j));
        }
        if (board[j][i]==0) {
          moves.add(new Move(j, i));
        }
      }
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
  
  public static void main(String[] args) {
    Board board = new Board();
    System.out.println(board);
  }
}