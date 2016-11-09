package main.javaSrc.Entities.EntityImpl;

import main.javaSrc.Entities.Candidate;
import main.javaSrc.Entities.Election;
import main.javaSrc.Entities.PoliticalParty;
import main.javaSrc.helpers.EVException;

/**
 * Created by User on 11/8/2016.
 */
public class CandidateImpl extends EntityImpl implements Candidate {

    private String name;
    private PoliticalParty politicalParty;
    private Election election;
    private  int voteCount;

    public CandidateImpl(String name, PoliticalParty politicalParty, Election election) {
        this.name=name;
        this.politicalParty = politicalParty;
        this.election = election;
    }

    public CandidateImpl() {

    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name=name;
    }

    @Override
    public int getVoteCount() {
        return voteCount;
    }

    @Override
    public void setVoteCount(int voteCount) throws EVException {
        this.voteCount = voteCount;
    }

    @Override
    public void addVote() {
        voteCount++;
    }

    @Override
    public Election getElection() throws EVException {
        return election;
    }

    @Override
    public void setElection(Election election) throws EVException {
        this.election = election;
    }

    @Override
    public PoliticalParty getPoliticalParty() throws EVException {
        return politicalParty;
    }

    @Override
    public void setPoliticalParty(PoliticalParty politicalParty) throws EVException {
        this.politicalParty = politicalParty;
    }
}
