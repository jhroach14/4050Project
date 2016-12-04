package main.javaSrc.DBHelpers.Managers;

import main.javaSrc.DBHelpers.ObjectLayer;
import main.javaSrc.Entities.Candidate;
import main.javaSrc.Entities.Election;
import main.javaSrc.Entities.ElectoralDistrict;
import main.javaSrc.Entities.EntityImpl.CandidateImpl;
import main.javaSrc.Entities.EntityImpl.ElectoralDistrictImpl;
import main.javaSrc.Entities.Voter;
import main.javaSrc.helpers.EVException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by User on 11/10/2016.
 */
public class District_VoterManager {

    private ObjectLayer objectLayer;
    private Connection conn;
    
    public District_VoterManager(ObjectLayer objectLayer, Connection connection) {
        this.objectLayer=objectLayer;
        this.conn=connection;
    }

    public void store(Voter voter, ElectoralDistrict electoralDistrict) {


        String insertVoter_electoralDistrict = "insert into District_Voters (District_ID, Voter_ID) values (?, ?)";

        PreparedStatement stmt = null;
        int queryExecution;

        try {
            stmt = conn.prepareStatement( insertVoter_electoralDistrict );

            if(voter.getId() > 0)
                stmt.setInt(2, voter.getId());
            else
                throw new EVException("Voter_electoralManager.save can't save a voter: ") ;

            if(electoralDistrict.getId() > 0)
                stmt.setInt(1, electoralDistrict.getId());
            else
                throw new EVException("Voter_electoralManger.save can't save a voter: ") ;

            queryExecution = stmt.executeUpdate();

            if(queryExecution < 1)
                throw new EVException("Voter_electoralManager.save failed to save voter");

            String updateVoter = "update Voter set District_ID = ?;";

            stmt.setInt(1,electoralDistrict.getId());

        }
        catch(Exception e){
            e.printStackTrace();
            //throw new EVException("Voter_electoralManager.store failed to save a voter" +e);
        }


    }

    public ElectoralDistrict restore(Voter voter) throws EVException {
        StringBuffer query = new StringBuffer(500);
        Statement stmt = null;

        if(voter.getId() <1)
            throw new EVException("voter_district.restore could not restore non persistent voter");

        query.append("select District.District_ID, District.District_Name");
        query.append(" from District ");
        query.append("join District_Voters ");
        query.append("on District.District_ID = District_Voters.District_ID ");
        query.append(" Where District_Voters.Voter_ID = '" + voter.getId() + "'");

        try{
            stmt = conn.createStatement();

            if (stmt.execute(query.toString())) {
                int districtid;
                String districtName="";
                ElectoralDistrict electoralDistrict = null;

                ResultSet rs = stmt.getResultSet();

                while (rs.next()) {
                    districtid = rs.getInt(1);
                    districtName = rs.getString(2);

                    electoralDistrict = objectLayer.createElectoralDistrict();
                    electoralDistrict.setId(districtid);
                    electoralDistrict.setName(districtName);
                    electoralDistrict.setPersistent(true);
                    break;
                }
                return electoralDistrict;
            }
        } catch(Exception e){
            e.printStackTrace();
            throw new EVException("Election_Candidates.store failed to save a candidate_Issue" +e);
        }
        throw new EVException("Election_Candidates.restore could not restore candidate object");

    }

    public List<Voter> restore(ElectoralDistrict electoralDistrict) throws EVException {

        StringBuffer query = new StringBuffer(500);
        Statement stmt = null;
        List<Voter> voters = new ArrayList<Voter>();

        if (electoralDistrict.getId() < 1)
            throw new EVException("CandidateManger.restore could not restore persistent Candidate_Elections");

        query.append("select Voter.Voter_ID, Voter.First_Name, Voter.Last_Name, Voter.Username, Voter.User_Password, Voter.Email_Address, Voter.Address, Voter.City, Voter.State, Voter.Zip");
        query.append(" from Voter ");
        query.append("join District_Voters ");
        query.append("on Voter.Voter_ID = District_Voters.Voter_ID ");
        query.append("Where District_Voters.District_ID = '" + electoralDistrict.getId() + "'");

        try {

            stmt = conn.createStatement();

            // retrieve the persistent voter objects
            //
            if( stmt.execute( query.toString() ) ) { // statement returned a result

                int voterId;
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
                    firstName = rs.getString( 2 );
                    lastName = rs.getString( 3);
                    userName = rs.getString( 4 );
                    userPassword = rs.getString( 5 );
                    emailAddress = rs.getString( 6 );
                    address = rs.getString( 7 );
                    state = rs.getString( 8 );
                    city = rs.getString( 9 );
                    zip = rs.getString( 10 );

                    nextVoter = objectLayer.createVoter(); // create a proxy voter object
                    // and now set its retrieved attributes
                    nextVoter.setId( voterId );
                    nextVoter.setFirstName( firstName );
                    nextVoter.setLastName( lastName );
                    nextVoter.setUserName( userName );
                    nextVoter.setUserPassword( userPassword );
                    nextVoter.setEmailAddress( emailAddress );
                    nextVoter.setAddress( address );
                    nextVoter.setState( state );
                    nextVoter.setCity( city );
                    nextVoter.setZip( Integer.parseInt(zip) );
                    nextVoter.setPersistent(true);

                    voters.add( nextVoter );
                }

                return voters;
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new EVException("BallotManager.store failed to save a ballot_issue" + e);
        }
        throw new EVException("BallotManager.restore could not restore ballot object");
    }

    public void delete(Voter voter, ElectoralDistrict electoralDistrict) throws EVException {
        String               deleteElection = "delete from District_Voters where District_ID = ? and Voter_ID = ?";
        PreparedStatement    stmt = null;
        int                  queryExecution;

        try{
            stmt = conn.prepareStatement( deleteElection );
            if(voter.getId() >0)
                stmt.setInt(2, voter.getId());
            else
                throw new EVException("Election_Candidates.delete failed to delete candidate_Issues");
            if(electoralDistrict.getId() > 0)
                stmt.setInt(1, electoralDistrict.getId());
            else
                throw new EVException("Election_CandidatesManager.delete failed to delete candidate_Issues");
            queryExecution = stmt.executeUpdate();
            if(queryExecution != 1)
                throw new EVException("Election_CandidatesManager.delete failed to delete");
        }
        catch( SQLException e ) {
            e.printStackTrace();
            throw new EVException( "Election_CandidatesManager.delete: failed to delete a candidate: " + e );
        }
        
    }

    public void deleteVoters(ElectoralDistrict electoralDistrict) throws EVException{

        String               deleteElection = "delete from District_Voters where District_ID = ?";
        PreparedStatement    stmt = null;
        int                  queryExecution;

        try{
            stmt = conn.prepareStatement( deleteElection );
            if(electoralDistrict.getId() > 0)
                stmt.setInt(1, electoralDistrict.getId());
            else
                throw new EVException("Election_CandidatesManager.delete failed to delete candidate_Issues");
            queryExecution = stmt.executeUpdate();
            if(queryExecution != 1)
                throw new EVException("Election_CandidatesManager.delete failed to delete");
        }
        catch( SQLException e ) {
            e.printStackTrace();
            throw new EVException( "Election_CandidatesManager.delete: failed to delete a candidate: " + e );
        }

    }
}
