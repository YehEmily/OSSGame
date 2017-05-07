package main;

public class Pet {
  
  private int age, cleanliness, fullness;
  
  public Pet () {
    age = 0;
    cleanliness = 10;
    fullness = 10;
  }
  
  public void updateStats () {
    feed(-1);
    clean(-1);
  }
  
  public int getAge () {
    return age;
  }
  
  public void ageUp (int n) {
    age += n;
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
}