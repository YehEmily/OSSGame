public abstract class AIPlayerObject extends Player {
  
  public AIPlayerObject (String name) {
    super(name);
  }
  
  abstract String getNextShot();
  
}