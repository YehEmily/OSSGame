/**
 * AIPlayerObject class: Abstract class that all Battleship AIPlayers extend.
 * Contains several absolutely necessary methods...
 * getNextShot(Board b): Gets next move based on board. (Note that AIPlayerRandomShots doesn't really need the parameter...)
 * 
 */

public abstract class AIPlayerObject extends Player {
  
  public AIPlayerObject (String name) {
    super(name);
  }
  
  abstract String getNextShot(Board b);
  
}