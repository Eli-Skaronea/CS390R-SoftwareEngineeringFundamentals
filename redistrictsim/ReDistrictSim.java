package redistrictsim;

import java.util.ArrayList;
import java.util.Scanner;

/**
 * Take a region of voters, whose party affiliation is either A or B, in an n x
 * m grid, and divide the region into districts. The number of districts and
 * party favoring will be determined by the user.
 *
 * @author Group X
 */
public class ReDistrictSim {

    private static final Scanner reader = new Scanner(System.in);

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        int numOfDistricts = askNumOfDistricts();
        int numOfVoters = numOfDistricts * numOfDistricts;
        ArrayList<Voter> census = populateVoters(numOfDistricts);
        Region region = new Region(numOfVoters, census);
        String pref = askPartyPreference();
        drawDistricts(pref, numOfDistricts, region);

        //To-DO:
        //  Determine districts in the region in a gerrymandered way with random
        //      voters given
        //  Output region with districts with better outline than current output
    }

    /**
     * Populates a list of voters given the numberOfDistricts
     *
     * @param numOfDistricts - how big a region will be (numOfDistricts^2)
     * @return List of voters the size of the region
     */
    public static ArrayList<Voter> populateVoters(int numOfDistricts) {
        ArrayList<Voter> census = new ArrayList<>();
        int numOfVoters = numOfDistricts * numOfDistricts;
        int swingVoter1 = numOfVoters - numOfDistricts;
        int swingVoter2 = (numOfVoters - numOfDistricts) - 1;
        for (int i = 0; i < numOfVoters; i++) {
            if (i == swingVoter1) {
                census.add(new Voter(0));
            } else if (i == swingVoter2) {
                census.add(new Voter(1));
            } else if ((i % 2) == 0) {
                census.add(new Voter(1));
            } else {
                census.add(new Voter(0));
            }
        }
        return census;
    }

    /**
     * Create a list of Voters with pseudo random partyAffiliation
     *
     * @param numOfDistricts - how many voters to create
     * @return List of voters, party B will always have one more voter
     */
    public static ArrayList<Voter> populateVotersRandomly(int numOfDistricts) {
        ArrayList<Voter> census = new ArrayList<>();
        int numOfVoters = numOfDistricts * numOfDistricts;
        int num_A_Voters = numOfVoters / 2;
        int num_B_Voters = numOfVoters - num_A_Voters;

        for (int i = 0; i < numOfVoters; i++) {
            if (num_A_Voters > 0 || num_B_Voters > 0) {
                census.add(new Voter(chooseRandomPartyID()));
            } else if (num_A_Voters == 0) {
                census.add(new Voter(1));
            } else {
                census.add(new Voter(0));
            }
        }
        return census;
    }

    /**
     * Ask the user for how many districts they need to create
     *
     * @return number of Districts entered by the user
     */
    private static int askNumOfDistricts() {
        System.out.println("How many districts are there?: ");
        int numberOfDistricts = Integer.parseInt(reader.nextLine());
        return numberOfDistricts;
    }

    /**
     * Ask the user for who they want to win the region
     *
     * @return the party the user entered;
     */
    private static String askPartyPreference() {
        String input = "";
        System.out.println("Do you want to favor party A, B, or neither (0)? ");
        input = reader.nextLine().toUpperCase();
        if(input.equals("A") || input.equals("B") || input.equals("0")){
            return input;
        }
        else{
            System.out.println("Not a valid input! No party assigned bias");
        }
        
        return input;
    }
    
    

    /**
     * Give a randomPartyID to a voter
     *
     * @return a 1 or 0
     */
    public static int chooseRandomPartyID() {
        int partyId = Math.random() > .5 ? (0) : (1);
        return partyId;
    }

    /**
     * Draw the districts to a region
     *
     * @param pref - the party to bias
     * @param numOfDistricts - the size of the region
     * @param r - the region to draw
     */
    public static void drawDistricts(String pref, int numOfDistricts, Region r) {

        if (pref.equalsIgnoreCase("A")) {
            System.out.println(r.drawRegionA(populateVoters(numOfDistricts)));
        } else if (pref.equalsIgnoreCase("B")) {
            System.out.println(r.drawRegionB(populateVoters(numOfDistricts)));
        } else {
            System.out.println("There is an equal split.");
        }
    }

}
