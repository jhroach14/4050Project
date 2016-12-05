package main.javaSrc.DBHelpers.Managers;


import main.javaSrc.DBHelpers.ObjectLayer;
import main.javaSrc.Entities.Ballot;
import main.javaSrc.Entities.BallotItem;
import main.javaSrc.Entities.Election;
import main.javaSrc.Entities.EntityImpl.BallotItemImpl;
import main.javaSrc.Entities.EntityImpl.ElectionImpl;
import main.javaSrc.helpers.EVException;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by User on 11/9/2016.
 */
public class Ballot_ElectionsManager {
    private Connection conn;
    private ObjectLayer objectLayer;

    public Ballot_ElectionsManager(Connection conn, ObjectLayer objectlayer) {
        this.objectLayer = objectlayer;
        this.conn = conn;
    }

    public void store(Ballot ballot, BallotItem ballotItem)throws EVException {

        String insertBallot_Election = "insert into Ballot_Elections (Ballot_ID, Election_ID) values (?, ?)";
        PreparedStatement stmt = null;
        int queryExecution;

        try {
            stmt = conn.prepareStatement( insertBallot_Election );

            if(ballot.getId() > 0)
                stmt.setInt(1, ballot.getId());
            else
                throw new EVException("Ballot_ElectionsManager.save can't save a ballot_election: Ballot ID undefined") ;

            if(ballotItem.getId() > 0)
                stmt.setInt(2, ballotItem.getId());
            else
                throw new EVException("Ballot_ElectionsManger.save can't save a ballot_election: Ballot_Item ID undefined") ;

            queryExecution = stmt.executeUpdate();

            if(queryExecution < 1)
                throw new EVException("Ballot_ElectionsManager.save failed to save ballot_Election");

        }
        catch(SQLException e){
            e.printStackTrace();
            throw new EVException("Ballot_ElectionsManager.store failed to save a ballot_Election" +e);
        }


    }

    public List<Ballot> restoreMult (BallotItem ballotItem) throws EVException{
        StringBuffer query = new StringBuffer(500);
        Statement stmt = null;

        if(ballotItem.getId() <1)
            throw new EVException("Ballot_Elections.restore could not restore non persistent election");

        query.append("select Ballot_ID, Start_Date, End_Date, Election_ID from (select Ballot.Ballot_ID, Ballot.Start_Date, Ballot.End_Date, Ballot_Elections.Election_ID from Ballot inner join Ballot_Elections on Ballot_Elections.Ballot_ID = Ballot.Ballot_ID) as T where Election_ID = " + ballotItem.getId());

        try{
            stmt = conn.createStatement();

            if(stmt.execute(query.toString())) {
                int ballotId;
                Date startDate;
                Date closeDate;
                Ballot newBallot = null;

                ResultSet rs = stmt.getResultSet();
                List<Ballot> ballots =new ArrayList<>();
                while(rs.next()){
                    ballotId = rs.getInt( 1 );
                    startDate = rs.getDate( 2 );
                    closeDate = rs.getDate( 3 );

                    newBallot = objectLayer.createBallot();
                    newBallot.setId( ballotId );
                    newBallot.setOpenDate( startDate );
                    newBallot.setCloseDate( closeDate );
                    newBallot.setPersistent(true);
                    ballots.add(newBallot);
                }
                return ballots;
            }
        }

        catch(SQLException e){
            e.printStackTrace();
            throw new EVException("Ballot_Elections.store failed to save a ballot_Election" +e);
        }
        throw new EVException("Ballot_Elections.restore could not restore ballot object");
    }

    public Ballot restore (BallotItem ballotItem) throws EVException{
        StringBuffer query = new StringBuffer(500);
        Statement stmt = null;

        if(ballotItem.getId() <1)
            throw new EVException("Ballot_Elections.restore could not restore non persistent election");

        query.append("select Ballot_ID, Start_Date, End_Date, Election_ID from (select Ballot.Ballot_ID, Ballot.Start_Date, Ballot.End_Date, Ballot_Elections.Election_ID from Ballot inner join Ballot_Elections on Ballot_Elections.Ballot_ID = Ballot.Ballot_ID) as T where Election_ID = " + ballotItem.getId());

        try{
            stmt = conn.createStatement();

            if(stmt.execute(query.toString())) {
                int ballotId;
                Date startDate;
                Date closeDate;
                Ballot newBallot = null;

                ResultSet rs = stmt.getResultSet();

                while(rs.next()){
                    ballotId = rs.getInt( 1 );
                    startDate = rs.getDate( 2 );
                    closeDate = rs.getDate( 3 );

                    newBallot = objectLayer.createBallot();
                    newBallot.setId( ballotId );
                    newBallot.setOpenDate( startDate );
                    newBallot.setCloseDate( closeDate );
                    newBallot.setPersistent(true);
                    break;
                }
                return newBallot;
            }
        }

        catch(SQLException e){
            e.printStackTrace();
            throw new EVException("Ballot_Elections.store failed to save a ballot_Election" +e);
        }
        throw new EVException("Ballot_Elections.restore could not restore ballot object");
    }

    public List<BallotItem> restore(Ballot ballot) throws EVException{
        StringBuffer query = new StringBuffer(500);
        Statement stmt = null;
        List<BallotItem> ballotItems = new ArrayList<BallotItem>();

        if(ballot.getId() <1)
            throw new EVException("Ballot_Elections.restore could not restore persistent Ballot_Elections");

        query.append("select Election_ID, Office_Name, Is_Partisan, Vote_Count from (select Election.Election_ID, Election.Office_Name, Election.Is_Partisan, Election.Vote_Count, Ballot_Elections.Ballot_ID from Election inner join Ballot_Elections on Ballot_Elections.Election_ID = Election.Election_ID) as T where Ballot_ID = " + ballot.getId());

        try {
            stmt = conn.createStatement();

            if (stmt.execute(query.toString())) { // statement returned a result

                int electionId;
                String officeName;
                boolean isPartisan;
                int voteCount;

                ElectionImpl newBallotItem = null;

                ResultSet rs = stmt.getResultSet();


                while (rs.next()) {

                    electionId = rs.getInt(1);
                    officeName = rs.getString(2);
                    isPartisan = rs.getBoolean(3);
                    voteCount = rs.getInt(4);

                    newBallotItem = new ElectionImpl();
                    newBallotItem.setId(electionId);
                    newBallotItem.setOffice(officeName);
                    newBallotItem.setIsPartisan(isPartisan);
                    newBallotItem.setVoteCount(voteCount);
                    newBallotItem.setPersistent(true);


                    ballotItems.add(newBallotItem);
                }

                return ballotItems;
            }
        }

        catch(SQLException e){
            e.printStackTrace();
            throw new EVException("Ballot_Elections. store failed to save a ballot_Election" +e);
        }
        throw new EVException("Ballot_election.restore could not restore ballot object");
    }

    public void delete(Ballot ballot, BallotItem ballotItem) throws EVException{
        String               deleteBallot = "delete from Ballot_Elections where Ballot_ID = ? and Election_ID = ?";
        PreparedStatement    stmt = null;
        int                  queryExecution;

        try{
            stmt = conn.prepareStatement( deleteBallot );
            if(ballot.getId() >0)
                stmt.setInt(1, ballot.getId());
            else
                throw new EVException("Ballot_Elections.delete failed to delete ballot_Election");
            if(ballotItem.getId() > 0)
                stmt.setInt(2, ballotItem.getId());
            else
                throw new EVException("Ballot_ElectionsManager.delete failed to delete ballot_Election");
            queryExecution = stmt.executeUpdate();
            if(queryExecution != 1 )
                throw new EVException("Ballot_ElectionsManager.delete failed to delete");
        }
        catch( SQLException e ) {
            e.printStackTrace();
            throw new EVException( "Ballot_ElectionsManager.delete: failed to delete a ballot: " + e );
        }
    }

    public void delete(Ballot ballot) throws EVException{
        String               deleteBallot = "delete from Ballot_Elections where Ballot_ID = ?";
        PreparedStatement    stmt = null;
        int                  queryExecution;

        try{
            stmt = conn.prepareStatement( deleteBallot );
            if(ballot.getId() > 0)
                stmt.setInt(1, ballot.getId());
            else
                throw new EVException("Ballot_Elections.delete failed to delete ballot_Election");

            queryExecution = stmt.executeUpdate();
            //if(queryExecution != 1 )
                //throw new EVException("Ballot_ElectionsManager.delete failed to delete");
        }
        catch( SQLException e ) {
            e.printStackTrace();
            throw new EVException( "Ballot_ElectionsManager.delete: failed to delete a ballot: " + e );
        }
    }

    public void delete(Election election) throws EVException{
        String               deleteBallot = "delete from Ballot_Elections where Election_ID = ?";
        PreparedStatement    stmt = null;
        int                  queryExecution;

        try{
            stmt = conn.prepareStatement( deleteBallot );
            if(election.getId() > 0)
                stmt.setInt(1, election.getId());
            else
                throw new EVException("Ballot_Elections.delete failed to delete ballot_Election");

            queryExecution = stmt.executeUpdate();
            //if(queryExecution != 1 )
            //throw new EVException("Ballot_ElectionsManager.delete failed to delete");
        }
        catch( SQLException e ) {
            e.printStackTrace();
            throw new EVException( "Ballot_ElectionsManager.delete: failed to delete a ballot: " + e );
        }
    }
}
