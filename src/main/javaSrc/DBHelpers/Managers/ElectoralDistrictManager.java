package main.javaSrc.DBHelpers.Managers;

import main.javaSrc.DBHelpers.ObjectLayer;
import main.javaSrc.Entities.Ballot;
import main.javaSrc.Entities.ElectoralDistrict;
import main.javaSrc.helpers.EVException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by User on 11/2/2016.
 */
public class ElectoralDistrictManager extends Manager{


    public ElectoralDistrictManager(Connection conn, ObjectLayer objectLayer){
        super(conn, objectLayer);
    }

    public ElectoralDistrictManager() {

    }

    public List<ElectoralDistrict> restore(ElectoralDistrict electoralDistrict) throws EVException {

        Statement    stmt = null;
        String query ="";
        List<ElectoralDistrict>   electoralDistricts = new ArrayList<ElectoralDistrict>();

        if( electoralDistrict != null ) {
            query = electoralDistrict.getRestoreString();
        }

        try {

            stmt = conn.createStatement();

            // retrieve the persistent electoralDistrict objects
            //
            if( stmt.execute( query) ) { // statement returned a result

                int electoralDistrictId;
                String name;
                String zip;
                ElectoralDistrict nextElectoralDistrict = null;

                ResultSet rs = stmt.getResultSet();

                // retrieve the retrieved electoralDistricts
                while( rs.next() ) {

                    electoralDistrictId = rs.getInt( 1 );
                    name = rs.getString( 2 );
                    zip = rs.getString( 3 );

                    nextElectoralDistrict = objectLayer.createElectoralDistrict(); // create a proxy electoralDistrict object
                    // and now set its retrieved attributes
                    nextElectoralDistrict.setId( electoralDistrictId );
                    nextElectoralDistrict.setName( name );
                    nextElectoralDistrict.setZip( zip );
                    nextElectoralDistrict.setPersistent(true);

                    electoralDistricts.add( nextElectoralDistrict );
                }

                return electoralDistricts;
            }
        }
        catch( Exception e ) {      // just in case...
            throw new EVException( "ElectoralDistrictManager.restore: Could not restore persistent electoralDistrict objects; Root cause: " + e );
        }

        throw new EVException( "ElectoralDistrictManager.restore: Could not restore persistent electoralDistrict objects" );

    }

    public ElectoralDistrict store(ElectoralDistrict electoralDistrict) throws EVException{
        String insertElectoralDistrict = "insert into District ( District_Name , District_Zip) values ( ?, ?)";
        String updateElectoralDistrict = "update District set District_Name = ?, District_Zip = ? where District_ID = ?";
        PreparedStatement stmt = null;
        int queryExecution;
        int electoralDistrictId;

        try {

            if( !electoralDistrict.isPersistent() ) {
                stmt = conn.prepareStatement(insertElectoralDistrict);
            }else {
                stmt = conn.prepareStatement(updateElectoralDistrict);
            }

            stmt = electoralDistrict.insertStoreData(stmt);

            queryExecution = stmt.executeUpdate();

            if( !electoralDistrict.isPersistent() ) {
                if( queryExecution >= 1 ) {
                    electoralDistrict = (ElectoralDistrict)setId(stmt,electoralDistrict);
                    electoralDistrict.setPersistent(true);
                }
                else
                    throw new EVException( "ElectoralDistrictManager.save: failed to save a electoralDistrict" );
            }
            else {
                if( queryExecution < 1 )
                    throw new EVException( "ElectoralDistrictManager.save: failed to save a electoralDistrict" );
            }
        }
        catch( SQLException e ) {
            e.printStackTrace();
            throw new EVException( "ElectoralDistrictManager.save: failed to save a electoralDistrict: " + e );
        }

        return electoralDistrict;
    }

    public void delete(ElectoralDistrict electoralDistrict) throws EVException {

        String               deleteElectoralDistrict = "delete from District where District_ID = ?";
        PreparedStatement    stmt = null;
        int                  queryExecution;

        if( !electoralDistrict.isPersistent() ) // is the electoralDistrict object persistent?  If not, nothing to actually delete
            return;

        try {
            stmt = conn.prepareStatement( deleteElectoralDistrict );
            stmt.setInt( 1, electoralDistrict.getId() );
            queryExecution = stmt.executeUpdate();
            if( queryExecution == 1 ) {
                return;
            }
            else
                throw new EVException( "ElectoralDistrictManager.delete: failed to delete a electoralDistrict" );
        }
        catch( SQLException e ) {
            e.printStackTrace();
            throw new EVException( "ElectoralDistrictManager.delete: failed to delete a electoralDistrict: " + e );        }
    }

}

