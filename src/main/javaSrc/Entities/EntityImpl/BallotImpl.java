package main.javaSrc.Entities.EntityImpl;

import main.javaSrc.Entities.*;
import main.javaSrc.helpers.EVException;
import org.codehaus.jackson.annotate.JsonIgnore;


import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by User on 11/8/2016.
 */
public class BallotImpl extends EntityImpl implements Ballot {
    private Date openDate;
    private Date closeDate;
    private boolean approved;
    private List<BallotItem> ballotItems;
    private List<VoteRecord> voteRecords;

    public BallotImpl(Date openDate, Date closeDate, boolean approved) {
        this.approved = approved;
        this.openDate=openDate;
        this.closeDate=closeDate;
    }

    public BallotImpl() {

    }
    @JsonIgnore
    @Override
    public PreparedStatement insertStoreData(PreparedStatement stmt) throws EVException, SQLException {
        //Cannot be null
        if( getOpenDate() != null )
            stmt.setDate( 1, (Date) getOpenDate());
        else
            throw new EVException( "BallotManager.save: can't save a ballot: Start Date undefined" );

        if( getCloseDate() != null ) {
            stmt.setDate(2, (Date) getCloseDate());
        }else {
            throw new EVException("BallotManager.save: can't save a ballot: Close Date undefined");
        }

        return stmt;
    }

    @JsonIgnore
    @Override
    public String getType() {
        return "Ballot";
    }

    @JsonIgnore
    @Override
    public String getRestoreString() {
        StringBuffer query = new StringBuffer( 100 );
        StringBuffer condition = new StringBuffer( 100 );
        String restoreStr = "select Ballot_ID, Start_Date, End_Date from Ballot";

        condition.setLength( 0 );
        query.append( restoreStr );

        if( getId() > 0 ) { // id is unique, so it is sufficient to get a person
            query.append(" where Ballot_ID = " + getId());
        }
        else {

            if( getOpenDate() != null )
                condition.append( " where Start_Date = '" + getOpenDate() + "'" );

            if( getCloseDate() != null ) {
                if( condition.length() > 0 )
                    condition.append( " and" );
                else
                    condition.append( " where" );
                condition.append( " End_Date = '" + getCloseDate() + "'" );
            }

        }
        query.append( condition );

        return query.toString();
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
