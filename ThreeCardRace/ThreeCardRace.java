package swefun.cardgames;

import swefun.cardgames.playingcards.Card;
import swefun.cardgames.playingcards.Deck;
import swefun.cardgames.playingcards.Suit;

import java.util.*;

/**
 * Driver for the Three-Card Race game.
 * <p>
 * <P>The three-card race game is a two-player contest (players A and B).
 * The game is played with a standard 52-card deck.
 * Only the color of the cards, red or black, is significant;
 * ranks do not matter.</P>
 * <p>
 * Play of the game:
 * <OL>
 * <LI>Player A designates a length-three sequence indicating the colors
 * of three sequential cards (sequence A).</LI>
 * <LI>Player B designates a length-three sequence indicating the colors
 * of three sequential cards (sequence B).</LI>
 * <LI>The deck is shuffled.</LI>
 * <LI>Cards are dealt face up one at a time from the deck
 * until three sequential cards are revealed whose colors
 * exactly match a designated sequence (A or B).
 * The player whose sequence was matched earns a point
 * ("takes the trick") and all dealt cards are discarded.</LI>
 * <LI>The game continues by repeating the previous step
 * until the deck is exhausted.</LI>
 * <LI>The player with the highest point total (greatest number of
 * tricks taken) wins the game.</LI>
 * </OL>
 *
 * @author Dr. Jody Paul, TTAB
 * @version 1.6 ThreeCardRace.java 424 2017-04-16
 */
public class ThreeCardRace extends GameRunner {
   /**
    * Color constants for this game.
    */
   public static final Set<String> COLORS = new HashSet<>();

   /**
    * Number of suit colors required for three card race game.
    */
   public static final int NUM_COLORS = 2;

   /**
    * Length of color sequence.
    */
   public static final int SEQUENCE_LENGTH = 3;

   /**
    * Number of players in this game.
    */
   public static final int NUM_PLAYERS = 2;

   /**
    * The default delay when dealing cards (in milliseconds).
    */
   public static final int DEFAULT_DELAY = 500;

   /**
    * The list of players.
    */
   private List<ThreeCardRacePlayer> players = new ArrayList<>();

   /**
    * The scores of players.
    */
   private Map<ThreeCardRacePlayer, Integer> playerScores = new HashMap<>();

   /**
    * If true, game will show turn by turn actions.
    */
   private boolean showTrace;

   /**
    * Time of delay between turns (ms).
    */
   private int delay;

   /**
    * The deck used for game.
    */
   private Deck deck;

   /**
    * The current cards that have been drawn in a round.
    */
   private List<Card> boardCards = new ArrayList<>();

   static {
      for (Suit s : Suit.values()) {
         COLORS.add(s.color());
      }
   }

   /**
    * Construct Three Card Game Runner.
    * Creates one interactive player and one non-interactive player.
    * Uses colors from Suit.
    */
   public ThreeCardRace() {
      this(new ThreeCardRacePlayer("Human", true),
           new ThreeCardRacePlayer("Automaton", false));
   }

   /**
    * Construct Three Card Game Runner, adds players, and sets scores to 0.
    * Uses colors from Suit; exits if invalid number of suit colors.
    *
    * @param playerA the first player
    * @param playerB the second player
    */
   public ThreeCardRace(final ThreeCardRacePlayer playerA,
                        final ThreeCardRacePlayer playerB) {

      players.add(playerA);
      players.add(playerB);
      playerScores.put(playerA, 0);
      playerScores.put(playerB, 0);


   }

   /**
    * Play one game and report results.
    *
    * @param showTrace if true, then the method displays each card dealt and identifies tricks as they are taken
    * @return {@code <MapThreeCardRacePlayer, int>} that associates each player with the number of tricks taken by that
    * player
    */
   public final Map<ThreeCardRacePlayer, Integer> playGame(final boolean showTrace) throws Exception {

      this.showTrace = showTrace;

      if (showTrace && delay == -1) { //delay not specified
         this.delay = DEFAULT_DELAY;
      } else if (!showTrace) {
         this.delay = 0;
      }

      if (!verifyPlayers()) {
         throw new Exception("One or both players are invalid.");
      }


      ThreeCardRacePlayer p1 = players.get(0);
      ThreeCardRacePlayer p2 = players.get(1);

      //player.getSequence() defies expected behavior of a getter. It must be passed in a second sequence.
      List<String> p1Seq = p1.getSequence(null);

      if (p2.isInteractive()) {
         System.out.println("Player 1's Sequence is " + p1Seq.toString());
      }

      //sequence is stored inside player. Return value is not used
      p2.getSequence(p1Seq);


      deck = new Deck();
      deck.shuffle();

      System.out.println("Press 'Enter' to start!");
      Scanner sc = new Scanner(System.in);
      sc.nextLine();


      while (deck.size() > 0) {
         ThreeCardRaceUiParameters p = getThreeCardRaceUiParameters();

         if (showTrace) {
            ThreeCardRaceUI.drawBoard(p);
            playTurn();
            Thread.sleep(delay);
         } else {
            playTurn();
         }

      }

      ThreeCardRaceUI.drawEndScreen(getThreeCardRaceUiParameters());

      return playerScores;
   }

   /**
    * Assigns parameters to be passed into the UI methods.
    *
    * @return A parameter object containing players names, scores, the current deck, and board cards
    */
   private ThreeCardRaceUiParameters getThreeCardRaceUiParameters() {
      ThreeCardRaceUiParameters p = new ThreeCardRaceUiParameters();
      p.p1 = players.get(0);
      p.p2 = players.get(1);
      p.p1Score = playerScores.get(p.p1);
      p.p2Score = playerScores.get(p.p2);
      p.deck = deck;
      p.boardCards = boardCards;

      return p;
   }

   /**
    * Runnable method that starts a new Three Card Race game.
    *
    * @param args currently ignored
    */
   public static void main(final String[] args) {
      System.out.println("Three-Card Race (T-TAB)");

      List<String> playerTypes = promptForPlayerTypes();
      Object[] traceAndDelay = promptForTraceAndDelay();

      ThreeCardRace theGame = createPlayers(playerTypes);
      theGame.showTrace = (boolean) traceAndDelay[0];
      theGame.delay = (int) traceAndDelay[1];

      try {
         theGame.playGame(theGame.showTrace);
      } catch (Exception e) {
         e.printStackTrace();
      }

   }

   /**
    * Asks for user input on whether player 1 and 2 are human or computer.
    *
    * @return a list of "human" or "computer" strings
    */
   private static List<String> promptForPlayerTypes() {
      String ask1 = "Is Player 1 Human? (y/n): ";
      String ask2 = "Is Player 2 Human? (y/n): ";

      Scanner reader = new Scanner(System.in);
      System.out.println(ask1);
      String answ1 = reader.nextLine();
      System.out.println(ask2);
      String answ2 = reader.nextLine();

      List<String> rslts = new ArrayList<>();

      rslts.add((answ1.startsWith("y")) ? ("human") : ("computer"));
      rslts.add((answ2.startsWith("y")) ? ("human") : ("computer"));

      return rslts;
   }

   /**
    * Initialize the players of the three card race.
    *
    * @param choices - a list of "human" or "computer" strings
    * @return a List of players
    */
   private static ThreeCardRace createPlayers(final List<String> choices) {
      ThreeCardRacePlayer player1;
      ThreeCardRacePlayer player2;

      player1 = new ThreeCardRacePlayer("Player1", (choices.get(0).equals("human")));
      player2 = new ThreeCardRacePlayer("Player2", (choices.get(1).equals("human")));

      return new ThreeCardRace(player1, player2);
   }

   /**
    * Asks if the players would like to see turn by turn data and how much.
    * delay between turns
    * @return Array containing a boolean for trace and integer for delay.
    */
   private static Object[] promptForTraceAndDelay() {
      System.out.println("Show trace? <y/n>: ");
      Scanner reader = new Scanner(System.in);
      boolean showTrace = reader.nextLine().startsWith("y");
      int delay = -1;

      if (showTrace) {
         System.out.println("How much of a delay (in milliseconds)? ");
         String ln = reader.nextLine();
         try {
            delay = Integer.parseInt(ln);
         } catch (NumberFormatException e) {
            e.printStackTrace();
            System.out.println("Could not parse delay as as integer");
         }
      }

      return new Object[]{showTrace, delay};
   }

   /**
    * Verifies there are 2 or more players to play the game.
    * @return boolean true if players were verified
    */
   private boolean verifyPlayers() {

      return (players.size() >= 2);
   }

   /**
    * Plays a turn, draw one card from the top and add it to the list of
    * cards in play.
    */
   private void playTurn() {
      Card card = deck.deal();
      boardCards.add(card);

      compareSequencesAndUpdate();
   }

   /**
    * Checks if a player has won the trick by comparing the current sequence of cards on the board to each player's
    * chosen sequence.
    */
   private void compareSequencesAndUpdate() {

      ThreeCardRacePlayer p1 = players.get(0);
      ThreeCardRacePlayer p2 = players.get(1);

      List<String> p1seq = p1.getSequence(null);
      List<String> p2seq = p2.getSequence(null);

      boolean p1Match = isSequenceMatch(p1seq);
      boolean p2Match = isSequenceMatch(p2seq);

      if (p1Match) {
         playerWinsTrick(p1);
      }
      if (p2Match) {
         playerWinsTrick(p2);
      }

   }


   /**
    * Compares the board sequence to the player's sequence.
    *
    * @param sequence - the sequence being compared to the cards in play.
    * @return true if there is a match
    */
   private boolean isSequenceMatch(final List<String> sequence) {
      if (boardCards.size() < SEQUENCE_LENGTH) {
         return false;
      }

      //uses reverse ordering to compare the last card on the board to the last player sequence color,
      //then the second from last, and third from last
      for (int i = 0; i < SEQUENCE_LENGTH; i++) {
         int lastIndex = boardCards.size() - 1;
         String boardColor = boardCards.get(lastIndex - i).suit().color();
         String playerColor = sequence.get(2 - i);

         if (!boardColor.equals(playerColor)) {
            return false;
         }
      }

      return true;
   }


   /**
    * Awards a point to a player when they win a trick, also draws the board if
    * show trace is on.
    *
    * @param winningPlayer - the player who won the trick
    */
   private void playerWinsTrick(final ThreeCardRacePlayer winningPlayer) {

      if (showTrace) {
         ThreeCardRaceUI.drawBoard(getThreeCardRaceUiParameters());
         int score = playerScores.get(winningPlayer);
         playerScores.put(winningPlayer, ++score);
         ThreeCardRaceUI.trickWon(winningPlayer, getThreeCardRaceUiParameters());

      } else {
         int score = playerScores.get(winningPlayer);
         playerScores.put(winningPlayer, ++score);
      }
      resetBoard();
   }


   /**
    * Clears the cards in the current round.
    */
   private void resetBoard() {
      boardCards.clear();
   }

} //end ThreeCardRace


/**
 * A class that encapsulates the parameters needed for the UI.
 * Uses the default empty constructor.
 */
class ThreeCardRaceUiParameters {
   ThreeCardRacePlayer p1;
   ThreeCardRacePlayer p2;
   int p1Score;
   int p2Score;
   Deck deck;
   List<Card> boardCards;

}

/**
 * DrawBoard method -
 * Draws the running game board - Lists player names, scores, sequences,
 * cards in deck, and cards played in round.
 * trickWon method -
 * Outputs who won the trick, and what cards won them the trick.
 * <p>
 * drawEndScreen method -
 * Outputs the end screen, Players, points, and sequences, who won,
 * and how many points they did it by.
 */
final class ThreeCardRaceUI {

   private ThreeCardRaceUI() { }

   /**
    * Draws the game board that will be displayed after each card is dealt.
    *
    * @param params - The object containing the game state information that is to be displayed.
    */
   static void drawBoard(final ThreeCardRaceUiParameters params) {
      int playerOneScore = params.p1Score;
      int playerTwoScore = params.p2Score;
      String playerOneName = params.p1.name();
      List<String> playerOneSeq = params.p1.getSequence(null);
      String playerTwoName = params.p2.name();
      List<String> playerTwoSeq = params.p2.getSequence(null);
      int cardsLeftInDeck = params.deck.size();
      List<Card> cardsInPlay = params.boardCards;

      String divider = "*****************************************************************************";
      System.out.println("");
      System.out.println(divider);
      System.out.printf("* Player Name: %15s %30s %15s %n", playerOneName, playerTwoName, "*");
      System.out.printf("* Score: %18s %30s %18s %n", playerOneScore, playerTwoScore, "*");
      System.out.printf("* Sequence: %24s %30s %9s %n", playerOneSeq, playerTwoSeq, "*");
      System.out.printf("* %75s %n", "*");
      System.out.println(divider);
      System.out.printf("* %75s %n", "*");
      int spacer = (params.deck.size() <= 9) ? 54 : 53;
      System.out.printf("* Cards Left In Deck: " + cardsLeftInDeck + "%" + spacer + "s %n", "*");
      System.out.printf("* Cards Drawn This Round: %51s %n", "*");

      //Displays cards played in the round, runs as many times as rounds since last trick won.
      for (int i = cardsInPlay.size() - 1; i >= 0; i--) {
         Card theCard = cardsInPlay.get(i);
         String card = "(" + theCard.suit().color() + ") " + theCard.toString();
         int wallSize = (71 - card.length());
         String format = "%" + wallSize + "s";
         System.out.printf("* " + (i + 1) + ".) " + card + format + "\n", "*");
      }
      System.out.printf("* %75s %n", "*");
      System.out.println(divider);

   }

   /**
    * Draws the game message that will be displayed when a player wins a trick.
    *
    * @param winningPlayer - the name of the player who won
    * @param params - the object containing the game state information that is to be displayed
    */
   static void trickWon(final ThreeCardRacePlayer winningPlayer, final ThreeCardRaceUiParameters params) {
      String lastThreeCards = "";
      for (int i = 0; i < 3; i++) {
         int firstCard = params.boardCards.size() - 3;
         Card c = params.boardCards.get(firstCard + i);
         lastThreeCards = lastThreeCards + " " + c.toString() + ((i < 2) ? (",") : (""));
      }

      String divider = "*****************************************************************************";
      System.out.println(divider);
      System.out.printf("* %75s %n", "*");
      System.out.printf("*%29s", winningPlayer.name());
      System.out.printf(" has won the trick! %27s %n", "*");
      System.out.printf("* %75s %n", "*");
      int wallSize = (55 - lastThreeCards.length());
      String format = "%" + wallSize + "s";
      System.out.printf("* Cards in Sequence: " + lastThreeCards + " " + format + "\n", "*");
      System.out.println(divider);
   }

   /**
    * Draws the game message that will be displayed when the game is over.
    *
    * @param params - The object containing the game state information that is to be displayed.
    */
   static void drawEndScreen(ThreeCardRaceUiParameters params) {
      int playerOneScore = params.p1Score;
      int playerTwoScore = params.p2Score;
      String playerOneName = params.p1.name();
      List<String> playerOneSeq = params.p1.getSequence(null);
      String playerTwoName = params.p2.name();
      List<String> playerTwoSeq = params.p2.getSequence(null);

      String winner;
      int winningScore;

      if (playerOneScore > playerTwoScore) {
         winner = playerOneName;
         winningScore = playerOneScore;
      } else if (playerOneScore < playerTwoScore) {
         winner = playerTwoName;
         winningScore = playerTwoScore;
      } else {
         winner = "None! It was a tie!";
         winningScore = playerTwoScore;

      }

      System.out.println("");
      String divider = "*****************************************************************************";
      System.out.println(divider);
      System.out.printf("*%35s %40s %n", "GAME OVER!", "*");
      System.out.printf("* %75s %n", "*");
      System.out.printf("* %75s %n", "*");
      System.out.printf("* Player Name: %15s %30s %15s %n", playerOneName, playerTwoName, "*");
      System.out.printf("* Score: %18s %30s %18s %n", playerOneScore, playerTwoScore, "*");
      System.out.printf("* Sequence: %24s %30s %9s %n", playerOneSeq, playerTwoSeq, "*");
      System.out.printf("* %75s %n", "*");
      System.out.printf("* %-15s %7s", "Winner (Score): ", winner);
      System.out.printf(" (" + winningScore + ")%47s %n", "*");
      System.out.printf("* %75s %n", "*");
      System.out.println(divider);

   }

}

