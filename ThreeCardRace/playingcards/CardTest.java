package swefun.cardgames.playingcards;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import org.junit.Test;
/**
 * Tests for Card class.
 *
 * @author  Dr. Jody Paul
 * @version 1.2 $Revision: 295 $
 */
public class CardTest {
    /** The Clubs suit. */
    private static final Suit CLUBS = Suit.valueOf("CLUBS");
    /** The Diamonds suit. */
    private static final Suit DIAMONDS = Suit.valueOf("DIAMONDS");
    /** The Hearts suit. */
    private static final Suit HEARTS = Suit.valueOf("HEARTS");
    /** The Spades suit. */
    private static final Suit SPADES = Suit.valueOf("SPADES");
    /** The Seven rank. */
    private static final Rank SEVEN = Rank.valueOf("SEVEN");
    /** The Jack rank. */
    private static final Rank JACK = Rank.valueOf("JACK");
    /** The Queen rank. */
    private static final Rank QUEEN = Rank.valueOf("QUEEN");

    /** Queen of Hearts cardID. */
    private static final int QH_ID
                = (HEARTS.ordinal() * Rank.values().length) + QUEEN.ordinal();

    /** Seven of Clubs cardID. */
    private static final int SC_ID
                = (CLUBS.ordinal() * Rank.values().length) + SEVEN.ordinal();

    /**
     * Verify default card construction and access.
     * Exercises equals and hashCode methods.
     */
    @Test
    public final void defaultCardTest() {
        Card cardDefault = new Card();
        assertNotNull(cardDefault.rank());
        assertNotNull(cardDefault.suit());
        assertEquals(cardDefault, (new Card()));
        assertEquals(cardDefault.hashCode(), (new Card()).hashCode());
    }

    /**
     * Verify Queen of Hearts construction and access.
     * Exercises equals and hashCode methods.
     */
    @Test
    public final void queenOfHeartsTest() {
        Card cardQH = new Card(QUEEN, HEARTS);
        assertEquals(QUEEN, cardQH.rank());
        assertEquals(HEARTS, cardQH.suit());
        assertEquals(QH_ID, cardQH.cardID());
        assertEquals("Queen of Hearts", cardQH.toString());
        assertEquals(cardQH, (new Card(QH_ID)));
        assertEquals(cardQH.hashCode(), (new Card(QH_ID)).hashCode());
    }

    /**
     * Verify 7 of Clubs construction and access.
     * Exercises equals and hashCode methods.
     */
    @Test
    public final void sevenOfClubsTest() {
        Card card7C = new Card(SEVEN, CLUBS);
        assertEquals(SEVEN, card7C.rank());
        assertEquals(SC_ID, card7C.cardID());
        assertEquals(CLUBS, card7C.suit());
        assertEquals("Seven of Clubs", card7C.toString());
        assertEquals(card7C, (new Card(SC_ID)));
        assertEquals(card7C.hashCode(), (new Card(SC_ID)).hashCode());
    }

    /**
     * Verify copy construction.
     * Exercises equals and hashCode methods.
     */
    @Test
    public final void copyTest() {
        Card cardDefault = new Card();
        Card copyD = new Card(cardDefault);
        assertEquals(cardDefault, copyD);
        assertEquals(cardDefault.hashCode(), copyD.hashCode());
        Card cardJD = new Card(JACK, DIAMONDS);
        Card copyJD = new Card(cardJD);
        assertEquals(cardJD, copyJD);
        assertEquals(cardJD.hashCode(), copyJD.hashCode());
        assertEquals(DIAMONDS, copyJD.suit());
        assertEquals(JACK, copyJD.rank());
    }

    /** Verify behavior of equals in presence of null. */
    @Test
    public final void equalsNullTest() {
        Card card7C = new Card(SEVEN, CLUBS);
        assertFalse(card7C.equals(null));
    }

    /** Verify behavior of equals with object of different class. */
    @Test
    public final void equalsOtherClassTest() {
        Card card7C = new Card(SEVEN, CLUBS);
        assertFalse(card7C.equals("Seven of Clubs"));
    }
}
