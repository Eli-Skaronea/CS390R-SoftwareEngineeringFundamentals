package swefun.cardgames.playingcards;

import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;
/**
 * A hand comprised of cards.
 * A hand is also represented by a unique long integer value.
 * @author Dr. Jody Paul
 * @version 1.4 $Id: Hand.java 295 2017-03-24 21:26:19Z jody $
 */
public class Hand implements Iterable<Card> {
    /** The default number of cards in a hand. */
    static final int HAND_SIZE = 5;

    /** Cards in this hand. */
    private final List<Card> handList = new ArrayList<Card>();

    /**
     * Construct a hand of default size from a default deck of cards.
     */
    public Hand() {
        this(HAND_SIZE);
    }

    /**
     * Construct a hand of specified size from a default deck of cards.
     * @param handSize the number of cards in this hand
     */
    public Hand(final int handSize) {
        this(handSize, new Deck());
    }

    /**
     * Construct a hand from the beginning of a given deck.
     * @param handSize the number of cards in this hand
     * @param fromDeck the deck from which to draw cards
     */
    public Hand(final int handSize, final Deck fromDeck) {
        this(handSize, fromDeck, 0);
    }

    /**
     * Construct a hand given a deck and a starting position
     *     within that deck.
     * @param handSize the number of cards in this hand
     * @param fromDeck the deck from which to draw cards
     * @param start the starting index of cards in the deck
     */
    public Hand(final int handSize, final Deck fromDeck, final int start) {
        for (int i = 0; i < handSize; i++) {
            this.handList.add(fromDeck.getCard(i + start));
        }
    }

    /**
     * Create a new hand given a hand encoding.
     * @param encoding the encoding of the desired hand
     */
    public Hand(final long encoding) {
        long carrier = encoding;
        int  currentCard;

        for (int i = HAND_SIZE - 1; i >= 0; i--) {
            currentCard = (int) carrier % Deck.ENCODER;
            this.handList.add(new Card(currentCard));
            carrier = (carrier - currentCard) / Deck.ENCODER;
        }
    }

    /**
     * Hand copy constructor.
     * Uses references to the original cards.
     * @param orig the hand to be copied
     */
    public Hand(final Hand orig) {
        for (Card c : orig) {
            this.handList.add(c);
        }
    }

    @Override
    public Iterator<Card> iterator() {
        return handList.iterator();
    }

    /**
     * Two hands are considered equivalent if they both
     * contain equivalent cards in the same order.
     * @param obj the object to compare with this hand
     * @return true if and only if both hands have the same
     *         cards in the same order
     */
    @Override
    public boolean equals(final Object obj) {
        if (obj == null) {
            return false;
        }
        if (!(obj.getClass().equals(this.getClass()))) {
            return false;
        }
        Hand hobj = (Hand) obj;
        for (int i = 0; i < getSize(); i++) {
            if (!this.handList.get(i).equals(hobj.handList.get(i))) {
                return false;
            }
        }
        return true;
    }

    @Override
    public int hashCode() {
        int code = 0;
        for (Card c : this.handList) {
            code += c.hashCode();
        }
        return code;
    }

    /**
     * Retrieve the card at a given position within this hand.
     * <p><b>Precondition: </b>
     *     <CODE>0 &le; which &le; </CODE>size of hand</p>
     * @param which the index of the desired card
     * @return the card at the specified index
     *         or at index 0 if precondition is not met
     */
    public final Card getCard(final int which) {
        if (0 <= which && which < this.handList.size()) {
            return this.handList.get(which);
        }
        return this.handList.get(0);
    }

    /**
     * Set a specified card of this hand.
     * <p><b>Precondition: </b>
     *     <CODE>0 &le; which &le; </CODE>size of hand</p>
     * @param which the index of the card to change
     * @param newCard the card to be placed at the specified index
     */
    public final void setCard(final int which, final Card newCard) {
        if (0 <= which && which < this.handList.size()) {
            handList.set(which, newCard);
        }
    }

    /**
     * Access the number of cards that make up this hand.
     * @return the size of this hand
     */
    public int getSize() {
        return this.handList.size();
    }

    /**
     * Sort this hand by ascending card rank; ignores suit.
     */
    public void sort() {
        int min;
        Card temp;

        for (int i = 0; i < this.handList.size(); i++) {
            min = i;
            for (int j = i; j < this.handList.size(); j++) {
                if ((this.handList.get(j))
                         .rank()
                         .compareTo((this.handList.get(min)).rank()) < 0) {
                    min = j;
                }
            }
            temp = this.handList.get(i);
            this.handList.set(i, this.handList.get(min));
            this.handList.set(min, temp);
        }
    }

    /**
     * Encode this hand as an integer value.
     * Uses encoding scheme determined by the {@link Deck} class.
     * @return the encoding of this hand
     */
    public final long encode() {
        long encoding = 0;
        for (int i = this.handList.size() - 1; i >= 0; i--) {
            encoding = (Deck.ENCODER * encoding)
                       + this.handList.get(i).cardID();
        }
        return encoding;
    }

    /**
     * Render this hand as a string of delimited card names.
     * @return displayable string for this hand
     */
    @Override
    public String toString() {
        String s = "";
        for (int i = 0; i < this.handList.size(); i++) {
            s += "[" + handList.get(i) + "]";
        }
        return s;
    }

    /** Arbitrary hand index for internal testing. */
    private static final int TEST_START_INDEX = 19;
    /**
     * Internal unit testing.
     * @param args ignored
     */
    public static void main(final String[] args) {
        Hand localHand = new Hand(HAND_SIZE, new Deck());
        System.out.println(localHand);
        System.out.println("  Hand encoding: " + localHand.encode());
        localHand = new Hand(HAND_SIZE, new Deck(), TEST_START_INDEX);
        System.out.println(localHand);
        System.out.println("  Hand encoding: " + localHand.encode());
    }
}
