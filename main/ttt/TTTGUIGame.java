package ttt;

import java.util.*;

public class TTTGUIGame {

  private Player user;
  private AIPlayer computer;
  private boolean isGameOver, isUserTurn;
  private Board board;
  private String winner;
  
  /**
   * Constructor
   */
  public TTTGUIGame () {
    board = new Board();
    this.user = new Player("Player 1");
    this.computer = new AIPlayer();
    user.setTurn(true); // User goes first
    isUserTurn = true;
    isGameOver = false;
    winner = "";
  }
  
  public boolean isUserTurn () {
    return isUserTurn;
  }
  
  /**
   * userTurn method: Places the user's mark as passed in through the GUI.
   * 
   * @param   coordinate of mark
   */
  public void userTurn (String coord) {
    try {
      Move m = convertMove(coord);
      placeMark(m);
      user.setTurn(false);
      isUserTurn = false;
      computer.setTurn(true);
    } catch (NullPointerException ex) {
      System.out.println("Looks like the game is over?");
      System.out.println(ex);
      isGameOver = true;
    }
  }
  
  /**
   * computerTurn method: Performs actions necessary for computer to place mark
   */
  public int[] computerTurn () {
    int[] res = new int[2];
    try {
      Move nextMove = computer.getNextMove(board);
      res[0] = nextMove.getFirst(); res[1] = nextMove.getSecond();
      isGameOver = ((res[0] == -1) || (res[1] == -1));
      if (!isGameOver) {
        placeMark(nextMove);
        computer.setTurn(false);
        user.setTurn(true);
        isUserTurn = true;
      }
    } catch (NullPointerException ex) {
      System.out.println("Looks like the game is over?");
      System.out.println(ex);
      isGameOver = true;
    }
    return res;
  }
  
  /**
   * convertMove method: Converts a string coordinate into a Move object
   * 
   * @param   coordinate in form of string
   * @return  coordinate in form of move
   */
  public static Move convertMove (String coordinate) {
    String[] coordinates = coordinate.split(",");
    Move m = new Move (Integer.parseInt(coordinates[0]),
                       Integer.parseInt(coordinates[1]));
    return m;
  }
  
  /**
   * chackGameOver method: Checks conditions for endgame state
   */
  public boolean isGameOver () {
    try {
      for (int i = 0; i < 3; ++i) {
        if ((board.getBoard()[i][0]!=0) &&
            (board.getBoard()[i][0]==board.getBoard()[i][1]) &&
            (board.getBoard()[i][1]==board.getBoard()[i][2])) {
          setWinner(board.getBoard()[i][0]);
          return true;
        } else if ((board.getBoard()[0][i]!=0) &&
                   (board.getBoard()[0][i]==board.getBoard()[1][i]) &&
                   (board.getBoard()[1][i]==board.getBoard()[2][i])) {
          setWinner(board.getBoard()[0][i]);
          return true;
        }
      }
      if ((board.getBoard()[0][0]!=0) && 
          (board.getBoard()[0][0]==board.getBoard()[1][1]) &&
          (board.getBoard()[1][1]==board.getBoard()[2][2])) {
        setWinner(board.getBoard()[0][0]);
        return true;
      } else if ((board.getBoard()[2][0]!=0) &&
                 (board.getBoard()[2][0]==board.getBoard()[1][1]) &&
                 (board.getBoard()[1][1]==board.getBoard()[0][2])) {
        setWinner(board.getBoard()[2][0]);
        return true;
      }
      return false;
    } catch (NullPointerException ex) {
      return false;
    }
  }
  
  /**
   * placeMark method: Places a move made by a given player
   * 
   * @param   move to be made
   * @param   player making move
   */
  private void placeMark (Move move) {
    int coordinate1 = move.getFirst();
    int coordinate2 = move.getSecond();
    if (isUserTurn) board.addMark(coordinate1, coordinate2, true);
    else board.addMark(coordinate1, coordinate2, false);
  }
  
  /**
   * setWinner method: Sets the winner of a given game
   */
  private void setWinner (int i) {
    if (i==1) {
      winner = user.getName();
    } else {
      winner = "Computer";
    }
  }
}