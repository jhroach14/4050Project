package main.javaSrc.DBHelpers.Managers;

import main.javaSrc.DBHelpers.ObjectLayer;
import main.javaSrc.Entities.Ballot;
import main.javaSrc.Entities.BallotItem;
import main.javaSrc.Entities.EntityImpl.BallotItemImpl;
import main.javaSrc.helpers.EVException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by User on 11/9/2016.
 */
public class Ballot_IssuesManager {
    
    private ObjectLayer objectLayer;
    private Connection conn;
    
    public Ballot_IssuesManager(ObjectLayer objectLayer, Connection conn){
        this.objectLayer=objectLayer;
        this.conn=conn;
    }

    public void store(Ballot ballot, BallotItem ballotItem)throws EVException {

        String insertBallot_Issue = "insert into Ballot_Issues (Ballot_ID, Issue_ID) values (?, ?)";
        PreparedStatement stmt = null;
        int queryExecution;

        try {
            stmt = conn.prepareStatement( insertBallot_Issue );

            if(ballot.getId() > 0)
                stmt.setInt(1, ballot.getId());
            else
                throw new EVException("Ballot_IssueManager.save can't save a ballot_issue: Ballot ID undefined") ;

            if(ballotItem.getId() > 0)
                stmt.setInt(2, ballotItem.getId());
            else
                throw new EVException("Ballot_IssueManger.save can't save a ballot_issue: Ballot_Item ID undefined") ;

            queryExecution = stmt.executeUpdate();

            if(queryExecution < 1)
                throw new EVException("Ballot_IssueManager.save failed to save ballot_Issue");

        }
        catch(SQLException e){
            e.printStackTrace();
            throw new EVException("Ballot_IssueManager.store failed to save a ballot_Issue" +e);
        }


    }

    public Ballot restore (BallotItem ballotItem) throws EVException{
        StringBuffer query = new StringBuffer(500);
        Statement stmt = null;

        if(ballotItem.getId() <1)
            throw new EVException("Ballot_Issues.restore could not restore non persistent issue");

        query.append( "select Ballot.Ballot_ID, Ballot.Start_Date, Ballot.End_Date " );
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
            throw new EVException("Ballot_Issues.store failed to save a ballot_Issue" +e);
        }
        throw new EVException("Ballot_Issues.restore could not restore ballot object");
    }

    public List<BallotItem> restore(Ballot ballot) throws EVException{
        StringBuffer query = new StringBuffer(500);
        Statement stmt = null;
        List<BallotItem> ballotItems = new ArrayList<BallotItem>();

        if(ballot.getId() <1)
            throw new EVException("Ballot_Issues.restore could not restore persistent Ballot_Issues");

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
            throw new EVException("Ballot_Issues. store failed to save a ballot_Issue" +e);
        }
        throw new EVException("Ballot_issue.restore could not restore ballot object");
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
                throw new EVException("Ballot_Issues.delete failed to delete ballot_Issues");
            if(ballotItem.getId() > 0)
                stmt.setInt(2, ballotItem.getId());
            else
                throw new EVException("Ballot_IssuesManager.delete failed to delete ballot_Issues");
            queryExecution = stmt.executeUpdate();
            if(queryExecution != 1)
                throw new EVException("Ballot_IssuesManager.delete failed to delete");
        }
        catch( SQLException e ) {
            e.printStackTrace();
            throw new EVException( "Ballot_IssuesManager.delete: failed to delete a ballot: " + e );
        }
    }
}


