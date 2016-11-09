package main.javaSrc.DBHelpers.Managers;

import main.javaSrc.DBHelpers.ObjectLayer;
import main.javaSrc.Entities.PoliticalParty;
import main.javaSrc.helpers.EVException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by User on 11/2/2016.
 */
public class PoliticalPartyManager {

    private ObjectLayer objectLayer = null;
    private Connection conn = null;

    public PoliticalPartyManager(Connection conn, ObjectLayer objectLayer){
        this.conn = conn;
        this.objectLayer = objectLayer;
    }

    public PoliticalPartyManager() {

    }

    public List<PoliticalParty> restore(PoliticalParty politicalParty) throws EVException {

        String       selectPoliticalParty = "select Party_ID, Party_Name from Party";
        Statement    stmt = null;
        StringBuffer query = new StringBuffer( 100 );
        StringBuffer condition = new StringBuffer( 100 );
        List<PoliticalParty>   politicalParties = new ArrayList<PoliticalParty>();

        condition.setLength( 0 );

        // form the query based on the given politicalParty object instance
        query.append( selectPoliticalParty );

        if( politicalParty != null ) {
            if( politicalParty.getId() >= 0 ) { // id is unique, so it is sufficient to get a person
                query.append(" where Party_ID = " + politicalParty.getId());
            }
            else {

                if( politicalParty.getName() != null )
                    condition.append( " where Party_Name = '" + politicalParty.getName() + "'" );

            }
            query.append( condition );
        }

        try {

            stmt = conn.createStatement();

            // retrieve the persistent politicalParty objects
            //
            if( stmt.execute( query.toString() ) ) { // statement returned a result

                int politicalPartyId;
                String name;
                PoliticalParty nextPoliticalParty = null;

                ResultSet rs = stmt.getResultSet();

                // retrieve the retrieved politicalPartys
                while( rs.next() ) {

                    politicalPartyId = rs.getInt( 1 );
                    name = rs.getString( 2 );

                    nextPoliticalParty = objectLayer.createPoliticalParty(); // create a proxy politicalParty object
                    // and now set its retrieved attributes
                    nextPoliticalParty.setId( politicalPartyId );
                    nextPoliticalParty.setName( name );

                    politicalParties.add( nextPoliticalParty );
                }

                return politicalParties;
            }
        }
        catch( Exception e ) {      // just in case...
            throw new EVException( "PoliticalPartyManager.restore: Could not restore persistent politicalParty objects; Root cause: " + e );
        }

        throw new EVException( "PoliticalPartyManager.restore: Could not restore persistent politicalParty objects" );

    }

    public void store(PoliticalParty politicalParty) throws EVException{
        String insertPoliticalParty = "insert into Party ( Party_Name ) values ( ? )";
        String updatePoliticalParty = "update Party set Party_Name = ?";
        PreparedStatement stmt = null;
        int queryExecution;
        int politicalPartyId;

        try {

            if( !politicalParty.isPersistent() )
                stmt = conn.prepareStatement( insertPoliticalParty );
            else
                stmt = conn.prepareStatement( updatePoliticalParty );

            //Cannot be null

            if( politicalParty.getName() != null )
                stmt.setString( 1, politicalParty.getName() );
            else
                throw new EVException( "PoliticalPartyManager.save: can't save a politicalParty: Name undefined" );


            queryExecution = stmt.executeUpdate();

            if( !politicalParty.isPersistent() ) {
                if( queryExecution >= 1 ) {
                    String sql = "select last_insert_id()";
                    if( stmt.execute( sql ) ) { // statement returned a result

                        // retrieve the result
                        ResultSet r = stmt.getResultSet();

                        // we will use only the first row!
                        //
                        while( r.next() ) {

                            // retrieve the last insert auto_increment value
                            politicalPartyId = r.getInt( 1 );
                            if( politicalPartyId > 0 )
                                politicalParty.setId( politicalPartyId ); // set this person's db id (proxy object)
                        }
                    }
                }
                else
                    throw new EVException( "PoliticalPartyManager.save: failed to save a politicalParty" );
            }
            else {
                if( queryExecution < 1 )
                    throw new EVException( "PoliticalPartyManager.save: failed to save a politicalParty" );
            }
        }
        catch( SQLException e ) {
            e.printStackTrace();
            throw new EVException( "PoliticalPartyManager.save: failed to save a politicalParty: " + e );
        }


    }

    public void delete(PoliticalParty politicalParty) throws EVException {

        String               deletePoliticalParty = "delete from Party where Party_ID = ?";
        PreparedStatement    stmt = null;
        int                  queryExecution;

        if( !politicalParty.isPersistent() ) // is the politicalParty object persistent?  If not, nothing to actually delete
            return;

        try {
            stmt = conn.prepareStatement( deletePoliticalParty );
            stmt.setInt( 1, politicalParty.getId() );
            queryExecution = stmt.executeUpdate();
            if( queryExecution == 1 ) {
                return;
            }
            else
                throw new EVException( "PoliticalPartyManager.delete: failed to delete a politicalParty" );
        }
        catch( SQLException e ) {
            e.printStackTrace();
            throw new EVException( "PoliticalPartyManager.delete: failed to delete a politicalParty: " + e );        }
    }

}

