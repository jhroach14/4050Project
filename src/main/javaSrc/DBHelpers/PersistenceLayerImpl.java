package main.javaSrc.DBHelpers;

import main.javaSrc.DBHelpers.Managers.*;
import main.javaSrc.Entities.*;
import main.javaSrc.Entities.EntityImpl.ElectionsOfficerImpl;
import main.javaSrc.helpers.EVException;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.sql.Connection;
import java.util.List;

/**
 * Created by User on 10/27/2016.
 */
public class PersistenceLayerImpl implements PersistenceLayer{

    BallotManager ballotManager = null;
    CandidateManager candidateManager = null;
    ElectionManager electionManager = null;
    ElectionsOfficerManager electionsOfficerManager = null;
    ElectoralDistrictManager electoralDistrictManager = null;
    IssueManager issueManager = null;
    PoliticalPartyManager politicalPartyManager = null;
    VoterManager voterManager = null;
    VoteRecordManager voteRecordManager = null;

    public PersistenceLayerImpl() {

    }

    public PersistenceLayerImpl(Connection connection,ObjectLayer objectLayer) {
        this.ballotManager = new BallotManager(connection,objectLayer);
        this.candidateManager = new CandidateManager(connection,objectLayer);
        this.electionManager = new ElectionManager(connection,objectLayer);
        this.electionsOfficerManager = new ElectionsOfficerManager(connection,objectLayer);
        this.electoralDistrictManager = new ElectoralDistrictManager(connection,objectLayer);
        this.issueManager = new IssueManager(connection,objectLayer);
        this.politicalPartyManager =  new PoliticalPartyManager(connection,objectLayer);
        this.voterManager = new VoterManager(connection,objectLayer);
        this.voteRecordManager = new VoteRecordManager(connection,objectLayer);
    }

    @Override
    public List<ElectionsOfficer> restoreElectionsOfficer(ElectionsOfficer electionsOfficer) throws EVException {
        return electionsOfficerManager.restore(electionsOfficer);
    }

    @Override
    public void storeElectionsOfficer(ElectionsOfficer electionsOfficer) throws EVException {
        electionsOfficerManager.store(electionsOfficer);

    }

    @Override
    public void deleteElectionsOfficer(ElectionsOfficer electionsOfficer) throws EVException {
        electionsOfficerManager.delete(electionsOfficer);
    }

    @Override
    public List<Voter> restoreVoter(Voter modelVoter) throws EVException {
        return voterManager.restore(modelVoter);
    }

    @Override
    public void storeVoter(Voter voter) throws EVException {
        voterManager.store(voter);
    }

    @Override
    public void deleteVoter(Voter voter) throws EVException {
        voterManager.delete(voter);
    }

    @Override
    public List<Ballot> restoreBallot(Ballot modelBallot) throws EVException {
        return ballotManager.restore(modelBallot);
    }

    @Override
    public void storeBallot(Ballot ballot) throws EVException {
        ballotManager.store(ballot);
    }

    @Override
    public void deleteBallot(Ballot ballot) throws EVException {
        ballotManager.delete(ballot);
    }

    @Override
    public List<Candidate> restoreCandidate(Candidate modelCandidate) throws EVException {
        return candidateManager.restore(modelCandidate);
    }

    @Override
    public void storeCandidate(Candidate candidate) throws EVException {
        candidateManager.store(candidate);
    }

    @Override
    public void deleteCandidate(Candidate candidate) throws EVException {
        candidateManager.delete(candidate);
    }

    @Override
    public List<Election> restoreElection(Election modelElection) throws EVException {
        return electionManager.restore(modelElection);
    }

    @Override
    public void storeElection(Election election) throws EVException {
        electionManager.store(election);
    }

    @Override
    public void deleteElection(Election election) throws EVException {
        electionManager.delete(election);
    }

    @Override
    public List<ElectoralDistrict> restoreElectoralDistrict(ElectoralDistrict modelElectoralDistrict) throws EVException {
        return electoralDistrictManager.restore(modelElectoralDistrict);
    }

    @Override
    public void storeElectoralDistrict(ElectoralDistrict electoralDistrict) throws EVException {
        electoralDistrictManager.store(electoralDistrict);
    }

    @Override
    public void deleteElectoralDistrict(ElectoralDistrict electoralDistrict) throws EVException {
        electoralDistrictManager.delete(electoralDistrict);
    }

    @Override
    public List<Issue> restoreIssue(Issue modelIssue) throws EVException {
        return issueManager.restore(modelIssue);
    }

    @Override
    public void storeIssue(Issue issue) throws EVException {
        issueManager.store(issue);
    }

    @Override
    public void deleteIssue(Issue issue) throws EVException {
        issueManager.delete(issue);
    }

    @Override
    public List<PoliticalParty> restorePoliticalParty(PoliticalParty modelPoliticalParty) throws EVException {
        return politicalPartyManager.restore(modelPoliticalParty);
    }

    @Override
    public void storePoliticalParty(PoliticalParty politicalParty) throws EVException {
        politicalPartyManager.store(politicalParty);
    }

    @Override
    public void deletePoliticalParty(PoliticalParty politicalParty) throws EVException {
        politicalPartyManager.delete(politicalParty);
    }

    @Override
    public List<VoteRecord> restoreVoteRecord(VoteRecord modelVoteRecord) throws EVException {
        return voteRecordManager.restore(modelVoteRecord);
    }

    @Override
    public void storeVoteRecord(VoteRecord voteRecord) throws EVException {
        voteRecordManager.store(voteRecord);
    }

    @Override
    public void deleteVoteRecord(VoteRecord voteRecord) throws EVException {
        voteRecordManager.delete(voteRecord);
    }



   /* @Override
    public void storeBallotIncludesBallotItem(Ballot ballot, BallotItem ballotItem) throws EVException {
        ballotManager.store(ballot, ballotItem);
    }

    @Override
    public Ballot restoreBallotIncludesBallotItem(BallotItem ballotItem) throws EVException {
        return ballotManager.restore(ballotItem);
    }

    @Override
    public List<BallotItem> restoreBallotIncludesBallotItem(Ballot ballot) throws EVException {
        return ballotManager.restoreBallotItems(ballot);
    }

    @Override
    public void deleteBallotIncludesBallotItem(Ballot ballot, BallotItem ballotItem) throws EVException {
        ballotManager.delete(ballot, ballotItem);
    }

    @Override
    public void storeCandidateIsCandidateInElection(Candidate candidate, Election election) throws EVException {
        candidateManager.store(candidate, election);
    }

    @Override
    public Election restoreCandidateIsCandidateInElection(Candidate candidate) throws EVException {
        return candidateManager.restoreElectionCandidate(candidate);
    }

    @Override
    public List<Candidate> restoreCandidateIsCandidateInElection(Election election) throws EVException {
        return candidateManager.restore(election);
    }

    @Override
    public void deleteCandidateIsCandidateInElection(Candidate candidate, Election election) throws EVException {
        candidateManager.delete(candidate, election);
    }

    @Override
    public void storeElectoralDistrictHasBallotBallot(ElectoralDistrict electoralDistrict, Ballot ballot) throws EVException {
        electoralDistrictManager.store(electoralDistrict, ballot);
    }

    @Override
    public ElectoralDistrict restoreElectoralDistrictHasBallotBallot(Ballot ballot) throws EVException {
        return electoralDistrictManager.restoreElectoralDistrict(ballot);
    }

    @Override
    public List<Ballot> restoreElectoralDistrictHasBallotBallot(ElectoralDistrict electoralDistrict) throws EVException {
        return electoralDistrictManager.restoreBallot(electoralDistrict);
    }

    @Override
    public void deleteElectoralDistrictHasBallotBallot(ElectoralDistrict electoralDistrict, Ballot ballot) throws EVException {
        electoralDistrictManager.delete(electoralDistrict, ballot);
    }

    @Override
    public void storeCandidateIsMemberOfPoliticalParty(Candidate candidate, PoliticalParty politicalParty) throws EVException {
        candidateManager.store(candidate, politicalParty);
    }

    @Override
    public PoliticalParty restoreCandidateIsMemberOfPoliticalParty(Candidate candidate) throws EVException {
        return politicalPartyManager.restore(candidate);
    }

    @Override
    public List<Candidate> restoreCandidateIsMemberOfPoliticalParty(PoliticalParty politicalParty) throws EVException {
        return candidateManager.restore(politicalParty);
    }

    @Override
    public void deleteCandidateIsMemberOfElection(Candidate candidate, PoliticalParty politicalParty) throws EVException {
        candidateManager.delete(candidate, politicalParty);
    }

    @Override
    public void storeVoterBelongsToElectoralDistrict(Voter voter, ElectoralDistrict electoralDistrict) throws EVException {
        voterManager.store(voter, electoralDistrict);
    }

    @Override
    public ElectoralDistrict restoreVoterBelongsToElectoralDistrict(Voter voter) throws EVException {
        return electoralDistrictManager.restore(voter);
    }

    @Override
    public List<Voter> restoreVoterBelongsToElectoralDistrict(ElectoralDistrict electoralDistrict) throws EVException {
        return electoralDistrictManager.restore(electoralDistrict);
    }

    @Override
    public void deleteVoterBelongsToElection(Voter voter, ElectoralDistrict electoralDistrict) throws EVException {
        voterManager.delete(voter, electoralDistrict);
    }*/
}
