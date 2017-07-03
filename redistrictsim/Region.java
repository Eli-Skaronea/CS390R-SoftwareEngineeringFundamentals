package redistrictsim;

import java.util.ArrayList;

/**
 *
 * @author Group X
 */
public class Region {

    Object[][] region;
    
    /**
     * Default constructor for a Region - makes a 5 x 5 grid
     */
    public Region() {
        int walls = 5; //Default construction for 5x5 region.
        region = new Object[walls][walls];       
    }
    
    /**
     * Constructs a square region with area number of voters squared
     * @param numOfVoters - the width and length of the region
     * @param census - the voters that fill the region
     */
    public Region(int numOfVoters, ArrayList<Voter> census) {
        int walls = (int)Math.sqrt(numOfVoters);
        region = new Object[walls][walls];
        for(int i = 0; i < walls; i++){
            for(int j = 0; j < walls; j++){
                region[i][j] = census.get(j);
            }
        }
    }
    
    /**
     * Draws the region returns a string output with bias to party B
     * @param census - the voters filling the region
     * @return - an output of the region filled with voters
     */
    public String drawRegionB(ArrayList<Voter> census) {
        int j = 0;
        String regionOutput = "";
        for (int r = 0; r < region.length; r++) {
            regionOutput = regionOutput + "-----------------\n";
            for (int c = 0; c < region.length; c++) {
                if (c == 0) {
                    regionOutput = regionOutput + ("|");
                }
                region[r][c] = census.get(j).getPartyName();  // Initialize the cell
                j++;
                regionOutput = regionOutput + ("[" + region[r][c] + "]"); // Display the content of cell board
                if (c + 1 == region.length) {
                    regionOutput = regionOutput + ("|");
                }
            }
            regionOutput = regionOutput + "\n";  // go to next line
        }
        regionOutput = regionOutput + ("-----------------");
        
        return regionOutput;
    }
    
    /**
     * Draws the region returns a string output with bias to party A
     * @param census - the voters filling the region
     * @return - an output of the region filled with voters
     */
    public String drawRegionA(ArrayList<Voter> census) {
        int j = 0;
        String regionOutput = "";
        regionOutput = regionOutput + (" /====================\\\n");
        for (int r = 0; r < region.length; r++) {
            for (int c = 0; c < region.length; c++) {
                if (c == 0) {
                    regionOutput = regionOutput + ("|");
                }
                regionOutput = regionOutput + ("| ");
                region[r][c] = census.get(j).getPartyName();  // Initialize the cell
                j++;
                regionOutput = regionOutput + (region[r][c] + " "); // Display the content of cell board
                if (c + 1 == region.length) {
                    regionOutput = regionOutput + (" ||");
                }
            }
            regionOutput = regionOutput + "\n";  // go to next line
        }
        regionOutput = regionOutput + (" \\====================/\n");
        
        return regionOutput;
    }
    
      /**
       * To-DO: Implement a gerrymander algorithm
       * @param census - the voters used
       * @param partyBias - the party that is to be biased
       * @return a List of Strings to be drawn out by the drawRegion method
       */
//    public ArrayList<String> createDistricts(ArrayList<Voter> census, String partyBias) {
//        int currentVoter = 0;
//        int currentDistrict = 1;
//        int numPartyA = 0;
//        int numPartyB = 0;
//        String neighborVoterParty;
//        int numOfVoters = census.size();
//        int numVotersNeededInDistrict = (int)Math.sqrt(numOfVoters) / 2;
//        ArrayList<String> dividedVoters = new ArrayList<>();
//        dividedVoters.add(census.get(0).partyAffiliation + "1");
//        numVotersNeededInDistrict--;
//        String neighborToCheck = "left";
//
//        while (numOfVoters != 0) {
//            currentVoter++;
//            switch (neighborToCheck) {
//                case "left":
//                    neighborVoterParty = checkLeftNeighbor(census, currentVoter);
//                    if (partyBias.equals(neighborVoterParty)) {
//                        if(numVotersNeededInDistrict != 0 && ){
//                            dividedVoters.add(census.get(currentVoter).partyAffiliation + "currentDistrict");
//                        }
//                    }
//                    break;
//                case "right":
//                    break;
//                case "south":
//                    break;
//                case "north":
//                    break;
//                default:
//                    break;
//            }
//
//                neighborToCheck = neighborToCheck(census, currentVoter);
//            }
//        return dividedVoters;
//    
//    }

    /**
     * Checks the leftNeighbor of the voter passed in as index
     * @param census - the list of voters
     * @param index - the voter who is checking their neighbor
     * @return the leftNeighbor's partyAffiliation
     */
    public String checkLeftNeighbor(ArrayList<Voter> census, int index) {
        int leftNeighbor = index + 1;
        int numOfVoters = census.size();
        boolean hasNeighbor = hasNeighbor(numOfVoters, leftNeighbor, "left");
        
        
        if (hasNeighbor) {
            return census.get(index + 1).getPartyName();
        } else {
            return "empty";
        }
    }
    
    /**
     * Checks the rightNeighbor of the voter passed in as index
     * @param census - the list of voters
     * @param index - the voter who is checking their neighbor
     * @return the rightNeighbor's partyAffiliation
     */
    public String checkRightNeighbor(ArrayList<Voter> census, int index) {
        int rightNeighbor = index - 1;
        int numOfVoters = census.size();
        boolean hasNeighbor = hasNeighbor(numOfVoters, rightNeighbor, "right");
        if (hasNeighbor) {
            return census.get(rightNeighbor).getPartyName();
        } else {
            return "empty";
        }
    }

    /**
     * Checks the southNeighbor of the voter passed in as index
     * @param census - the list of voters
     * @param index - the voter who is checking their neighbor
     * @return the southNeighbor's partyAffiliation
     */
    public String checkSouthNeighbor(ArrayList<Voter> census, int index) {
        int southNeighbor = index + (int) Math.sqrt(census.size());
        int numOfVoters = census.size();
        boolean hasNeighbor = hasNeighbor(numOfVoters, southNeighbor, "south");
        if (hasNeighbor) {
            return census.get(southNeighbor).getPartyName();
        } else {
            return "empty";
        }
    }

    /**
     * Checks the northNeighbor of the voter passed in as index
     * @param census - the list of voters
     * @param index - the voter who is checking their neighbor
     * @return the northNeighbor's partyAffiliation
     */
    public String checkNorthNeighbor(ArrayList<Voter> census, int index) {
        int northNeighbor = index - (int) Math.sqrt(census.size());
        int numOfVoters = census.size();
        boolean hasNeighbor = hasNeighbor(numOfVoters, northNeighbor, "north");
        if (hasNeighbor) {
            return census.get(northNeighbor).getPartyName();
        } else {
            return "empty";
        }

    }
        
    //TO-DO: Implement the next neighbor to check based on gerrymander algorithm 
//    public String neighborToCheck(ArrayList<Voter> census, int currentVoter) {
//        String neighborToCheck = "left";
//        if (checkLeftNeighbor(census, currentVoter).equals("empty")) {
//            return checkRightNeighbor(census, currentVoter);
//        } else if (checkRightNeighbor(census, currentVoter).equals("empty")) {
//            return checkSouthNeighbor(census, currentVoter);
//        } else if (checkSouthNeighbor(census, currentVoter).equals("empty")) {
//            return checkNorthNeighbor(census, currentVoter);
//        } else {
//            return neighborToCheck;
//        }
//    }
    
    /**
     * Checks if the voter has no neighbor
     * @param numOfVoters - the size of the region (length * width)
     * @param index - the place in the region the voter is
     * @param neighborToCheck - the direction being asked to check
     * @return true if a neighbor exists in that direction to the voter
     */
    public boolean hasNeighbor(int numOfVoters, int index, String neighborToCheck){
        boolean hasNeighbor = true;
        //The every number in the first column is a multiple of the square root(numOfVoters)
        int indexThatBeginRows = (int)Math.sqrt(numOfVoters);
        switch (neighborToCheck) {
            case "left":
                //Check if the index is in the last column
                if(index % indexThatBeginRows == 0){
                    hasNeighbor = false;
                }   break;
            case "right":
                //Check if the index is in the first column
                if((index - 1) % indexThatBeginRows == 0){
                    hasNeighbor = false;
                }   break;
            case "south":
                //Check if the index is in the last row
                if(index - indexThatBeginRows >= numOfVoters - indexThatBeginRows){
                    hasNeighbor = false;
                }  break;
            case "north":
                //Check if the index is in the first row
                if(index < 0){
                    hasNeighbor = false;
                }   break;
            default:
                hasNeighbor = true;
        }
        
        return hasNeighbor;
       
    }
}
