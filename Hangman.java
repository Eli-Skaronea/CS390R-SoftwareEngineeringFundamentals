package hangman;

import java.util.*;
import java.util.stream.*;
/**
 *
 * @author Jody Paul, Eli S
 */
public class Hangman implements HangmanManager {
    
    /**
     * List of eligible words to choose from
     */
    private List<String> dictionary = new ArrayList<>();
    /**
     * Length of the goal word
     */
    private final int length;
    /**
     * Number of guesses until the player loses
     */
    private final int wrongGuessLimit;
    /**
     * Number of wrong guesses made by the user
     */
    private int wrongGuessCount;
    /**
     * A set of words with the decided length of the goal word 
     */
    private final Set<String> wordSet;
    /**
     * The word chosen as the goal word
     */
    private String chosenWord = "";
    /**
     * Letters guessed by the user
     */
    private final SortedSet<Character> userGuesses = new TreeSet<>();

    /**
     * Constructor for a hangman object
     * @param dictionary - the list of possible goal words
     * @param length - the length of the goal word will be
     * @param wrongGuessLimit - amount of guesses before the user loses
     */
    public Hangman(final List<String> dictionary, final int length,
            final int wrongGuessLimit) {
        if (length < 1 || wrongGuessLimit < 1) {
            throw new IllegalArgumentException("Word must have 1 or more letters,"
                    + "and Guess Limit must be greater than 0.");
        }
        this.dictionary = dictionary;
        this.length = length;
        this.wrongGuessLimit = wrongGuessLimit;
        this.wordSet = words();
        if (!wordSet.isEmpty()) {
            this.chosenWord = chooseWord(wordSet);
        }
        if (wordSet.isEmpty()) {
            throw new IllegalArgumentException(("No letters that length in the dictionary."));
        }

    }
    /**
     * Chooses a random word from the list of possible goal words
     * @param words - a set of words that are the same length
     */
    private String chooseWord(Set<String> words) {
        int elementChosen = (int) (Math.random() * wordSet.size() - 1);
        List<String> wordList = new ArrayList<>(words);
        return wordList.get(elementChosen);
    }
    /**
     * Filters the dictionary down into a list of words that have length the user entered
     * If the dictionary length == 1 returns the goal word of the game 
     * @return a set of words the same length - all possible goal words
     */
    @Override
    public Set<String> words() {
        Set<String> chosenWords = new TreeSet<>();
        if (!chosenWords.isEmpty() || wrongGuessCount == wrongGuessLimit) {
            chosenWords.clear();
            chosenWords.add(chosenWord);
        } else {
            dictionary.stream()
                    .filter((currentWord) -> (currentWord.length() == length))
                    .forEach((currentWord) -> {
                        chosenWords.add(currentWord);
                    });
        }
        
        return chosenWords;
    }
    /**
     * 
     * @return the number of guesses before the user loses
     */
    @Override
    public int wrongGuessLimit() {
        return wrongGuessLimit;
    }
    /**
     * Determines how many guesses the user has before they lose
     * @return guesses left until the user loses
     */
    @Override
    public int guessesLeft() {
        int guessesLeft = wrongGuessLimit - wrongGuessCount;
        return guessesLeft;
    }
    /**
     * 
     * @return a sorted set of letters the user has guessed
     */
    @Override
    public SortedSet<Character> guesses() {
        return userGuesses;

    }

    @Override
    /**
     * Replace all the '-' characters in the pattern string with letters that have
     * been guessed
     * @return the current pattern string
     */
    public String pattern() {
        if (chosenWord.isEmpty()) {
            throw new IllegalStateException("There is no goal word!");
        }
       
        //PATTERN GENERATION//
        char[] charArray = chosenWord.toCharArray();
        return IntStream.range(0,charArray.length).mapToObj(x -> {
            if(userGuesses.contains(charArray[x]))
                return String.valueOf(charArray[x]);
            else {
                return "-";
            }
        }).collect(Collectors.joining(" "));
        
        
    }

    @Override
    /**
     * Records the user guess and returns the number of times it is in the goal word
     * @param guess - the letter guessed by the user 
     */
    public int record(char guess) {
        int numberInWord = 0;
        checkValidGuess(guess);

        for (int i = 0; i < chosenWord.length(); i++) {
            if (chosenWord.charAt(i) == guess) {
                numberInWord++;
            }
        }
        updatePlayerGuessInfo(numberInWord, guess);
        return numberInWord;
    }
    /**
     * Checks the guess is a valid guess based on error situations
     * @param guess letter guessed by user
     */
    private void checkValidGuess(char guess) {

        if (userGuesses.contains(guess)) {
            throw new IllegalArgumentException("Letter has already been guessed");
        } else if (guessesLeft() == 0) {
            throw new IllegalStateException("No guesses left - how is a dead man guessing?!");
        } else if (wordSet.isEmpty()) {
            throw new IllegalStateException("No goal word!");
        }
    }
    /**
     * Updates the wrong guess count and letters that have been guessed by the user
     * @param numberInWord - number of times the letter appears in the goal word
     * @param guess - letter guessed by the user
     */
    private void updatePlayerGuessInfo(int numberInWord, char guess) {
        if (numberInWord == 0) {
            wrongGuessCount++;
        }

        userGuesses.add(guess);
    }

}
