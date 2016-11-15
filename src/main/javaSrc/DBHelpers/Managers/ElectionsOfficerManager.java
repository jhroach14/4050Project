package main.javaSrc.DBHelpers.Managers;

import main.javaSrc.DBHelpers.ObjectLayer;
import main.javaSrc.Entities.ElectionsOfficer;
import main.javaSrc.helpers.EVException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by User on 11/2/2016.
 */
public class ElectionsOfficerManager extends Manager{


    public ElectionsOfficerManager(Connection conn, ObjectLayer objectLayer){
        super(conn, objectLayer);
    }

    public ElectionsOfficerManager() {

    }

    public List<ElectionsOfficer> restore(ElectionsOfficer electionsOfficer) throws EVException {

        Statement    stmt = null;
        String query = "";
        List<ElectionsOfficer>   electionsOfficers = new ArrayList<ElectionsOfficer>();

        if( electionsOfficer != null ) {
            query = electionsOfficer.getRestoreString();
        }

        try {

            stmt = conn.createStatement();

            // retrieve the persistent electionsOfficer objects
            //
            if( stmt.execute( query) ) { // statement returned a result

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

    public ElectionsOfficer store(ElectionsOfficer electionsOfficer) throws EVException{
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

            stmt = electionsOfficer.insertStoreData(stmt);

            queryExecution = stmt.executeUpdate();

            if( !electionsOfficer.isPersistent() ) {
                if( queryExecution >= 1 ) {
                   electionsOfficer = (ElectionsOfficer)setId(stmt,electionsOfficer);
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

        return electionsOfficer;
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

