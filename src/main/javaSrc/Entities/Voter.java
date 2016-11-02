package main.javaSrc.Entities;

import main.javaSrc.helpers.EVException;

import java.util.List;


public interface Voter extends User {
    
    int getVoterId();
    
    
    void setVoterId(int voterId);
    
    
    int getAge();
    
    
    void setAge(int age);
    
    
    ElectoralDistrict getElectoralDistrict() throws EVException;
    
    
    void setElectoralDistrict(ElectoralDistrict electoralDistrict) throws EVException;
    
    
    List<VoteRecord> getBallotVoteRecords() throws EVException;
    
}
