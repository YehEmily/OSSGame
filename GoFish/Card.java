public class Card {
  
  private String type;
  private int number;
  
  public Card (String type, int number) {
    this.type = type;
    this.number = number;
  }
  
  public String getType () {
    return type;
  }
  
  public int getNumber () {
    return number;
  }
  
  public boolean equals (Card other) {
    return (this.number == other.number);
  }
  
  public String toString () {
    String s = type + " " + number;
    return s;
  }
}