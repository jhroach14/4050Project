package main.javaSrc.DBHelpers.Managers;

import main.javaSrc.DBHelpers.ObjectLayer;
import main.javaSrc.Entities.ElectionsOfficer;
import main.javaSrc.Entities.Entity;
import main.javaSrc.Entities.EntityImpl.EntityImpl;
import main.javaSrc.helpers.EVException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by User on 11/2/2016.
 */
public class ElectionsOfficerManager {

    private ObjectLayer objectLayer = null;
    private Connection conn = null;

    public ElectionsOfficerManager(Connection conn, ObjectLayer objectLayer){
        this.conn = conn;
        this.objectLayer = objectLayer;
    }

    public ElectionsOfficerManager() {

    }

    public List<ElectionsOfficer> restore(ElectionsOfficer electionsOfficer) throws EVException {

        String       selectElectionsOfficer = "select Elections_Officer_ID, First_Name, Last_Name, Username, User_Password, Email_Address, Address, City, State, Zip from ElectionsOfficer";
        Statement    stmt = null;
        StringBuffer query = new StringBuffer( 100 );
        StringBuffer condition = new StringBuffer( 100 );
        List<ElectionsOfficer>   electionsOfficers = new ArrayList<ElectionsOfficer>();

        condition.setLength( 0 );

        // form the query based on the given electionsOfficer object instance
        query.append( selectElectionsOfficer );

        if( electionsOfficer != null ) {
            if( electionsOfficer.getId() >= 0 ) { // id is unique, so it is sufficient to get a person
                query.append(" where Elections_Officer_ID = " + electionsOfficer.getId());
            }
            else {

                if( electionsOfficer.getFirstName() != null )
                    condition.append( " where First_Name = '" + electionsOfficer.getFirstName() + "'" );

                if( electionsOfficer.getLastName() != null ) {
                    if( condition.length() > 0 )
                        condition.append( " and" );
                    else
                        condition.append( " where" );
                    condition.append( " Last_Name = '" + electionsOfficer.getLastName() + "'" );
                }

                if( electionsOfficer.getUserName() != null ){
                    if( condition.length() > 0 )
                        condition.append( " and" );
                    else
                        condition.append( " where" );
                    condition.append( " User_Name = '" + electionsOfficer.getUserName() + "'" );
                }


                if( electionsOfficer.getUserPassword() != null ){
                    if( condition.length() > 0 )
                        condition.append( " and" );
                    else
                        condition.append( " where" );
                    condition.append( " User_Password = '" + electionsOfficer.getUserPassword() + "'" );

                }

                if( electionsOfficer.getEmailAddress() != null ){
                    if( condition.length() > 0 )
                        condition.append( " and" );
                    else
                        condition.append( " where" );
                    condition.append( " Email_Address = '" + electionsOfficer.getEmailAddress() + "'" );

                }

                if( electionsOfficer.getAddress() != null ){
                    if( condition.length() > 0 )
                        condition.append( " and" );
                    else
                        condition.append( " where" );
                    condition.append( " Address = '" + electionsOfficer.getAddress() + "'" );

                }

                if( electionsOfficer.getCity() != null ){
                    if( condition.length() > 0 )
                        condition.append( " and" );
                    else
                        condition.append( " where" );
                    condition.append( " City = '" + electionsOfficer.getCity() + "'" );

                }

                if( electionsOfficer.getState() != null ){
                    if( condition.length() > 0 )
                        condition.append( " and" );
                    else
                        condition.append( " where" );
                    condition.append( " State = '" + electionsOfficer.getState() + "'" );

                }

                if( Integer.toString(electionsOfficer.getZip()) != null ){
                    if( condition.length() > 0 )
                        condition.append( " and" );
                    else
                        condition.append( " where" );
                    condition.append( " Zip = '" + electionsOfficer.getZip() + "'" );

                }

                query.append( condition );
            }
        }

        try {

            stmt = conn.createStatement();

            // retrieve the persistent electionsOfficer objects
            //
            if( stmt.execute( query.toString() ) ) { // statement returned a result

                int electionsOfficerId;
                String firstName;
                String lastName;
                String userName;
                String userPassword;
                String emailAddress;
                String address;
                String state;
                String city;
                String zip;
                ElectionsOfficer   nextElectionsOfficer = null;

                ResultSet rs = stmt.getResultSet();

                // retrieve the retrieved electionsOfficers
                while( rs.next() ) {

                    electionsOfficerId = rs.getInt( 1 );
                    firstName = rs.getString( 2 );
                    lastName = rs.getString( 3 );
                    userName = rs.getString( 4 );
                    userPassword = rs.getString( 5 );
                    emailAddress = rs.getString( 6 );
                    address = rs.getString( 7 );
                    state = rs.getString( 8 );
                    city = rs.getString( 9 );
                    zip = rs.getString( 10 );

                    nextElectionsOfficer = objectLayer.createElectionsOfficer(); // create a proxy electionsOfficer object
                    // and now set its retrieved attributes
                    nextElectionsOfficer.setId( electionsOfficerId );
                    nextElectionsOfficer.setFirstName( firstName );
                    nextElectionsOfficer.setLastName( lastName );
                    nextElectionsOfficer.setUserName( userName );
                    nextElectionsOfficer.setUserPassword( userPassword );
                    nextElectionsOfficer.setEmailAddress( emailAddress );
                    nextElectionsOfficer.setAddress( address );
                    nextElectionsOfficer.setState( state );
                    nextElectionsOfficer.setCity( city );
                    nextElectionsOfficer.setZip( Integer.parseInt(zip) );

                    electionsOfficers.add( nextElectionsOfficer );
                }

                return electionsOfficers;
            }
        }
        catch( Exception e ) {      // just in case...
            throw new EVException( "electionsOfficerManager.restore: Could not restore persistent electionsOfficer objects; Root cause: " + e );
        }

        throw new EVException( "electionsOfficerManager.restore: Could not restore persistent electionsOfficer objects" );

    }

    public void store(ElectionsOfficer electionsOfficer) throws EVException{
        String insertElectionsOfficer = "insert into ElectionsOfficer ( First_Name, Last_Name, Username, User_Password, Email_Address, Address, City, State, Zip) values ( ?, ?, ?, ?, ?, ?, ?, ?, ? )";
        String updateElectionsOfficer = "update ElectionsOfficer set First_Name = ?, Last_Name = ?, Username = ?, User_Password = ?, Email_Address = ?, Address = ?, City = ?, State = ?, Zip = ?";
        PreparedStatement stmt = null;
        int queryExecution;
        int electionsOfficerId;

        try {

            if( !electionsOfficer.isPersistent() )
                stmt = conn.prepareStatement( insertElectionsOfficer );
            else
                stmt = conn.prepareStatement( updateElectionsOfficer );

            //Cannot be null

            if( electionsOfficer.getFirstName() != null )
                stmt.setString( 1, electionsOfficer.getFirstName() );
            else
                throw new EVException( "ElectionsOfficerManager.save: can't save a electionsOfficer: First Name undefined" );

            if( electionsOfficer.getLastName() != null )
                stmt.setString( 2, electionsOfficer.getLastName() );
            else
                throw new EVException( "ElectionsOfficerManager.save: can't save a electionsOfficer: Last Name undefined" );

            if( electionsOfficer.getUserName() != null )
                stmt.setString( 3, electionsOfficer.getUserName() );
            else
                throw new EVException( "ElectionsOfficerManager.save: can't save a electionsOfficer: Username undefined" );

            if( electionsOfficer.getUserPassword() != null )
                stmt.setString( 4, electionsOfficer.getUserPassword() );
            else
                throw new EVException( "ElectionsOfficerManager.save: can't save a electionsOfficer: Password undefined" );

            //The rest can be null

            if( electionsOfficer.getEmailAddress() != null )
                stmt.setString( 5, electionsOfficer.getEmailAddress() );
            else
                stmt.setNull( 5, java.sql.Types.VARCHAR );

            if( electionsOfficer.getAddress() != null )
                stmt.setString( 6, electionsOfficer.getAddress() );
            else
                stmt.setNull( 6, java.sql.Types.VARCHAR );

            if( electionsOfficer.getCity() != null )
                stmt.setString( 7, electionsOfficer.getCity() );
            else
                stmt.setNull( 7, java.sql.Types.VARCHAR );

            if( electionsOfficer.getState() != null )
                stmt.setString( 8, electionsOfficer.getState() );
            else
                stmt.setNull( 8, java.sql.Types.CHAR );

            //ElectionsOfficer.getZip() returns an int, the db schema specified zip as a char array of length 5
            //I just converted the .getZip() output to a string. Could cause problems later
            if( Integer.toString(electionsOfficer.getZip()) != null )
                stmt.setString( 9, Integer.toString(electionsOfficer.getZip()) );
            else
                stmt.setNull( 9, java.sql.Types.CHAR );

            queryExecution = stmt.executeUpdate();

            if( !electionsOfficer.isPersistent() ) {
                if( queryExecution >= 1 ) {
                    String sql = "select last_insert_id()";
                    if( stmt.execute( sql ) ) { // statement returned a result

                        // retrieve the result
                        ResultSet r = stmt.getResultSet();

                        // we will use only the first row!
                        //
                        while( r.next() ) {

                            // retrieve the last insert auto_increment value
                            electionsOfficerId = r.getInt( 1 );
                            if( electionsOfficerId > 0 )
                                electionsOfficer.setId( electionsOfficerId ); // set this person's db id (proxy object)
                        }
                    }
                }
                else
                    throw new EVException( "ElectionsOfficerManager.save: failed to save a electionsOfficer" );
            }
            else {
                if( queryExecution < 1 )
                    throw new EVException( "ElectionsOfficerManager.save: failed to save a electionsOfficer" );
            }
        }
        catch( SQLException e ) {
            e.printStackTrace();
            throw new EVException( "ElectionsOfficerManager.save: failed to save a electionsOfficer: " + e );
        }


    }

    public void delete(ElectionsOfficer electionsOfficer) throws EVException {

        String               deleteElectionsOfficer = "delete from ElectionsOfficer where Elections_Officer_ID = ?";
        PreparedStatement    stmt = null;
        int                  queryExecution;

        if( !electionsOfficer.isPersistent() ) // is the electionsOfficer object persistent?  If not, nothing to actually delete
            return;

        try {
            stmt = conn.prepareStatement( deleteElectionsOfficer );
            stmt.setInt( 1, electionsOfficer.getId() );
            queryExecution = stmt.executeUpdate();
            if( queryExecution == 1 ) {
                return;
            }
            else
                throw new EVException( "electionsOfficerManager.delete: failed to delete a electionsOfficer" );
        }
        catch( SQLException e ) {
            e.printStackTrace();
            throw new EVException( "electionsOfficerManager.delete: failed to delete a electionsOfficer: " + e );        }
    }

}

