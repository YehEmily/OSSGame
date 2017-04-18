package main;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Game implements ItemListener {

  private JPanel menu;
  private final JButton NEW_GAME, LOAD_GAME, EXIT;
  
  public Game () {
    menu = new JPanel(new CardLayout());
    NEW_GAME = new JButton("New Game");
    LOAD_GAME = new JButton("Load Game");
    EXIT = new JButton("Exit");
  }
  
  public void addComponentToPane (Container pane) {
    JPanel options = new JPanel();
    
    options.add(NEW_GAME);
    options.add(LOAD_GAME);
    options.add(EXIT);
    
    options.setPreferredSize(new Dimension(400,800));
    
    menu.add(options, "TESTING");
    pane.add(menu, BorderLayout.CENTER);
  }
  
  public void itemStateChanged (ItemEvent evt) {
    CardLayout cl = (CardLayout)(menu.getLayout());
    cl.show(menu, (String)evt.getItem());
  }
  
  private static void createAndShowGUI () {
    JFrame frame = new JFrame("Welcome!");
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    Game game = new Game();
    game.addComponentToPane(frame.getContentPane());
    
    frame.pack();
    frame.setVisible(true);
  }
  
  public static void main (String[] args) {
    try {
      //UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
      UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");
    } catch (UnsupportedLookAndFeelException ex) {
      ex.printStackTrace();
    } catch (IllegalAccessException ex) {
      ex.printStackTrace();
    } catch (InstantiationException ex) {
      ex.printStackTrace();
    } catch (ClassNotFoundException ex) {
      ex.printStackTrace();
    }
    /* Turn off metal's use of bold fonts */
    UIManager.put("swing.boldMetal", Boolean.FALSE);
    
    //Schedule a job for the event dispatch thread:
    //creating and showing this application's GUI.
    javax.swing.SwingUtilities.invokeLater(new Runnable() {
      public void run() {
        createAndShowGUI();
      }
    });
  }
}