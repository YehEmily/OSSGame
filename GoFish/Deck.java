import java.util.*;

public class Deck {
  
  private final int DECK_SIZE;
  private Card[] cards;
  private Stack<Card> deck;
  
  public Deck () {
    DECK_SIZE = 52;
    cards = new Card[DECK_SIZE];
    fillCards();
    shuffleCards();
    
    deck = new Stack<Card>();
    fillDeck();
  }
  
  public Stack<Card> getDeck () {
    return deck;
  }
  
  public Card drawCard () {
    return deck.pop();
  }
  
  private void fillCards () {
    Card card;
    
    for (int i = 0; i < 13; ++i) {
      card = new Card ("hearts", i+1);
      cards[i] = card;
    }
    
    for (int i = 13; i < 26; ++i) {
      card = new Card ("diamonds", i-12);
      cards[i] = card;
    }
    
    for (int i = 26; i < 39; ++i) {
      card = new Card ("spades", i-25);
      cards[i] = card;
    }
    
    for (int i = 39; i < 52; ++i) {
      card = new Card ("clovers", i-38);
      cards[i] = card;
    }
  }
  
  private void shuffleCards () {
    int index;
    Random random = new Random();
    
    for (int i = 0; i < DECK_SIZE; ++i) {
      index = random.nextInt(52);
      Card storage = cards[i];
      cards[i] = cards[index]; cards[index] = storage;
    }
  }
  
  private void fillDeck () {
    for (int i = 0; i < DECK_SIZE; ++i) {
      deck.add(cards[i]);
    }
  }
  
  public String toString () {
    String s = "";
    for (int i = 0; i < DECK_SIZE; ++i) {
      s += cards[i] + "\n";
    }
    return s;
  }
}