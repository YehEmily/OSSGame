package main;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import battleship.BSGUIGame;
//import gofish.GoFishPanel;
import ttt.TTTGUIGame;

public class MainGame implements ItemListener {
  
  private JButton feed, wash, exit, save, chooseTTT, chooseBS, chooseGF;
  private JButton[][] tttButtons, bsButtonsAI, bsButtonsPlayer;
  private JPanel panel;
  private JLabel instructions;
  
  final static String GOFISH = "Play Go Fish!";
  final static String TICTACTOE = "Play Tic-Tac-Toe!";
  final static String BATTLESHIP = "Play Battleship!";
  final static String NONE = "--";
  
  private TTTGUIGame ttt;
  private BSGUIGame bs;
  private Pet oliner;
  
  public MainGame() {
    String intro = intro();
    instructions = new JLabel(intro);
    feed = new JButton("Feed");
    wash = new JButton("Wash");
    exit = new JButton("Exit");
    save = new JButton("Save");
    initBL(feed); initBL(wash); initBL(exit); initBL(save);
    
    ttt = new TTTGUIGame();
    bs = new BSGUIGame();
    oliner = new Pet();
  }
  
  public void addComponentToPane (Container pane) {
    JPanel comboPane = new JPanel();
    String comboBoxItems[] = { NONE, GOFISH, TICTACTOE, BATTLESHIP };
    JComboBox cb = new JComboBox(comboBoxItems);
    cb.setEditable(false);
    cb.addItemListener(this);
    comboPane.add(cb);
    
    JPanel intro = new JPanel();
    intro.add(instructions);
    
    JPanel options = new JPanel();
    options.add(feed);
    options.add(wash);
    options.add(save);
    options.add(exit);
    
    JPanel menu = new JPanel(new BorderLayout());
    menu.add(intro, BorderLayout.NORTH);
    menu.add(options, BorderLayout.SOUTH);
    
    JPanel placeholder = new JPanel();
    placeholder.add(new JButton("Select a game from the menu!"));
    
    JPanel gfOption = new JPanel();
    chooseGF = new JButton("Yes! " + GOFISH);
    initBL(chooseGF);
    gfOption.add(chooseGF);
    
    JPanel tttOption = createTicTacToeGUI();
    
    JPanel bsOption = createBattleshipGUI();
    
    panel = new JPanel(new CardLayout());
    panel.add(placeholder, NONE);
    panel.add(tttOption, TICTACTOE);
    panel.add(bsOption, BATTLESHIP);
    panel.add(gfOption, GOFISH);
    
    pane.add(options, BorderLayout.PAGE_START);
    pane.add(comboPane, BorderLayout.CENTER);
    pane.add(panel, BorderLayout.SOUTH);
  }
  
  public void itemStateChanged (ItemEvent evt) {
    CardLayout cl = (CardLayout)(panel.getLayout());
    cl.show(panel, (String)evt.getItem());
  }
  
  private static void createAndShowGUI () {
    JFrame frame = new JFrame("Welcome!");
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    MainGame game = new MainGame();
    game.addComponentToPane(frame.getContentPane());
    frame.pack();
    frame.setVisible(true);
  }
  
  private JPanel createBattleshipGUI () {
    JPanel bsGUI = new JPanel(new BorderLayout());
    
    bsButtonsAI = new JButton[10][10];
    bsButtonsPlayer = new JButton[10][10];
    
    JPanel bsGUIAI = new JPanel(new GridLayout(10, 10));
    JPanel bsGUIPlayer = new JPanel(new GridLayout(10,10));
    JPanel filler = new JPanel();
    
    for (int i = 0; i < 10; ++i) {
      for (int j = 0; j < 10; ++j) {
        JButton b = new JButton("<html> - </html>");
        b.addActionListener(new ButtonListener());
        bsButtonsAI[i][j] = b;
        bsGUIAI.add(bsButtonsAI[i][j]);
      }
    }
    
    for (int i = 0; i < 10; ++i) {
      for (int j = 0; j < 10; ++j) {
        JButton b = new JButton("<html> - </html>");
        b.addActionListener(new ButtonListener());
        bsButtonsPlayer[i][j] = b;
        bsGUIPlayer.add(bsButtonsPlayer[i][j]);
      }
    }
    
    bsGUI.add(bsGUIAI, BorderLayout.EAST);
    bsGUI.add(filler, BorderLayout.CENTER);
    bsGUI.add(bsGUIPlayer, BorderLayout.WEST);
    
    return bsGUI;
  }
  
  private JPanel createTicTacToeGUI () {
    tttButtons = new JButton[3][3];
    JPanel tttGUI = new JPanel(new GridLayout(3, 3));
    for (int i = 0; i < 3; ++i) {
      for (int j = 0; j < 3; ++j) {
        JButton b = new JButton("<html><span style='font-size: 70px'> - </span></html>");
        b.addActionListener(new ButtonListener());
        tttButtons[i][j] = b;
        tttGUI.add(tttButtons[i][j]);
      }
    }
    return tttGUI;
  }
  
  private void initBL (JButton b) {
    b.addActionListener(new ButtonListener());
  }
  
  private String intro () {
    return "<html><span style='font-size: 15px'>Welcome!<br/>" +
      "Your objective in this game is to take care of an Oliner for 8 semesters.<br/>" +
      "Oliners are notorious for being easily bored, so you'll need to play lots of games with them.<br/>" +
      "You can pick any game from the drop-down menu below. Have fun!<br/>" +
      "PS: Don't forget to feed and wash your Oliner regularly, too!<br/></span></html>";
  }
  
  private class ButtonListener implements ActionListener {
    public void actionPerformed (ActionEvent event) {
      if (!ttt.isGameOver()) {
        for (int i = 0; i < 3; ++i) {
          for (int j = 0; j < 3; ++j) {
            if ((event.getSource() == tttButtons[i][j]) && ttt.isUserTurn()) {
              tttButtons[i][j].setText("<html><span style='font-size: 70px'> X </span></html>");
              ttt.userTurn(i + "," + j);
              
              int[] compMove = ttt.computerTurn();
              if ((compMove[0] != -1) && (compMove[1] != -1))
                tttButtons[compMove[0]][compMove[1]].setText("<html><span style='font-size: 70px'> O </span></html>");
            }
          }
        }
      }
      // TO DO: do something cool when someone wins
      // TO DO: do something else when there's a tie
      
      for (int i = 0; i < 10; ++i) {
        for (int j = 0; j < 10; ++j) {
          if ((event.getSource() == bsButtonsPlayer[i][j]) && (!bs.isGameOver())) {
            bs.userTurn(bs.getRows()[i] + j);
            if (bs.playerHitWasMade())
              bsButtonsPlayer[i][j].setText("<html> X </html>");
            else
              bsButtonsPlayer[i][j].setText("<html> O </html>");
            
            int[] coords = bs.computerTurn();
            System.out.println(coords[0] + coords[1]);
            if (bs.computerHitWasMade())
              bsButtonsAI[coords[0]][coords[1]].setText("<html> X </html>");
            else
              bsButtonsAI[coords[0]][coords[1]].setText("<html> O </html>");
          }
        }
      }
      // TO DO: do something cool when someone wins
      // TO DO: Mark space as X if hit, H if sunk, O if miss
      // TO DO: Allow player to mark their own ships
      // TO DO: Make sure if player hits same button again, nothing happens (is still turn)
      
      if (event.getSource() == exit) System.exit(0);
      else System.out.println("Button not implemented yet.");
    }
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