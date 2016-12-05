package main.javaSrc.DBHelpers.Managers;

import main.javaSrc.DBHelpers.ObjectLayer;
import main.javaSrc.Entities.Candidate;
import main.javaSrc.Entities.Election;
import main.javaSrc.Entities.EntityImpl.CandidateImpl;
import main.javaSrc.Entities.PoliticalParty;
import main.javaSrc.helpers.EVException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by User on 11/10/2016.
 */
public class Party_CandidatesManager {

    private ObjectLayer objectLayer;
    private Connection conn;

    public Party_CandidatesManager(ObjectLayer objectLayer, Connection connection) {
        this.objectLayer=objectLayer;
        this.conn=connection;
    }

    public void store(Candidate candidate, PoliticalParty politicalParty) {
        String insertParty_Candidates = "insert into Party_Candidates (Party_ID, Candidate_ID) values (?, ?)";
        PreparedStatement stmt = null;
        int queryExecution;

        try {
            stmt = conn.prepareStatement( insertParty_Candidates );

            if(candidate.getId() > 0)
                stmt.setInt(2, candidate.getId());
            else
                throw new EVException("Election_CandidatesManager.save can't save a candidate_election: candidate ID undefined") ;

            if(politicalParty.getId() > 0)
                stmt.setInt(1, politicalParty.getId());
            else
                throw new EVException("Election_CandidatesManger.save can't save a candidate_election: election ID undefined") ;

            queryExecution = stmt.executeUpdate();

            if(queryExecution < 1)
                throw new EVException("Election_CandidatesManager.save failed to save candidate_Election");

        }
        catch(Exception e){
            e.printStackTrace();
            //throw new EVException("Election_CandidatessManager.store failed to save a candidate_Election" +e);
        }
    }

    public PoliticalParty restore(Candidate candidate) throws EVException {

        StringBuffer query = new StringBuffer(500);
        Statement stmt = null;

        if(candidate.getId() <1)
            throw new EVException("Party_Candidates.restore could not restore non persistent candidate");

        query.append("select Party_ID, Party_Name from (select Party.Party_ID, Party.Party_Name, Party_Candidates.Candidate_ID from Party inner join Party_Candidates on Party.Party_ID = Party_Candidates.Party_ID) as T where Candidate_ID = " + candidate.getId());

        try{
            stmt = conn.createStatement();

            if (stmt.execute(query.toString())) {
                int politicalPartyId;
                String name;
                PoliticalParty politicalParty = null;

                ResultSet rs = stmt.getResultSet();

                while (rs.next()) {
                    politicalPartyId = rs.getInt( 1 );
                    name = rs.getString( 2 );

                    politicalParty = objectLayer.createPoliticalParty(); // create a proxy politicalParty object
                    // and now set its retrieved attributes
                    politicalParty.setId( politicalPartyId );
                    politicalParty.setName( name );
                    politicalParty.setPersistent(true);
                    break;
                }
                return politicalParty;
            }
        }

        catch(SQLException e){
            e.printStackTrace();
            throw new EVException("Election_Candidates.store failed to save a candidate_Issue" +e);
        }
        throw new EVException("Election_Candidates.restore could not restore candidate object");
    }

    public List<Candidate> restore(PoliticalParty politicalParty) throws EVException {

        StringBuffer query = new StringBuffer(500);
        Statement stmt = null;
        List<Candidate> candidates = new ArrayList<Candidate>();

        if (politicalParty.getId() < 1)
            throw new EVException("CandidateManger.restore could not restore persistent Candidate_Elections");

        query.append("select Candidate_ID, Candidate_Name, Vote_Count from (select Candidate.Candidate_ID, Candidate.Candidate_Name, Candidate.Vote_Count, Party_Candidates.Party_ID from Candidate inner join Party_Candidates on Candidate.Candidate_ID = Party_Candidates.Candidate_ID) as T where Party_ID = " + politicalParty.getId());

        try {
            stmt = conn.createStatement();

            if (stmt.execute(query.toString())) { // statement returned a result

                int candidateId;
                String candidateName;
                int voteCount;


                Candidate newCandidate = null;

                ResultSet rs = stmt.getResultSet();


                while (rs.next()) {

                    candidateId = rs.getInt(1);
                    candidateName = rs.getString(2);
                    voteCount = rs.getInt(3);

                    newCandidate = new CandidateImpl();
                    newCandidate.setId(candidateId);
                    newCandidate.setName(candidateName);
                    newCandidate.setVoteCount(voteCount);
                    newCandidate.setPersistent(true);

                    candidates.add(newCandidate);
                }

                return candidates;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new EVException("BallotManager.store failed to save a ballot_issue" + e);
        }
        throw new EVException("BallotManager.restore could not restore ballot object");

    }

    public void delete(Candidate candidate, PoliticalParty politicalParty) throws EVException {

        String               deleteElection = "delete from Party_Candidates where Candidate_ID = ? and Party_ID = ?";
        PreparedStatement    stmt = null;
        int                  queryExecution;

        try{
            stmt = conn.prepareStatement( deleteElection );
            if(candidate.getId() >0)
                stmt.setInt(1, candidate.getId());
            else
                throw new EVException("Election_Candidates.delete failed to delete candidate_Issues");
            if(politicalParty.getId() > 0)
                stmt.setInt(2, politicalParty.getId());
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

    public void deleteParties(Candidate candidate) throws EVException{
        String               deleteElection = "delete from Party_Candidates where Candidate_ID = ? ";
        PreparedStatement    stmt = null;
        int                  queryExecution;

        try{
            stmt = conn.prepareStatement( deleteElection );
            if(candidate.getId() >0)
                stmt.setInt(1, candidate.getId());
            else
                throw new EVException("Election_Candidates.delete failed to delete candidate_Issues");
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
