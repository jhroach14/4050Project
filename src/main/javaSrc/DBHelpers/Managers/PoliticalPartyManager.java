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
public class PoliticalPartyManager extends Manager{


    public PoliticalPartyManager(Connection conn, ObjectLayer objectLayer){
        super(conn,objectLayer);
    }

    public PoliticalPartyManager() {

    }

    public List<PoliticalParty> restore(PoliticalParty politicalParty) throws EVException {

        Statement    stmt = null;
        String query = "";
        List<PoliticalParty>   politicalParties = new ArrayList<PoliticalParty>();

        if( politicalParty != null ) {
            query = politicalParty.getRestoreString();
        }

        try {

            stmt = conn.createStatement();

            // retrieve the persistent politicalParty objects
            //
            if( stmt.execute( query ) ) { // statement returned a result

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
                    nextPoliticalParty.setPersistent(true);

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

    public PoliticalParty store(PoliticalParty politicalParty) throws EVException{
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

            stmt = politicalParty.insertStoreData(stmt);

            queryExecution = stmt.executeUpdate();

            if( !politicalParty.isPersistent() ) {
                if( queryExecution >= 1 ) {
                    politicalParty = (PoliticalParty)setId(stmt,politicalParty);
                    politicalParty.setPersistent(true);
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

        return politicalParty;
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

