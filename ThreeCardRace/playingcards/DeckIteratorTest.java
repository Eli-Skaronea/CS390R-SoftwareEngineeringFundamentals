package swefun.cardgames.playingcards;

import java.util.List;
import java.util.ArrayList;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import org.junit.Test;
/**
 * Tests for class DeckIterator.
 *
 * @author  Dr. Jody Paul
 * @version 1.3 $Revision: 295 $
 */
public class DeckIteratorTest {
    /** Number of cards per deck for composite tests. */
    private static final int TEST_NUM_CARDS_PER_DECK = 11;
    /**
     * Composite tests of DeckIterator.
     */
    @Test
    public void compositeTest() {
        int numCardsPerDeck = TEST_NUM_CARDS_PER_DECK;
        DeckIterator di = new DeckIterator(numCardsPerDeck);
        List<Card> cardlist = new ArrayList<Card>();
        for (int i = 0; i < numCardsPerDeck; i++) {
            cardlist.add(new Card(i));
        }
        Deck initialDeck = new Deck(cardlist);
        assertNotNull(initialDeck);
        Deck currentDeck = null;
        long numPermutations = 1;
        for (int i = 2; i <= numCardsPerDeck; i++) {
            numPermutations *= i;
        }
        for (int i = 0; i < numPermutations; i++) {
            assertTrue(
                "hasNext() expected to be true, permutations not exhausted",
                di.hasNext());
            currentDeck = di.next();
            assertEquals(numCardsPerDeck, currentDeck.size());
            for (int k = 0; k < numCardsPerDeck; k++) {
                assertTrue("Expected card not found in deck.",
                           cardlist.contains(currentDeck.deal()));
            }
        }
        assertFalse("hasNext() expected to be false, permutations exhausted",
                    di.hasNext());
    }
}
