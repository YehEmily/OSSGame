package GoFish;

import java.util.*;

public class AIPlayer extends Player {
  
  private LinkedList<Card> memoryBank;
  
  public AIPlayer (String name) {
    super(name);
    memoryBank = new LinkedList<Card>();
  }
  
  public AIPlayer () {
    super("Computer");
    memoryBank = new LinkedList<Card>();
  }
  
  public LinkedList<Card> getMemoryBank() {
    return memoryBank;
  }
  
  public void addToMemoryBank (Card c) {
    memoryBank.add(c);
  }
}