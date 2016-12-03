package main.javaSrc.Entities;

//import main.javaSrc.Entities.EntityImpl.VoterRecordImpl;
import main.javaSrc.helpers.EVException;
import org.codehaus.jackson.map.annotate.JsonDeserialize;

import java.sql.Date;

//@JsonDeserialize(as = VoterRecordImpl.class)
public interface VoteRecord extends Entity{
    
    Date getDate();
    
    
    void setDate(Date date);
    
    
    Voter getVoter() throws EVException;
    
    
    void setVoter(Voter voter) throws EVException;
    
    
    Ballot getBallot() throws EVException;
    
    
    void setBallot(Ballot ballot) throws EVException;

}
