package main.javaSrc.Entities;

import main.javaSrc.Entities.EntityImpl.ElectoralDistrictImpl;
import main.javaSrc.helpers.EVException;
import org.codehaus.jackson.map.annotate.JsonDeserialize;

import java.util.List;

public interface ElectoralDistrict extends Entity{
    
    String getName();
    
    
    void setName(String name);
    
    
    List<Voter> getVoters() throws EVException;
        
    
    List<Ballot> getBallots() throws EVException;
    
    
    void addBallot(Ballot ballot) throws EVException;
    
    
    void deleteBallot(Ballot ballot) throws EVException;
}
