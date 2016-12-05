package main.javaSrc.Entities;

import main.javaSrc.Entities.EntityImpl.ElectoralDistrictImpl;
import main.javaSrc.helpers.EVException;
import org.codehaus.jackson.map.annotate.JsonDeserialize;

import java.util.List;

public interface ElectoralDistrict extends Entity{
    
    String getName();
    String getZip();

    
    void setName(String name);
    void setZip(String zip);

}
