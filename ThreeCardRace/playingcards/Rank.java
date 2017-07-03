package swefun.cardgames.playingcards;

/**
 * Enumeration class for the ranks of cards.
 * Supports a value() method that returns
 * integer values: Ace = 1, ..., King = 13
 * @author Dr. Jody Paul
 * @version 1.2 $Id: Rank.java 295 2017-03-24 21:26:19Z jody $
 */
public enum Rank {
    ACE("Ace", 1),
    DEUCE("Deuce", 2),
    TREY("Trey", 3),
    FOUR("Four", 4),
    FIVE("Five", 5),
    SIX("Six", 6),
    SEVEN("Seven", 7),
    EIGHT("Eight", 8),
    NINE("Nine", 9),
    TEN("Ten", 10),
    JACK("Jack", 11),
    QUEEN("Queen", 12),
    KING("King", 13);

    /** The display name of this rank. */
    private String name;

    /** The integer value of this rank. */
    private int intRank;

    /**
     * Construct Rank.
     * @param displayName the display name of this rank
     * @param value the integer value of this rank
     */
    Rank(final String displayName, final int value) {
        this.name = displayName;
        this.intRank = value;
    }

    /**
     * Access the integer value of this rank.
     * @return the integer value of this rank
     */
    public int value() {
        return this.intRank;
    }

    @Override
    public String toString() {
        return this.name;
    }
}
