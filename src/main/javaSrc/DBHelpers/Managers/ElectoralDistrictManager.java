package main.javaSrc.DBHelpers.Managers;

import main.javaSrc.DBHelpers.ObjectLayer;
import main.javaSrc.Entities.ElectoralDistrict;
import main.javaSrc.helpers.EVException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by User on 11/2/2016.
 */
public class ElectoralDistrictManager {

    private ObjectLayer objectLayer = null;
    private Connection conn = null;

    public ElectoralDistrictManager(Connection conn, ObjectLayer objectLayer){
        this.conn = conn;
        this.objectLayer = objectLayer;
    }

    public List<ElectoralDistrict> restore(ElectoralDistrict electoralDistrict) throws EVException {

        String       selectElectoralDistrict = "select District_ID, District_Name from District";
        Statement    stmt = null;
        StringBuffer query = new StringBuffer( 100 );
        StringBuffer condition = new StringBuffer( 100 );
        List<ElectoralDistrict>   electoralDistricts = new ArrayList<ElectoralDistrict>();

        condition.setLength( 0 );

        // form the query based on the given electoralDistrict object instance
        query.append( selectElectoralDistrict );

        if( electoralDistrict != null ) {
            if( electoralDistrict.getId() >= 0 ) { // id is unique, so it is sufficient to get a person
                query.append(" where District_ID = " + electoralDistrict.getId());
            }
            else {

                if( electoralDistrict.getName() != null )
                    condition.append( " where District_Name = '" + electoralDistrict.getName() + "'" );

            }
            query.append( condition );
        }

        try {

            stmt = conn.createStatement();

            // retrieve the persistent electoralDistrict objects
            //
            if( stmt.execute( query.toString() ) ) { // statement returned a result

                int electoralDistrictId;
                String name;
                ElectoralDistrict nextElectoralDistrict = null;

                ResultSet rs = stmt.getResultSet();

                // retrieve the retrieved electoralDistricts
                while( rs.next() ) {

                    electoralDistrictId = rs.getInt( 1 );
                    name = rs.getString( 2 );

                    nextElectoralDistrict = objectLayer.createElectoralDistrict(); // create a proxy electoralDistrict object
                    // and now set its retrieved attributes
                    nextElectoralDistrict.setId( electoralDistrictId );
                    nextElectoralDistrict.setName( name );

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

    public void store(ElectoralDistrict electoralDistrict) throws EVException{
        String insertElectoralDistrict = "insert into District ( District_Name ) values ( ? )";
        String updateElectoralDistrict = "update District set District_Name = ?";
        PreparedStatement stmt = null;
        int queryExecution;
        int electoralDistrictId;

        try {

            if( !electoralDistrict.isPersistent() )
                stmt = conn.prepareStatement( insertElectoralDistrict );
            else
                stmt = conn.prepareStatement( updateElectoralDistrict );

            //Cannot be null

            if( electoralDistrict.getName() != null )
                stmt.setString( 1, electoralDistrict.getName() );
            else
                throw new EVException( "ElectoralDistrictManager.save: can't save a electoralDistrict: Name undefined" );


            queryExecution = stmt.executeUpdate();

            if( !electoralDistrict.isPersistent() ) {
                if( queryExecution >= 1 ) {
                    String sql = "select last_insert_id()";
                    if( stmt.execute( sql ) ) { // statement returned a result

                        // retrieve the result
                        ResultSet r = stmt.getResultSet();

                        // we will use only the first row!
                        //
                        while( r.next() ) {

                            // retrieve the last insert auto_increment value
                            electoralDistrictId = r.getInt( 1 );
                            if( electoralDistrictId > 0 )
                                electoralDistrict.setId( electoralDistrictId ); // set this person's db id (proxy object)
                        }
                    }
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

