import javax.swing.*;
import java.awt.*;
import java.util.*;
import javax.imageio.ImageIO;
import java.io.*;
import java.awt.event.*;

public class GoFishPanel extends JPanel {
  
  // Instance Variables
  static final int HEIGHT = 1000;
  static final int WIDTH = 1700;
  
  private JFrame frame;
  private JPanel north, south, west, east, center;
  private JLabel header, status, user_score, computer_score,
    deck_status, turn_status, round_status;
  private JButton deckButton, match, query, quit, new_game;
  
  private Player currentPlayer; // Whose turn it is
  private LinkedList<JButton> user_hand, computer_hand;
  private LinkedList<Card> selected_cards;
  private boolean isReset;
  private int round;
  
  private Container userHandPointer, computerHandPointer;
  
  private GoFish game;
  
  /**
   * Constructor: Instantiates all instance variables with the help of a few helper methods.
   */
  public GoFishPanel () {
    try {
      initializeVariables();
    } catch (NullPointerException ex) {
      System.out.println("Game exited.");
      System.exit(0);
    }
    initializeFrame();
    frame.setVisible(true);
    playGame();
  }
  
  /**
   * playGame method: Where the main gameplay is executed.
   */
  public void playGame () {
    while (!game.isGameOver()) {
      while (currentPlayer.equals(game.getPlayer())) {
        System.out.print("");
        if (game.getPlayer().getHand().size() == 0) {
          if (!game.isGameOver()) {
          status.setText("Looks like you ran out of cards. Click the deck to try to draw some more.");
          } else {
            currentPlayer = new Player("Nike, Goddess of Victory");
          }
        }
      }
      while (currentPlayer.equals(game.getComputer())) {
        if (game.getComputer().getHand().isEmpty()) {
          if (!game.isGameOver()) {
            status.setText("Computer ran out of cards. Give it a sec to draw some more.");
            smellTheRoses(1000);
            while ((game.getComputer().getHand().size() < 4) && (game.cardsRemaining() > 0)) {
              game.drawCard(1, game.getComputer());
              updateGUI();
            }
          } else {
            currentPlayer = new Player("Nike, Goddess of Victory");
          }
        }
        
        matchComputerCards ();
        Card query = game.computerQuery();
        try {
          int query_number = query.getNumber();
          status.setText("Computer asked: 'Got any... " +
                         query_number + "s?'");
          smellTheRoses(2000);
          if (game.hasCardAndTransferred(game.getPlayer(), query)) {
            updateGUI();
            status.setText(game.getPlayer().getName() + " handed over their " + query_number + "s.");
            smellTheRoses(2000);
          } else {
            status.setText(game.getPlayer().getName() + " didn't have any " + query_number + "s. Go fish!");
            smellTheRoses(1000);
            game.drawCard(1, game.getComputer());
            currentPlayer = game.getPlayer();
            updateGUI();
          }
        }  catch (NullPointerException ex) {
          status.setText("Oh, looks like the game is over!");
          currentPlayer = new Player("Nike, Goddess of Victory");
        }
        round++;
      }
    }
    turn_status.setText("The game has ended.");
    status.setText("Game over! " + game.getWinner().getName() + " is the winner! Thanks for a fun game!");
  }
  
  private void smellTheRoses (int milliseconds) {
    try {
      Thread.sleep(milliseconds);
    } catch (InterruptedException ex) {
      System.out.println(ex);
    }
  }
  
  /**
   * updateGUI method: Updates the GUI every time a step is taken in the game.
   */
  public void updateGUI () {
    // Update labels
    user_score.setText(game.getPlayer().getName() + " has made " + game.getPlayer().getPoints() + " matches.");
    computer_score.setText("Computer has made " + game.getComputer().getPoints() + " matches.");
    turn_status.setText("<html><div style='text-align: center'>It's " + currentPlayer.getName() + "'s turn.");
    deck_status.setText("<html><div style='text-align: center'>There are " + game.cardsRemaining()
                               + " cards left in the deck.</div></html>");
    round_status.setText("<html><div style='text-align: center'>This is round " + round + ".");
    
    // Update user's hand
//    Container parentUser = user_hand.getFirst().getParent();
    user_hand.clear();
    paintCards("User", game.getPlayer().getHand());
    fillContainerWithButtons(userHandPointer, user_hand);
    userHandPointer.revalidate();
    userHandPointer.repaint();
    
    // Update computer's hand
//    Container parentComputer = computer_hand.getFirst().getParent();
    computer_hand.clear();
    paintCards("Computer", game.getComputer().getHand());
    fillContainerWithButtons(computerHandPointer, computer_hand);
    computerHandPointer.revalidate();
    computerHandPointer.repaint();
    
    // Deselect all cards
    selected_cards.clear();
    
    // Repaint the frame
    frame.repaint();
  }
  
  private void fillContainerWithButtons (Container parent, LinkedList<JButton> buttons) {
    parent.removeAll();
    for (int i = 0; i < buttons.size(); ++i) {
      parent.add(buttons.get(i));
    }
  }
  
  /**
   * initializeVariables: Initializes all the variables in the frame.
   */
  private void initializeVariables () {
    game = new GoFish();
    selected_cards = new LinkedList<Card>();
    
    currentPlayer = game.getPlayer(); // Player goes first
    round = 1; // Start with round 1
    
    // Initialize JLabels
    user_score = new JLabel (game.getPlayer().getName() + " has made " + game.getPlayer().getPoints() + " matches.");
    computer_score = new JLabel ("Computer has made " + game.getComputer().getPoints() + " matches.");
    deck_status = new JLabel("<html><div style='text-align: center'>There are " + game.cardsRemaining()
                               + " cards left in the deck.</div></html>");
    turn_status = new JLabel("<html><div style='text-align: center'>It's " + currentPlayer.getName() + "'s turn.");
    round_status = new JLabel("<html><div style='text-align: center'>This is round " + round + ".");
    header = new JLabel ("Let's Play Go Fish!");
    
    setLabelStyle(user_score, 25, Color.WHITE);
    setLabelStyle(computer_score, 25, Color.WHITE);
    setLabelStyle(round_status, 25, Color.WHITE);
    setLabelStyle(deck_status, 25, new Color(0, 200, 255));
    setLabelStyle(turn_status, 25, new Color(0, 200, 255));
    setLabelStyle(header, 55, Color.WHITE);
    
    status = new JLabel ("<html>If there are any pairs in your hand, click them and hit the 'Match' button.<br/>" +
                         "If you don't have any pairs, click any card and hit the 'Query' button to see if the other " +
                         "player has any matches.<br/>" +
                         "Click 'Restart' or 'Quit' to restart or quit the game at any time.");
    status.setFont(new Font("Tahoma", Font.BOLD, 20));
    status.setForeground(Color.BLACK);
    
    user_hand = new LinkedList<JButton>(); // Create representation of user's hand
    computer_hand = new LinkedList<JButton>(); // " computer's hand
    
    // Initialize JButtons
    match = new JButton("Match");
    query = new JButton("Query");
    new_game = new JButton("Restart");
    quit = new JButton("Quit");
    
    setButtonStyle(match, Color.WHITE);
    setButtonStyle(query, Color.WHITE);
    setButtonStyle(new_game, Color.WHITE);
    setButtonStyle(quit, Color.WHITE);
    
    north = makeNorthPanel();
    south = makeSouthPanel();
    west = makeWestPanel();
    east = makeEastPanel();
    center = makeCenterPanel();
  }
  
  /**
   * initializeFrame: Initializes the JFrame.
   */
  private void initializeFrame () {
    if (isReset) {
      frame.removeAll();
      isReset = false;
    }
    
    frame = new JFrame("Go Fish!");
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setResizable(false);
    frame.pack();
    frame.setSize(WIDTH, HEIGHT);
    
    frame.setLayout(new BorderLayout());    
    
    frame.add(north, BorderLayout.NORTH);
    frame.add(south, BorderLayout.SOUTH);
    frame.add(center, BorderLayout.CENTER);
    frame.add(west, BorderLayout.WEST);
    frame.add(east, BorderLayout.EAST);
    
    frame.setVisible(true);
  }
  
  /**
   * paintCards: Paints all the cards (JButtons) and adds them to the appropriate hand.
   * 
   * @param playerName: Name of player to whose hand the cards should be added
   *        (helps to determine whether cards should be face-up or flipped over)
   * @param hand: The hand to which the cards should be added
   */
  private void paintCards (String playerName, LinkedList<Card> hand) {
    for (int i = 0; i < hand.size(); ++i) {
      JButton button = new JButton();
      button.addActionListener(new ButtonListener());
      if (playerName.equals("User")) {
        Card card = hand.get(i);
        button.setIcon(createImageIcon(card));
        button.setBackground(Color.WHITE);
        user_hand.add(button);
      } else {
        try {
          Image img = ImageIO.read(new File("mystery.png"));
          ImageIcon ii = new ImageIcon(img);
          button.setIcon(ii);
          button.setBackground(Color.WHITE);
          computer_hand.add(button);
        } catch (Exception ex) {
          System.out.println(ex);
        }
      }
    }
  }
  
  /**
   * setLabelStyle: Helper method that stylizes JLabels.
   * 
   * @param label: Label to be stylized
   * @param size: Font size of label
   * @param color: Background color of label
   */
  private void setLabelStyle (JLabel label, int size, Color color) {
    label.setFont(new Font("Tahoma", Font.BOLD, size));
    label.setBackground(color);
    label.setHorizontalAlignment(JLabel.CENTER);
  }
  
  /**
   * setButtonStyle: Helper method that stylizes JButtons.
   * 
   * @param button: Button to be stylized
   * @param color: Background color of button
   */
  private void setButtonStyle(JButton button, Color color) {
    button.setBackground(color);
    button.setForeground(Color.BLACK);
    button.setFont(new Font("Tahoma", Font.BOLD, 25));
    button.setPreferredSize(new Dimension(WIDTH/6, 20));
  }
  
  /**
   * createImageIcon: Helper method that creates an image icon of a card.
   * 
   * @param card: Card to be created
   * @return: ImageIcon of card (if image file could be found)
   */
  private static ImageIcon createImageIcon (Card card) {
    String cardType = card.getType();
    int cardNum = card.getNumber();
    try {
      Image img = ImageIO.read(new File(cardType + cardNum + ".png"));
      ImageIcon ii = new ImageIcon(img);
      return ii;
    } catch (Exception ex) {
      System.out.println(ex + "\n" + cardType + cardNum + ".png");
      return null;
    }
  }
  
  /**
   * makeNorthPanel: Creates the top panel.
   */
  private JPanel makeNorthPanel() {
    JPanel northPanel = new JPanel();
    northPanel.setBackground(new Color(0, 200, 255));
    northPanel.add(header);
    northPanel.setVisible(true);
    
    return northPanel;
  }
  
  /**
   * makeSouthPanel: Creates the bottom panel.
   */
  private JPanel makeSouthPanel() {
    JPanel southPanel = new JPanel();
    southPanel.setBackground(new Color(0, 200, 255));
    southPanel.add(status);
    southPanel.setVisible(true);
    
    return southPanel;
  }
  
  /**
   * makeWestPanel: Creates the left panel.
   */
  private JPanel makeWestPanel () {
    GridLayout layout = new GridLayout(4, 1);
    JPanel westPanel = new JPanel();
    westPanel.setBackground(Color.WHITE);
    westPanel.setLayout(layout);
    
    deckButton = new JButton();
    try {
      Image img = ImageIO.read(new File("mystery.png"));
      ImageIcon ii = new ImageIcon(img);
      deckButton.setIcon(ii);
    } catch (Exception ex) {
      System.out.println(ex);
    }
    
    deckButton.setBackground(Color.WHITE);
    
    westPanel.add(round_status);
    westPanel.add(turn_status);
    westPanel.add(deck_status);
    
    deckButton.addActionListener(new ButtonListener());
    westPanel.add(deckButton);
    
    westPanel.setPreferredSize(new Dimension(WIDTH/6, HEIGHT));
    return westPanel;
  }
  
  /**
   * makeEastPanel: Creates the right panel.
   */
  private JPanel makeEastPanel () {
    JPanel eastPanel = new JPanel();
    GridLayout layout = new GridLayout(4, 1);
    eastPanel.setLayout(layout);
    eastPanel.setBackground(Color.WHITE);
    eastPanel.setPreferredSize(new Dimension(WIDTH/6, HEIGHT));
    
    match.addActionListener(new ButtonListener());
    eastPanel.add(match);
    
    query.addActionListener(new ButtonListener());
    eastPanel.add(query);
    
    new_game.addActionListener(new ButtonListener());
    eastPanel.add(new_game);
    
    quit.addActionListener(new ButtonListener());
    eastPanel.add(quit);
    
    return eastPanel;
  }
  
  /**
   * makeCenterPanel: Creates the center panel.
   */
  private JPanel makeCenterPanel() {
    JPanel centerPanel = new JPanel();
    GridLayout layout = new GridLayout(4, 1);
    centerPanel.setLayout(layout);
    centerPanel.setBackground(Color.WHITE);
    
    JPanel centerTop = new JPanel();
    JPanel topScore = new JPanel();
    JPanel centerBottom = new JPanel();
    JPanel bottomScore = new JPanel();
    
    userHandPointer = centerTop;
    computerHandPointer = centerBottom;
    
    centerTop.setBackground(Color.WHITE);
    topScore.setBackground(Color.WHITE);
    centerBottom.setBackground(Color.WHITE);
    bottomScore.setBackground(Color.WHITE);
    
    paintCards("User", game.getPlayerHand());
    
    for (int i = 0; i < user_hand.size(); ++i) {
      centerTop.add(user_hand.get(i));
    }
    
    paintCards("Computer", game.getComputerHand());
    
    for (int i = 0; i < computer_hand.size(); ++i) {
      centerBottom.add(computer_hand.get(i));
    }
    
    topScore.add(user_score);
    bottomScore.add(computer_score);
    
    centerPanel.add(centerTop);
    centerPanel.add(user_score);
    centerPanel.add(centerBottom);
    centerPanel.add(computer_score);
    return centerPanel;
  }
  
  /**
   * matchComputerCards: Matches cards for the computer and removes them from its hand.
   */
  private void matchComputerCards () {
    int[] matches = game.findMatches();
    int[] no_matches = {-1, -1};
    while ((matches[0] != no_matches[0]) && (matches[1] != no_matches[1])) {
      game.removeMatch(game.getComputer(), game.getComputer().getHand().get(matches[1]),
                       game.getComputer().getHand().get(matches[0]));
      matches = game.findMatches();
    }
    smellTheRoses(1000);
    updateGUI();
  }
  
  /**
   * queryComputer: Helper method for when the player makes a query.
   */
  private void queryComputer () {
    Card c = selected_cards.remove();
    if (game.hasCardAndTransferred(game.getComputer(), c)) { // If the computer has the card
      smellTheRoses(1000);
      updateGUI();
      status.setText("Computer had " + c.getNumber() + "(s)!");
      
    } else { // If the computer doesn't have the card
      smellTheRoses(1000);
      game.drawCard(1, game.getPlayer());
      game.getComputer().addToMemoryBank(c);
      
      currentPlayer = game.getComputer();
      updateGUI();
      status.setText("Computer didn't have any " + c.getNumber() + "s. Go fish!");
    }
  }
  
  private class ButtonListener implements ActionListener {
    
    public void actionPerformed (ActionEvent event) {
      if (event.getSource() == quit) System.exit(0);
      
      if (event.getSource() == new_game) {
        reset();
      }
      
      if (currentPlayer.equals(game.getPlayer())) {
        
        // If player is trying to make a match AND has only selected 2 cards
        if ((event.getSource() == match) && (selected_cards.size() == 2)) {
          if (game.isMatch(selected_cards.get(0), selected_cards.get(1))) {
            int index1 = game.getPlayerHand().indexOf(selected_cards.get(0));
            int index2 = game.getPlayerHand().indexOf(selected_cards.get(1));
            
            if (index1 > index2) { // Ensure index1 is smaller
              int temp = index1;
              index1 = index2;
              index2 = temp;
            }
            
            game.removeMatch(currentPlayer, selected_cards.get(0), selected_cards.get(1));
            selected_cards.clear();
            updateGUI();
            status.setText("Nice match!");
          } else {
            updateGUI();
            status.setText("That wasn't a match. Try again?");
          }
          
          // If player is trying to make a match BUT has not selected 2 cards
        } else if ((event.getSource() == match) && (selected_cards.size() != 2)) {
          status.setText("You can't select fewer or more than 2 cards to match. Try again?");
        }
        
        // Check if player has selected any cards
        for (int i = 0; i < user_hand.size(); ++i) {
          if (event.getSource() == user_hand.get(i)) {
            if (selected_cards.contains(game.getPlayerHand().get(i))) {
              selected_cards.remove(game.getPlayerHand().get(i));
              user_hand.get(i).setBackground(Color.WHITE); // Unselected = white
            } else {
              user_hand.get(i).setBackground(Color.BLACK); // Selected = black
              selected_cards.add(game.getPlayerHand().get(i));
            }
          }
        }
        
        // If player is trying to ask the computer for a card AND has only selected 1 card
        if ((event.getSource() == query) && (selected_cards.size() == 1)) {
          status.setText(game.getPlayer().getName() + " asked: 'Got any... " +
                         selected_cards.peek().getNumber() + "s?'");
          queryComputer();
          
          // If player is trying to ask the computer for a card BUT has not selected 1 card
        } else if ((event.getSource() == query) && (selected_cards.size() != 1)) {
          updateGUI();
          status.setText("You can't select fewer or more than 1 card to query. Try again?");
        }
        
        if (event.getSource() == deckButton && (game.getPlayer().getHand().size() == 0)
              && (game.cardsRemaining() != 0)) {
          while ((game.getPlayer().getHand().size() < 4) && (game.cardsRemaining() > 0)) {
            game.drawCard(1, game.getPlayer());
            updateGUI();
          }
        }
        else if (event.getSource() == deckButton && (game.getPlayer().getHand().size() == 0)
                   && (game.cardsRemaining() == 0)) {
          status.setText("Oops, the deck is empty. Looks like the game is just about over. " +
                         "Sit tight while we calculate the scores...");
          smellTheRoses(1000);
          currentPlayer = game.getComputer();
        }
      }
    }
    
    private void reset () {
      isReset = true;
      initializeVariables();
      initializeFrame();
    }
  }
  
  public static void main (String[] args) {
    new GoFishPanel();
  }
}