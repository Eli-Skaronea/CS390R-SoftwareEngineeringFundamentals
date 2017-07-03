
package redistrictsim;

import java.util.ArrayList;
import java.util.List;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Eli S
 */
public class RegionTest {
    public ArrayList<Voter> testCensus = new ArrayList<>();
    
    
        
    public RegionTest() {
        testCensus.add(new Voter(0));
        testCensus.add(new Voter(1));
        testCensus.add(new Voter(0));
        testCensus.add(new Voter(1));
    }

    /**
     * Test of drawRegion method, of class Region.
     * TO-DO: Test the output fits a default layout
     */
//    @Test
//    public void testDrawRegion() {
//        
//        ArrayList<Voter> testCensus = new ArrayList<>();
//        
//        for(int i = 0; i < 4; i++){
//            testCensus.add(new Voter(0));
//        }
//        Region testRegion = new Region(5, testCensus);
//        
//        assertEquals(testOutput, testRegion.drawRegion(testCensus));
//        
//    }

    /**
     * Test of drawRegionA method, of class Region.
     * TO-DO: Test the method draws a default layout
     */
//    @Test
//    public void testDrawRegionA() {
//    }
    
    /**
     * Test of checkLeftNeighbor method, or class Region.
     * This test creates a 2 x 2 grid A, B, A, B
     * Returns "B" for the left voter from census when index is 0
     * Returns "empty" when left voter is empty
     * 
     */
    @Test
    public void testCheckLeftNeighbor(){
        int index = 0;
        RegionTest regionTest = new RegionTest();
        Region simpleTestRegion = new Region (4, testCensus);
        
        String leftNeighbor = simpleTestRegion.checkLeftNeighbor(regionTest.testCensus, index);
        String emptyNeighbor = simpleTestRegion.checkLeftNeighbor(regionTest.testCensus, index - 1);
        
        assertEquals("B", leftNeighbor);
        assertEquals("empty", emptyNeighbor);
    }
    
    /**
     * Test of checkRightNeighbor method, or class Region.
     * This test creates a 2 x 2 grid A, B, A, B
     * Returns "A" for the for voter from census when index is 1
     * Returns "empty" when right voter is empty
     * 
     */
    @Test
    public void testCheckRightNeighbor(){
        int index = 3;
        RegionTest regionTest = new RegionTest();
        Region simpleTestRegion = new Region (4, testCensus);
        
        String rightNeighbor = simpleTestRegion.checkRightNeighbor(regionTest.testCensus, index);
        String emptyNeighbor = simpleTestRegion.checkRightNeighbor(regionTest.testCensus, index - 1);
        
        assertEquals("A", rightNeighbor);
        assertEquals("empty", emptyNeighbor);
    }
    
    /**
     * Test of checkSouthNeighbor method, or class Region.
     * This test creates a 2 x 2 grid A, B, A, B
     * Returns "A" for the south voter from census when index is 0
     * Returns "empty" when south voter is empty
     * 
     */
    @Test
    public void testCheckSouthNeighbor(){
        int index = 0;
        RegionTest regionTest = new RegionTest();
        Region simpleTestRegion = new Region (4, testCensus);
        
        String southNeighbor = simpleTestRegion.checkSouthNeighbor(regionTest.testCensus, index);
        String emptyNeighbor = simpleTestRegion.checkSouthNeighbor(regionTest.testCensus, 2);
        
        assertEquals("A", southNeighbor);
        assertEquals("empty", emptyNeighbor);
    }
    
    /**
     * Test of checkNorthNeighbor method, or class Region.
     * This test creates a 2 x 2 grid A, B, A, B
     * Returns "A" for the north voter from census when index is 0
     * Returns "empty" when north voter is empty
     * 
     */
    @Test
    public void testCheckNorthNeighbor(){
        int index = 2;
        RegionTest regionTest = new RegionTest();
        Region simpleTestRegion = new Region (4, testCensus);
        
        String northNeighbor = simpleTestRegion.checkNorthNeighbor(regionTest.testCensus, index);
        String emptyNeighbor = simpleTestRegion.checkNorthNeighbor(regionTest.testCensus, 0);
        assertEquals("A", northNeighbor);
        assertEquals("empty", emptyNeighbor);
    }
    
    /**
     * Tests checkNeighbor methods when a block has neighbors in every direction
     * This test creates a 3 x 3 grid of all 'A's
     * Returns "empty" when left voter is empty
     * 
     */
    @Test
    public void testFourNeighbors(){
        
        ArrayList<Voter> census = new ArrayList<>();
        int index = 4;
        for(int i = 0; i < 9; i++){
            census.add(new Voter(0));
        }
        Region testRegion = new Region (9, census);
        
        String northNeighbor = testRegion.checkNorthNeighbor(census, index);
        String southNeighbor = testRegion.checkSouthNeighbor(census, index);
        String leftNeighbor = testRegion.checkLeftNeighbor(census, index);
        String rightNeighbor = testRegion.checkRightNeighbor(census, index);
        
        //Make sure all the neighbors have values of "A"
        assertEquals("North neighbor is A", "A", northNeighbor);
        assertEquals("South neighbor is A","A", southNeighbor);
        assertEquals("Left neighbor is A","A", leftNeighbor);
        assertEquals("Right neighbor is A","A", rightNeighbor);
         
         
    }
}
