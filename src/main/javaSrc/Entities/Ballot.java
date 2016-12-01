package main.javaSrc.Entities;

//import main.javaSrc.Entities.EntityImpl.BallotImpl;
import main.javaSrc.helpers.EVException;
//import org.codehaus.jackson.map.annotate.JsonDeserialize;

import java.sql.Date;
import java.util.List;

//@JsonDeserialize(as = BallotImpl.class)
 public interface Ballot extends Entity{
    
      Date getOpenDate();
    
    
      void setOpenDate(Date openDate);
    
    
      Date getCloseDate();
    
    
      void setCloseDate(Date closeDate);


}
