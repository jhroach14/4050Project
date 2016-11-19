package main.javaSrc.Entities.EntityImpl;

import main.javaSrc.Entities.Ballot;
import main.javaSrc.Entities.ElectoralDistrict;
import main.javaSrc.Entities.Voter;
import main.javaSrc.helpers.EVException;
import org.codehaus.jackson.annotate.JsonIgnore;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by User on 11/8/2016.
 */
public class ElectoralDistrictImpl extends EntityImpl implements ElectoralDistrict {

    private String name;

    public ElectoralDistrictImpl(String name) {
        this.name = name;
    }

    public ElectoralDistrictImpl() {

    }

    @JsonIgnore
    @Override
    public String getRestoreString(){

        StringBuffer query = new StringBuffer( 100 );
        StringBuffer condition = new StringBuffer( 100 );
        String restoreStr = "select District_ID, District_Name from District";

        condition.setLength( 0 );
        query.append( restoreStr );

        if( getId() > 0 ) { // id is unique, so it is sufficient to get a person
            query.append(" where District_ID = " + getId());
        }
        else {

            if( getName() != null )
                condition.append( " where District_Name = '" + getName() + "'" );

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
            throw new EVException( "ElectoralDistrictManager.save: can't save a electoralDistrict: Name undefined" );

        return stmt;
    }

    @JsonIgnore
    @Override
    public String getType() {
        return "ElectoralDistrict";
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
