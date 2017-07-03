
package redistrictsim;

import java.util.*;
import org.hamcrest.CoreMatchers;
import org.junit.Test;
import static org.junit.Assert.*;
import org.hamcrest.Matcher;

/**
 *
 * @author Eli S
 */
public class ReDistrictSimTest {
    
    public ReDistrictSimTest() {
    }

    /**
     * Test of populateVoters method, of class ReDistrictSim.
     */
    //TO-DO Implementation - test it is populating with voters
    @Test
    public void testPopulateVoters() {
        ArrayList<Voter> testCensusA = new ArrayList<>();
        testCensusA.add(new Voter(1));
        testCensusA.add(new Voter(1));
        testCensusA.add(new Voter(0));
        testCensusA.add(new Voter(0));
        
        ArrayList<Voter> testVoter = ReDistrictSim.populateVoters(1);
        //assertTrue(testCensusA.equals(testVoter));        //Current assert testing if populated with voters
        assertFalse(testVoter.isEmpty());
        
    }

    /**
     * Test of populateVotersRandomly method, of class ReDistrictSim.
     * If list is filled with a voter, it will have a partyName field
     */
    @Test
    public void testPopulateVotersRandomly() {
        ArrayList<Voter> testRandomVoterList = ReDistrictSim.populateVotersRandomly(1);
        CoreMatchers.containsString(testRandomVoterList.get(0).getPartyName());
    }

    /**
     * Test of chooseRandomPartyID method, of class ReDistrictSim.
     * Will alway return a 1 or 0 for a random party ID
     */
    @Test
    public void testChooseRandomPartyID() {
        
        int randomNum = ReDistrictSim.chooseRandomPartyID();
        assertTrue("Always returns 1 or 0", randomNum == 0 || randomNum == 1);
        
    }
    
}
