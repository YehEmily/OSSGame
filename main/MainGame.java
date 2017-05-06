// Add AI visuals (Battleship: PDF display, TTT: Trees, GoFish: Memory bank)
// Statistics about different AIs, algorithms, iterations (poster), live demo

package main;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.TimerTask;
import java.util.Timer;
import javax.imageio.ImageIO;
import java.io.*;
import java.net.*;

import battleship.BSGUIGame;
//import gofish.GoFish;
import gofish.GoFishPanel;
//import gofish.Cards.*;
import ttt.TTTGUIGame;

public class MainGame implements ItemListener {
  
  private JButton feed, clean, exit, save, chooseGF;
  private JButton[][] tttButtons, bsButtonsAI, bsButtonsPlayer;
  private JPanel panel;
  private JLabel instructions, fullness, cleanliness, age, petImage;
  private JLabel tttStatus, bsStatus;
  
  final static String GOFISH = "Play Go Fish!";
  final static String TICTACTOE = "Play Tic-Tac-Toe!";
  final static String BATTLESHIP = "Play Battleship!";
  final static String NONE = "--";
  
  private TTTGUIGame ttt;
  private BSGUIGame bs;
  private Pet oliner;
  
  private boolean game_on;
  
  public MainGame() {
    String intro = intro();
    instructions = new JLabel(intro);
    feed = new JButton("Feed");
    clean = new JButton("Clean");
    exit = new JButton("Exit");
    save = new JButton("Save");
    initButton(feed); initButton(clean); initButton(exit); initButton(save);
    
    ttt = new TTTGUIGame();
    bs = new BSGUIGame();
    oliner = new Pet();
    
    fullness = new JLabel("Fullness: " + oliner.getFullness()); 
    cleanliness = new JLabel("Cleanliness: " + oliner.getCleanliness());
    age = new JLabel("Age: " + oliner.getAge());
    
    game_on = true;
    
    Timer t1 = new Timer();
    t1.scheduleAtFixedRate(new TimerTask() {
      public void run() {
        oliner.updateStats();
        updateLabels();
      }
    }, 0, 10000); // run first occurrence immediately, then every ten seconds
    
    Timer t2 = new Timer();
    t2.scheduleAtFixedRate(new TimerTask() {
      public void run() {
        if (oliner.getAge() != 8) {
          oliner.ageUp();
          updateLabels();
        } else {
          updateImage();
          oliner.ageUp();
          updateLabels();
        }
      }
    }, 0, 30000); // run first occurrence immediately, then every thirty seconds
  }
  
  private void updateImage () {
    URL url = MainGame.class.getResource("/main/images/oliner.gif");
    ImageIcon ii = new ImageIcon(url);
    petImage.setIcon(ii);
  }
  
  private void updateLabels () {
    fullness.setText("Fullness: " + oliner.getFullness());
    cleanliness.setText("Cleanliness: " + oliner.getCleanliness());
    age.setText("Age: " + oliner.getAge());
  }
  
  public boolean getGameState () {
    return game_on;
  }
  
  public void gameOff () {
    game_on = false;
  }
  
  public void addComponentToPane (Container pane) {
    URL url = MainGame.class.getResource("/main/images/egg.gif");
    ImageIcon ii = new ImageIcon(url);
    petImage = new JLabel(ii);
    
    JPanel comboPane = new JPanel();
    String comboBoxItems[] = { NONE, GOFISH, TICTACTOE, BATTLESHIP };
    JComboBox cb = new JComboBox(comboBoxItems);
    cb.setEditable(false);
    cb.addItemListener(this);
    comboPane.add(cb);
    
    JPanel intro = new JPanel();
    intro.add(instructions);
    
    JPanel stats = new JPanel();
    stats.add(fullness); stats.add(cleanliness); stats.add(age);
    
    JPanel petAndStats = new JPanel(new BorderLayout());
    petAndStats.add(stats, BorderLayout.NORTH);
    petAndStats.add(petImage, BorderLayout.CENTER);
    
    JPanel options = new JPanel();
    options.add(feed); options.add(clean); options.add(save); options.add(exit);
    
    JPanel menu = new JPanel(new BorderLayout());
    menu.add(intro, BorderLayout.NORTH);
    menu.add(petAndStats, BorderLayout.CENTER);
    menu.add(options, BorderLayout.SOUTH);
    
    JPanel placeholder = new JPanel();
    placeholder.add(new JButton("Select a game from the menu!"));
    
    JPanel gfOption = new JPanel();
    chooseGF = new JButton("Yes! " + GOFISH);
    initButton(chooseGF);
    gfOption.add(chooseGF);
    JPanel tttOption = createTicTacToeGUI();
    JPanel bsOption = createBattleshipGUI();
    
    panel = new JPanel(new CardLayout());
    panel.add(placeholder, NONE);
    panel.add(tttOption, TICTACTOE);
    panel.add(bsOption, BATTLESHIP);
    panel.add(gfOption, GOFISH);
    
    pane.add(menu, BorderLayout.PAGE_START);
    pane.add(comboPane, BorderLayout.CENTER);
    pane.add(panel, BorderLayout.SOUTH);
  }
  
  public void itemStateChanged (ItemEvent evt) {
    CardLayout cl = (CardLayout)(panel.getLayout());
    cl.show(panel, (String)evt.getItem());
  }
  
  private void cleanOliner () {
    oliner.clean(1);
    System.out.println(oliner.getCleanliness());
  }
  
  private void feedOliner () {
    oliner.feed(1);
    System.out.println(oliner.getFullness());
  }
  
  public static void createAndShowGUI () {
    JFrame frame = new JFrame("Welcome!");
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    MainGame game = new MainGame();
    game.addComponentToPane(frame.getContentPane());
    frame.pack();
    frame.setVisible(true);
    frame.setResizable(false);
    frame.setBackground(Color.WHITE);
  }
  
  private JPanel createBattleshipGUI () {
    JPanel bsGUI = new JPanel(new BorderLayout());
    
    bsButtonsAI = new JButton[10][10];
    bsButtonsPlayer = new JButton[10][10];
    
    JPanel bsGUIAI = new JPanel(new GridLayout(10, 10));
    JPanel bsGUIPlayer = new JPanel(new GridLayout(10,10));
    JPanel filler = new JPanel();
    filler.setPreferredSize(new Dimension(150, 100));
    
    for (int i = 0; i < 10; ++i) {
      for (int j = 0; j < 10; ++j) {
        JButton b = new JButton("<html> - </html>");
        initButton(b);
        bsButtonsAI[i][j] = b;
        bsGUIAI.add(bsButtonsAI[i][j]);
      }
    }
    
    for (int i = 0; i < 10; ++i) {
      for (int j = 0; j < 10; ++j) {
        JButton b = new JButton("<html> - </html>");
        initButton(b);
        bsButtonsPlayer[i][j] = b;
        bsGUIPlayer.add(bsButtonsPlayer[i][j]);
      }
    }
    
    bsStatus = new JLabel("<html><div style='text-align: center'>Game in progress!</div></html>", SwingConstants.CENTER);
    Font font = new Font("Arial", Font.BOLD, 20);
    bsStatus.setFont(font);
    
    bsGUI.add(bsGUIAI, BorderLayout.EAST);
    bsGUI.add(filler, BorderLayout.CENTER);
    bsGUI.add(bsGUIPlayer, BorderLayout.WEST);
    bsGUI.add(bsStatus, BorderLayout.SOUTH);

    return bsGUI;
  }
  
  private JPanel createTicTacToeGUI () {
    JPanel fullGUI = new JPanel(new BorderLayout());
    
    tttButtons = new JButton[3][3];
    JPanel tttGUI = new JPanel(new GridLayout(3, 3));
    for (int i = 0; i < 3; ++i) {
      for (int j = 0; j < 3; ++j) {
        JButton b = new JButton("<html><span style='font-size: 70px'> - </span></html>");
        initButton(b);
        tttButtons[i][j] = b;
        tttGUI.add(tttButtons[i][j]);
      }
    }
    
    fullGUI.add(tttGUI, BorderLayout.CENTER);
    
    tttStatus = new JLabel("<html><div style='text-align: center'>Game in progress!</div></html>", SwingConstants.CENTER);
    Font font = new Font("Arial", Font.BOLD, 20);
    tttStatus.setFont(font);
    
    fullGUI.add(tttStatus, BorderLayout.SOUTH);
    
    return fullGUI;
  }
  
  private void initButton (JButton b) {
    b.addActionListener(new ButtonListener());
    Font font = new Font("Arial", Font.BOLD, 12);
    b.setBackground(Color.WHITE);
    b.setFont(font);
  }
  
  private String intro () {
    return "<html><span style=''>Welcome!<br/>" +
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
      } else {
        for (int i = 0; i < 3; ++i) {
          for (int j = 0; j < 3; ++j) {
            tttButtons[i][j].setBackground(Color.BLACK);
          }
        }
        if (ttt.getWinner().equals("Computer")) {
          tttStatus.setText("<html><div style='text-align: center'>Game completed! The AI won!</div></html>");
        } else if (!ttt.getWinner().equals("Tie")) {
          tttStatus.setText("<html><div style='text-align: center'>Game completed! You won!</div></html>");
        } else {
          tttStatus.setText("<html><div style='text-align: center'>Game completed! It's a tie!</div></html>");
        }
      }
      // TO DO: Be able to reset games
      
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
      
      if (event.getSource() == exit) {
        System.exit(0);
        game_on = false;
      } else if (event.getSource() == feed) {
        feedOliner();
        fullness.setText("Fullness: " + oliner.getFullness());
      } else if (event.getSource() == clean) {
        cleanOliner();
        cleanliness.setText("Cleanliness: " + oliner.getCleanliness());
      } else System.out.println("Button not implemented yet.");
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
    UIManager.put("swing.boldMetal", Boolean.FALSE);
    
    javax.swing.SwingUtilities.invokeLater(new Runnable() {
      public void run() {
        createAndShowGUI();
      }
    });
  }
}