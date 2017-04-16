import java.util.*;
import javax.swing.JOptionPane;

public class GoFish {
  
  private Player user;
  private AIPlayer computer;
  private Stack<Card> deck;
  
  public GoFish () {
    String name = JOptionPane.showInputDialog("What is your name?");
    
    user = new Player();
    if (name.length() > 0) user = new Player(name);
    
    computer = new AIPlayer("Computer");
    deck = new Deck().getDeck();
    
    drawCard(5, user); drawCard(5, computer);
    
    String introduction = "<html>The objective of this game is to accumulate " +
      "points by matching up cards according to their values.<br/>Each round, " +
      "each player can ask the other if they have any of a certain kind of " +
      "card.<br/>For example, you can ask, 'got any... 8s?' If the other player " +
      "has any cards that match, they must give them up.<br/>Otherwise, they " +
      "will tell you to 'go fish' and you must draw a card from the deck.<br/>" +
      "The game ends when there are no more cards left in play.<br/>" +
      "Good luck and have fun!</html>";
    
    JOptionPane.showMessageDialog(null, introduction);
  }
  
  public void drawCard (int draws, Player player) {
    for (int i = 0; i < draws; ++i) {
      player.addCard(deck.pop());
    }
  }
  
  public boolean isMatch (Card a, Card b) {
    return (a.equals(b));
  }
  
  public void removeMatch (Player player, Card a, Card b) {
    player.addPoints(1);
    player.getHand().remove(a);
    player.getHand().remove(b);
    
    LinkedList<Card> computerMemory = computer.getMemoryBank();
    if (computerMemory.contains(a)) {
      computerMemory.remove(a);
    }
  }
  
  public int[] findMatches () {
    for (int i = 0; i < computer.getHand().size(); ++i) {
      for (int j = computer.getHand().size()-1; j > i; --j) {
        if (isMatch (computer.getHand().get(i), computer.getHand().get(j))) {
          int[] match = {i, j};
          return match;
        }
      }
    }
    int[] match = {-1, -1};
    return match;
  }
  
  public boolean hasCardAndTransferred (Player player, Card c) {
    LinkedList<Card> matches = new LinkedList<Card>();
    for (int i = 0; i < player.getHand().size(); ++i) {
      if (player.getHand().get(i).equals(c)) {
        matches.add(player.getHand().get(i));
      }
    }
    if (matches.size() > 0) {
      transferCards(player, matches);
      return true;
    }
    return false;
  }
  
  public void transferCards (Player player, LinkedList<Card> cards) {
    while (!cards.isEmpty()) {
      Card card = cards.remove();
      player.getHand().remove(card);
      getOtherPlayer(player).addCard(card);
    }
  }
  
  public Card computerQuery () {
    // check if memorybank contains cards
    // if so, choose card
    // if not, choose random
    
    LinkedList<Card> bank = computer.getMemoryBank();
    LinkedList<Card> hand = computer.getHand();
    for (int i = 0; i < hand.size(); ++i) {
      if (bank.contains(hand.get(i))) {
        return hand.get(i);
      }
    }
    
    Random rand = new Random();
    try {
      int index = rand.nextInt(computer.getHand().size());
      return computer.getHand().get(index);
    } catch (IllegalArgumentException ex) {
      return null;
    }
  }
  
  public LinkedList<Card> getPlayerHand () {
    return user.getHand();
  }
  
  public LinkedList<Card> getComputerHand () {
    return computer.getHand();
  }
  
  public Player getPlayer () {
    return user;
  }
  
  public AIPlayer getComputer () {
    return computer;
  }
  
  public Player getOtherPlayer (Player p) {
    if (p.equals(user)) {
      return computer;
    }
    return user;
  }
  
  public int cardsRemaining () {
    return deck.size();
  }
  
  public boolean isGameOver () {
    return ((deck.size() == 0) && (user.getHand().size() == 0) && (computer.getHand().size() == 0));
  }
  
  public Player getWinner () {
    if (user.getPoints() > computer.getPoints()) return user;
    return computer;
  }
}