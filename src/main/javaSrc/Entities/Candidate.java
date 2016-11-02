package main.javaSrc.Entities;


import main.javaSrc.helpers.EVException;

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
