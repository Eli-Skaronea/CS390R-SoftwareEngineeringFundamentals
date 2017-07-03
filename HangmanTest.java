
package hangman;

import java.util.*;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Eli S
 */
public class HangmanTest {

    private final List<String> testDictionary = new ArrayList<>();
    private int length;
    private int wrongGuessLimit;

    public HangmanTest() {

    }

    /**
     * Test of words method, of class Hangman.
     */
    @Test
    public void testWords() {
        
        length = 4;
        wrongGuessLimit = 10;
        testDictionary.add("four");
        testDictionary.add("tree");
        Set<String> testSet = new TreeSet<>();
        testSet.add("four");
        testSet.add("tree");
        Hangman testHanger = new Hangman(testDictionary, length, wrongGuessLimit);

        assertEquals(testSet, testHanger.words());
        assertEquals("four", testHanger.words().iterator().next());

    }
    /**
     * Tests that the wrongGuessLimit returns the correct value input by the user
     */
    @Test
    public void testWrongGuessLimit(){
        length = 1;
        testDictionary.add("I");
        wrongGuessLimit = 4;
        Hangman hanger = new Hangman(testDictionary, length, wrongGuessLimit);
        assertEquals(4, hanger.wrongGuessLimit());
    }

    /**
     * Test of guessesLeft method, of class Hangman.
     * Checks the guessesLeft method returns the correct value
     */
    @Test
    public void testGuessesLeft() {
        
        wrongGuessLimit = 10;
        int wrongGuessesMade = 5;
        int wrongGuessLeft = wrongGuessLimit - wrongGuessesMade;
        
        assertEquals(5, wrongGuessLeft);
    }

    /**
     * Test of guesses method, of class Hangman.
     * Checks when a guess is made, it is added to the sorted set of guesses
     */
    @Test
    public void testGuesses() {
        
        testDictionary.add("word");
        length = 4;
        wrongGuessLimit = 10;
        Hangman testHanger = new Hangman(testDictionary, length, wrongGuessLimit);
        SortedSet<Character> guessSet = new TreeSet<>();
        guessSet.add('a');
        guessSet.add('b');
        guessSet.add('w');
        
        testHanger.record('a');
        testHanger.record('b');
        testHanger.record('w');
        
        assertEquals(guessSet, testHanger.guesses());
        
    }

    /**
     * Test of pattern method, of class Hangman.
     * Compares the pattern before and after a letter is added to guess
     */
    @Test
    public void testPattern() {
        
        length = 4;
        wrongGuessLimit = 10;
        testDictionary.add("word");
        
        String pattern = "- - - -";
        Hangman testHanger = new Hangman(testDictionary, length, wrongGuessLimit);
        
        assertEquals(pattern, testHanger.pattern());
        
        testHanger.record('w');
        
        assertEquals("w - - -", testHanger.pattern());
        
    }

    /**
     * Test of record method, of class Hangman.
     * Makes sure the record method returns the correct number of times a guess
     * appears in a word
     */
    @Test
    public void testRecord() {
        
        length = 4;
        wrongGuessLimit = 10;
        testDictionary.add("test");
        Hangman testHangar = new Hangman(testDictionary, 4, 4);

        assertEquals(0, testHangar.record('a'));
        assertEquals(1, testHangar.record('e'));
        assertEquals(2, testHangar.record('t'));

    }
    /**
     * Tests a set is created when there is only one word that fits the size.
     * 
     */
    @Test
    public void testWordListIsOneAndBecomesTheGoalWord() {
        
        length = 4;
        wrongGuessLimit = 10;
        testDictionary.add("word");
        Set<String> oneWordSet = new TreeSet<>();
        oneWordSet.add("word");

        Hangman testHangar = new Hangman(testDictionary, length, wrongGuessLimit);

        assertEquals("Should return the word 'word'", oneWordSet, testHangar.words());

    }

}
