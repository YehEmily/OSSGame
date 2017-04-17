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
  
  
//    private JPanel cards; //a panel that uses CardLayout
//    final static String BUTTONPANEL = "Card with JButtons";
//    final static String TEXTPANEL = "Card with JTextField";
//    
//    public void addComponentToPane(Container pane) {
//        //Put the JComboBox in a JPanel to get a nicer look.
//        JPanel comboBoxPane = new JPanel(); //use FlowLayout
//        String comboBoxItems[] = { BUTTONPANEL, TEXTPANEL, TEXTPANEL };
//        JComboBox cb = new JComboBox(comboBoxItems);
//        cb.setEditable(false);
//        cb.addItemListener(this);
//        comboBoxPane.add(cb);
//        
//        //Create the "cards".
//        JPanel card1 = new JPanel();
//        card1.add(new JButton("Button 1"));
//        card1.add(new JButton("Button 2"));
//        card1.add(new JButton("Button 3"));
//        
//        JPanel card2 = new JPanel();
//        card2.add(new JTextField("TextField", 20));
//        
//        //Create the panel that contains the "cards".
//        cards = new JPanel(new CardLayout());
//        cards.add(card1, BUTTONPANEL);
//        cards.add(card2, TEXTPANEL);
//        
//        pane.add(comboBoxPane, BorderLayout.PAGE_START);
//        pane.add(cards, BorderLayout.CENTER);
//    }
//    
//    public void itemStateChanged(ItemEvent evt) {
//        CardLayout cl = (CardLayout)(cards.getLayout());
//        cl.show(cards, (String)evt.getItem());
//    }
//    
//    /**
//     * Create the GUI and show it.  For thread safety,
//     * this method should be invoked from the
//     * event dispatch thread.
//     */
//    private static void createAndShowGUI() {
//        //Create and set up the window.
//        JFrame frame = new JFrame("CardLayoutDemo");
//        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        
//        //Create and set up the content pane.
//        Game demo = new Game();
//        demo.addComponentToPane(frame.getContentPane());
//        
//        //Display the window.
//        frame.pack();
//        frame.setVisible(true);
//    }
//    
//    public static void main(String[] args) {
//        /* Use an appropriate Look and Feel */
//        try {
//            //UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
//            UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");
//        } catch (UnsupportedLookAndFeelException ex) {
//            ex.printStackTrace();
//        } catch (IllegalAccessException ex) {
//            ex.printStackTrace();
//        } catch (InstantiationException ex) {
//            ex.printStackTrace();
//        } catch (ClassNotFoundException ex) {
//            ex.printStackTrace();
//        }
//        /* Turn off metal's use of bold fonts */
//        UIManager.put("swing.boldMetal", Boolean.FALSE);
//        
//        //Schedule a job for the event dispatch thread:
//        //creating and showing this application's GUI.
//        javax.swing.SwingUtilities.invokeLater(new Runnable() {
//            public void run() {
//                createAndShowGUI();
//            }
//        });
//    }
}