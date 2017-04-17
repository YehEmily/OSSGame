package GoFish;

import java.util.*;

public class Player {
  
  private String name;
  private int points;
  private LinkedList<Card> hand;
  
  public Player (String name) {
    this.name = name;
    this.points = 0;
    this.hand = new LinkedList<Card>();
  }
  
  public Player () {
    this.name = "No Name";
    this.points = 0;
    this.hand = new LinkedList<Card>();
  }
  
  public boolean hasCard (Card card) {
    return hand.contains(card);
  }
  
  public String getName () {
    return name;
  }
  
  public void setName (String name) {
    this.name = name;
  }
  
  public LinkedList<Card> getHand () {
    return hand;
  }
  
  public void addCard (Card card) {
    hand.add(card);
  }
  
  public int getPoints () {
    return points;
  }
  
  public void addPoints (int n) {
    points += n;
  }
  
  public boolean equals (Player other) {
    return (this.name == other.name);
  }
  
  public String toString () {
    String s = name + " has " + points + " points.";
    return s;
  }
}