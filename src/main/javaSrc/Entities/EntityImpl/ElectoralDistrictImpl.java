package main.javaSrc.Entities.EntityImpl;

import main.javaSrc.Entities.Ballot;
import main.javaSrc.Entities.ElectoralDistrict;
import main.javaSrc.Entities.Voter;
import main.javaSrc.helpers.EVException;

import java.util.List;

/**
 * Created by User on 11/8/2016.
 */
public class ElectoralDistrictImpl extends EntityImpl implements ElectoralDistrict {

    private String name;
    private List<Voter> voters;
    private List<Ballot> ballots;

    public ElectoralDistrictImpl(String name) {
        this.name = name;
    }

    public ElectoralDistrictImpl() {

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
    public List<Voter> getVoters() throws EVException {
        return voters;
    }

    @Override
    public List<Ballot> getBallots() throws EVException {
        return ballots;
    }

    @Override
    public void addBallot(Ballot ballot) throws EVException {
        ballots.add(ballot);
    }

    @Override
    public void deleteBallot(Ballot ballot) throws EVException {
        ballots.remove(ballot);
    }
}
