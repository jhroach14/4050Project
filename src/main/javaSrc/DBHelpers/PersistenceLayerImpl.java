package main.javaSrc.DBHelpers;

import main.javaSrc.DBHelpers.Managers.*;
import main.javaSrc.Entities.*;
import main.javaSrc.helpers.EVException;

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
    Ballot_ElectionsManager ballot_electionsManager = null;
    Ballot_IssuesManager ballot_issuesManager = null;
    Election_CandidatesManager election_candidatesManager = null;
    District_BallotsManager district_ballotsManager= null;
    District_VoterManager district_voterManager = null;
    Party_CandidatesManager party_candidatesManager = null;


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
        this.ballot_electionsManager = new Ballot_ElectionsManager(connection,objectLayer);
        this.ballot_issuesManager = new Ballot_IssuesManager(objectLayer,connection);
        this.election_candidatesManager = new Election_CandidatesManager(objectLayer,connection);
        this.district_ballotsManager = new District_BallotsManager(objectLayer,connection);
        this.district_voterManager = new District_VoterManager(objectLayer,connection);
        this.party_candidatesManager = new Party_CandidatesManager(objectLayer,connection);
    }

    @Override
    public List<ElectionsOfficer> restoreElectionsOfficer(ElectionsOfficer electionsOfficer) throws EVException {
        return electionsOfficerManager.restore(electionsOfficer);
    }

    @Override
    public ElectionsOfficer storeElectionsOfficer(ElectionsOfficer electionsOfficer) throws EVException {
        return electionsOfficerManager.store(electionsOfficer);

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
    public Voter storeVoter(Voter voter) throws EVException {
        return voterManager.store(voter);
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
    public Ballot storeBallot(Ballot ballot) throws EVException {
        return ballotManager.store(ballot);
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
    public Candidate storeCandidate(Candidate candidate) throws EVException {
        return candidateManager.store(candidate);
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
    public Election storeElection(Election election) throws EVException {
        return electionManager.store(election);
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
    public ElectoralDistrict storeElectoralDistrict(ElectoralDistrict electoralDistrict) throws EVException {
        return electoralDistrictManager.store(electoralDistrict);
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
    public Issue storeIssue(Issue issue) throws EVException {
        return issueManager.store(issue);
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
    public PoliticalParty storePoliticalParty(PoliticalParty politicalParty) throws EVException {
        return politicalPartyManager.store(politicalParty);
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
    public VoteRecord storeVoteRecord(VoteRecord voteRecord) throws EVException {
        return voteRecordManager.store(voteRecord);
    }

    @Override
    public void deleteVoteRecord(VoteRecord voteRecord) throws EVException {
        voteRecordManager.delete(voteRecord);
    }






    @Override
    public void storeBallotIncludesBallotItem(Ballot ballot, BallotItem ballotItem) throws EVException {

        if(ballotItem instanceof Election){
            ballot_electionsManager.store(ballot,ballotItem);
        }else
        if(ballotItem instanceof Issue){
            ballot_issuesManager.store(ballot,ballotItem);
        }
    }

    @Override
    public Ballot restoreBallotIncludesBallotItem(BallotItem ballotItem) throws EVException {

        Ballot ballot = null;
        if(ballotItem instanceof Election){
            ballot = ballot_electionsManager.restore(ballotItem);
        }else
        if(ballotItem instanceof Issue){
            ballot = ballot_issuesManager.restore(ballotItem);
        }
        return ballot;
    }

    @Override
    public List<BallotItem> restoreBallotIncludesBallotItem(Ballot ballot) throws EVException {

        List<BallotItem> ballotItems= ballot_issuesManager.restore(ballot);
        ballotItems.addAll(ballot_electionsManager.restore(ballot));

        return ballotItems;

    }

    @Override
    public void deleteBallotIncludesBallotItem(Ballot ballot, BallotItem ballotItem) throws EVException {
        if(ballotItem instanceof Election){
            ballot_electionsManager.delete(ballot,ballotItem);
        }else
        if(ballotItem instanceof Issue){
            ballot_issuesManager.delete(ballot,ballotItem);
        }
    }

    @Override
    public void storeCandidateIsCandidateInElection(Candidate candidate, Election election) throws EVException {
        election_candidatesManager.store(candidate,election);
    }

    @Override
    public Election restoreCandidateIsCandidateInElection(Candidate candidate) throws EVException {
        return election_candidatesManager.restore(candidate);
    }

    @Override
    public List<Candidate> restoreCandidateIsCandidateInElection(Election election) throws EVException {
        return election_candidatesManager.restore(election);
    }

    @Override
    public void deleteCandidateIsCandidateInElection(Candidate candidate, Election election) throws EVException {
        election_candidatesManager.delete(election,candidate);
    }




    @Override
    public void storeElectoralDistrictHasBallotBallot(ElectoralDistrict electoralDistrict, Ballot ballot) throws EVException {
        district_ballotsManager.store(electoralDistrict, ballot);
    }

    @Override
    public ElectoralDistrict restoreElectoralDistrictHasBallotBallot(Ballot ballot) throws EVException {
        return district_ballotsManager.restore(ballot);
    }

    @Override
    public List<Ballot> restoreElectoralDistrictHasBallotBallot(ElectoralDistrict electoralDistrict) throws EVException {
        return district_ballotsManager.restore(electoralDistrict);
    }

    @Override
    public void deleteElectoralDistrictHasBallotBallot(ElectoralDistrict electoralDistrict, Ballot ballot) throws EVException {
        district_ballotsManager.delete(electoralDistrict, ballot);
    }

    @Override
    public void storeCandidateIsMemberOfPoliticalParty(Candidate candidate, PoliticalParty politicalParty) throws EVException {
        party_candidatesManager.store(candidate, politicalParty);
    }

    @Override
    public PoliticalParty restoreCandidateIsMemberOfPoliticalParty(Candidate candidate) throws EVException {
        return party_candidatesManager.restore(candidate);
    }

    @Override
    public List<Candidate> restoreCandidateIsMemberOfPoliticalParty(PoliticalParty politicalParty) throws EVException {
        return party_candidatesManager.restore(politicalParty);
    }

    @Override
    public void deleteCandidateIsMemberOfElection(Candidate candidate, PoliticalParty politicalParty) throws EVException {
        party_candidatesManager.delete(candidate, politicalParty);
    }

    @Override
    public void storeVoterBelongsToElectoralDistrict(Voter voter, ElectoralDistrict electoralDistrict) throws EVException {
        district_voterManager.store(voter, electoralDistrict);
    }

    @Override
    public ElectoralDistrict restoreVoterBelongsToElectoralDistrict(Voter voter) throws EVException {
        return district_voterManager.restore(voter);
    }

    @Override
    public List<Voter> restoreVoterBelongsToElectoralDistrict(ElectoralDistrict electoralDistrict) throws EVException {
        return district_voterManager.restore(electoralDistrict);
    }

    @Override
    public void deleteVoterBelongsToElection(Voter voter, ElectoralDistrict electoralDistrict) throws EVException {
        district_voterManager.delete(voter, electoralDistrict);
    }
}
