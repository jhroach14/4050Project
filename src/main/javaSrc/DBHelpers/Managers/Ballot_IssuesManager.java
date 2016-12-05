package main.javaSrc.DBHelpers.Managers;

import main.javaSrc.DBHelpers.ObjectLayer;
import main.javaSrc.Entities.Ballot;
import main.javaSrc.Entities.BallotItem;
import main.javaSrc.Entities.EntityImpl.BallotItemImpl;
import main.javaSrc.Entities.EntityImpl.IssueImpl;
import main.javaSrc.Entities.Issue;
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

        query.append("select Ballot_ID, Start_Date, End_Date, Issue_ID from (select Ballot.Ballot_ID, Ballot.Start_Date, Ballot.End_Date, Ballot_Issues.Issue_ID from Ballot inner join Ballot_Issues on Ballot_Issues.Ballot_ID = Ballot.Ballot_ID) as T where Issue_ID = " + ballotItem.getId());

//
//        query.append( "select Ballot.Ballot_ID, Ballot.Start_Date, Ballot.End_Date " );
//        query.append(" from Ballot ");
//        query.append("join Ballot_Issues");
//        query.append("on Ballot.Ballot_ID = Ballot_Issues.Ballot_ID");
//        query.append("Where Ballot_Issues.Issues_ID = '" + ballotItem.getId() + "'");

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
                    newBallot.setPersistent(true);
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

        query.append("select Issue_ID, Question, Vote_Count, Yes_Count, No_Count from (select Issue.Issue_ID, Issue.Question, Issue.Vote_Count, Issue.Yes_Count, Issue.No_count, Ballot_Issues.Ballot_ID from Issue inner join Ballot_Issues on Ballot_Issues.Issue_ID = Issue.Issue_ID) as T where Ballot_ID = " + ballot.getId());
        try {
            stmt = conn.createStatement();

            if (stmt.execute(query.toString())) { // statement returned a result

                int issueId;
                int voteCount;
                int yesCount;
                int noCount;
                String question;

                Issue newBallotItem = null;

                ResultSet rs = stmt.getResultSet();


                while (rs.next()) {

                    issueId = rs.getInt(1);
                    question = rs.getString(2);
                    voteCount = rs.getInt(3);
                    yesCount = rs.getInt(4);
                    noCount = rs.getInt(5);

                    newBallotItem = new IssueImpl();
                    newBallotItem.setId(issueId);
                    newBallotItem.setVoteCount(voteCount);
                    newBallotItem.setYesCount(yesCount);
                    newBallotItem.setNoCount(noCount);
                    newBallotItem.setQuestion(question);
                    newBallotItem.setPersistent(true);

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
           /* if(queryExecution != 1)
                throw new EVException("Ballot_IssuesManager.delete failed to delete");*/
        }
        catch( SQLException e ) {
            e.printStackTrace();
            throw new EVException( "Ballot_IssuesManager.delete: failed to delete a ballot: " + e );
        }
    }

    public void delete(Ballot ballot) throws EVException{
        String               deleteBallot = "delete from Ballot_Issues where Ballot_ID = ?";
        PreparedStatement    stmt = null;
        int                  queryExecution;

        try{
            stmt = conn.prepareStatement( deleteBallot );
            if(ballot.getId() > 0)
                stmt.setInt(1, ballot.getId());
            else
                throw new EVException("Ballot_Issues.delete failed to delete ballot_Issues");

            queryExecution = stmt.executeUpdate();
            //if(queryExecution != 1 )
                //throw new EVException("Ballot_IssuesManager.delete failed to delete");
        }
        catch( SQLException e ) {
            e.printStackTrace();
            throw new EVException( "Ballot_IssuesManager.delete: failed to delete a ballot: " + e );
        }
    }

    public void delete(Issue issue) throws EVException{
        String               deleteBallot = "delete from Ballot_Issues where Issue_ID = ?";
        PreparedStatement    stmt = null;
        int                  queryExecution;

        try{
            stmt = conn.prepareStatement( deleteBallot );
            if(issue.getId() > 0)
                stmt.setInt(1, issue.getId());
            else
                throw new EVException("Ballot_Issues.delete failed to delete ballot_Issues");

            queryExecution = stmt.executeUpdate();
            //if(queryExecution != 1 )
            //throw new EVException("Ballot_IssuesManager.delete failed to delete");
        }
        catch( SQLException e ) {
            e.printStackTrace();
            throw new EVException( "Ballot_IssuesManager.delete: failed to delete a ballot: " + e );
        }
    }

    public List<Ballot> restoreMult(BallotItem ballotItem) throws EVException {
        StringBuffer query = new StringBuffer(500);
        Statement stmt = null;

        if(ballotItem.getId() <1)
            throw new EVException("Ballot_Issues.restore could not restore non persistent issue");

        query.append("select Ballot_ID, Start_Date, End_Date, Issue_ID from (select Ballot.Ballot_ID, Ballot.Start_Date, Ballot.End_Date, Ballot_Issues.Issue_ID from Ballot inner join Ballot_Issues on Ballot_Issues.Ballot_ID = Ballot.Ballot_ID) as T where Issue_ID = " + ballotItem.getId());

        try{
            stmt = conn.createStatement();

            if(stmt.execute(query.toString())) {
                int ballotId;
                Date startDate;
                Date closeDate;
                Ballot newBallot = null;

                ResultSet rs = stmt.getResultSet();
                List<Ballot> ballots = new ArrayList<>();
                while(rs.next()){
                    ballotId = rs.getInt( 1 );
                    startDate = rs.getDate( 2 );
                    closeDate = rs.getDate( 3 );

                    newBallot = objectLayer.createBallot();
                    newBallot.setId( ballotId );
                    newBallot.setOpenDate( startDate );
                    newBallot.setCloseDate( closeDate );
                    newBallot.setPersistent(true);
                    ballots.add(newBallot);
                }
                return ballots;
            }
        }

        catch(SQLException e){
            e.printStackTrace();
            throw new EVException("Ballot_Issues.store failed to save a ballot_Issue" +e);
        }
        throw new EVException("Ballot_Issues.restore could not restore ballot object");
    }
}


