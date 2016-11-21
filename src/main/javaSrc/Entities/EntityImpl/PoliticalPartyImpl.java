package main.javaSrc.Entities.EntityImpl;

import main.javaSrc.Entities.Candidate;
import main.javaSrc.Entities.PoliticalParty;
import main.javaSrc.helpers.EVException;
import org.codehaus.jackson.annotate.JsonIgnore;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by User on 11/8/2016.
 */
public class PoliticalPartyImpl extends EntityImpl implements PoliticalParty {
    String name;

    public PoliticalPartyImpl(String name) {
        this.name = name;
    }

    public PoliticalPartyImpl(){

    }

    @JsonIgnore
    @Override
    public String getRestoreString() throws EVException {
        StringBuffer query = new StringBuffer( 100 );
        StringBuffer condition = new StringBuffer( 100 );
        String restoreStr = "select Party_ID, Party_Name from Party";

        condition.setLength( 0 );
        query.append( restoreStr );

        if( getId() >= 0 ) { // id is unique, so it is sufficient to get a person
            query.append(" where Party_ID = " + getId());
        }
        else {

            if( getName() != null ){
                condition.append( " where Party_Name = '" + getName() + "'" );
            }
            query.append( condition );
        }

        return query.toString();
    }

    @JsonIgnore
    @Override
    public PreparedStatement insertStoreData(PreparedStatement stmt) throws EVException, SQLException {
        //Cannot be null

        if( getName() != null )
            stmt.setString( 1, getName() );
        else
            throw new EVException( "PoliticalPartyManager.save: can't save a politicalParty: Name undefined" );

        return stmt;
    }

    @JsonIgnore
    @Override
    public String getType() {
        return "PoliticalParty";
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

}
