package main.javaSrc.Entities;

import main.javaSrc.Entities.EntityImpl.ElectionImpl;
import main.javaSrc.helpers.EVException;
import org.codehaus.jackson.map.annotate.JsonDeserialize;

import java.util.List;



public interface Election extends BallotItem {
    
     String getOffice();
    
    
     void setOffice(String office);
    
    
     boolean getIsPartisan();
    
    
     void setIsPartisan(boolean isPartisan);
}
