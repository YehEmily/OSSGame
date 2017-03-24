import java.util.*;

// I am not at all sure what I'm doing wrong, or where the bug might be
// However, it has been 8 hours and I haven't made any progress
// So I think I will stop for now :(

// Learn about Java enumerators and iterators

public class TicTacToeGame {
  Player user;
  AIPlayer computer;
  boolean isGameOver;
  Board board;
  String winner;
  
  public TicTacToeGame () {
    board = new Board();
    System.out.println("What is your name?");
    this.user = new Player(request());
    this.computer = new AIPlayer();
    user.setTurn(true);
    isGameOver = false;
    winner = "";
    play();
  }
  
  private void play () {
    while (!isGameOver) {
      if (user.isTurn()) {
        userTurn();
      }
      
      System.out.println(toString());
      if (checkGameOver()) break;
      
      if (computer.isTurn()) {
        computerTurn();
      }
      
      System.out.println(toString());
      if (checkGameOver()) break;
    }
    if (!winner.equals("")) {
      System.out.println("Congratulations! " + winner + " has won!");
    } else {
      System.out.println("No more moves remaining. It's a draw!");
    }
  }
  
  private void userTurn () {
    System.out.println("It's " + user.getName() + "'s turn.");
    System.out.println("Enter a coordinate in the form X,X to mark it.");
    try {
      String coordinate = request();
      if (coordinate.length() != 3) {
        System.out.println("Invalid coordinate; try again.");
      } else {
        placeMark(convertMove(coordinate), user);
        user.setTurn(false);
        computer.setTurn(true);
      }
    } catch (NullPointerException ex) {
      isGameOver = true;
    }
  }
  
  private void computerTurn () {
    System.out.println("It's Computer's turn.");
//    try {
      Move nextMove = computer.getNextMove(board);
//      System.out.println(nextMove);
      placeMark(nextMove, computer);
      computer.setTurn(false);
      user.setTurn(true);
//    } catch (NullPointerException ex) {
//      System.out.println("Null pointer exception caught");
//      System.out.println("Poo");
//      isGameOver = true;
//    }
  }
  
  private Move convertMove (String coordinate) {
    String[] coordinates = coordinate.split(",");
    Move m = new Move (Integer.parseInt(coordinates[0])-1,
                       Integer.parseInt(coordinates[1])-1);
    return m;
  }
  
  private String request () {
    Scanner scan = new Scanner (System.in);
    try {
      String s = scan.next();
      scan.close();
      return s;
    } catch (NoSuchElementException ex) {
      System.out.println("Terminating program.");
      return null;
    }
  }
  
  private void placeMark (Move move, Player player) {
    int coordinate1 = move.getFirst();
    int coordinate2 = move.getSecond();
    if (player.equals(user)) board.addMark(coordinate1, coordinate2, true);
    else board.addMark(coordinate1, coordinate2, false);
  }
  
  private boolean checkGameOver () {
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
  
  private void setWinner (int i) {
    if (i==1) {
      user.incScore();
      winner = user.getName();
    } else {
      computer.incScore();
      winner = "Computer";
    }
  }
  
  public String toString () {
    return board.toString();
  }
  
  public static void main (String[] args) {
    new TicTacToeGame();
  }
}