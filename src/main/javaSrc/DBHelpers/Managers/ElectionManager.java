package main.javaSrc.DBHelpers.Managers;

import main.javaSrc.DBHelpers.ObjectLayer;
import main.javaSrc.Entities.Election;
import main.javaSrc.helpers.EVException;


import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by User on 11/2/2016.
 */
public class ElectionManager extends Manager{


    public ElectionManager(Connection conn, ObjectLayer objectLayer){
        super(conn,objectLayer);
    }

    public ElectionManager() {

    }

    public List<Election> restore(Election election) throws EVException {

        Statement    stmt = null;
        String query="";
        List<Election>   elections = new ArrayList<Election>();

        if( election != null ) {
            query = election.getRestoreString();
        }

        try {

            stmt = conn.createStatement();

            // retrieve the persistent election objects
            //
            if( stmt.execute( query) ) { // statement returned a result

                int electionId;
                String officeName;
                int isPartisanInt;
                boolean isPartisan;
                int voteCount;
                Election   nextElection = null;

                ResultSet rs = stmt.getResultSet();

                // retrieve the retrieved elections
                while( rs.next() ) {

                    electionId = rs.getInt( 1 );
                    officeName = rs.getString( 2 );
                    isPartisanInt = rs.getInt( 3 );
                    if(isPartisanInt == 1)
                        isPartisan = true;
                    else
                        isPartisan = false;
                    voteCount = rs.getInt( 4 );

                    nextElection = objectLayer.createElection(); // create a proxy election object
                    // and now set its retrieved attributes
                    nextElection.setId( electionId );
                    nextElection.setOffice( officeName );
                    nextElection.setIsPartisan( isPartisan );
                    nextElection.setVoteCount( voteCount );
                    nextElection.setPersistent(true);

                    elections.add( nextElection );
                }

                return elections;
            }
        }
        catch( Exception e ) {      // just in case...
            throw new EVException( "electionManager.restore: Could not restore persistent election objects; Root cause: " + e );
        }

        throw new EVException( "electionManager.restore: Could not restore persistent election objects" );

    }

    public Election store(Election election) throws EVException{
        String insertElection = "insert into Election ( Office_Name, Is_Partisan, Vote_Count) values ( ?, ?, ? )";
        String updateElection = "update Election set  Office_Name = ?, Is_Partisan = ?, Vote_Count = ?";
        PreparedStatement stmt = null;
        int queryExecution;
        int electionId;

        try {

            if( !election.isPersistent() ) {
                stmt = conn.prepareStatement(insertElection);
            }else {
                stmt = conn.prepareStatement(updateElection);
            }

            stmt = election.insertStoreData(stmt);

            queryExecution = stmt.executeUpdate();

            if( !election.isPersistent() ) {
                if( queryExecution >= 1 ) {
                   election = (Election) setId(stmt,election);
                    election.setPersistent(true);
                }
                else
                    throw new EVException( "ElectionManager.save: failed to save a election" );
            }
            else {
                if( queryExecution < 1 )
                    throw new EVException( "ElectionManager.save: failed to save a election" );
            }
        }
        catch( SQLException e ) {
            e.printStackTrace();
            throw new EVException( "ElectionManager.save: failed to save a election: " + e );
        }
        return election;

    }

    public void delete(Election election) throws EVException {

        String               deleteElection = "delete from Election where Election_ID = ?";
        PreparedStatement    stmt = null;
        int                  queryExecution;

        if( !election.isPersistent() ) // is the election object persistent?  If not, nothing to actually delete
            return;

        try {
            stmt = conn.prepareStatement( deleteElection );
            stmt.setInt( 1, election.getId() );
            queryExecution = stmt.executeUpdate();
            if( queryExecution == 1 ) {
                return;
            }
            else
                throw new EVException( "electionManager.delete: failed to delete a election" );
        }
        catch( SQLException e ) {
            e.printStackTrace();
            throw new EVException( "electionManager.delete: failed to delete a election: " + e );        }
    }

}

