package main.javaSrc.Entities;

import main.javaSrc.helpers.EVException;

import java.util.Date;
import java.util.List;


 public interface Ballot extends Entity{
    
      Date getOpenDate();
    
    
      void setOpenDate(Date openDate);
    
    
      Date getCloseDate();
    
    
      void setCloseDate(Date closeDate);
    
    
      ElectoralDistrict getElectoralDistrict() throws EVException;
    
    
      void setElectoralDistrict(ElectoralDistrict electoralDistrict) throws EVException;
    
    
      List<BallotItem> getBallotItems() throws EVException;
    
    
      void addBallotItem(BallotItem ballotItem) throws EVException;
    
    
      void deleteBallotItem(BallotItem ballotItem) throws EVException;
    
    
      List<VoteRecord> getVoterVoteRecords() throws EVException;

}
