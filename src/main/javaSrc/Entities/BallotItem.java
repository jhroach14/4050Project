package main.javaSrc.Entities;


import main.javaSrc.Entities.EntityImpl.BallotItemImpl;
import main.javaSrc.helpers.EVException;
import org.codehaus.jackson.map.annotate.JsonDeserialize;

public interface BallotItem extends Entity{
    
     int getVoteCount();
    
    
     void setVoteCount(int voteCount) throws EVException;

    
     void addVote();
    
    
     Ballot getBallot() throws EVException;
    
    
     void setBallot(Ballot ballot) throws EVException;
}
