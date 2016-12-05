package main.javaSrc.Entities;

import main.javaSrc.Entities.EntityImpl.ElectoralDistrictImpl;
import main.javaSrc.helpers.EVException;
import org.codehaus.jackson.map.annotate.JsonDeserialize;

import java.util.List;

public interface Token extends Entity{

    String getTokenValue();

    void setTokenValue(String token);

}