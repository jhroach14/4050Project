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
public class VoterManager {

    private ObjectLayer objectLayer = null;
    private Connection conn = null;

    public VoterManager(Connection conn, ObjectLayer objectLayer){
        this.conn = conn;
        this.objectLayer = objectLayer;
    }

    public VoterManager() {

    }

    public List<Voter> restore(Voter voter) throws EVException {

        String       selectVoter = "select Voter_ID, District_ID, First_Name, Last_Name, Username, User_Password, Email_Address, Address, City, State, Zip from Voter";
        Statement    stmt = null;
        StringBuffer query = new StringBuffer( 100 );
        StringBuffer condition = new StringBuffer( 100 );
        List<Voter>   voters = new ArrayList<Voter>();

        condition.setLength( 0 );

        // form the query based on the given voter object instance
        query.append( selectVoter );

        if( voter != null ) {
            if( voter.getId() >= 0 ) { // id is unique, so it is sufficient to get a person
                query.append(" where Voter_ID = " + voter.getId());
            }
            else {

                if( voter.getElectoralDistrict().getId() >= 0 )
                    condition.append( " where District_ID = '" + voter.getElectoralDistrict().getId() + "'" );

                if( voter.getFirstName() != null ) {
                    if( condition.length() > 0 )
                        condition.append( " and" );
                    else
                        condition.append( " where" );
                    condition.append( " First_Name = '" + voter.getFirstName() + "'" );
                }

                if( voter.getLastName() != null ) {
                    if( condition.length() > 0 )
                        condition.append( " and" );
                    else
                        condition.append( " where" );
                    condition.append( " Last_Name = '" + voter.getLastName() + "'" );
                }

                if( voter.getUserName() != null ){
                    if( condition.length() > 0 )
                        condition.append( " and" );
                    else
                        condition.append( " where" );
                    condition.append( " User_Name = '" + voter.getUserName() + "'" );
                }


                if( voter.getUserPassword() != null ){
                    if( condition.length() > 0 )
                        condition.append( " and" );
                    else
                        condition.append( " where" );
                    condition.append( " User_Password = '" + voter.getUserPassword() + "'" );

                }

                if( voter.getEmailAddress() != null ){
                    if( condition.length() > 0 )
                        condition.append( " and" );
                    else
                        condition.append( " where" );
                    condition.append( " Email_Address = '" + voter.getEmailAddress() + "'" );

                }

                if( voter.getAddress() != null ){
                    if( condition.length() > 0 )
                        condition.append( " and" );
                    else
                        condition.append( " where" );
                    condition.append( " Address = '" + voter.getAddress() + "'" );

                }

                if( voter.getCity() != null ){
                    if( condition.length() > 0 )
                        condition.append( " and" );
                    else
                        condition.append( " where" );
                    condition.append( " City = '" + voter.getCity() + "'" );

                }

                if( voter.getState() != null ){
                    if( condition.length() > 0 )
                        condition.append( " and" );
                    else
                        condition.append( " where" );
                    condition.append( " State = '" + voter.getState() + "'" );

                }

                if( Integer.toString(voter.getZip()) != null ){
                    if( condition.length() > 0 )
                        condition.append( " and" );
                    else
                        condition.append( " where" );
                    condition.append( " Zip = '" + voter.getZip() + "'" );

                }

            }
            query.append( condition );
        }

        try {

            stmt = conn.createStatement();

            // retrieve the persistent voter objects
            //
            if( stmt.execute( query.toString() ) ) { // statement returned a result

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

    public void store(Voter voter) throws EVException{
        String insertVoter = "insert into Voter ( District_ID, First_Name, Last_Name, Username, User_Password, Email_Address, Address, City, State, Zip) values ( ?, ?, ?, ?, ?, ?, ?, ?, ?, ? )";
        String updateVoter = "update Voter set District_ID = ?, First_Name = ?, Last_Name = ?, Username = ?, User_Password = ?, Email_Address = ?, Address = ?, City = ?, State = ?, Zip = ?";
        PreparedStatement stmt = null;
        int queryExecution;
        int voterId;

        try {

            if( !voter.isPersistent() )
                stmt = conn.prepareStatement( insertVoter );
            else
                stmt = conn.prepareStatement( updateVoter );


            //Can be null
            if( voter.getElectoralDistrict().getId() >= 0 )
                stmt.setInt( 1, voter.getElectoralDistrict().getId() );
            else
                stmt.setNull( 1, java.sql.Types.INTEGER );

            //Cannot be null

            if( voter.getFirstName() != null )
                stmt.setString( 2, voter.getFirstName() );
            else
                throw new EVException( "VoterManager.save: can't save a voter: First Name undefined" );

            if( voter.getLastName() != null )
                stmt.setString( 3, voter.getLastName() );
            else
                throw new EVException( "VoterManager.save: can't save a voter: Last Name undefined" );

            if( voter.getUserName() != null )
                stmt.setString( 4, voter.getUserName() );
            else
                throw new EVException( "VoterManager.save: can't save a voter: Username undefined" );

            if( voter.getUserPassword() != null )
                stmt.setString( 5, voter.getUserPassword() );
            else
                throw new EVException( "VoterManager.save: can't save a voter: Password undefined" );

            //The rest can be null

            if( voter.getEmailAddress() != null )
                stmt.setString( 6, voter.getEmailAddress() );
            else
                stmt.setNull( 6, java.sql.Types.VARCHAR );

            if( voter.getAddress() != null )
                stmt.setString( 7, voter.getAddress() );
            else
                stmt.setNull( 7, java.sql.Types.VARCHAR );

            if( voter.getCity() != null )
                stmt.setString( 8, voter.getCity() );
            else
                stmt.setNull( 8, java.sql.Types.VARCHAR );

            if( voter.getState() != null )
                stmt.setString( 9, voter.getState() );
            else
                stmt.setNull( 9, java.sql.Types.CHAR );

            //Voter.getZip() returns an int, the db schema specified zip as a char array of length 5
            //I just converted the .getZip() output to a string. Could cause problems later
            if( Integer.toString(voter.getZip()) != null )
                stmt.setString( 10, Integer.toString(voter.getZip()) );
            else
                stmt.setNull( 10, java.sql.Types.CHAR );

            queryExecution = stmt.executeUpdate();

            if( !voter.isPersistent() ) {
                if( queryExecution >= 1 ) {
                    String sql = "select last_insert_id()";
                    if( stmt.execute( sql ) ) { // statement returned a result

                        // retrieve the result
                        ResultSet r = stmt.getResultSet();

                        // we will use only the first row!
                        //
                        while( r.next() ) {

                            // retrieve the last insert auto_increment value
                            voterId = r.getInt( 1 );
                            if( voterId > 0 )
                                voter.setId( voterId ); // set this person's db id (proxy object)
                        }
                    }
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


    }

    public void store(Voter voter, ElectoralDistrict electoralDistrict) throws EVException {

        String insertVoter = "insert into Voter ( District_ID, First_Name, Last_Name, Username, User_Password, Email_Address, Address, City, State, Zip) values ( ?, ?, ?, ?, ?, ?, ?, ?, ?, ? )";
        String updateVoter = "update Voter set District_ID = ?, First_Name = ?, Last_Name = ?, Username = ?, User_Password = ?, Email_Address = ?, Address = ?, City = ?, State = ?, Zip = ?";
        PreparedStatement stmt = null;
        int queryExecution;
        int voterId;

        try {

            if( !voter.isPersistent() )
                stmt = conn.prepareStatement( insertVoter );
            else
                stmt = conn.prepareStatement( updateVoter );


            //Cannot be null
            if( electoralDistrict.getId() >= 0 )
                stmt.setInt( 1, electoralDistrict.getId() );
            else
                throw new EVException( "VoterManager.save: can't save a voter: First Name undefined" );

            if( voter.getFirstName() != null )
                stmt.setString( 2, voter.getFirstName() );
            else
                throw new EVException( "VoterManager.save: can't save a voter: First Name undefined" );

            if( voter.getLastName() != null )
                stmt.setString( 3, voter.getLastName() );
            else
                throw new EVException( "VoterManager.save: can't save a voter: Last Name undefined" );

            if( voter.getUserName() != null )
                stmt.setString( 4, voter.getUserName() );
            else
                throw new EVException( "VoterManager.save: can't save a voter: Username undefined" );

            if( voter.getUserPassword() != null )
                stmt.setString( 5, voter.getUserPassword() );
            else
                throw new EVException( "VoterManager.save: can't save a voter: Password undefined" );

            //The rest can be null

            if( voter.getEmailAddress() != null )
                stmt.setString( 6, voter.getEmailAddress() );
            else
                stmt.setNull( 6, java.sql.Types.VARCHAR );

            if( voter.getAddress() != null )
                stmt.setString( 7, voter.getAddress() );
            else
                stmt.setNull( 7, java.sql.Types.VARCHAR );

            if( voter.getCity() != null )
                stmt.setString( 8, voter.getCity() );
            else
                stmt.setNull( 8, java.sql.Types.VARCHAR );

            if( voter.getState() != null )
                stmt.setString( 9, voter.getState() );
            else
                stmt.setNull( 9, java.sql.Types.CHAR );

            //Voter.getZip() returns an int, the db schema specified zip as a char array of length 5
            //I just converted the .getZip() output to a string. Could cause problems later
            if( Integer.toString(voter.getZip()) != null )
                stmt.setString( 10, Integer.toString(voter.getZip()) );
            else
                stmt.setNull( 10, java.sql.Types.CHAR );

            queryExecution = stmt.executeUpdate();

            if( !voter.isPersistent() ) {
                if( queryExecution >= 1 ) {
                    String sql = "select last_insert_id()";
                    if( stmt.execute( sql ) ) { // statement returned a result

                        // retrieve the result
                        ResultSet r = stmt.getResultSet();

                        // we will use only the first row!
                        //
                        while( r.next() ) {

                            // retrieve the last insert auto_increment value
                            voterId = r.getInt( 1 );

                            if( voterId > 0 ){
                                voter.setId( voterId ); // set this person's db id (proxy object)
                                voter.setElectoralDistrict(electoralDistrict);
                            }
                        }
                    }
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


    }







    public void delete(Voter voter, ElectoralDistrict electoralDistrict) throws EVException {

        String               deleteVoter = "delete from Voter where Voter_ID = ? and District_ID = ?";
        PreparedStatement    stmt = null;
        int                  queryExecution;

        if( !voter.isPersistent() )
            return;

        try {
            stmt = conn.prepareStatement( deleteVoter );
            stmt.setInt( 1, voter.getId() );
            stmt.setInt(2, electoralDistrict.getId());
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

