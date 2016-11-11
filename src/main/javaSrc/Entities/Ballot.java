package main.javaSrc.Entities;

import main.javaSrc.Entities.EntityImpl.BallotImpl;
import main.javaSrc.helpers.EVException;
import org.codehaus.jackson.map.annotate.JsonDeserialize;

import java.sql.Date;
import java.util.List;

@JsonDeserialize(as = BallotImpl.class)
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
