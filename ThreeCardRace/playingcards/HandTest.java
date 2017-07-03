package swefun.cardgames.playingcards;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import org.junit.Test;
import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;
/**
 * The test class for Hand.
 * @author  Dr. Jody Paul
 * @version 1.2 $Revision: 295 $
 */
public class HandTest {
    /** The Clubs suit. */
    private static final Suit CLUBS = Suit.valueOf("CLUBS");
    /** The Diamonds suit. */
    private static final Suit DIAMONDS = Suit.valueOf("DIAMONDS");
    /** The Hearts suit. */
    private static final Suit HEARTS = Suit.valueOf("HEARTS");
    /** The Spades suit. */
    private static final Suit SPADES = Suit.valueOf("SPADES");
    /** The Ace rank. */
    private static final Rank ACE = Rank.valueOf("ACE");
    /** The Deuce rank. */
    private static final Rank DEUCE = Rank.valueOf("DEUCE");
    /** The Trey rank. */
    private static final Rank TREY = Rank.valueOf("TREY");
    /** The Four rank. */
    private static final Rank FOUR = Rank.valueOf("FOUR");
    /** The Five rank. */
    private static final Rank FIVE = Rank.valueOf("FIVE");
    /** The Nine rank. */
    private static final Rank NINE = Rank.valueOf("NINE");
    /** The Ten rank. */
    private static final Rank TEN = Rank.valueOf("TEN");
    /** The Jack rank. */
    private static final Rank JACK = Rank.valueOf("JACK");

    /** Card at location 0. */
    private static final Card FIRST_CARD
                = new Card(Rank.values()[0], Suit.values()[0]);

    /** Location of the Five of Diamonds in default deck. */
    private static final int FIVE_DIAMONDS_LOC
                = (DIAMONDS.ordinal() * Rank.values().length) + FIVE.ordinal();
    /** Location of Nine of Spades in default deck. */
    private static final int NINE_SPADES_LOC
                = (SPADES.ordinal() * Rank.values().length) + NINE.ordinal();
    /** Initial index of Trey of Club in default constructed hand. */
    private static final int TREY_CLUBS_INDEX
                = (CLUBS.ordinal() * Rank.values().length) + TREY.ordinal();


    /** Check default hand construction, access, sort. */
    @Test
    public final void defaultHandTest() {
        Hand defaultHand = new Hand();
        Card card0 = defaultHand.getCard(0);
        assertNotNull(card0);
        assertEquals(FIRST_CARD.rank(), card0.rank());
        assertEquals(FIRST_CARD.suit(), card0.suit());
        Card lastCard = defaultHand.getCard(defaultHand.getSize() - 1);
        assertNotNull(lastCard);
        assertEquals(Rank.values()[defaultHand.getSize() - 1], lastCard.rank());
        assertEquals(Suit.values()[0], lastCard.suit());
        defaultHand.sort();
        Iterator<Card> handIterator = defaultHand.iterator();
        assertNotNull(handIterator);
        for (int i = 0; i < defaultHand.getSize(); i++) {
            assertTrue(handIterator.hasNext());
            Card c = handIterator.next();
            assertEquals(Suit.values()[0], c.suit());
            assertEquals(Rank.values()[i], c.rank());
        }
    }

    /** Check hand comprised of all initial card of default deck. */
    @Test
    public final void aceHeartsHandTest() {
        Hand crazyHand = new Hand(0);
        for (Card c : crazyHand) {
            assertEquals(new Card(Rank.values()[0], Suit.values()[0]), c);
        }
    }

    /** Checks Royal Flush in Spades. */
    @Test
    public final void royalHandTest() {
        Hand royalHand = new Hand(Hand.HAND_SIZE, new Deck(), NINE_SPADES_LOC);
        royalHand.setCard(0, new Card(ACE, SPADES));
        for (Card c : royalHand) {
            assertEquals(SPADES, c.suit());
        }
        assertEquals(ACE, royalHand.getCard(0).rank());
        for (int i = TEN.ordinal(), c = 1; i < Rank.values().length; i++) {
            assertEquals(Rank.values()[i], royalHand.getCard(c++).rank());
        }
        Hand copyByID = new Hand(royalHand.encode());
        Hand copyByCopyConstructor = new Hand(royalHand);
        assertEquals(royalHand, copyByID);
        assertEquals(royalHand, copyByCopyConstructor);
        assertEquals(copyByID, copyByCopyConstructor);
    }

    /**
     * Checks hand iterator.
     */
    @Test
    public void iteratorTest() {
        Hand handy = new Hand();
        Iterator<Card> itr = handy.iterator();
        assertNotNull(itr);
        assertEquals(true, itr.hasNext());
        List<Card> cards = new ArrayList<Card>(Hand.HAND_SIZE);
        Suit firstSuit = Suit.values()[0];
        cards.add(new Card(ACE, firstSuit));
        cards.add(new Card(DEUCE, firstSuit));
        cards.add(new Card(TREY, firstSuit));
        cards.add(new Card(FOUR, firstSuit));
        cards.add(new Card(FIVE, firstSuit));
        assertEquals(Hand.HAND_SIZE, cards.size());
        for (int i = 0; i < Hand.HAND_SIZE; i++) {
            assertTrue(itr.hasNext());
            assertTrue(itr.next().equals(cards.get(i)));
        }
        assertFalse(itr.hasNext());
        for (Card c : handy) {
            assertTrue(cards.contains(c));
        }
    }

    /**
     * Verify simple invocations of setCard and getCard.
     */
    @Test
    public void setAndGetTest() {
        Hand handy = new Hand();
        assertEquals(new Card(TREY, CLUBS),
                     handy.getCard(TREY_CLUBS_INDEX));
        Card jackClubs = new Card(JACK, CLUBS);
        handy.setCard(TREY_CLUBS_INDEX, jackClubs);
        assertEquals(jackClubs, handy.getCard(TREY_CLUBS_INDEX));
    }

    /**
     * Verify invocation of getCard with out of bound index.
     */
    @Test
    public void getCardOutOfBoundsTest() {
        Hand handy = new Hand();
        assertEquals(handy.getCard(0), handy.getCard(handy.getSize()));
        assertEquals(handy.getCard(0), handy.getCard(-1));
    }

    /**
     * Test the encoding of a hand.
     */
    @Test
    public void encodeTest() {
        long computedCode = 0L;
        Hand handy = new Hand(Hand.HAND_SIZE, new Deck(), FIVE_DIAMONDS_LOC);
        long code = handy.encode();
        Hand codeHand = new Hand(code);
        assertNotNull(codeHand);
        for (int i = codeHand.getSize() - 1; i >= 0; i--) {
            assertEquals(handy.getCard(i), codeHand.getCard(i));
            computedCode = (Deck.ENCODER * computedCode)
            + handy.getCard(i).cardID();
        }
        assertEquals(code, computedCode);
    }

    /** Number of sorting tests to run. */
    private static final int NUM_SORT_TESTS = 20;
    /**
     * Sorting tests.
     */
    @Test
    public void sortTest() {
        Hand handy = new Hand(Hand.HAND_SIZE, new Deck(), FIVE_DIAMONDS_LOC);
        assertEquals(handy.getCard(0), new Card(FIVE, DIAMONDS));
        // Reverse the order of the hand.
        for (int i = 0; i < Hand.HAND_SIZE / 2; i++) {
            Card temp = handy.getCard(i);
            handy.setCard(i, handy.getCard(Hand.HAND_SIZE - (1 + i)));
            handy.setCard(Hand.HAND_SIZE - (1 + i), temp);
        }
        assertEquals(handy.getCard(0), new Card(NINE, DIAMONDS));
        handy.sort();
        assertEquals(handy.getCard(0), new Card(FIVE, DIAMONDS));
        for (int i = 0; i < Hand.HAND_SIZE - 1; i++) {
            assertTrue(handy.getCard(i).rank().value()
                < handy.getCard(i + 1).rank().value());
        }
        // Generate and test random hands.
        for (int h = 0; h < NUM_SORT_TESTS; h++) {
            Deck deck = new Deck();
            int ci = (int) Math.random() * (Deck.DECK_SIZE - Hand.HAND_SIZE);
            for (int i = 0; i < Hand.HAND_SIZE; i++) {
                handy.setCard(i, deck.getCard(ci + i));
            }
            handy.sort();
            for (int i = 0; i < Hand.HAND_SIZE - 1; i++) {
                assertTrue(handy.getCard(i).rank().value()
                    < handy.getCard(i + 1).rank().value());
            }
        }
    }

    /**
     * Test equals and hashCode.
     */
    @Test
    public void equalsTest() {
        Hand handy0 = new Hand();
        assertEquals(handy0, handy0);
        Hand handy1 = new Hand(Hand.HAND_SIZE, new Deck());
        assertEquals(handy1, handy1);
        assertEquals(handy0, handy1);
        assertEquals(handy0.hashCode(), handy1.hashCode());
        Hand handy2 = new Hand(Hand.HAND_SIZE, new Deck(), 0);
        assertEquals(handy0, handy2);
        assertEquals(handy0.hashCode(), handy2.hashCode());
        Hand handy3 = new Hand(Hand.HAND_SIZE, new Deck(), FIVE_DIAMONDS_LOC);
        assertFalse(handy0.equals(handy3));
        assertFalse(handy0.equals(null));
        assertFalse(handy0.equals("Hand"));
    }
}
