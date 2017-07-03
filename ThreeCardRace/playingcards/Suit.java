package swefun.cardgames.playingcards;
/**
 * Enumeration class for the suits of cards.
 * Supports a color() method that retrieves the
 * color string associated with a suit:
 * Clubs = "BLACK", Diamonds = "RED",
 * Hearts = "RED", Spades = "BLACK".
 * @author Dr. Jody Paul
 * @version 1.2 $Id: Suit.java 295 2017-03-24 21:26:19Z jody $
 */
public enum Suit {
    CLUBS("Clubs", "BLACK"),
    DIAMONDS("Diamonds", "RED"),
    HEARTS("Hearts", "RED"),
    SPADES("Spades", "BLACK");

    /** The display name of this suit. */
    private String name;

    /** The color of this suit. */
    private String suitColor;

    /** The color red. */
    public static final String RED = "RED";

    /** The color black. */
    public static final String BLACK = "BLACK";

    /**
     * Construct Suit.
     * @param displayName the display name of this suit
     * @param color the color of this suit
     */
    Suit(final String displayName, final String color) {
        this.name = displayName;
        if (color.compareToIgnoreCase(RED) == 0) {
            this.suitColor = RED;
        } else if (color.compareToIgnoreCase(BLACK) == 0) {
            this.suitColor = BLACK;
        } else {
            throw (new IllegalArgumentException("Color must be "
                                                 + BLACK + " or "
                                                 + RED + "(found "
                                                 + color + ")"));
        }
    }

    /**
     * Access the color of this suit.
     * @return the color of this suit
     */
    public String color() {
        return this.suitColor;
    }

    @Override
    public String toString() {
        return this.name;
    }
}
