package main.javaSrc.DBHelpers.Managers;

import main.javaSrc.DBHelpers.ObjectLayer;
import main.javaSrc.Entities.ElectoralDistrict;
import main.javaSrc.Entities.EntityImpl.ElectoralDistrictImpl;
import main.javaSrc.Entities.Voter;
import main.javaSrc.helpers.EVException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by User on 11/2/2016.
 */
public class VoterManager extends Manager{

    public VoterManager(Connection conn, ObjectLayer objectLayer){
        super(conn,objectLayer);
    }

    public VoterManager() {

    }

    public List<Voter> restore(Voter voter) throws EVException {

        Statement    stmt = null;
        String query = "";
        List<Voter>   voters = new ArrayList<Voter>();

        if( voter != null ) {
            query = voter.getRestoreString();
        }

        try {

            stmt = conn.createStatement();

            // retrieve the persistent voter objects
            //
            if( stmt.execute( query) ) { // statement returned a result

                int voterId;
                int districtId;
                String firstName;
                String lastName;
                String userName;
                String userPassword;
                String emailAddress;
                String address;
                String state;
                String city;
                String zip;
                Voter   nextVoter = null;

                ResultSet rs = stmt.getResultSet();

                // retrieve the retrieved voters
                while( rs.next() ) {

                    voterId = rs.getInt( 1 );
                    districtId = rs.getInt( 2 );
                    firstName = rs.getString( 3 );
                    lastName = rs.getString( 4 );
                    userName = rs.getString( 5 );
                    userPassword = rs.getString( 6 );
                    emailAddress = rs.getString( 7 );
                    address = rs.getString( 8 );
                    state = rs.getString( 9 );
                    city = rs.getString( 10 );
                    zip = rs.getString( 11 );

                    nextVoter = objectLayer.createVoter(); // create a proxy voter object
                    // and now set its retrieved attributes
                    nextVoter.setId( voterId );

                    //This part creates a new ElectoralDistrict as a model **ElectoralDistrict still needs to be implemented**
                    //then checks the objectLayer to see if it can find a district with that districtId
                    //if so, then assigns the voters district to be that found district
                    if(districtId >= 0) {
                        ElectoralDistrict d = new ElectoralDistrictImpl();
                        d.setId(districtId);
                        ElectoralDistrict districtToSet = objectLayer.findElectoralDistrict( d ).get(0);
                        if(districtToSet != null)
                            nextVoter.setElectoralDistrict(districtToSet);
                    }


                    nextVoter.setFirstName( firstName );
                    nextVoter.setLastName( lastName );
                    nextVoter.setUserName( userName );
                    nextVoter.setUserPassword( userPassword );
                    nextVoter.setEmailAddress( emailAddress );
                    nextVoter.setAddress( address );
                    nextVoter.setState( state );
                    nextVoter.setCity( city );
                    nextVoter.setZip( Integer.parseInt(zip) );

                    voters.add( nextVoter );
                }

                return voters;
            }
        }
        catch( Exception e ) {      // just in case...
            throw new EVException( "voterManager.restore: Could not restore persistent voter objects; Root cause: " + e );
        }

        throw new EVException( "voterManager.restore: Could not restore persistent voter objects" );

    }

    public Voter store(Voter voter) throws EVException{
        String insertVoter = "insert into Voter ( District_ID, First_Name, Last_Name, Username, User_Password, Email_Address, Address, City, State, Zip) values ( ?, ?, ?, ?, ?, ?, ?, ?, ?, ? )";
        String updateVoter = "update Voter set District_ID = ?, First_Name = ?, Last_Name = ?, Username = ?, User_Password = ?, Email_Address = ?, Address = ?, City = ?, State = ?, Zip = ?";
        PreparedStatement stmt = null;
        int queryExecution;
        int voterId;

        try {

            if( !voter.isPersistent() ) {
                stmt = conn.prepareStatement(insertVoter);
            }else {
                stmt = conn.prepareStatement(updateVoter);
            }

            stmt=voter.insertStoreData(stmt);

            queryExecution = stmt.executeUpdate();

            if( !voter.isPersistent() ) {
                if( queryExecution >= 1 ) {
                   voter = (Voter) setId(stmt,voter);
                }
                else
                    throw new EVException( "VoterManager.save: failed to save a voter" );
            }
            else {
                if( queryExecution < 1 )
                    throw new EVException( "VoterManager.save: failed to save a voter" );
            }
        }
        catch( SQLException e ) {
            e.printStackTrace();
            throw new EVException( "VoterManager.save: failed to save a voter: " + e );
        }

        return voter;
    }








    public void delete(Voter voter) throws EVException {

        String               deleteVoter = "delete from Voter where Voter_ID = ?";
        PreparedStatement    stmt = null;
        int                  queryExecution;

        if( !voter.isPersistent() ) // is the voter object persistent?  If not, nothing to actually delete
            return;

        try {
            stmt = conn.prepareStatement( deleteVoter );
            stmt.setInt( 1, voter.getId() );
            queryExecution = stmt.executeUpdate();
            if( queryExecution == 1 ) {
                return;
            }
            else
                throw new EVException( "voterManager.delete: failed to delete a voter" );
        }
        catch( SQLException e ) {
            e.printStackTrace();
            throw new EVException( "voterManager.delete: failed to delete a voter: " + e );        }
    }

}

