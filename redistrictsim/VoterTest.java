
package redistrictsim;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Eli S
 */
public class VoterTest {
    
    public VoterTest() {
    }

    /**
     * Test of getPartyName method, of class Voter.
     * When a voter is created, they should always have a partyName
     */
    @Test
    public void testGetPartyName() {
        Voter testVoterA = new Voter(0);
        Voter testVoterB = new Voter(1);
        
        
        assertEquals("A", testVoterA.getPartyName());
        assertEquals("B", testVoterB.getPartyName());
        
    }
    /**
     * Tests voterAffilliation field
     * Should always be an "A" or "B"
     */
    @Test
	public void testVoterHasAffiliation() {
		Voter testVoter = new Voter();
		String voterPartyAffiliation;

		voterPartyAffiliation = testVoter.getPartyName();

		assertTrue(voterPartyAffiliation.equals("A") ||
					voterPartyAffiliation.equals("B"));
	}
    
}
