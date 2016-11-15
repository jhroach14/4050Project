package main.javaSrc.DBHelpers.Managers;

import main.javaSrc.DBHelpers.ObjectLayer;
import main.javaSrc.Entities.*;
import main.javaSrc.Entities.EntityImpl.BallotImpl;
import main.javaSrc.Entities.EntityImpl.VoterImpl;
import main.javaSrc.helpers.EVException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by User on 11/2/2016.
 */
public class VoteRecordManager extends Manager{

    public VoteRecordManager(Connection conn, ObjectLayer objectLayer){
        super(conn,objectLayer);
    }

    public VoteRecordManager() {

    }

    public List<VoteRecord> restore(VoteRecord voteRecord) throws EVException {

        Statement    stmt = null;
        String query = "";
        List<VoteRecord>   voteRecords = new ArrayList<VoteRecord>();

        if( voteRecord != null ) {
            query = voteRecord.getRestoreString();
        }

        try {

            stmt = conn.createStatement();

            // retrieve the persistent voteRecord objects
            //
            if( stmt.execute( query) ) { // statement returned a result

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
                        Voter v = new VoterImpl();
                        v.setId(voterId);
                        Voter voterToSet = objectLayer.findVoter( v ).get(0);
                        if(voterToSet != null)
                            nextVoteRecord.setVoter(voterToSet);
                    }

                    if(ballotId >= 0) {
                        Ballot b = new BallotImpl();
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

    public VoteRecord store(VoteRecord voteRecord) throws EVException{
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

            stmt = voteRecord.insertStoreData(stmt);


            queryExecution = stmt.executeUpdate();

            if( !voteRecord.isPersistent() ) {

                if( queryExecution >= 1 ) {
                    voteRecord = (VoteRecord) setId(stmt,voteRecord);
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
        return voteRecord;

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

