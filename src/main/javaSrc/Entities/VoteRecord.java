package main.javaSrc.Entities;

import main.javaSrc.helpers.EVException;

import java.util.Date;


public interface VoteRecord extends Entity{
    
    Date getDate();
    
    
    void setDate(Date date);
    
    
    Voter getVoter() throws EVException;
    
    
    void setVoter(Voter voter) throws EVException;
    
    
    Ballot getBallot() throws EVException;
    
    
    void setBallot(Ballot ballot) throws EVException;

}
