package main.javaSrc.DBHelpers.Managers;

import main.javaSrc.DBHelpers.ObjectLayer;
import main.javaSrc.Entities.Candidate;
import main.javaSrc.Entities.Election;
import main.javaSrc.Entities.EntityImpl.CandidateImpl;
import main.javaSrc.helpers.EVException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by User on 11/9/2016.
 */
public class Election_CandidatesManager {
    private ObjectLayer objectLayer;
    private Connection conn;
    
    public Election_CandidatesManager(ObjectLayer objectLayer, Connection conn){
        this.objectLayer = objectLayer;
        this.conn = conn;
    }
    
    public void store(Candidate candidate, Election election) {
        
        String insertElection_Candidates = "insert into Election_Candidates (Candidate_ID, Election_ID) values (?, ?)";
        PreparedStatement stmt = null;
        int queryExecution;

        try {
            stmt = conn.prepareStatement( insertElection_Candidates );

            if(candidate.getId() > 0)
                stmt.setInt(1, candidate.getId());
            else
                throw new EVException("Election_CandidatesManager.save can't save a candidate_election: candidate ID undefined") ;

            if(election.getId() > 0)
                stmt.setInt(2, election.getId());
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

    public Election restore (Candidate candidate) throws EVException{
        StringBuffer query = new StringBuffer(500);
        Statement stmt = null;

        if(candidate.getId() <1)
            throw new EVException("Election_Candidates.restore could not restore non persistent candidate");

        query.append("select Election.Election_ID, Election.Office_Name, Election.Is_Partisan, Election.Vote_Count");
        query.append(" from Election");
        query.append(" join Election_Candidates");
        query.append(" on Election.Election_ID = Election_Candidates.Election_ID");
        query.append(" where Election_Candidates.Candidate_ID = " + candidate.getId());

        try{
            stmt = conn.createStatement();

            if (stmt.execute(query.toString())) {
                int electionID;
                String officeName;
                boolean isPartisan;
                int voteCount;
                Election newElection = null;

                ResultSet rs = stmt.getResultSet();

                while (rs.next()) {
                    electionID = rs.getInt(1);
                    officeName = rs.getString(2);
                    isPartisan = rs.getBoolean(3);
                    voteCount = rs.getInt(4);

                    newElection = objectLayer.createElection();
                    newElection.setId(electionID);
                    newElection.setOffice(officeName);
                    newElection.setIsPartisan(isPartisan);
                    newElection.setVoteCount(voteCount);
                    newElection.setPersistent(true);
                    break;
                }
                return newElection;
            }
        }

        catch(SQLException e){
            e.printStackTrace();
            throw new EVException("Election_Candidates.store failed to save a candidate_Issue" +e);
        }
        throw new EVException("Election_Candidates.restore could not restore candidate object");
    }

    public List<Candidate> restore(Election election) throws EVException{
        StringBuffer query = new StringBuffer(500);
        Statement stmt = null;
        List<Candidate> candidates = new ArrayList<Candidate>();

        if (election.getId() < 1)
            throw new EVException("CandidateManger.restore could not restore persistent Candidate_Elections");

        /*query.append("select Candidate.Candidate_Name, Candidate.Vote_Count");
        query.append(" from Candidate");
        query.append(" join Election_Candidates");
        query.append(" on Election.Election_ID = Election_Candidates.Election_ID");
        query.append(" where Election.Election_ID = " + election.getId());
*/
        query.append("select Candidate_ID, Candidate_Name, Vote_Count from (select Candidate.Candidate_ID, Candidate.Candidate_Name, Candidate.Vote_Count, Election_Candidates.Election_ID from Candidate inner join Election_Candidates on Candidate.Candidate_ID = Election_Candidates.Candidate_ID) as T where Election_ID = " + election.getId());


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
                /*String candidateName;
                int voteCount;


                Candidate newCandidate = null;

                ResultSet rs = stmt.getResultSet();


                while (rs.next()) {

                    candidateName = rs.getString(1);
                    voteCount = rs.getInt(2);

                    newCandidate = new CandidateImpl();
                    newCandidate.setName(candidateName);
                    newCandidate.setVoteCount(voteCount);
                    newCandidate.setPersistent(true);

                    candidates.add(newCandidate);
                }
                */

                return candidates;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new EVException("BallotManager.store failed to save a ballot_issue" + e);
        }
        throw new EVException("BallotManager.restore could not restore ballot object");
    }


    public void delete(Election election, Candidate candidate) throws EVException{
        String               deleteElection = "delete from Election_Candidates where Candidate_ID = ? and Election_ID = ?";
        PreparedStatement    stmt = null;
        int                  queryExecution;

        try{
            stmt = conn.prepareStatement( deleteElection );
            if(candidate.getId() >0)
                stmt.setInt(1, candidate.getId());
            else
                throw new EVException("Election_Candidates.delete failed to delete candidate_Issues");
            if(election.getId() > 0)
                stmt.setInt(2, election.getId());
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

    public void deleteElections(Candidate candidate) throws EVException {
        String               deleteElection = "delete from Election_Candidates where Candidate_ID = ?";
        PreparedStatement    stmt = null;
        int                  queryExecution;

        try{
            stmt = conn.prepareStatement( deleteElection );
            if(candidate.getId() >0)
                stmt.setInt(1, candidate.getId());
            else
                throw new EVException("Election_Candidates.delete failed to delete candidate_Issues");
            queryExecution = stmt.executeUpdate();
            /*if(queryExecution != 1)
                throw new EVException("Election_CandidatesManager.delete failed to delete");*/
        }
        catch( SQLException e ) {
            e.printStackTrace();
            throw new EVException( "Election_CandidatesManager.delete: failed to delete a candidate: " + e );
        }
    }
}
