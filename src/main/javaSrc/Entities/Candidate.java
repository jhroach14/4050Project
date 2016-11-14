package main.javaSrc.Entities;


import main.javaSrc.Entities.EntityImpl.CandidateImpl;
import main.javaSrc.helpers.EVException;
import org.codehaus.jackson.map.annotate.JsonDeserialize;


public interface Candidate extends Entity{
    
    String getName();
    
    
    void setName(String name);
    
    
    int getVoteCount();
    
    
    void setVoteCount(int voteCount) throws EVException;
    
    
    void addVote();

    
    Election getElection() throws EVException;
    
    
    void setElection(Election election) throws EVException;
    
    
    PoliticalParty getPoliticalParty() throws EVException;
    
    
    void setPoliticalParty(PoliticalParty politicalParty) throws EVException;
}
