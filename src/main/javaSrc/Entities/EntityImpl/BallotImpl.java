package main.javaSrc.Entities.EntityImpl;

import main.javaSrc.Entities.*;
import main.javaSrc.helpers.EVException;


import java.util.Date;
import java.util.List;

/**
 * Created by User on 11/8/2016.
 */
public class BallotImpl extends EntityImpl implements Ballot {
    private Date openDate;
    private Date closeDate;
    private boolean approved;
    private ElectoralDistrict electoralDistrict;
    private List<BallotItem> ballotItems;
    private List<VoteRecord> voteRecords;

    public BallotImpl(Date openDate, Date closeDate, boolean approved, ElectoralDistrict electoralDistrict) {
        this.openDate=openDate;
        this.closeDate=closeDate;
        this.approved=approved;
        this.electoralDistrict = electoralDistrict;
    }

    public BallotImpl() {

    }

    @Override
    public java.util.Date getOpenDate() {
        return openDate;
    }

    @Override
    public void setOpenDate(java.util.Date openDate) {
        this.openDate = openDate;
    }

    @Override
    public java.util.Date getCloseDate() {
        return closeDate;
    }

    @Override
    public void setCloseDate(java.util.Date closeDate) {
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
