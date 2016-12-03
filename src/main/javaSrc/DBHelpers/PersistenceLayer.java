package main.javaSrc.DBHelpers;


import main.javaSrc.Entities.*;
import main.javaSrc.helpers.EVException;

import java.util.List;

public interface PersistenceLayer {
    
    public List<ElectionsOfficer> restoreElectionsOfficer(ElectionsOfficer modelElectionsOfficer ) throws EVException;

    
    public ElectionsOfficer storeElectionsOfficer(ElectionsOfficer electionsOfficer ) throws EVException;

    
    public void deleteElectionsOfficer( ElectionsOfficer electionsOfficer ) throws EVException;

    
    public List<Voter> restoreVoter(Voter modelVoter ) throws EVException;

    
    public Voter storeVoter(Voter voter ) throws EVException;

    
    public void deleteVoter( Voter voter ) throws EVException;

    
    public List<Ballot> restoreBallot( Ballot modelBallot ) throws EVException;

    
    public Ballot storeBallot(Ballot ballot ) throws EVException;

    
    public void deleteBallot( Ballot ballot ) throws EVException;

    
    public List<Candidate> restoreCandidate(Candidate modelCandidate ) throws EVException;

    
    public Candidate storeCandidate(Candidate candidate ) throws EVException;

    
    public void deleteCandidate( Candidate candidate ) throws EVException;

    
    public List<Election> restoreElection( Election modelElection ) throws EVException;

    
    public Election storeElection(Election election ) throws EVException;

    
    public void deleteElection( Election election ) throws EVException;

    
    public List<ElectoralDistrict> restoreElectoralDistrict( ElectoralDistrict modelElectoralDistrict ) throws EVException;

    
    public ElectoralDistrict storeElectoralDistrict(ElectoralDistrict electoralDistrict ) throws EVException;

    
    public void deleteElectoralDistrict( ElectoralDistrict electoralDistrict ) throws EVException;

    
    public List<Issue> restoreIssue( Issue modelIssue ) throws EVException;

    
    public Issue storeIssue(Issue issue ) throws EVException;

    
    public void deleteIssue( Issue issue ) throws EVException;

    
    public List<PoliticalParty> restorePoliticalParty( PoliticalParty modelPoliticalParty ) throws EVException;

    
    public PoliticalParty storePoliticalParty(PoliticalParty politicalParty ) throws EVException;

    
    public void deletePoliticalParty( PoliticalParty politicalParty ) throws EVException;

    
    public List<VoteRecord> restoreVoteRecord( VoteRecord modelVoteRecord ) throws EVException;

    
    public VoteRecord storeVoteRecord(VoteRecord voteRecord ) throws EVException;

    
    public void deleteVoteRecord( VoteRecord voteRecord ) throws EVException;

    
    public void storeBallotIncludesBallotItem( Ballot ballot, BallotItem ballotItem ) throws EVException;


    public Ballot restoreBallotIncludesBallotItem( BallotItem ballotItem ) throws EVException;


    public List<BallotItem> restoreBallotIncludesBallotItem( Ballot ballot ) throws EVException;


    public void deleteBallotIncludesBallotItem( Ballot ballot, BallotItem ballotItem ) throws EVException;


    public void deleteBallotFromAllAssociations( Ballot ballot ) throws EVException;


    public void deleteIssueFromAllAssociations( Issue issue ) throws EVException;


    public void deleteElectionFromAllAssociations( Election election ) throws EVException;


    public void storeCandidateIsCandidateInElection( Candidate candidate, Election election ) throws EVException;


    public Election restoreCandidateIsCandidateInElection( Candidate candidate ) throws EVException;


    public List<Candidate> restoreCandidateIsCandidateInElection( Election election ) throws EVException;


    public void deleteCandidateIsCandidateInElection( Candidate candidate, Election election ) throws EVException;


    public void storeElectoralDistrictHasBallotBallot( ElectoralDistrict electoralDistrict, Ballot ballot ) throws EVException;


    public ElectoralDistrict restoreElectoralDistrictHasBallotBallot( Ballot ballot ) throws EVException;


    public List<Ballot> restoreElectoralDistrictHasBallotBallot( ElectoralDistrict electoralDistrict ) throws EVException;


    public void deleteElectoralDistrictHasBallotBallot( ElectoralDistrict electoralDistrict, Ballot ballot ) throws EVException;



    public void storeCandidateIsMemberOfPoliticalParty( Candidate candidate, PoliticalParty politicalParty ) throws EVException;


    public PoliticalParty restoreCandidateIsMemberOfPoliticalParty( Candidate candidate ) throws EVException;


    public List<Candidate> restoreCandidateIsMemberOfPoliticalParty( PoliticalParty politicalParty ) throws EVException;


    public void deleteCandidateIsMemberOfElection( Candidate candidate, PoliticalParty politicalParty ) throws EVException;


    public void storeVoterBelongsToElectoralDistrict( Voter voter, ElectoralDistrict electoralDistrict ) throws EVException;


    public ElectoralDistrict restoreVoterBelongsToElectoralDistrict( Voter voter ) throws EVException;


    public List<Voter> restoreVoterBelongsToElectoralDistrict( ElectoralDistrict electoralDistrict ) throws EVException;


    public void deleteVoterBelongsToElection( Voter voter, ElectoralDistrict electoralDistrict ) throws EVException;
}
