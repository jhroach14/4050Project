package main.javaSrc.DBHelpers.Managers;

import main.javaSrc.DBHelpers.ObjectLayer;
import main.javaSrc.Entities.Candidate;
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
public class CandidateManager {

    private ObjectLayer objectLayer = null;
    private Connection conn = null;

    public CandidateManager(Connection conn, ObjectLayer objectLayer){
        this.conn = conn;
        this.objectLayer = objectLayer;
    }

    public CandidateManager() {

    }

    public List<Candidate> restore(Candidate candidate) throws EVException {

        String       selectCandidate = "select Candidate_ID, Candidate_Name, Party_ID, Vote_Count from Candidate";
        Statement    stmt = null;
        StringBuffer query = new StringBuffer( 100 );
        StringBuffer condition = new StringBuffer( 100 );
        List<Candidate>   candidates = new ArrayList<Candidate>();

        condition.setLength( 0 );

        // form the query based on the given candidate object instance
        query.append( selectCandidate );

        if( candidate != null ) {
            if( candidate.getId() >= 0 ) { // id is unique, so it is sufficient to get a person
                query.append(" where Candidate_ID = " + candidate.getId());
            }
            else {

                if( candidate.getName() != null )
                    condition.append( " where Candidate_Name = '" + candidate.getName() + "'" );


                if( candidate.getPoliticalParty().getId() >= 0){
                    if( condition.length() > 0 )
                        condition.append( " and" );
                    else
                        condition.append( " where" );
                    condition.append( " Party_ID = '" + candidate.getPoliticalParty().getId() + "'" );
                }


                if( candidate.getVoteCount() >= 0){
                    if( condition.length() > 0 )
                        condition.append( " and" );
                    else
                        condition.append( " where" );
                    condition.append( " Vote_Count = '" + candidate.getVoteCount() + "'" );

                }

            }
            query.append( condition );
        }

        try {

            stmt = conn.createStatement();

            // retrieve the persistent candidate objects
            //
            if( stmt.execute( query.toString() ) ) { // statement returned a result

                int candidateId;
                String name;
                int partyId;
                int voteCount;
                Candidate nextCandidate = null;

                ResultSet rs = stmt.getResultSet();

                // retrieve the retrieved candidates
                while( rs.next() ) {

                    candidateId = rs.getInt( 1 );
                    name = rs.getString( 2 );
                    partyId = rs.getInt( 3 );
                    voteCount = rs.getInt( 4 );

                    nextCandidate = objectLayer.createCandidate(); // create a proxy candidate object
                    // and now set its retrieved attributes
                    nextCandidate.setId( candidateId );
                    nextCandidate.setName( name );

                    //This part creates a new PoliticalParty as a model **PoliticalParty still needs to be implemented**
                    //then checks the objectLayer to see if it can find a party with that partyId
                    //if so, then assigns the candidates party to be that found party
                    if(partyId >= 0) {
                        PoliticalParty p = new PoliticalPartyImpl();
                        p.setId(partyId);
                        PoliticalParty partyToSet = objectLayer.findPoliticalParty( p ).get(0);
                        if(partyToSet != null)
                            nextCandidate.setPoliticalParty(partyToSet);
                    }

                    nextCandidate.setVoteCount( voteCount );

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

    public void store(Candidate candidate) throws EVException{
        String insertCandidate = "insert into Candidate ( Candidate_Name, Party_ID, Vote_Count) values ( ?, ?, ? )";
        String updateCandidate = "update Candidate set Candidate_Name = ?, Party_ID = ?, Vote_Count = ?";
        PreparedStatement stmt = null;
        int queryExecution;
        int candidateId;

        try {

            if( !candidate.isPersistent() )
                stmt = conn.prepareStatement( insertCandidate );
            else
                stmt = conn.prepareStatement( updateCandidate );

            //Cannot be null

            if( candidate.getName() != null )
                stmt.setString( 1, candidate.getName() );
            else
                throw new EVException( "CandidateManager.save: can't save a candidate: Name undefined" );


            //The rest can be null
            if(candidate.getPoliticalParty()!=null){
                if( candidate.getPoliticalParty().getId() >= 0 )
                    stmt.setInt( 2, candidate.getPoliticalParty().getId() );
                else
                    stmt.setNull( 2, java.sql.Types.INTEGER );
            }else{
                stmt.setNull( 2, java.sql.Types.INTEGER );
            }

            if( candidate.getVoteCount() >= 0 )
                stmt.setInt( 3, candidate.getVoteCount() );
            else
                stmt.setNull( 3, java.sql.Types.INTEGER );

            queryExecution = stmt.executeUpdate();

            if( !candidate.isPersistent() ) {
                if( queryExecution >= 1 ) {
                    String sql = "select last_insert_id()";
                    if( stmt.execute( sql ) ) { // statement returned a result

                        // retrieve the result
                        ResultSet r = stmt.getResultSet();

                        // we will use only the first row!
                        //
                        while( r.next() ) {

                            // retrieve the last insert auto_increment value
                            candidateId = r.getInt( 1 );
                            if( candidateId > 0 )
                                candidate.setId( candidateId ); // set this person's db id (proxy object)
                        }
                    }
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

