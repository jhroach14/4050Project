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
    private String zip;

    public ElectoralDistrictImpl(String name, String zip) {
        this.name = name;
        this.zip = zip;
    }

    public ElectoralDistrictImpl() {

    }

    @JsonIgnore
    @Override
    public String getRestoreString(){

        StringBuffer query = new StringBuffer( 100 );
        StringBuffer condition = new StringBuffer( 100 );
        String restoreStr = "select District_ID, District_Name, District_Zip from District";

        condition.setLength( 0 );
        query.append( restoreStr );

        if( getId() > 0 ) { // id is unique, so it is sufficient to get a person
            query.append(" where District_ID = " + getId());
        }
        else {

            if( getName() != null )
                condition.append( " where District_Name = '" + getName() + "'" );
            else {

                if( getZip() != null )
                    condition.append( " where District_Zip = '" + getZip() + "'" );

            }

        }
        query.append( condition );

        return query.toString();
    }

    @JsonIgnore
    @Override
    public PreparedStatement insertStoreData(PreparedStatement stmt) throws EVException, SQLException {
        //Cannot be null

        if( getName() != null && getZip() != null ) {
            stmt.setString(1, getName());
            stmt.setString(2, getZip());
        } else
            throw new EVException( "ElectoralDistrictManager.save: can't save a electoralDistrict: Name undefined" );

        if(isPersistent()){
            stmt.setInt(3,getId());
        }

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
    public String getZip() {
        return zip;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public void setZip(String zip) { this.zip = zip; }
}
