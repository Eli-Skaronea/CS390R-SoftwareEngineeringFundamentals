package swefun.cardgames.playingcards;

import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;
/**
 * Represents a deck of playing cards.
 * Position indices are zero-based, such that index 0
 * represents the "top" of a non-empty deck.
 * @author Dr. Jody Paul
 * @version 1.4 $Id: Deck.java 295 2017-03-24 21:26:19Z jody $
 */
public class Deck implements Iterable<Card> {
    /** Number of cards in a standard deck. */
    public static final int DECK_SIZE
              = Suit.values().length * Rank.values().length;

    /** Minimum number of bits needed to encode a unique card. */
    private static final int MIN_BITS
        = (int) Math.ceil(Math.log((double) DECK_SIZE) / Math.log(2.0));

    /**
     * Encoding constant used in deck-encoding formula:
     * &sum; cardID[i] * ENCODER<SUP>i</SUP> ,&nbsp;
     *    &forall;i &#8717; 0 &le; i &lt; DECK_SIZE
     * .
     */
    static final int ENCODER = (int) Math.pow(2, MIN_BITS);

    /** List of cards in this deck. */
    private final List<Card> cardList = new ArrayList<Card>(DECK_SIZE);

    /** Constructs an ordered deck of cards. */
    public Deck() {
        for (Suit s : Suit.values()) {
            for (Rank r : Rank.values()) {
                this.cardList.add(new Card(r, s));
            }
        }
    }

    /**
     * Constructs a deck with references to cards
     * provided by the parameter.
     * @param cards the cards forming the new deck
     */
    public Deck(final List<Card> cards) {
        for (Card c : cards) {
                this.cardList.add(c);
        }
    }

    /**
     * Constructs a deck of cards with references
     * to cards provided by the parameter.
     * @param cards the cards forming the new deck
     */
    public Deck(final Card[] cards) {
        for (Card c : cards) {
                this.cardList.add(c);
        }
    }

    /**
     * Returns the number of cards in this deck.
     * @return the size of this deck
     */
    public int size() {
        return this.cardList.size();
    }

    /**
     * Returns the card at a specified position
     * in this deck.
     * @param which the position of the desired card
     * @return the card at the specified position
     */
    public Card getCard(final int which) {
        return cardList.get(which);
    }

    /**
     * Adds the specified card to the bottom of this deck.
     * Note that this makes no requirement as to whether or not
     * the specified card already exists in the deck;
     * thus, duplicate references may result.
     * @param newCard the card to be added
     * @return true if the contents of the deck was changed
     */
    public boolean add(final Card newCard) {
        return cardList.add(newCard);
    }

    /**
     * Inserts the specified card at the specified position
     * in this deck.
     * Shifts the card at the specified index (if any) and
     * any subsequent cards one position to the right (increments
     * their indices by one).
     * @param index index at which the specified card is to be inserted
     * @param newCard the card to be added
     * @throws IndexOutOfBoundsException if the index is out of range
     *             <CODE>(index &lt; 0 || index &gt; this.size())</CODE>
     */
    public void add(final int index, final Card newCard) {
        cardList.add(index, newCard);
    }

    /**
     * Removes the specified card from this deck if it is present.
     * If this deck does not contain the card, the deck is unchanged.
     * <P>More formally, removes the card with the lowest
     * index <CODE>i</CODE> such that
     * <CODE>(card==null ? get(i)==null : card.equals(get(i)))</CODE>
     * (if such an element exists).
     * </P>
     * <P>Returns true if this list contained the specified element
     * (or equivalently, if this list changed as a result of the call).
     * </P>
     * @param card the card to be removed
     * @return true if the contents of the deck was changed
     */
    public boolean remove(final Card card) {
        return cardList.remove(card);
    }

    /**
     * Removes the card at the specified position
     * in this deck.
     * Shifts any subsequent cards one position to the left
     * (decrements their indices by one).
     * Returns the card that was removed from the list.
     * @param index index of the card to be removed
     * @return the card previously at the specified position
     * @throws IndexOutOfBoundsException if the index is out of range
     *             <CODE>(index &lt; 0 || index &gt;= this.size())</CODE>
     */
    public Card remove(final int index) {
            return cardList.remove(index);
    }

    /**
     * Specifies the card at a given position by exchanging cards.
     * Puts the card currently at that position into the position
     * vacated by the specified card.
     * Behavior maintains the integrity of the deck;
     * that is, the deck contains the same cards before and
     * after invocation of this method.
     * <P><B>Preconditions:</B>
     * <UL>
     * <LI>The specified card must already exist within the deck.</LI>
     * <LI>The specified position must be valid</LI>
     * </UL>
     * Behavior when preconditions are not met is undefined.
     * Exceptions may be thrown if preconditions are not met.
     *
     *  @param  which   Location in which to put specified card
     *  @param  inCard  The specified Card
     *  @return The card now at the indicated position
     */
    public Card setCard(final int which, final Card inCard) {
        // Find inCard's current position.
        int inCardIndex = this.cardList.indexOf(inCard);
        // Swap the two cards.
        Card tempCard = this.cardList.get(which);
        this.cardList.set(which, this.cardList.get(inCardIndex));
        this.cardList.set(inCardIndex, tempCard);
        return this.cardList.get(which);
    }

    @Override
    public Iterator<Card> iterator() {
        return this.cardList.iterator();
    }

    /**
     * Removes the card from the beginning of the deck.
     * @return the card that was at the beginning of the deck;
     *         null if deck contains no cards
     */
    public Card deal() {
        if (this.cardList.size() < 1) {
            return null;
        }
        return this.cardList.remove(0);
    }
    /**
     * Performs a simple shuffle by exchanging cards.
     * Uses {@link java.util.Collections#shuffle(List)}
     */
    public void shuffle() {
        java.util.Collections.shuffle(this.cardList);
    }

    /**
     * Returns string representation of every card in this deck
     * and in the order they appear in the deck.
     * @return displayable string for this deck
     */
    @Override
    public String toString() {
        String deckString = "";
        for (Card c : this.cardList) {
            deckString += c + "   ";
        }
        return deckString;
    }

    /**
     * Overridden equals method:
     *   number and order of equivalent cards must be the same.
     * @param obj the object to compare with this deck
     * @return true if and only all if cards match in order
     */
    @Override
    public boolean equals(final Object obj) {
        if (obj == null) {
            return false;
        }
        if (!(obj.getClass().equals(this.getClass()))) {
            return false;
        }
        Deck dobj = (Deck) obj;
        boolean equivalent = true;
        if (dobj.size() == this.size()) {
            for (int i = 0; i < this.size(); i++) {
                if (!dobj.getCard(i).equals(this.getCard(i))) {
                    equivalent = false;
                }
            }
        } else {
            equivalent = false;
        }
        return equivalent;
    }

    @Override
    public int hashCode() {
        long encoding = 0;
        for (Card c : this.cardList) {
            encoding = (ENCODER * encoding) + c.cardID();
        }
        return (new Long(encoding).intValue());
    }

    /**
     * Internal unit testing.
     * @param args ignored
     */
    public static void main(final String[] args) {
        System.out.println("DECK_SIZE = " + DECK_SIZE);
        Deck localDeck = new Deck();
        assert (DECK_SIZE == localDeck.size());
        System.out.println("New Deck.  Top card: " + localDeck.getCard(0));
        localDeck.shuffle();
        System.out.println("Shuffled.  Top card: " + localDeck.getCard(0));
        localDeck.shuffle();
        System.out.println("Shuffled.  Top card: " + localDeck.getCard(0));
    }
}
