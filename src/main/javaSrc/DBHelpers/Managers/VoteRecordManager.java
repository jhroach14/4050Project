package main.javaSrc.DBHelpers.Managers;

import main.javaSrc.DBHelpers.ObjectLayer;
import main.javaSrc.Entities.*;
import main.javaSrc.helpers.EVException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by User on 11/2/2016.
 */
public class VoteRecordManager {

    private ObjectLayer objectLayer = null;
    private Connection conn = null;

    public VoteRecordManager(Connection conn, ObjectLayer objectLayer){
        this.conn = conn;
        this.objectLayer = objectLayer;
    }

    public List<VoteRecord> restore(VoteRecord voteRecord) throws EVException {

        String       selectVoteRecord = "select Record_ID, Record_Date, Voter_ID, Ballot_ID from Record";
        Statement    stmt = null;
        StringBuffer query = new StringBuffer( 100 );
        StringBuffer condition = new StringBuffer( 100 );
        List<VoteRecord>   voteRecords = new ArrayList<VoteRecord>();

        condition.setLength( 0 );

        // form the query based on the given voteRecord object instance
        query.append( selectVoteRecord );

        if( voteRecord != null ) {
            if( voteRecord.getId() >= 0 ) { // id is unique, so it is sufficient to get a person
                query.append(" where Record_ID = " + voteRecord.getId());
            }
            else {

                if( voteRecord.getDate() != null )
                    condition.append( " where Record_Date = '" + voteRecord.getDate() + "'" );

                if( voteRecord.getVoter().getId() >= 0 ) {
                    if( condition.length() > 0 )
                        condition.append( " and" );
                    else
                        condition.append( " where" );
                    condition.append( " Voter_ID = '" + voteRecord.getVoter().getId() + "'" );
                }

                if( voteRecord.getBallot().getId() >= 0){
                    if( condition.length() > 0 )
                        condition.append( " and" );
                    else
                        condition.append( " where" );
                    condition.append( " Ballot_ID = '" + voteRecord.getBallot().getId() + "'" );
                }

            }
            query.append( condition );
        }

        try {

            stmt = conn.createStatement();

            // retrieve the persistent voteRecord objects
            //
            if( stmt.execute( query.toString() ) ) { // statement returned a result

                int voteRecordId;
                Date recordDate;
                int voterId;
                int ballotId;
                VoteRecord nextVoteRecord = null;

                ResultSet rs = stmt.getResultSet();

                // retrieve the retrieved voteRecords
                while( rs.next() ) {

                    voteRecordId = rs.getInt( 1 );
                    recordDate = rs.getDate( 2 );
                    voterId = rs.getInt( 3 );
                    ballotId = rs.getInt( 4 );


                    nextVoteRecord = objectLayer.createVoteRecord(); // create a proxy voteRecord object
                    // and now set its retrieved attributes
                    nextVoteRecord.setId( voteRecordId );
                    nextVoteRecord.setDate( recordDate );

                    //Check VoterManager or CandidateManager for an explanation
                    if(voterId >= 0) {
                        Voter v = new Voter();
                        v.setId(voterId);
                        Voter voterToSet = objectLayer.findVoter( v ).get(0);
                        if(voterToSet != null)
                            nextVoteRecord.setVoter(voterToSet);
                    }

                    if(ballotId >= 0) {
                        Ballot b = new Ballot();
                        b.setId(ballotId);
                        Ballot ballotToSet = objectLayer.findBallot( b ).get(0);
                        if(ballotToSet != null)
                            nextVoteRecord.setBallot(ballotToSet);
                    }


                    voteRecords.add( nextVoteRecord );
                }

                return voteRecords;
            }
        }
        catch( Exception e ) {      // just in case...
            throw new EVException( "voteRecordManager.restore: Could not restore persistent voteRecord objects; Root cause: " + e );
        }

        throw new EVException( "voteRecordManager.restore: Could not restore persistent voteRecord objects" );

    }

    public void store(VoteRecord voteRecord) throws EVException{
        String insertVoteRecord = "insert into Record ( Record_Date, Voter_ID, Ballot_ID ) values ( ?, ?, ? )";
        String updateVoteRecord = "update Record set Record_Date = ?, Voter_ID = ?, Ballot_ID = ? ";
        PreparedStatement stmt = null;
        int queryExecution;
        int voteRecordId;

        try {

            if( !voteRecord.isPersistent() )
                stmt = conn.prepareStatement( insertVoteRecord );
            else
                stmt = conn.prepareStatement( updateVoteRecord );

            //Cannot be null

            if( voteRecord.getDate() != null )
                stmt.setDate( 1, (Date) voteRecord.getDate());
            else
                throw new EVException( "VoteRecordManager.save: can't save a voteRecord: Date undefined" );

            if( voteRecord.getVoter().getVoterId() >= 0 )
                stmt.setInt( 2, voteRecord.getVoter().getVoterId() );
            else
                throw new EVException( "VoteRecordManager.save: can't save a voteRecord: Voter_ID undefined" );

            if( voteRecord.getBallot().getId() >= 0 )
                stmt.setInt( 3, voteRecord.getBallot().getId() );
            else
                throw new EVException( "VoteRecordManager.save: can't save a voteRecord: Ballot_ID undefined" );


            queryExecution = stmt.executeUpdate();

            if( !voteRecord.isPersistent() ) {
                if( queryExecution >= 1 ) {
                    String sql = "select last_insert_id()";
                    if( stmt.execute( sql ) ) { // statement returned a result

                        // retrieve the result
                        ResultSet r = stmt.getResultSet();

                        // we will use only the first row!
                        //
                        while( r.next() ) {

                            // retrieve the last insert auto_increment value
                            voteRecordId = r.getInt( 1 );
                            if( voteRecordId > 0 )
                                voteRecord.setId( voteRecordId ); // set this person's db id (proxy object)
                        }
                    }
                }
                else
                    throw new EVException( "VoteRecordManager.save: failed to save a voteRecord" );
            }
            else {
                if( queryExecution < 1 )
                    throw new EVException( "VoteRecordManager.save: failed to save a voteRecord" );
            }
        }
        catch( SQLException e ) {
            e.printStackTrace();
            throw new EVException( "VoteRecordManager.save: failed to save a voteRecord: " + e );
        }


    }

    public void delete(VoteRecord voteRecord) throws EVException {

        String               deleteVoteRecord = "delete from VoteRecord where Record_ID = ?";
        PreparedStatement    stmt = null;
        int                  queryExecution;

        if( !voteRecord.isPersistent() ) // is the voteRecord object persistent?  If not, nothing to actually delete
            return;

        try {
            stmt = conn.prepareStatement( deleteVoteRecord );
            stmt.setInt( 1, voteRecord.getId() );
            queryExecution = stmt.executeUpdate();
            if( queryExecution == 1 ) {
                return;
            }
            else
                throw new EVException( "voteRecordManager.delete: failed to delete a voteRecord" );
        }
        catch( SQLException e ) {
            e.printStackTrace();
            throw new EVException( "voteRecordManager.delete: failed to delete a voteRecord: " + e );        }
    }

}

