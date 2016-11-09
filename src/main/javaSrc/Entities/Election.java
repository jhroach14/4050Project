package main.javaSrc.Entities;

import main.javaSrc.helpers.EVException;

import java.util.List;



public interface Election extends BallotItem {
    
     String getOffice();
    
    
     void setOffice(String office);
    
    
     boolean getIsPartisan();
    
    
     void setIsPartisan(boolean isPartisan);
    
    
     List<Candidate> getCandidates() throws EVException;
    
    
     void addCandidate(Candidate candidate) throws EVException;
    
    
     void deleteCandidate(Candidate candidate) throws EVException;
}
