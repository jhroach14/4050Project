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
public class ElectionManager {

    private ObjectLayer objectLayer = null;
    private Connection conn = null;

    public ElectionManager(Connection conn, ObjectLayer objectLayer){
        this.conn = conn;
        this.objectLayer = objectLayer;
    }

    public List<Election> restore(Election election) throws EVException {

        String       selectElection = "select Election_ID, Office_Name, Is_Partisan, Vote_Count from Election";
        Statement    stmt = null;
        StringBuffer query = new StringBuffer( 100 );
        StringBuffer condition = new StringBuffer( 100 );
        List<Election>   elections = new ArrayList<Election>();

        condition.setLength( 0 );

        // form the query based on the given election object instance
        query.append( selectElection );

        if( election != null ) {
            if( election.getId() >= 0 ) { // id is unique, so it is sufficient to get a person
                query.append(" where Elections_Officer_ID = " + election.getId());
            }
            else {

                if( election.getOffice() != null )
                    condition.append( " where Office_Name = '" + election.getOffice() + "'" );


                if( election.getIsPartisan() || !election.getIsPartisan()){
                    if( condition.length() > 0 )
                        condition.append( " and" );
                    else
                        condition.append( " where" );
                    condition.append( " Is_Partisan = '" + election.getIsPartisan() + "'" );
                }


                if( election.getVoteCount() >= 0 ){
                    if( condition.length() > 0 )
                        condition.append( " and" );
                    else
                        condition.append( " where" );
                    condition.append( " Vote_Count = '" + election.getVoteCount() + "'" );

                }
            }
            query.append( condition );
        }

        try {

            stmt = conn.createStatement();

            // retrieve the persistent election objects
            //
            if( stmt.execute( query.toString() ) ) { // statement returned a result

                int electionId;
                String officeName;
                boolean isPartisan;
                int voteCount;
                Election   nextElection = null;

                ResultSet rs = stmt.getResultSet();

                // retrieve the retrieved elections
                while( rs.next() ) {

                    electionId = rs.getInt( 1 );
                    officeName = rs.getString( 2 );
                    isPartisan = rs.getBoolean( 3 );
                    voteCount = rs.getInt( 4 );

                    nextElection = objectLayer.createElection(); // create a proxy election object
                    // and now set its retrieved attributes
                    nextElection.setId( electionId );
                    nextElection.setOffice( officeName );
                    nextElection.setIsPartisan( isPartisan );
                    nextElection.setVoteCount( voteCount );

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

    public void store(Election election) throws EVException{
        String insertElection = "insert into Election ( Office_Name, Is_Partisan, Vote_Count) values ( ?, ?, ? )";
        String updateElection = "update Election set  Office_Name = ?, Is_Partisan = ?, Vote_Count = ?";
        PreparedStatement stmt = null;
        int queryExecution;
        int electionId;

        try {

            if( !election.isPersistent() )
                stmt = conn.prepareStatement( insertElection );
            else
                stmt = conn.prepareStatement( updateElection );

            //Cannot be null

            if( election.getOffice() != null )
                stmt.setString( 1, election.getOffice() );
            else
                throw new EVException( "ElectionManager.save: can't save a election: Office Name undefined" );

            if( election.getIsPartisan() || !election.getIsPartisan() )
                stmt.setBoolean( 2, election.getIsPartisan() );
            else
                throw new EVException( "ElectionManager.save: can't save a election: Is_Partisan undefined" );


            //The rest can be null

            if( election.getVoteCount() >= 0 )
                stmt.setInt( 3, election.getVoteCount() );
            else
                stmt.setNull( 3, java.sql.Types.INTEGER );


            queryExecution = stmt.executeUpdate();

            if( !election.isPersistent() ) {
                if( queryExecution >= 1 ) {
                    String sql = "select last_insert_id()";
                    if( stmt.execute( sql ) ) { // statement returned a result

                        // retrieve the result
                        ResultSet r = stmt.getResultSet();

                        // we will use only the first row!
                        //
                        while( r.next() ) {

                            // retrieve the last insert auto_increment value
                            electionId = r.getInt( 1 );
                            if( electionId > 0 )
                                election.setId( electionId ); // set this person's db id (proxy object)
                        }
                    }
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

