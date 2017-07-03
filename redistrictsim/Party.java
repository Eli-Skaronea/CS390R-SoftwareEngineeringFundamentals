/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package redistrictsim;

/**
 *
 * @author Group X
 */
public class Party {
    String partyName;
    int partyId;
    
    public Party(){
        
    }
    
    public Party(int partyId, String partyName){
        this.partyId = partyId;
        this.partyName = partyName;
    }

    private String getPartyName() {
        return partyName;
    }

    private void setPartyName(String partyName) {
        this.partyName = partyName;
    }

    private int getPartyId() {
        return partyId;
    }

    private void setPartyId(int partyId) {
        this.partyId = partyId;
    }
    
    
    
}
