package main;

public class Pet {
  
  private String name;
  private int age, cleanliness, fullness;
  private boolean isAdult;
  private long currentTime;
  private long elapsedTime;
  
  public Pet () {
    name = "Spot";
    age = 0;
    cleanliness = 10;
    fullness = 10;
    currentTime = System.nanoTime();
    elapsedTime = 0;
  }
  
  public void updateStats () {
    feed(-1);
    clean(-1);
    System.out.println("Fullness: " + fullness);
    System.out.println("Cleanliness: " + cleanliness);
  }
  
  public Pet (String name) {
    this.name = name;
    age = 0;
    cleanliness = 10;
    fullness = 10;
  }
  
  public int getAge () {
    return age;
  }
  
  public void ageUp () {
    age++;
    if (age >= 10) isAdult = true;
  }
  
  public int getCleanliness () {
    return cleanliness;
  }
  
  public void clean (int inc) {
    cleanliness += inc;
  }
  
  public int getFullness () {
    return fullness;
  }
  
  public void feed (int inc) {
    fullness += inc;
  }
  
  public boolean isAdult () {
    return isAdult;
  }
}