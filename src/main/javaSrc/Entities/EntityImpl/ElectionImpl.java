package main.javaSrc.Entities.EntityImpl;

import main.javaSrc.Entities.*;
import main.javaSrc.helpers.EVException;
import org.codehaus.jackson.annotate.JsonIgnore;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by User on 11/8/2016.
 */
public class ElectionImpl extends BallotItemImpl implements Election{

    private String office;
    private boolean isPartisan;

    public ElectionImpl(String office, boolean isPartisan) {
        this.office = office;
        this.isPartisan = isPartisan;
    }

    public ElectionImpl(){

    }

    @JsonIgnore
    @Override
    public String getRestoreString() throws EVException {
        StringBuffer query = new StringBuffer( 100 );
        StringBuffer condition = new StringBuffer( 100 );
        String restoreStr = "select Election_ID, Office_Name, Is_Partisan, Vote_Count from Election";

        condition.setLength( 0 );
        query.append( restoreStr );

        if( getId() > 0 ) { // id is unique, so it is sufficient to get a person
            query.append(" where Election_ID = " + getId());
        }
        else {

            if( getOffice() != null )
                condition.append( " where Office_Name = '" + getOffice() + "'" );


            if( getVoteCount() >= 0 ){
                if( condition.length() > 0 )
                    condition.append( " and" );
                else
                    condition.append( " where" );
                condition.append( " Vote_Count = " + getVoteCount() );

            }
        }
        query.append( condition );

        return query.toString();
    }

    @JsonIgnore
    @Override
    public PreparedStatement insertStoreData(PreparedStatement stmt) throws EVException, SQLException {
        //Cannot be null

        if( getOffice() != null )
            stmt.setString( 1, getOffice() );
        else
            throw new EVException( "ElectionManager.save: can't save a  Office Name undefined" );

        if( getIsPartisan() || !getIsPartisan() )
            stmt.setBoolean( 2, getIsPartisan() );
        else
            throw new EVException( "ElectionManager.save: can't save a  Is_Partisan undefined" );


        //The rest can be null

        if( getVoteCount() >= 0 )
            stmt.setInt( 3, getVoteCount() );
        else
            stmt.setNull( 3, java.sql.Types.INTEGER );

        if(isPersistent()){
            stmt.setInt(4,this.getId());
        }
        
        return stmt;
    }
    @JsonIgnore
    @Override
    public String getType(){
        return "Election";
    }

    @Override
    public String getOffice() {
        return office;
    }

    @Override
    public void setOffice(String office) {
        this.office=office;
    }

    @Override
    public boolean getIsPartisan() {
        return isPartisan;
    }

    @Override
    public void setIsPartisan(boolean isPartisan) {
        this.isPartisan=isPartisan;
    }

}
