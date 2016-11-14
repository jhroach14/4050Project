package main.javaSrc.Entities;

import main.javaSrc.Entities.EntityImpl.VoterImpl;
import main.javaSrc.helpers.EVException;
import org.codehaus.jackson.map.annotate.JsonDeserialize;

import java.util.List;

@JsonDeserialize(as = VoterImpl.class)
public interface Voter extends User {
    
    int getVoterId();
    
    
    void setVoterId(int voterId);
    
    
    int getAge();
    
    
    void setAge(int age);
    
    
    ElectoralDistrict getElectoralDistrict() throws EVException;
    
    
    void setElectoralDistrict(ElectoralDistrict electoralDistrict) throws EVException;
    
    
    List<VoteRecord> getBallotVoteRecords() throws EVException;
    
}
