package main.javaSrc.DBHelpers.Managers;

import main.javaSrc.DBHelpers.ObjectLayer;
import main.javaSrc.Entities.Candidate;
import main.javaSrc.Entities.Election;
import main.javaSrc.Entities.EntityImpl.CandidateImpl;
import main.javaSrc.Entities.Election;
import main.javaSrc.Entities.EntityImpl.PoliticalPartyImpl;
import main.javaSrc.Entities.PoliticalParty;
import main.javaSrc.helpers.EVException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by User on 11/2/2016.
 */
public class CandidateManager extends Manager{

    public CandidateManager(Connection conn, ObjectLayer objectLayer){
        super(conn,objectLayer);
    }

    public CandidateManager() {

    }

    public List<Candidate> restore(Candidate candidate) throws EVException {

        Statement    stmt = null;
        String query = "";
        List<Candidate>   candidates = new ArrayList<Candidate>();

        if( candidate != null ) {

            query = candidate.getRestoreString();

        }

        try {

            stmt = conn.createStatement();

            // retrieve the persistent candidate objects
            //
            if( stmt.execute( query.toString() ) ) { // statement returned a result

                int candidateId;
                String name;
                int voteCount;
                Candidate nextCandidate = null;

                ResultSet rs = stmt.getResultSet();

                // retrieve the retrieved candidates
                while( rs.next() ) {

                    candidateId = rs.getInt( 1 );
                    name = rs.getString( 2 );
                    voteCount = rs.getInt( 3 );

                    nextCandidate = objectLayer.createCandidate(); // create a proxy candidate object

                    // and now set its retrieved attributes
                    nextCandidate.setId( candidateId );
                    nextCandidate.setName( name );
                    nextCandidate.setVoteCount( voteCount );
                    nextCandidate.setPersistent(true);

                    candidates.add( nextCandidate );
                }

                return candidates;
            }
        }
        catch( Exception e ) {      // just in case...
            throw new EVException( "candidateManager.restore: Could not restore persistent candidate objects; Root cause: " + e );
        }

        throw new EVException( "candidateManager.restore: Could not restore persistent candidate objects" );

    }

    public Candidate store(Candidate candidate) throws EVException{
        String insertCandidate = "insert into Candidate ( Candidate_Name, Vote_Count) values ( ?, ? )";
        String updateCandidate = "update Candidate set Candidate_Name = ?, Vote_Count = ? where Candidate_ID = ?";
        PreparedStatement stmt = null;
        int queryExecution;
        int candidateId;

        try {

            if( !candidate.isPersistent() ) {
                stmt = conn.prepareStatement(insertCandidate);
            }else {
                stmt = conn.prepareStatement(updateCandidate);
            }

            stmt = candidate.insertStoreData(stmt);

            queryExecution = stmt.executeUpdate();

            if( !candidate.isPersistent() ) {
                if( queryExecution >= 1 ) {
                    candidate =(Candidate) setId(stmt,candidate);
                    candidate.setPersistent(true);
                }
                else
                    throw new EVException( "CandidateManager.save: failed to save a candidate" );
            }
            else {
                if( queryExecution < 1 )
                    throw new EVException( "CandidateManager.save: failed to save a candidate" );
            }
        }
        catch( SQLException e ) {
            e.printStackTrace();
            throw new EVException( "CandidateManager.save: failed to save a candidate: " + e );
        }

        return candidate;
    }


    public void delete(Candidate candidate) throws EVException {

        String               deleteCandidate = "delete from Candidate where Candidate_ID = ?";
        PreparedStatement    stmt = null;
        int                  queryExecution;

        if( !candidate.isPersistent() ) // is the candidate object persistent?  If not, nothing to actually delete
            return;

        try {
            stmt = conn.prepareStatement( deleteCandidate );
            stmt.setInt( 1, candidate.getId() );
            queryExecution = stmt.executeUpdate();
            if( queryExecution == 1 ) {
                return;
            }
            else
                throw new EVException( "candidateManager.delete: failed to delete a candidate" );
        }
        catch( SQLException e ) {
            e.printStackTrace();
            throw new EVException( "candidateManager.delete: failed to delete a candidate: " + e );        }
    }


}

