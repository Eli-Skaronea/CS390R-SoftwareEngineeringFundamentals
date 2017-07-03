package swefun.cardgames;
/**
 * Generic driver for Card Games project.
 *
 * @author Dr. Jody Paul
 * @version 1.2 $Id: GameRunner.java 295 2017-03-24 21:26:19Z jody $
 */
public class GameRunner {
    /** The version of this class source code. */
    public static final String VERSION = version("$Revision: 295 $");

    /**
     * Protected constructor for this base class.
     */
    protected GameRunner() {
        // Constructor is intentionally empty.
    }

    /**
     * Drive the card games project.
     * @param args Currently ignored.
     */
    public static void main(final String[] args) {
        System.out.println("GameRunner [Version: " + VERSION + "]");
    }

    /**
     * Produce nicely formatted version string
     * derived from Subversion keyword substitution of
     * <CODE>&#36Revision&#58</CODE>.
     * @param revision the standard subversion rendering of revision
     * @return a well-formatted version string
     */
    public static String version(final String revision) {
        String version = revision.replace("$Revision: ", "");
        version = version.replace(" $", "");
        return version;
    }
}
