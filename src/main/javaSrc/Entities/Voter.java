package main.javaSrc.Entities;

//import main.javaSrc.Entities.EntityImpl.VoterImpl;
import main.javaSrc.helpers.EVException;
import org.codehaus.jackson.map.annotate.JsonDeserialize;

import java.util.List;

//@JsonDeserialize(as = VoterImpl.class)
public interface Voter extends User {

    void setAge(int age);
    int getAge();
    
}
