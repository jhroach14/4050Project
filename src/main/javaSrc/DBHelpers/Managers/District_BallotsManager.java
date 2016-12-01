package main.javaSrc.DBHelpers.Managers;

import main.javaSrc.DBHelpers.ObjectLayer;
import main.javaSrc.Entities.Ballot;
import main.javaSrc.Entities.BallotItem;
import main.javaSrc.Entities.ElectoralDistrict;
import main.javaSrc.Entities.EntityImpl.BallotItemImpl;
import main.javaSrc.Entities.Voter;
import main.javaSrc.helpers.EVException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by User on 11/10/2016.
 */
public class District_BallotsManager {

    private ObjectLayer objectLayer;
    private Connection conn;

    public District_BallotsManager(ObjectLayer objectLayer, Connection connection) {
        this.objectLayer=objectLayer;
        this.conn=connection;
    }

    public void store(ElectoralDistrict electoralDistrict, Ballot ballot) {
        String insertBallot_electoralDistrict = "insert into District_Ballots (District_ID, Ballot_ID) values (?, ?)";
        PreparedStatement stmt = null;
        int queryExecution;

        try {
            stmt = conn.prepareStatement( insertBallot_electoralDistrict);

            if(electoralDistrict.getId() > 0)
                stmt.setInt(1, electoralDistrict.getId());
            else
                throw new EVException("Voter_electoralManager.save can't save a voter: ") ;

            if(ballot.getId() > 0)
                stmt.setInt(2, ballot.getId());
            else
                throw new EVException("Voter_electoralManger.save can't save a voter: ") ;

            queryExecution = stmt.executeUpdate();

            if(queryExecution < 1)
                throw new EVException("Voter_electoralManager.save failed to save voter");

        }
        catch(Exception e){
            e.printStackTrace();
            //throw new EVException("Voter_electoralManager.store failed to save a voter" +e);
        }
    }

    public ElectoralDistrict restore(Ballot ballot) throws EVException {
        StringBuffer query = new StringBuffer(500);
        Statement stmt = null;

        if(ballot.getId() <1)
            throw new EVException("voter_district.restore could not restore non persistent voter");

        query.append("select District.District_ID, District.DistrictName");
        query.append(" from District ");
        query.append("join District_Ballots");
        query.append("on District.District_ID = District_Ballots.District_ID");
        query.append("Where District_Ballots.Ballot_ID = '" + ballot.getId() + "'");

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

    public void delete(ElectoralDistrict electoralDistrict, Ballot ballot) throws EVException {

        String               deleteElection = "delete from District_Ballots where District_ID = ? and Ballot_ID = ?";
        PreparedStatement    stmt = null;
        int                  queryExecution;

        try{
            stmt = conn.prepareStatement( deleteElection );
            if(ballot.getId() >0)
                stmt.setInt(2, ballot.getId());
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

    public List<Ballot> restore(ElectoralDistrict electoralDistrict) throws EVException{
        StringBuffer query = new StringBuffer(500);
        Statement stmt = null;
        List<Voter> voters = new ArrayList<Voter>();
        List<Ballot>   ballots = new ArrayList<Ballot>();

        if (electoralDistrict.getId() < 1)
            throw new EVException("CandidateManger.restore could not restore persistent Candidate_Elections");

        query.append( "select Ballot.Ballot_ID, Ballot.Start_Date, Ballot.End_Date " );
        query.append(" from Ballot ");
        query.append("join District_Ballots");
        query.append(" on Ballot.Ballot_ID = District_Ballots.Ballot_ID");
        query.append(" Where District_Ballots.District_ID = '" + electoralDistrict.getId() + "'");

        try {

            stmt = conn.createStatement();

            // retrieve the persistent ballot objects
            //
            if( stmt.execute( query.toString() ) ) { // statement returned a result

                int ballotId;
                Date startDate;
                Date closeDate;
                Ballot nextBallot = null;

                ResultSet rs = stmt.getResultSet();

                // retrieve the retrieved ballots
                while( rs.next() ) {

                    ballotId = rs.getInt( 1 );
                    startDate = rs.getDate( 2 );
                    closeDate = rs.getDate( 3 );

                    nextBallot = objectLayer.createBallot(); // create a proxy ballot object
                    // and now set its retrieved attributes
                    nextBallot.setId( ballotId );
                    nextBallot.setOpenDate( startDate );
                    nextBallot.setCloseDate( closeDate );
                    nextBallot.setPersistent(true);

                    ballots.add( nextBallot );
                }

                return ballots;
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new EVException("BallotManager.store failed to save a ballot_issue" + e);
        }
        throw new EVException("BallotManager.restore could not restore ballot object");
    }
}
