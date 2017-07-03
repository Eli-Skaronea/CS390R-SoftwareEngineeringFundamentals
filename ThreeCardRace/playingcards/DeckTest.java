package swefun.cardgames.playingcards;

import java.util.List;
import java.util.ArrayList;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import org.junit.Test;
import static org.hamcrest.core.Is.is;
/**
 * Tests for class Deck.
 *
 * @author  Dr. Jody Paul
 * @version 1.4 $Revision: 295 $
 */
public class DeckTest {
    /** The Clubs suit. */
    private static final Suit CLUBS = Suit.valueOf("CLUBS");
    /** The Hearts suit. */
    private static final Suit HEARTS = Suit.valueOf("HEARTS");
    /** The Spades suit. */
    private static final Suit SPADES = Suit.valueOf("SPADES");
    /** The Ace rank. */
    private static final Rank ACE = Rank.valueOf("ACE");
    /** The Four rank. */
    private static final Rank FOUR = Rank.valueOf("FOUR");
    /** Location of Ace of Clubs. */
    private static final int ACECLUBS_LOC
                    = (CLUBS.ordinal() * Rank.values().length) + ACE.ordinal();
    /** Location of Ace of Hearts. */
    private static final int ACEHEARTS_LOC
                    = (HEARTS.ordinal() * Rank.values().length) + ACE.ordinal();

    /**
     * Verify basic behavior of default deck.
     */
    @Test
    public void defaultDeckTest() {
        Deck defaultDeck = new Deck();
        assertEquals(Deck.DECK_SIZE, defaultDeck.size());
        assertEquals(ACE, defaultDeck.getCard(ACE.ordinal()).rank());
        assertEquals(HEARTS,
           defaultDeck.getCard(HEARTS.ordinal() * Rank.values().length).suit());
        assertEquals(new Card(ACE, CLUBS),
                     defaultDeck.getCard(ACECLUBS_LOC));
        for (int i = 0; i < Deck.DECK_SIZE; i++) {
            assertEquals(Rank.values()[i % Rank.values().length],
                         defaultDeck.getCard(i).rank());
            assertEquals(Suit.values()[i / Rank.values().length],
                         defaultDeck.getCard(i).suit());
        }
    }

    /**
     * Verify behavior of equals method with default deck.
     */
    @Test
    public void defaultDeckEqualsTest() {
        Deck defaultDeck0 = new Deck();
        assertEquals(defaultDeck0, defaultDeck0);
        Deck defaultDeck1 = new Deck();
        assertEquals(new Deck(), new Deck());
        assertEquals(defaultDeck0, defaultDeck1);
        assertThat(defaultDeck0.equals(null), is(false));
    }

    /**
     * Verify behavior of equals method with modified decks.
     * Depends on correct behavior of mutator methods.
     */
    @Test
    public void modifiedDeckEqualsTest() {
        Card[] cards0 = {new Card(Rank.DEUCE, Suit.HEARTS),
                         new Card(Rank.SEVEN, Suit.SPADES),
                         new Card(Rank.QUEEN, Suit.DIAMONDS),
                         new Card(Rank.EIGHT, Suit.CLUBS)};
        Deck deck0 = new Deck(cards0);
        Deck deck1 = new Deck();
        assertThat(deck0.equals(null), is(false));
        assertThat(deck0.equals(deck1), is(false));
        Deck deck2 = new Deck(new Card[0]);
        assertThat(deck0.equals(deck2), is(false));
        deck2.add(new Card(Rank.DEUCE, Suit.HEARTS));
        assertThat(deck0.equals(deck2), is(false));
        deck2.add(new Card(Rank.SEVEN, Suit.SPADES));
        deck2.add(new Card(Rank.QUEEN, Suit.DIAMONDS));
        deck2.add(new Card(Rank.EIGHT, Suit.CLUBS));
        assertEquals(deck0, deck2);
        deck2.setCard(0, deck2.getCard(2));
        assertThat(deck0.equals(deck2), is(false));
    }

    /**
     * Verify behavior consistency of hashCode and equals methods.
     * Depends on correct behavior of mutator methods.
     */
    @Test
    public void hashCodeTest() {
        Card[] cards0 = {new Card(Rank.JACK, Suit.CLUBS),
                         new Card(Rank.TREY, Suit.SPADES),
                         new Card(Rank.KING, Suit.DIAMONDS),
                         new Card(Rank.NINE, Suit.HEARTS)};
        assertEquals(new Deck().hashCode(), new Deck().hashCode());
        Deck deck0 = new Deck(cards0);
        int hashcode0 = deck0.hashCode();
        Deck deck1 = new Deck(new Card[0]);
        deck1.add(new Card(Rank.NINE, Suit.HEARTS));
        deck1.add(0, new Card(Rank.TREY, Suit.SPADES));
        deck1.add(1, new Card(Rank.KING, Suit.DIAMONDS));
        deck1.add(0, new Card(Rank.JACK, Suit.CLUBS));
        assertEquals(hashcode0, deck1.hashCode());
    }

    /**
     * Verify that no cards are lost during shuffling.
     * Also exercises deck iterator.
     */
    @Test
    public void shuffledDeckTest() {
        Deck shuffled = new Deck();
        shuffled.shuffle();
        assertEquals(Deck.DECK_SIZE, shuffled.size());
        List<Card> cards = new ArrayList<Card>(Deck.DECK_SIZE);
        for (Card c : shuffled) {
            cards.add(c);
        }
        assertEquals(Deck.DECK_SIZE, cards.size());
        Deck defaultDeck = new Deck();
        for (Card c : defaultDeck) {
            assertTrue(cards.contains(c));
        }
    }

    /** Minimum number of displaced cards for reasonable shuffle. */
    private static final int SHUFFLE_OK = 10;
    /**
     * Check "reasonableness" of shuffle.
     */
    @Test
    public void shuffledDeckOKTest() {
        Deck shuffled = new Deck();
        shuffled.shuffle();
        int displaced = 0;
        Deck fresh = new Deck();
        for (int i = 0; i < Deck.DECK_SIZE; i++) {
            if (!fresh.getCard(i).equals(shuffled.getCard(i))) {
                ++displaced;
            }
        }
        assertTrue(SHUFFLE_OK < displaced);
    }

    /**
     * Test deck mutator setCard.
     */
    @Test
    public void mutateDeck() {
        Deck defaultDeck = new Deck();
        assertEquals(Deck.DECK_SIZE, defaultDeck.size());
        assertEquals(new Card(ACE, CLUBS),
                     defaultDeck.getCard(ACECLUBS_LOC));
        assertEquals(new Card(ACE, HEARTS),
                     defaultDeck.getCard(ACEHEARTS_LOC));
        // Swap ace of hearts with ace of clubs.
        Card cardHolder = defaultDeck.getCard(ACECLUBS_LOC);
        defaultDeck.setCard(ACECLUBS_LOC, defaultDeck.getCard(ACEHEARTS_LOC));
        defaultDeck.setCard(ACEHEARTS_LOC, cardHolder);
        int newAceClubsLoc = ACEHEARTS_LOC;
        int newAceHeartsLoc = ACECLUBS_LOC;
        assertEquals(Deck.DECK_SIZE, defaultDeck.size());
        assertEquals(ACE, defaultDeck.getCard(newAceClubsLoc).rank());
        assertEquals(CLUBS, defaultDeck.getCard(newAceClubsLoc).suit());
        assertEquals(ACE, defaultDeck.getCard(newAceHeartsLoc).rank());
        assertEquals(HEARTS, defaultDeck.getCard(newAceHeartsLoc).suit());
        // Swap four of spades with ace of hearts (at its new location).
        int fourSpadesLoc = (SPADES.ordinal() * Rank.values().length)
                            + FOUR.ordinal();
        cardHolder = defaultDeck.getCard(fourSpadesLoc);
        defaultDeck.setCard(fourSpadesLoc,
                            defaultDeck.getCard(newAceHeartsLoc));
        defaultDeck.setCard(newAceHeartsLoc, cardHolder);
        assertEquals(FOUR, defaultDeck.getCard(newAceHeartsLoc).rank());
        assertEquals(SPADES, defaultDeck.getCard(newAceHeartsLoc).suit());
        assertEquals(ACE, defaultDeck.getCard(fourSpadesLoc).rank());
        assertEquals(HEARTS, defaultDeck.getCard(fourSpadesLoc).suit());
    }

    /**
     * Check behavior of the deal method.
     */
    @Test
    public void dealTest() {
        Deck decky = new Deck();
        Deck refdeck = new Deck();
        for (Card c : refdeck) {
            assertEquals(c, decky.deal());
        }
        assertNull(decky.deal());
    }
}
