package main.javaSrc.Entities.EntityImpl;

import main.javaSrc.Entities.Ballot;
import main.javaSrc.Entities.BallotItem;
import main.javaSrc.Entities.Entity;
import main.javaSrc.helpers.EVException;

import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Created by User on 11/8/2016.
 */
public  class BallotItemImpl extends EntityImpl implements BallotItem {

    private int voteCount;
    private Ballot ballot;



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
    public Ballot getBallot() throws EVException {
        return ballot;
    }

    @Override
    public void setBallot(Ballot ballot) throws EVException {
        this.ballot=ballot;
    }

    @Override
    public String getRestoreString() throws EVException {
        return null;
    }

    @Override
    public PreparedStatement insertStoreData(PreparedStatement stmt) throws EVException, SQLException {
        return null;
    }
}
