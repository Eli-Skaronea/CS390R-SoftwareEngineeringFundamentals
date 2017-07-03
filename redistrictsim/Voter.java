package redistrictsim;

/**
 *
 * @author Group X
 */
public class Voter {
    public int partyAffiliation;
    
    /**
     * Default constructor for a voter - assigns a random party to the voter
     */
    public Voter(){
        this.partyAffiliation = (int) Math.rint(Math.random());
    }
    
    /**
     * Constructs a voter assigned to partyID given
     * @param partyNum - ID of party the voter will vote for
     */
    public Voter(int partyNum){
        this.partyAffiliation = partyNum;
    }
    
    /**
     * 
     * @return a voters PartyName
     */
    public String getPartyName(){
        if(partyAffiliation == 0){
            return "A";
        }
        else{
            return "B";
        }
    }
}

