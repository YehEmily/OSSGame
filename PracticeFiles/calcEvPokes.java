import javax.swing.*;

/**
 * Practice and brushing up on Java Swing components and capabilities.
 * This tool calculates the number of Pokemon that can be evolved at 
 * once, and provides information about whether this number of Pokemon
 * can be evolved in a given time limit (given 30 minutes per Lucky Egg).
 */
public class calcEvPokes {
  
  private boolean done;
  private int total_pokes;
  
  /**
   * Constructor
   */
  public calcEvPokes () {
    done = false;
    total_pokes = 0;
    gatherInfo();
  }
  
  /**
   * Gathers information about type of Pokemon to be evolved, number of
   * candies (evolution fodder) available, and number of candies necessary
   * per evolution. Returns a list of possible number of Pokemon that can
   * be evolved, as well as whether this number is feasible in the time limit
   * of 30 minutes.
   */
  public void gatherInfo () {
    String message = "You will be able to evolve...\n";
    while (!done) {
      String pokemon = query("What are you evolving?");
      int n = Integer.parseInt(query("How many " + pokemon +
                                     " candies do you have?"));
      int m = Integer.parseInt(query("How many candies does it take to " +
                                     "evolve a " + pokemon + "?"));
      int evos = calcEvols (n, m);
      total_pokes += evos;
      message += evos + " " + pokemon + "s\n";
      int c = JOptionPane.showConfirmDialog (null, "Will that be all the" +
                                             " Pokemon you are evolving?",
                                             "Confirmation",
                                             JOptionPane.YES_NO_CANCEL_OPTION);
      if (c == JOptionPane.YES_OPTION){
        done = true;
      }
    }
    message += "You can evolve " + total_pokes + " Pokemon in total.\nThat's ";
    if (total_pokes > 70) {
      message += "more Pokemon than ";
    } else if (total_pokes < 60) {
      message += "fewer Pokemon than ";
    } else {
      message += "about the right number of Pokemon ";
    }
    
    message += "you can fit in a Lucky Egg session (30 minutes).\n";
    message += "In fact, you can fit " + total_pokes + " evolutions in " + 
      calcTime(total_pokes) + ".";
    JOptionPane.showConfirmDialog(null, message, "Success",
                                  JOptionPane.CLOSED_OPTION);
  }
  
  /**
   * Calculates the amount of time that a given number of Pokemon will take
   * to evolve.
   * 
   * @param   number of pokemon
   * @return  total time as a string
   */
  public String calcTime (int p) {
    int time = p * 30;
    int minutes = time/60;
    int seconds = time%60;
    return minutes + " minutes and " + seconds + " seconds";
  }
  
  /**
   * Calculates the number of evolutions of a given Pokemon can be performed,
   * based on the number of candies that a player has.
   * 
   * @param   number of candies
   * @param   number of candies used per evolution
   * @return  number of evolutions possible
   */
  public int calcEvols (int n, int m) {
    if (n < m) return 0;
    int r = n % m; // Remainder
    int num_evos = n / m; // Base number of evolutions
    int additional_candies = num_evos * 2; // +1 candy/evo, +1 candy/transfer
    int residue = r + additional_candies; // Total residual candy
    int additional_evos = calcEvols (residue, m); // Recursion!!! So fancy
    return num_evos + additional_evos;
  }
  
  /**
   * Asks the user for input.
   * 
   * @param   question to be asked
   * @return  user's answer to question
   */
  public String query (String question) {
    JFrame frame = new JFrame("Query");
    String answer = JOptionPane.showInputDialog(frame, question);
    return answer;
  }
  
  
  public static void main (String[] args) {
    new calcEvPokes();
  }
  
}