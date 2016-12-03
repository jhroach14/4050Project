package main.javaSrc.Entities.EntityImpl;

import main.javaSrc.Entities.Candidate;
import main.javaSrc.Entities.Election;
import main.javaSrc.Entities.PoliticalParty;
import main.javaSrc.helpers.EVException;
import org.codehaus.jackson.annotate.JsonIgnore;

import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Created by User on 11/8/2016.
 */
public class CandidateImpl extends EntityImpl implements Candidate {

    private String name;
    private  int voteCount;

    public CandidateImpl(String name) {
        this.name=name;
    }

    public CandidateImpl() {

    }

    @JsonIgnore
    @Override
    public String getRestoreString() throws EVException {
        StringBuffer query = new StringBuffer( 100 );
        StringBuffer condition = new StringBuffer( 100 );
        String restoreStr = "select Candidate_ID, Candidate_Name, Vote_Count from Candidate";

        condition.setLength( 0 );
        query.append( restoreStr );

        if( getId() > 0 ) { // id is unique, so it is sufficient to get a person
            query.append(" where Candidate_ID = " + getId());
        }
        else {

            if( getName() != null )
                condition.append( " where Candidate_Name = '" + getName() + "'" );

            if( getVoteCount() >= 0){
                if( condition.length() > 0 )
                    condition.append( " and" );
                else
                    condition.append( " where" );
                condition.append( " Vote_Count = '" + getVoteCount() + "'" );

            }

        }
        query.append( condition );

        return query.toString();
    }

    @JsonIgnore
    @Override
    public PreparedStatement insertStoreData(PreparedStatement stmt) throws EVException, SQLException {
        //Cannot be null

        if( getName() != null )
            stmt.setString( 1, getName() );
        else
            throw new EVException( "CandidateManager.save: can't save a  Name undefined" );


        //The rest can be null

        if( getVoteCount() >= 0 )
            stmt.setInt( 2, getVoteCount() );
        else
            stmt.setNull( 2, java.sql.Types.INTEGER );

        if (isPersistent()){
            stmt.setInt(3,this.getId());
        }
        
        return stmt;
    }

    @JsonIgnore
    @Override
    public String getType() {
        return "Candidate";
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name=name;
    }

    @Override
    public int getVoteCount() {
        return voteCount;
    }

    @Override
    public void setVoteCount(int voteCount) throws EVException {
        this.voteCount = voteCount;
    }

    @Override
    public void addVote() {
        voteCount++;
    }

}
