package main.javaSrc.DBHelpers.Managers;

import main.javaSrc.DBHelpers.ObjectLayer;
import main.javaSrc.Entities.Ballot;
import main.javaSrc.Entities.BallotItem;
import main.javaSrc.Entities.EntityImpl.BallotImpl;
import main.javaSrc.Entities.EntityImpl.BallotItemImpl;
import main.javaSrc.helpers.EVException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by User on 11/2/2016.
 */
public class BallotManager extends Manager{


    public BallotManager(Connection conn, ObjectLayer objectLayer){
        super(conn,objectLayer);
    }

    public BallotManager() {

    }

    public List<Ballot> restore(Ballot ballot) throws EVException {

        Statement    stmt = null;
        String query = "";
        List<Ballot>   ballots = new ArrayList<Ballot>();

        if( ballot != null ) {
            query = ballot.getRestoreString();
        }

        try {

            stmt = conn.createStatement();

            // retrieve the persistent ballot objects
            //
            if( stmt.execute( query) ) { // statement returned a result

                int ballotId;
                Date startDate;
                Date closeDate;
                Ballot nextBallot = null;

                ResultSet rs = stmt.getResultSet();

                // retrieve the retrieved ballots
                while( rs.next() ) {

                    ballotId = rs.getInt( 1 );
                    startDate = rs.getDate( 2 );
                    closeDate = rs.getDate( 3 );

                    nextBallot = objectLayer.createBallot(); // create a proxy ballot object
                    // and now set its retrieved attributes
                    nextBallot.setId( ballotId );
                    nextBallot.setOpenDate( startDate );
                    nextBallot.setCloseDate( closeDate );
                    nextBallot.setPersistent(true);

                    ballots.add( nextBallot );
                }

                return ballots;
            }
        }
        catch( Exception e ) {      // just in case...
            throw new EVException( "BallotManager.restore: Could not restore persistent ballot objects; Root cause: " + e );
        }

        throw new EVException( "BallotManager.restore: Could not restore persistent ballot objects" );

    }

    public Ballot store(Ballot ballot) throws EVException{

        String insertBallot = "insert into Ballot ( Start_Date, End_Date ) values ( ?, ? )";
        String updateBallot = "update Ballot set Start_Date = ?, End_Date = ?";
        PreparedStatement stmt = null;
        int queryExecution;

        try {

            if( !ballot.isPersistent() ) {
                stmt = conn.prepareStatement(insertBallot);
            }else {
                stmt = conn.prepareStatement(updateBallot);
            }

            stmt = ballot.insertStoreData(stmt);

            queryExecution = stmt.executeUpdate();

            if( !ballot.isPersistent() ) {
                if( queryExecution >= 1 ) {
                    ballot = (Ballot) setId(stmt,ballot);
                    ballot.setPersistent(true);
                }
                else
                    throw new EVException( "BallotManager.save: failed to save a ballot" );
            }
            else {
                if( queryExecution < 1 )
                    throw new EVException( "BallotManager.save: failed to save a ballot" );
            }
        }
        catch( SQLException e ) {
            e.printStackTrace();
            throw new EVException( "BallotManager.save: failed to save a ballot: " + e );
        }
        return ballot;

    }



    public void delete(Ballot ballot) throws EVException {

        String               deleteBallot = "delete from Ballot where Ballot_ID = ?";
        String               deleteBallotInBallotIssues = "delete from Ballot_Issues where Ballot_ID = ?";
        String               deleteBallotInBallotElections = "delete from Ballot_Elections where Ballot_ID = ?";

        PreparedStatement    stmt = null;
        PreparedStatement    stmt1 = null;
        PreparedStatement    stmt2 = null;
        int                  queryExecution;
        int                  queryExecution1;
        int                  queryExecution2;

        if( !ballot.isPersistent() ) // is the ballot object persistent?  If not, nothing to actually delete
            return;

        try {
            stmt = conn.prepareStatement( deleteBallot );
            stmt1 = conn.prepareStatement( deleteBallotInBallotIssues );
            stmt2 = conn.prepareStatement( deleteBallotInBallotElections );
            stmt.setInt( 1, ballot.getId() );
            stmt1.setInt( 1, ballot.getId() );
            stmt2.setInt( 1, ballot.getId() );
            //queryExecution1 = stmt1.executeUpdate();
            //queryExecution2 = stmt2.executeUpdate();
            queryExecution = stmt.executeUpdate();


            if( queryExecution == 1 /*&& queryExecution1 == 1 && queryExecution2 == 1*/) {
                return;
            }
            else
                throw new EVException( "BallotManager.delete: failed to delete a ballot" );
        }
        catch( SQLException e ) {
            e.printStackTrace();
            throw new EVException( "BallotManager.delete: failed to delete a ballot: " + e );        }
    }

}

