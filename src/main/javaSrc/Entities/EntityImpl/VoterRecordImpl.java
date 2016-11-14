package main.javaSrc.Entities.EntityImpl;

import main.javaSrc.Entities.Ballot;
import main.javaSrc.Entities.VoteRecord;
import main.javaSrc.Entities.Voter;
import main.javaSrc.helpers.EVException;

import java.sql.Date;

/**
 * Created by User on 11/8/2016.
 */
public class VoterRecordImpl extends EntityImpl implements VoteRecord {

    private Date date;
    private Voter voter;
    private Ballot ballot;

    public VoterRecordImpl(Date date, Voter voter, Ballot ballot) {
        this.date = date;
        this.voter = voter;
        this.ballot = ballot;
    }

    public VoterRecordImpl(){

    }

    @Override
    public Date getDate() {
        return date;
    }

    @Override
    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public Voter getVoter() throws EVException {
        return voter;
    }

    @Override
    public void setVoter(Voter voter) throws EVException {
        this.voter = voter;
    }

    @Override
    public Ballot getBallot() throws EVException {
        return ballot;
    }

    @Override
    public void setBallot(Ballot ballot) throws EVException {
        this.ballot=ballot;
    }
}
