package main.javaSrc.Entities.EntityImpl;

import main.javaSrc.Entities.Candidate;
import main.javaSrc.Entities.Election;
import main.javaSrc.helpers.EVException;

import java.util.List;

/**
 * Created by User on 11/8/2016.
 */
public class ElectionImpl extends BallotItemImpl implements Election{

    private String office;
    private boolean isPartisan;
    private List<Candidate> candidates;

    public ElectionImpl(String office, boolean isPartisan, List<Candidate> candidates) {
        this.office = office;
        this.isPartisan = isPartisan;
        this.candidates = candidates;
    }

    public ElectionImpl(){

    }

    @Override
    public String getOffice() {
        return office;
    }

    @Override
    public void setOffice(String office) {
        this.office=office;
    }

    @Override
    public boolean getIsPartisan() {
        return isPartisan;
    }

    @Override
    public void setIsPartisan(boolean isPartisan) {
        this.isPartisan=isPartisan;
    }

    @Override
    public List<Candidate> getCandidates() throws EVException {
        return candidates;
    }

    @Override
    public void addCandidate(Candidate candidate) throws EVException {
            candidates.add(candidate);
    }

    @Override
    public void deleteCandidate(Candidate candidate) throws EVException {
            candidates.remove(candidate);
    }
}
