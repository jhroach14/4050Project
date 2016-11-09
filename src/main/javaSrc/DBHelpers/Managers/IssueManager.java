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
public class IssueManager {

    private ObjectLayer objectLayer = null;
    private Connection conn = null;

    public IssueManager(Connection conn, ObjectLayer objectLayer){
        this.conn = conn;
        this.objectLayer = objectLayer;
    }

    public List<Issue> restore(Issue issue) throws EVException {

        String       selectIssue = "select Issue_ID, Question, Vote_Count, Yes_Count, No_Count from Issue";
        Statement    stmt = null;
        StringBuffer query = new StringBuffer( 100 );
        StringBuffer condition = new StringBuffer( 100 );
        List<Issue>   issues = new ArrayList<Issue>();

        condition.setLength( 0 );

        // form the query based on the given issue object instance
        query.append( selectIssue );

        if( issue != null ) {
            if( issue.getId() >= 0 ) { // id is unique, so it is sufficient to get a person
                query.append(" where Issue_ID = " + issue.getId());
            }
            else {

                if( issue.getQuestion() != null )
                    condition.append( " where Question = '" + issue.getQuestion() + "'" );

                if( issue.getVoteCount() >= 0){
                    if( condition.length() > 0 )
                        condition.append( " and" );
                    else
                        condition.append( " where" );
                    condition.append( " Vote_Count = '" + issue.getVoteCount() + "'" );
                }

                if( issue.getYesCount() >= 0){
                    if( condition.length() > 0 )
                        condition.append( " and" );
                    else
                        condition.append( " where" );
                    condition.append( " Yes_Count = '" + issue.getYesCount() + "'" );
                }

                if( issue.getNoCount() >= 0){
                    if( condition.length() > 0 )
                        condition.append( " and" );
                    else
                        condition.append( " where" );
                    condition.append( " No_Count = '" + issue.getNoCount() + "'" );
                }

            }
            query.append( condition );
        }

        try {

            stmt = conn.createStatement();

            // retrieve the persistent issue objects
            //
            if( stmt.execute( query.toString() ) ) { // statement returned a result

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

    public void store(Issue issue) throws EVException{
        String insertIssue = "insert into Issue ( Question, Vote_Count, Yes_Count, No_Count ) values ( ?, ?, ?, ? )";
        String updateIssue = "update Issue set Question = ?, Vote_Count = ?, Yes_Count = ?, No_Count = ?";
        PreparedStatement stmt = null;
        int queryExecution;
        int issueId;

        try {

            if( !issue.isPersistent() )
                stmt = conn.prepareStatement( insertIssue );
            else
                stmt = conn.prepareStatement( updateIssue );

            //Cannot be null

            if( issue.getQuestion() != null )
                stmt.setString( 1, issue.getQuestion() );
            else
                throw new EVException( "IssueManager.save: can't save a issue: Question undefined" );

            //the rest can be null
            if( issue.getVoteCount() >= 0 )
                stmt.setInt( 2, issue.getVoteCount() );
            else
                stmt.setNull( 2, java.sql.Types.INTEGER );

            if( issue.getYesCount() >= 0 )
                stmt.setInt( 3, issue.getYesCount() );
            else
                stmt.setNull( 3, java.sql.Types.INTEGER );

            if( issue.getNoCount() >= 0 )
                stmt.setInt( 4, issue.getNoCount() );
            else
                stmt.setNull( 4, java.sql.Types.INTEGER );


            queryExecution = stmt.executeUpdate();

            if( !issue.isPersistent() ) {
                if( queryExecution >= 1 ) {
                    String sql = "select last_insert_id()";
                    if( stmt.execute( sql ) ) { // statement returned a result

                        // retrieve the result
                        ResultSet r = stmt.getResultSet();

                        // we will use only the first row!
                        //
                        while( r.next() ) {

                            // retrieve the last insert auto_increment value
                            issueId = r.getInt( 1 );
                            if( issueId > 0 )
                                issue.setId( issueId ); // set this person's db id (proxy object)
                        }
                    }
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

