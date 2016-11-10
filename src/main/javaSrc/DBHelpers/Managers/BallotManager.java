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
public class BallotManager {

    private ObjectLayer objectLayer = null;
    private Connection conn = null;

    public BallotManager(Connection conn, ObjectLayer objectLayer){
        this.conn = conn;
        this.objectLayer = objectLayer;
    }

    public BallotManager() {

    }

    public List<Ballot> restore(Ballot ballot) throws EVException {

        String       selectBallot = "select Ballot_ID, Start_Date, End_Date from Ballot";
        Statement    stmt = null;
        StringBuffer query = new StringBuffer( 100 );
        StringBuffer condition = new StringBuffer( 100 );
        List<Ballot>   ballots = new ArrayList<Ballot>();

        condition.setLength( 0 );

        // form the query based on the given ballot object instance
        query.append( selectBallot );

        if( ballot != null ) {
            if( ballot.getId() >= 0 ) { // id is unique, so it is sufficient to get a person
                query.append(" where Ballot_ID = " + ballot.getId());
            }
            else {

                if( ballot.getOpenDate() != null )
                    condition.append( " where Start_Date = '" + ballot.getOpenDate() + "'" );

                if( ballot.getCloseDate() != null ) {
                    if( condition.length() > 0 )
                        condition.append( " and" );
                    else
                        condition.append( " where" );
                    condition.append( " End_Date = '" + ballot.getCloseDate() + "'" );
                }

            }
            query.append( condition );
        }

        try {

            stmt = conn.createStatement();

            // retrieve the persistent ballot objects
            //
            if( stmt.execute( query.toString() ) ) { // statement returned a result

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

    public void store(Ballot ballot) throws EVException{
        String insertBallot = "insert into Ballot ( Start_Date, End_Date ) values ( ?, ? )";
        String updateBallot = "update Ballot set Start_Date = ?, End_Date = ?";
        PreparedStatement stmt = null;
        int queryExecution;
        int ballotId;

        try {

            if( !ballot.isPersistent() )
                stmt = conn.prepareStatement( insertBallot );
            else
                stmt = conn.prepareStatement( updateBallot );

            //Cannot be null

            if( ballot.getOpenDate() != null )
                stmt.setDate( 1, (Date) ballot.getOpenDate());
            else
                throw new EVException( "BallotManager.save: can't save a ballot: Start Date undefined" );

            if( ballot.getCloseDate() != null )
                stmt.setDate( 2, (Date) ballot.getCloseDate());
            else
                throw new EVException( "BallotManager.save: can't save a ballot: Close Date undefined" );


            queryExecution = stmt.executeUpdate();

            if( !ballot.isPersistent() ) {
                if( queryExecution >= 1 ) {
                    String sql = "select last_insert_id()";
                    if( stmt.execute( sql ) ) { // statement returned a result

                        // retrieve the result
                        ResultSet r = stmt.getResultSet();

                        // we will use only the first row!
                        //
                        while( r.next() ) {

                            // retrieve the last insert auto_increment value
                            ballotId = r.getInt( 1 );
                            if( ballotId > 0 )
                                ballot.setId( ballotId ); // set this person's db id (proxy object)
                        }
                    }
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


    }

    public void store(Ballot ballot, BallotItem ballotItem)throws EVException{
        String insertBallotIssue = "insert into Ballot_Issues (Ballot_ID, Issues_ID) values (?, ?)";
        PreparedStatement stmt = null;
        int queryExecution;

        try {
            stmt = conn.prepareStatement( insertBallotIssue );
            if(ballot.getId() > 0)
                stmt.setInt(1, ballot.getId());
            else
               throw new EVException("BallotManger.save can't save a ballot_issue: Ballot ID undefined") ;

            if(ballotItem.getId() > 0)
                stmt.setInt(2, ballotItem.getId());
            else
                throw new EVException("BallotManger.save can't save a ballot_issue: Ballot_Item ID undefined") ;

            queryExecution = stmt.executeUpdate();

            if(queryExecution < 1)
                throw new EVException("BallotManager.save failed to save ballot_issue");

        }
        catch(SQLException e){
            e.printStackTrace();
            throw new EVException("BallotManager.store failed to save a ballot_issue" +e);
        }


    }

    public Ballot restore (BallotItem ballotItem) throws EVException{
        StringBuffer query = new StringBuffer(500);
        Statement stmt = null;

        if(ballotItem.getId() <1)
            throw new EVException("BallotManager.restore could not restore persistent Ballot_Issue");

        query.append("select Ballot.Ballot_ID, Ballot.Start_Date, Ballot.End_Date ");
        query.append(" from Ballot ");
        query.append("join Ballot_Issues");
        query.append("on Ballot.Ballot_ID = Ballot_Issues.Ballot_ID");
        query.append("Where Ballot_Issues.Issues_ID = '" + ballotItem.getId() + "'");

        try{
            stmt = conn.createStatement();

            if(stmt.execute(query.toString())) {
                int ballotId;
                Date startDate;
                Date closeDate;
                Ballot newBallot = null;

                ResultSet rs = stmt.getResultSet();

                while(rs.next()){
                    ballotId = rs.getInt( 1 );
                    startDate = rs.getDate( 2 );
                    closeDate = rs.getDate( 3 );

                    newBallot = objectLayer.createBallot();
                    newBallot.setId( ballotId );
                    newBallot.setOpenDate( startDate );
                    newBallot.setCloseDate( closeDate );
                    break;
                }
                return newBallot;
            }
        }

        catch(SQLException e){
            e.printStackTrace();
            throw new EVException("BallotManager.store failed to save a ballot_issue" +e);
        }
        throw new EVException("BallotManager.restore could not restore ballot object");
    }

    public List<BallotItem> restoreBallotItems (Ballot ballot) throws EVException{
        StringBuffer query = new StringBuffer(500);
        Statement stmt = null;
        List<BallotItem> ballotItems = new ArrayList<BallotItem>();

        if(ballot.getId() <1)
            throw new EVException("BallotManager.restore could not restore persistent Ballot_Issue");

        query.append("select Issue.Vote_Count");
        query.append(" from Issue ");
        query.append("join Ballot_Issues");
        query.append("on Ballot.Ballot_ID = Ballot_Issues.Ballot_ID");
        query.append("Where Ballot.Ballot_ID = '" + ballot.getId() + "'");

        try {
            stmt = conn.createStatement();

            if (stmt.execute(query.toString())) { // statement returned a result

                int voteCount;

                BallotItem newBallotItem = null;

                ResultSet rs = stmt.getResultSet();


                while (rs.next()) {

                    voteCount = rs.getInt(1);

                    newBallotItem = new BallotItemImpl();
                    newBallotItem.setVoteCount(voteCount);

                    ballotItems.add(newBallotItem);
                }

                return ballotItems;
            }
        }

        catch(SQLException e){
            e.printStackTrace();
            throw new EVException("BallotManager.store failed to save a ballot_issue" +e);
        }
        throw new EVException("BallotManager.restore could not restore ballot object");
    }

    public void delete(Ballot ballot, BallotItem ballotItem) throws EVException{
        String               deleteBallot = "delete from Ballot_Issues where Ballot_ID = ? and Issue_ID = ?";
        PreparedStatement    stmt = null;
        int                  queryExecution;

        try{
            stmt = conn.prepareStatement( deleteBallot );
            if(ballot.getId() >0)
                stmt.setInt(1, ballot.getId());
            else
                throw new EVException("BallotManager.delete failed to delete ballotIssue");
            if(ballotItem.getId() > 0)
                stmt.setInt(2, ballotItem.getId());
            else
                throw new EVException("BallotManager.delete failed to delete ballotIssue");
            queryExecution = stmt.executeUpdate();
            if(queryExecution != 1)
                throw new EVException("BallotManager.delete failed to delete");
        }
        catch( SQLException e ) {
            e.printStackTrace();
            throw new EVException( "BallotManager.delete: failed to delete a ballot: " + e );
        }
    }

    public void delete(Ballot ballot) throws EVException {

        String               deleteBallot = "delete from Ballot where Ballot_ID = ?";
        PreparedStatement    stmt = null;
        int                  queryExecution;

        if( !ballot.isPersistent() ) // is the ballot object persistent?  If not, nothing to actually delete
            return;

        try {
            stmt = conn.prepareStatement( deleteBallot );
            stmt.setInt( 1, ballot.getId() );
            queryExecution = stmt.executeUpdate();
            if( queryExecution == 1 ) {
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

