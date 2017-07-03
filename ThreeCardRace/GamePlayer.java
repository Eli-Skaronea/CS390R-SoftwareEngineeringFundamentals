package swefun.cardgames;
/**
* A player of a card game.
*
* @author Dr. Jody Paul
* @version 1.0 $Id: GamePlayer.java 295 2017-03-24 21:26:19Z jody $
*/
public abstract class GamePlayer {
    /** The name of this player. */
    private String playerName = "Player";

    /** Indicator of interactivity. */
    private boolean isInteractive = false;

    /**
    * Construct a player with a specified name.
    * Defaults to interactive player.
    * @param name the name of this player
    */
    public GamePlayer(final String name) {
        this(name, true);
    }

    /**
     * Construct a player with a specified name
     * and interactivity.
     * @param name the name of this player
     * @param interactive true if interaction is required; false otherwise
     */
    public GamePlayer(final String name, final boolean interactive) {
        this.playerName = name;
        this.isInteractive = interactive;
    }

    /**
     * Access name of this player.
     * @return the name of this player
     */
    public String name() {
        return this.playerName;
    }

    /**
     * Access interactivity of this player.
     * @return true if interactive; false otherwise
     */
    public boolean isInteractive() {
        return this.isInteractive;
    }

    @Override public String toString() {
        return this.playerName;
    }
}
