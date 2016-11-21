package main.javaSrc.Entities.EntityImpl;

import main.javaSrc.Entities.Ballot;
import main.javaSrc.Entities.VoteRecord;
import main.javaSrc.Entities.Voter;
import main.javaSrc.helpers.EVException;
import org.codehaus.jackson.annotate.JsonIgnore;


import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;

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

    @JsonIgnore
    @Override
    public String getRestoreString() throws EVException {
        StringBuffer query = new StringBuffer( 100 );
        StringBuffer condition = new StringBuffer( 100 );
        String restoreStr = "select Record_ID, Record_Date, Voter_ID, Ballot_ID from Record";

        condition.setLength( 0 );
        query.append( restoreStr );

        if( getId() > 0 ) { // id is unique, so it is sufficient to get a person
            query.append(" where Record_ID = " + getId());
        }
        else {

            if( getDate() != null )
                condition.append( " where Record_Date = '" + getDate() + "'" );

            if( getVoter().getId() >= 0 ) {
                if( condition.length() > 0 )
                    condition.append( " and" );
                else
                    condition.append( " where" );
                condition.append( " Voter_ID = '" + getVoter().getId() + "'" );
            }

            if( getBallot().getId() >= 0){
                if( condition.length() > 0 )
                    condition.append( " and" );
                else
                    condition.append( " where" );
                condition.append( " Ballot_ID = '" + getBallot().getId() + "'" );
            }
            
            query.append(condition);
        }


        return query.toString();
    }

    @JsonIgnore
    @Override
    public PreparedStatement insertStoreData(PreparedStatement stmt) throws EVException, SQLException {
        //Cannot be null

        if( getDate() != null )
            stmt.setDate( 1, getDate());
        else
            throw new EVException( "VoteRecordManager.save: can't save a  Date undefined" );

        if( getVoter().getId() >= 0 )
            stmt.setInt( 2, getVoter().getId());
        else
            throw new EVException( "VoteRecordManager.save: can't save a  Voter_ID undefined" );

        if( getBallot().getId() >= 0 )
            stmt.setInt( 3, getBallot().getId());
        else
            throw new EVException( "VoteRecordManager.save: can't save a  Ballot_ID undefined" );

        return stmt;
    }

    @Override
    public String getType() {
        return "VoterRecord";
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
