package main.javaSrc.Entities.EntityImpl;

import main.javaSrc.Entities.Candidate;
import main.javaSrc.Entities.PoliticalParty;
import main.javaSrc.helpers.EVException;

import java.util.List;

/**
 * Created by User on 11/8/2016.
 */
public class PoliticalPartyImpl extends EntityImpl implements PoliticalParty {
    String name;
    List<Candidate> candidates;

    public PoliticalPartyImpl(String name) {
        this.name = name;
    }

    public PoliticalPartyImpl(){

    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public List<Candidate> getCandidates() throws EVException {
        return candidates;
    }

}
