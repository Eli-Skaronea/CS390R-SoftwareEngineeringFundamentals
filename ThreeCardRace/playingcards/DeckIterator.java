package swefun.cardgames.playingcards;

import java.math.BigInteger;
import java.util.Iterator;
/**
 * Produces sequence of decks covering all possible
 * orderings of the cards.
 * @author Dr. Jody Paul
 * @version 1.2 $Id: DeckIterator.java 295 2017-03-24 21:26:19Z jody $
 */
public class DeckIterator implements Iterator<Deck> {
    /** The default number of cards in a deck. */
    public static final int DEFAULT_NUMBER_OF_CARDS = Deck.DECK_SIZE;

    /** The card IDs for the current deck. */
    private int[] cardIDs;

    /** The cards of the current deck. */
    private Card[] cards;

    /** The number of cards per deck. */
    private int numCards;

    /** The number of distinct decks. */
    private BigInteger maxDistinctDecks;

    /** Iteration cursor. */
    private BigInteger currentIteration;

    /**
     * Constructor that uses default number of cards.
     */
    public DeckIterator() {
        this(DEFAULT_NUMBER_OF_CARDS);
    }

    /**
     * Constructor for iterator over decks of cards with specified size.
     * @param numberOfCards the number of cards per deck
     */
    public DeckIterator(final int numberOfCards) {
        this.numCards = numberOfCards;
        this.cardIDs = new int[numberOfCards];
        this.cards = new Card[numberOfCards];
        this.maxDistinctDecks = BigInteger.ONE;
        for (int i = 2; i <= numberOfCards; i++) {
            this.maxDistinctDecks
                    = this.maxDistinctDecks.multiply(new BigInteger("" + i));
        }
        for (int i = 0; i < numberOfCards; i++) {
            this.cardIDs[i] = i;
            this.cards[i] = new Card(i);
        }
        this.currentIteration = BigInteger.ZERO;
    }

    /**
     * Returns true if next() will return a new deck.
     * @return true if there is another deck; false otherwise
     */
    @Override
    public boolean hasNext() {
        return (this.currentIteration.compareTo(this.maxDistinctDecks) < 0);
    }

    /**
     * Returns the next deck in the iteration sequence.
     * @return the next deck
     */
    @Override
    public Deck next() {
        if (!this.currentIteration.equals(BigInteger.ZERO)) {
            int i = numCards - 1;
            while (cardIDs[i - 1] >= cardIDs[i]) {
                i = i - 1;
            }
            int j = this.numCards;
            while (cardIDs[j - 1] <= cardIDs[i - 1]) {
                j = j - 1;
            }
            swap(i - 1, j - 1); // Swap values at positions (i-1) and (j-1)
            i++;
            j = this.numCards;
            while (i < j) {
                swap(i - 1, j - 1);
                i++;
                j--;
            }
        }
        this.currentIteration = this.currentIteration.add(BigInteger.ONE);
        return new Deck(this.cards);
    }

    /**
     * Swaps cards at specified locations.
     * @param loc1 card location
     * @param loc2 card location
     */
    private void swap(final int loc1, final int loc2) {
        int temp = this.cardIDs[loc1];
        this.cardIDs[loc1] = this.cardIDs[loc2];
        this.cardIDs[loc2] = temp;
        Card tc = this.cards[loc1];
        this.cards[loc1] = this.cards[loc2];
        this.cards[loc2] = tc;
    }

    /** Number of cards per deck for internal testing. */
    private static final int TEST_NUM_CARDS_PER_DECK = 4;
    /**
     * Driver for internal testing.
     * @param args ignored
     */
    public static void main(final String[] args) {
        int numCardsPerDeck = TEST_NUM_CARDS_PER_DECK;
        DeckIterator di = new DeckIterator(numCardsPerDeck);
        Deck currentDeck = null;
        long numPermutations = 1;
        for (int i = 2; i <= numCardsPerDeck; i++) {
            numPermutations *= i;
        }
        System.out.println("Total of " + numPermutations + " decks.");
        for (int i = 0; i < numPermutations; i++) {
            System.out.println("hasNext() == " + di.hasNext());
            currentDeck = di.next();
            System.out.println(currentDeck);
        }
        System.out.println("Last deck processed.");
        System.out.println("hasNext() == " + di.hasNext());
    }
}
