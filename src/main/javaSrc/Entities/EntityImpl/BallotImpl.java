package main.javaSrc.Entities.EntityImpl;

import main.javaSrc.Entities.*;
import main.javaSrc.helpers.EVException;


import java.sql.Date;
import java.util.List;

/**
 * Created by User on 11/8/2016.
 */
public class BallotImpl extends EntityImpl implements Ballot {
    private Date openDate;
    private Date closeDate;
    private ElectoralDistrict electoralDistrict;
    private boolean approved;
    private List<BallotItem> ballotItems;
    private List<VoteRecord> voteRecords;

    public BallotImpl(Date openDate, Date closeDate, boolean approved, ElectoralDistrict electoralDistrict) {
        this.approved = approved;
        this.openDate=openDate;
        this.closeDate=closeDate;
        this.electoralDistrict = electoralDistrict;
    }

    public BallotImpl() {

    }

    @Override
    public Date getOpenDate() {
        return openDate;
    }

    @Override
    public void setOpenDate(Date openDate) {
        this.openDate = openDate;
    }

    @Override
    public Date getCloseDate() {
        return closeDate;
    }

    @Override
    public void setCloseDate(Date closeDate) {
        this.closeDate = closeDate;
    }

    @Override
    public ElectoralDistrict getElectoralDistrict() throws EVException {
        return electoralDistrict;
    }

    @Override
    public void setElectoralDistrict(ElectoralDistrict electoralDistrict) throws EVException {
        this.electoralDistrict = electoralDistrict;
    }

    @Override
    public List<BallotItem> getBallotItems() throws EVException {
        return ballotItems;
    }

    @Override
    public void addBallotItem(BallotItem ballotItem) throws EVException {
        ballotItems.add(ballotItem);
    }

    @Override
    public void deleteBallotItem(BallotItem ballotItem) throws EVException {
        ballotItems.remove(ballotItem);
    }

    @Override
    public List<VoteRecord> getVoterVoteRecords() throws EVException {
        return voteRecords;
    }
}
