package main.javaSrc.Entities;

import main.javaSrc.helpers.EVException;

import java.util.List;

public interface PoliticalParty extends Entity{
    
    String getName();
    
    
    void setName(String name);
    
    
    List<Candidate> getCandidates() throws EVException;
}
