package main;

public class Pet {
  
  private String name;
  private int age, cleanliness, fullness;
  private boolean isAdult;
  
  public Pet () {
    name = "Spot";
    age = 0;
    cleanliness = 10;
    fullness = 10;
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
  
  public void setCleanliness (int inc) {
    cleanliness += inc;
  }
  
  public int getFullness () {
    return fullness;
  }
  
  public void setFullness (int inc) {
    fullness += inc;
  }
  
  public boolean isAdult () {
    return isAdult;
  }
}