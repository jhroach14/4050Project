package main.javaSrc.Entities.EntityImpl;

import main.javaSrc.Entities.BallotItem;
import main.javaSrc.Entities.Issue;
import main.javaSrc.helpers.EVException;
import org.codehaus.jackson.annotate.JsonIgnore;

import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Created by User on 11/8/2016.
 */
public class IssueImpl extends BallotItemImpl implements Issue {

    private String question;
    private int yesCount;
    private int noCount;

    public IssueImpl(String question) {
        this.question = question;
    }

    public IssueImpl() {

    }

    @JsonIgnore
    @Override
    public String getRestoreString() throws EVException {
        StringBuffer query = new StringBuffer( 100 );
        StringBuffer condition = new StringBuffer( 100 );
        String restoreStr = "select Issue_ID, Question, Vote_Count, Yes_Count, No_Count from Issue";

        condition.setLength( 0 );
        query.append( restoreStr );

        if( getId() > 0 ) { // id is unique, so it is sufficient to get a person
            query.append(" where Issue_ID = " + getId());
        }
        else {

            if( getQuestion() != null ) {
                condition.append(" where Question = '" + getQuestion() + "'");

                if (getVoteCount() >= 0) {
                    if (condition.length() > 0)
                        condition.append(" and");
                    else
                        condition.append(" where");
                    condition.append(" Vote_Count = '" + getVoteCount() + "'");
                }

                if (getYesCount() >= 0) {
                    if (condition.length() > 0)
                        condition.append(" and");
                    else
                        condition.append(" where");
                    condition.append(" Yes_Count = '" + getYesCount() + "'");
                }

                if (getNoCount() >= 0) {
                    if (condition.length() > 0)
                        condition.append(" and");
                    else
                        condition.append(" where");
                    condition.append(" No_Count = '" + getNoCount() + "'");
                }
            }
            query.append( condition );
        }

        return query.toString();
    }

    @JsonIgnore
    @Override
    public PreparedStatement insertStoreData(PreparedStatement stmt) throws EVException, SQLException {
        //Cannot be null

        if( getQuestion() != null )
            stmt.setString( 1, getQuestion() );
        else
            throw new EVException( "IssueManager.save: can't save a  Question undefined" );

        //the rest can be null
        if( getVoteCount() >= 0 )
            stmt.setInt( 2, getVoteCount() );
        else
            stmt.setNull( 2, java.sql.Types.INTEGER );

        if( getYesCount() >= 0 )
            stmt.setInt( 3, getYesCount() );
        else
            stmt.setNull( 3, java.sql.Types.INTEGER );

        if( getNoCount() >= 0 )
            stmt.setInt( 4, getNoCount() );
        else
            stmt.setNull( 4, java.sql.Types.INTEGER );

        if (isPersistent()){
            stmt.setInt(5,this.getId());
        }

        return stmt;

    }

    @JsonIgnore
    @Override
    public String getType(){
        return "Issue";
    }

    @Override
    public String getQuestion() {
        return question;
    }

    @Override
    public void setQuestion(String question) {
        this.question=question;
    }

    @Override
    public int getYesCount() {
        return yesCount;
    }

    @Override
    public void setYesCount(int yesCount) throws EVException {
        this.yesCount=yesCount;
    }

    @Override
    public int getNoCount() {
        return noCount;
    }

    @Override
    public void setNoCount(int noCount){
        this.noCount=noCount;
    }

    @Override
    public void addYesVote() {
        yesCount++;
    }

    @Override
    public void addNoVote() {
        noCount++;
    }
}
