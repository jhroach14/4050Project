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
public class IssueManager extends Manager{


    public IssueManager(Connection conn, ObjectLayer objectLayer){
        super(conn,objectLayer);
    }

    public IssueManager() {

    }

    public List<Issue> restore(Issue issue) throws EVException {

        Statement    stmt = null;
        String query = "";
        List<Issue>   issues = new ArrayList<Issue>();

        if( issue != null ) {
            query = issue.getRestoreString();
        }

        try {

            stmt = conn.createStatement();

            // retrieve the persistent issue objects
            //
            if( stmt.execute( query ) ) { // statement returned a result

                int issueId;
                String question;
                int voteCount;
                int yesCount;
                int noCount;
                Issue nextIssue = null;

                ResultSet rs = stmt.getResultSet();

                // retrieve the retrieved issues
                while( rs.next() ) {

                    issueId = rs.getInt( 1 );
                    question = rs.getString( 2 );
                    voteCount = rs.getInt( 3 );
                    yesCount = rs.getInt( 4 );
                    noCount = rs.getInt( 5 );


                    nextIssue = objectLayer.createIssue(); // create a proxy issue object
                    // and now set its retrieved attributes
                    nextIssue.setId( issueId );
                    nextIssue.setQuestion( question );
                    nextIssue.setVoteCount( voteCount );
                    nextIssue.setYesCount( yesCount );
                    nextIssue.setNoCount( noCount );
                    nextIssue.setPersistent(true);

                    issues.add( nextIssue );
                }

                return issues;
            }
        }
        catch( Exception e ) {      // just in case...
            throw new EVException( "issueManager.restore: Could not restore persistent issue objects; Root cause: " + e );
        }

        throw new EVException( "issueManager.restore: Could not restore persistent issue objects" );

    }

    public Issue store(Issue issue) throws EVException{
        String insertIssue = "insert into Issue ( Question, Vote_Count, Yes_Count, No_Count ) values ( ?, ?, ?, ? )";
        String updateIssue = "update Issue set Question = ?, Vote_Count = ?, Yes_Count = ?, No_Count = ? where Issue_ID = ?";
        PreparedStatement stmt = null;
        int queryExecution;

        try {

            if( !issue.isPersistent() )
                stmt = conn.prepareStatement( insertIssue );
            else
                stmt = conn.prepareStatement( updateIssue );

            stmt = issue.insertStoreData(stmt);

            queryExecution = stmt.executeUpdate();

            if( !issue.isPersistent() ) {
                if( queryExecution >= 1 ) {
                    issue = (Issue)setId(stmt,issue);
                    issue.setPersistent(true);
                }
                else
                    throw new EVException( "IssueManager.save: failed to save a issue" );
            }
            else {
                if( queryExecution < 1 )
                    throw new EVException( "IssueManager.save: failed to save a issue" );
            }
        }
        catch( SQLException e ) {
            e.printStackTrace();
            throw new EVException( "IssueManager.save: failed to save a issue: " + e );
        }
        return issue;

    }

    public void delete(Issue issue) throws EVException {

        String               deleteIssue = "delete from Issue where Issue_ID = ?";
        PreparedStatement    stmt = null;
        int                  queryExecution;

        if( !issue.isPersistent() ) // is the issue object persistent?  If not, nothing to actually delete
            return;

        try {
            stmt = conn.prepareStatement( deleteIssue );
            stmt.setInt( 1, issue.getId() );
            queryExecution = stmt.executeUpdate();
            if( queryExecution == 1 ) {
                return;
            }
            else
                throw new EVException( "issueManager.delete: failed to delete a issue" );
        }
        catch( SQLException e ) {
            e.printStackTrace();
            throw new EVException( "issueManager.delete: failed to delete a issue: " + e );        }
    }

}

