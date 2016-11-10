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
public class ElectoralDistrictManager {

    private ObjectLayer objectLayer = null;
    private Connection conn = null;

    public ElectoralDistrictManager(Connection conn, ObjectLayer objectLayer){
        this.conn = conn;
        this.objectLayer = objectLayer;
    }

    public ElectoralDistrictManager() {

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

    //needs work to be done
    public void store(ElectoralDistrict electoralDistrict, Ballot ballot)throws EVException{
        String insertDistrictsBallot = "insert into Ballot (Start_Date, End_Date, District_ID) values ( ?, ?, ?)";
        String updateDistrictBallot = "update Ballot set Start_Date = ?, End_Date = ?, District_ID = ?";
        PreparedStatement stmt = null;
        int queryExecution;
        int ballotID;

        try {

            if( !ballot.isPersistent() )
                stmt = conn.prepareStatement( insertDistrictsBallot );
            else
                stmt = conn.prepareStatement( updateDistrictBallot );

            if( ballot.getOpenDate() != null )
                stmt.setDate( 1, (Date)ballot.getOpenDate());
            else
                throw new EVException( "ElectoralDistrictManager.save: can't save a ballot: Start Date undefined" );

            if( ballot.getCloseDate() != null )
                stmt.setDate( 2, (Date)ballot.getCloseDate() );
            else
                throw new EVException( "ElectoralDistrictManager.save: can't save a ballot: Close Date undefined" );

            if(electoralDistrict.getId() !=0)
                stmt.setInt(3, electoralDistrict.getId());
            else
                throw new EVException( "ElectoralDistrictManager.save: can't save a ballot: ElectoralDistrict_ID undefined" );


            queryExecution = stmt.executeUpdate();

            if( !ballot.isPersistent() ) {
                if( queryExecution >= 1 ) {
                    String sql = "select last_insert_id()";
                    if( stmt.execute( sql ) ) {
                        ResultSet r = stmt.getResultSet();

                        while( r.next() ) {
                            ballotID = r.getInt( 1 );
                            if( ballotID > 0 )
                                ballot.setId( ballotID ); // set this person's db id (proxy object)
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

    //needs work still
    public ElectoralDistrict restoreElectoralDistrict (Ballot ballot) throws EVException{
        return null;
    }

    //still needs work
    public List<Ballot> restoreBallot (ElectoralDistrict electoralDistrict) throws EVException{
        return null;
    }


    public void delete(ElectoralDistrict electoralDistrict, Ballot ballot) throws EVException{
        String               deleteElectoralDistrict = "delete from Ballot where District_ID = ?";
        PreparedStatement    stmt = null;
        int                  queryExecution;

        try{
            stmt = conn.prepareStatement( deleteElectoralDistrict );
            if(electoralDistrict.getId() >0)
                stmt.setInt(1, electoralDistrict.getId());
            else
                throw new EVException("ElectoralDistrictManager.delete failed to delete electoralDistrict");
            queryExecution = stmt.executeUpdate();
            if(queryExecution != 1)
                throw new EVException("ElectoralDistrictManager.delete failed to delete");
        }
        catch( SQLException e ) {
            e.printStackTrace();
            throw new EVException( "ElectoralDistrictManger.delete: failed to delete a ElectoralDistrict: " + e );
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

