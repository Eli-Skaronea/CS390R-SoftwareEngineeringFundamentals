package swefun.cardgames.playingcards;
/**
 * Model cards as from a deck of playing cards.
 * A card is represented by a Rank+Suit pair.
 * Each unique Rank+Suit pair is also identified by
 * a unique integer value, referred to as its ID,
 * which may be used for new card construction.
 *
 * @author Dr. Jody Paul
 * @version 1.6 $Id: Card.java 295 2017-03-24 21:26:19Z jody $
 */
public class Card {
    /** The rank of this card. */
    private Rank rank;

    /** The suit of this card. */
    private Suit suit;

    /** An integer uniquely identifying this rank-suit pair. */
    private int myCardID;

    /**
     * Constructor for a card with arbitrary rank and suit.
     */
    public Card() {
        this(Rank.valueOf("QUEEN"), Suit.valueOf("HEARTS"));
    }

    /**
     * Card constructor.
     * @param cardRank the rank for this card
     * @param cardSuit the suit for this card
     */
    public Card(final Rank cardRank, final Suit cardSuit) {
        this.rank = cardRank;
        this.suit = cardSuit;
        this.myCardID = generateCardID(this.rank, this.suit);
    }

    /**
     * Card copy constructor.
     * @param card the card to be copied
     */
    public Card(final Card card) {
        this(card.rank, card.suit);
    }

    /**
     * Card constructor from a card ID.
     * @param cardID the ID of the card to construct
     */
    public Card(final int cardID) {
        this(retrieveRank(cardID), retrieveSuit(cardID));
    }

    /**
     * Utility to generate an integer card ID corresponding
     * to the specified Rank+Suit pair.
     * <P><CODE>
     * cardID = (cardSuit.ordinal() * Rank.values().length))
     *          + cardRank.ordinal()
     * </CODE></P>
     * @param cardRank the rank of the card
     * @param cardSuit the suit of the card
     * @return the card ID associated with the given rank and suit
     */
    public static int generateCardID(final Rank cardRank, final Suit cardSuit) {
        int cardID = cardSuit.ordinal() * Rank.values().length;
        cardID += cardRank.ordinal();
        return cardID;
    }

    /**
     * Utility to retrieve rank from an integer card ID.
     * @param cardID the card ID to process
     * @return the rank associated with the given card ID
     */
    public static Rank retrieveRank(final int cardID) {
        return Rank.values()[cardID % Rank.values().length];
    }

    /**
     * Utility to retrieve suit from an integer card ID.
     * @param cardID the card ID to process
     * @return the suit associated with the given card ID
     */
    public static Suit retrieveSuit(final int cardID) {
        return Suit.values()[(int) Math.floor(cardID / Rank.values().length)];
    }

    /**
     * Access the rank of this card.
     * @return the rank of this card
     */
    public Rank rank() {
        return this.rank;
    }

    /**
     * Access the suit of this card.
     * @return the suit of this card
     */
    public Suit suit() {
        return this.suit;
    }

    /**
     * Access the ID of this card.
     * @return the ID of this card
     */
    public int cardID() {
        return this.myCardID;
    }

    /**
     * Render this card as commonly used phrase:
     *   <i>rank</i> of <i>suit</i>.
     * Examples: "Five of Diamonds", "King of Hearts"
     * @return display name of this card
     */
    @Override
    public String toString() {
        return this.rank + " of " + this.suit;
    }

    /**
     * Overridden equals method;
     *   both rank and suit must be the same.
     * @param obj the object to compare with this card
     * @return true if and only if rank and suit match
     */
    @Override
    public boolean equals(final Object obj) {
        if (obj == null) {
            return false;
        }
        if (!(obj.getClass().equals(this.getClass()))) {
            return false;
        }
        Card cobj = (Card) obj;
        return (this.rank.equals(cobj.rank) && this.suit.equals(cobj.suit));
    }

    @Override
    public int hashCode() {
        return (this.rank.hashCode() + this.suit.hashCode());
    }

    /**
     * Internal testing.
     * @param args ignored
     */
    public static void main(final String[] args) {
        Card c = new Card();
        Card d = new Card(c);
        assert (c != d);
        assert (c.equals(d));
        assert (c.cardID() == d.cardID());
    }
}
