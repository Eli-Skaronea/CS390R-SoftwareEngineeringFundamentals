package swefun.cardgames;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * A player of the three card race game.
 *
 * @author Dr. Jody Paul, TTAB
 * @version 1.5 $Id: ThreeCardRacePlayer.java 2017-04-15
 */
public class ThreeCardRacePlayer extends swefun.cardgames.GamePlayer {

   /**
    * The length of the sequence.
    */
   private static final int SEQUENCE_LENGTH = 3;

   /**
    * The max size of the input buffer for reading characters.
    */
   private static final int BUFFER_SIZE = 100;


   /**
    * A list of strings that represent the chosen colors in the player's sequence.
    */
   private List<String> playerSequence = new ArrayList<>();


   /**
    * Construct a default non-interactive player.
    */
   public ThreeCardRacePlayer() {

      this("Player", false);
   }

   /**
    * Construct a player with specified name and interactivity.
    *
    * @param name        the name of this player
    * @param interactive true if this is an interactive player; false otherwise
    */
   public ThreeCardRacePlayer(final String name, final boolean interactive) {

      super(name, interactive);
   }

   /**
    * Determines if a player is human based on if they are interactive.
    *
    * @return boolean result
    */
   private boolean isHuman() {
      return this.isInteractive();
   }


   /**
    * Return this player's sequence of choice.
    * If player is interactive, elicits sequence from the user.
    * Returned sequence must not match the parameter that is passed.
    *
    * @param sequence the other player's sequence; null or invalid size if none
    * @return the player's chosen sequence
    */
   public List<String> getSequence(final List<String> sequence) {
      List<String> otherSequence = sequence;
      List<String> playerSequence;

      if (this.playerSequence.size() == SEQUENCE_LENGTH) {
         return this.playerSequence;
      }


      if (isHuman()) {
         playerSequence = askSequenceFromHuman();
         while (playerSequence.equals(otherSequence)) {
            System.out.println("Bad Player! Your chosen sequence can't math the other player's. Try again");
            playerSequence = askSequenceFromHuman();
         }
      } else { //computer player
         playerSequence = generateComputerSequence();
         while (playerSequence.equals(otherSequence)) {
            playerSequence = generateComputerSequence();
         }
      }

      this.playerSequence = playerSequence;

      return playerSequence;

   }

   /**
    * Prompts the player to enter a sequence. The sequence is normalized and checked for correctness.
    *
    * @return A list of strings, each one containing "BLACK" or "RED".
    */
   private List<String> askSequenceFromHuman() {
      System.out.println("r = red   b = black");
      boolean valid = false;
      Scanner reader = new Scanner(System.in);
      char[] sequence = new char[BUFFER_SIZE];

      while (!valid) {
         System.out.println(
               "Player " + this.name() + " - Enter your 3 color sequence in the form <xxx> where x = {r or b}: ");
         sequence = formatSequence(reader.nextLine());
         valid = validSequence(sequence);
      }

      return convertSequenceToStringList(sequence);

   }

   /**
    * Creates a random sequence for a CPU player.
    * Uses Java's Math.Random library.
    *
    * @return A list of strings, each one containing "BLACK" or "RED".
    */
   private List<String> generateComputerSequence() {
      char[] sequence = new char[SEQUENCE_LENGTH];

      for (int i = 0; i < SEQUENCE_LENGTH; i++) {
         sequence[i] = (Math.random() < .5) ? ('r') : ('b');
      }

      return convertSequenceToStringList(sequence);
   }

   /**
    * Formats the sequence by stripping whitespace and then converting it into a char array.
    *
    * @param sequence in string form.
    * @return a char array of the sequence.
    */
   private char[] formatSequence(String sequence) {
      sequence = sequence.replaceAll("\\s+", "");
      sequence = sequence.toLowerCase();

      return sequence.toCharArray();
   }

   /**
    * Checks the validity of the sequenceLetters.
    *
    * @param sequenceLetters is a char array of 'r' and 'b' chars
    * @return If valid, returns true
    */
   private boolean validSequence(final char[] sequenceLetters) {

      if (sequenceLetters.length != SEQUENCE_LENGTH) {
         return false;
      }

      for (int i = 0; i < SEQUENCE_LENGTH; i++) {
         if (!(sequenceLetters[i] == 'b') && !(sequenceLetters[i] == 'r')) {
            return false;
         }
      }

      return true;
   }

   /**
    * Converts an array of characters into the correct color strings.
    *
    * @param seq is a sequence of 'r' and 'b' chars.
    * @return a three element list of strings. Each string can be "RED" or "BLACK".
    */

   private List<String> convertSequenceToStringList(final char[] seq) {

      List<String> seqAsStringList = new ArrayList<>();

      for (char c : seq) {
         seqAsStringList.add((c == 'r') ? ("RED") : ("BLACK"));
      }

      return seqAsStringList;
   }

}

