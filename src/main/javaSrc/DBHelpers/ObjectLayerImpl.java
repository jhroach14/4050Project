package main.javaSrc.DBHelpers;

import java.sql.Date;
import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import main.javaSrc.Entities.*;
import main.javaSrc.Entities.EntityImpl.*;
import main.javaSrc.helpers.EVException;
import main.javaSrc.helpers.Logger;

public class ObjectLayerImpl implements ObjectLayer {

    private static Logger log = new Logger( ObjectLayerImpl.class);

    public PersistenceLayer getPersistenceLayer() {
        return persistenceLayer;
    }

    public void setPersistenceLayer(PersistenceLayer persistenceLayer) {
        this.persistenceLayer = persistenceLayer;
    }

    private PersistenceLayer persistenceLayer;


    public ObjectLayerImpl(PersistenceLayer persistenceLayer) {

        this.persistenceLayer = persistenceLayer;
    }

    public ObjectLayerImpl(){
        this.persistenceLayer=null;
    }

    @Override
    public ElectionsOfficer createElectionsOfficer(String firstName, String lastName, String userName, String password, String emailAddress, String address, String state, int zip, String city) throws EVException {

        ElectionsOfficer officer = new ElectionsOfficerImpl(firstName,lastName,userName,password,emailAddress,address,state,zip,city);
        return officer;
    }

    @Override
    public ElectionsOfficer createElectionsOfficer() {

        return new ElectionsOfficerImpl();
    }

    @Override
    public List<ElectionsOfficer> findElectionsOfficer(ElectionsOfficer modelElectionsOfficer) throws EVException {

        persistenceLayer.restoreElectionsOfficer(modelElectionsOfficer);
        return null;
    }

    @Override
    public void storeElectionsOfficer(ElectionsOfficer electionsOfficer) throws EVException {

        persistenceLayer.storeElectionsOfficer(electionsOfficer);
    }

    @Override
    public void deleteElectionsOfficer(ElectionsOfficer electionsOfficer) throws EVException {

        persistenceLayer.deleteElectionsOfficer(electionsOfficer);
    }

    @Override
    public Voter createVoter(String firstName, String lastName, String userName, String password, String emailAddress, String address, int age,String state, int zip, String city) throws EVException {
        Voter voter = new VoterImpl(firstName,lastName,userName,password,emailAddress,address,age,state,zip,city);
        return voter;
    }

    @Override
    public Voter createVoter() {
        return new VoterImpl();
    }

    @Override
    public List<Voter> findVoter(Voter modelVoter) throws EVException {
        persistenceLayer.restoreVoter(modelVoter);
        return null;
    }

    @Override
    public void storeVoter(Voter voter) throws EVException {
        persistenceLayer.storeVoter(voter);
    }

    @Override
    public void deleteVoter(Voter voter) throws EVException {
        persistenceLayer.deleteVoter(voter);
    }

    @Override
    public PoliticalParty createPoliticalParty(String name) throws EVException {
        PoliticalParty party = new PoliticalPartyImpl(name);
        return party;
    }

    @Override
    public PoliticalParty createPoliticalParty() {
        return new PoliticalPartyImpl();
    }

    @Override
    public List<PoliticalParty> findPoliticalParty(PoliticalParty modelPoliticalParty) throws EVException {
        persistenceLayer.restorePoliticalParty(modelPoliticalParty);
        return null;
    }

    @Override
    public void storePoliticalParty(PoliticalParty politicalParty) throws EVException {
        persistenceLayer.storePoliticalParty(politicalParty);
    }

    @Override
    public void deletePoliticalParty(PoliticalParty politicalParty) throws EVException {
        persistenceLayer.deletePoliticalParty(politicalParty);
    }

    @Override
    public ElectoralDistrict createElectoralDistrict(String name) throws EVException {
        ElectoralDistrict district = new ElectoralDistrictImpl(name);
        return district;
    }

    @Override
    public ElectoralDistrict createElectoralDistrict() {
        return new ElectoralDistrictImpl();
    }

    @Override
    public List<ElectoralDistrict> findElectoralDistrict(ElectoralDistrict modelElectoralDistrict) throws EVException {
        persistenceLayer.restoreElectoralDistrict(modelElectoralDistrict);
        return null;
    }

    @Override
    public void storeElectoralDistrict(ElectoralDistrict electoralDistrict) throws EVException {
        persistenceLayer.storeElectoralDistrict(electoralDistrict);
    }

    @Override
    public void deleteElectoralDistrict(ElectoralDistrict electoralDistrict) throws EVException {
        persistenceLayer.deleteElectoralDistrict(electoralDistrict);
    }

    @Override
    public Ballot createBallot(Date openDate, Date closeDate, boolean approved, ElectoralDistrict electoralDistrict) throws EVException {
        Ballot ballot = new BallotImpl(openDate, closeDate, approved, electoralDistrict);
        return ballot;
    }

    @Override
    public Ballot createBallot() {
        return new BallotImpl();
    }

    @Override
    public List<Ballot> findBallot(Ballot modelBallot) throws EVException {
        persistenceLayer.restoreBallot(modelBallot);
        return null;
    }

    @Override
    public void storeBallot(Ballot ballot) throws EVException {
        persistenceLayer.storeBallot(ballot);
    }

    @Override
    public void deleteBallot(Ballot ballot) throws EVException {
        persistenceLayer.deleteBallot(ballot);
    }

    @Override
    public Candidate createCandidate(String name, PoliticalParty politicalParty, Election election) throws EVException {
        Candidate candidate = new CandidateImpl(name, politicalParty, election);
        return candidate;
    }

    @Override
    public Candidate createCandidate() {
        return new CandidateImpl();
    }

    @Override
    public List<Candidate> findCandidate(Candidate modelCandidate) throws EVException {
        persistenceLayer.restoreCandidate(modelCandidate);
        return null;
    }

    @Override
    public void storeCandidate(Candidate candidate) throws EVException {
        persistenceLayer.storeCandidate(candidate);
    }

    @Override
    public void deleteCandidate(Candidate candidate) throws EVException {
        persistenceLayer.deleteCandidate(candidate);
    }

    @Override
    public Issue createIssue(String question) throws EVException {
        Issue issue = new IssueImpl(question);
        return issue;
    }

    @Override
    public Issue createIssue() {
        return new IssueImpl();
    }

    @Override
    public List<Issue> findIssue(Issue modelIssue) throws EVException {
        persistenceLayer.restoreIssue(modelIssue);
        return null;
    }

    @Override
    public void storeIssue(Issue issue) throws EVException {
        persistenceLayer.storeIssue(issue);
    }

    @Override
    public void deleteIssue(Issue issue) throws EVException {
        persistenceLayer.deleteIssue(issue);
    }

    @Override
    public Election createElection(String office, boolean isPartisan) throws EVException {
        Election election = new ElectionImpl(office, isPartisan,null);
        return election;
    }

    @Override
    public Election createElection() {
        return new ElectionImpl();
    }

    @Override
    public List<Election> findElection(Election modelElection) throws EVException {
        persistenceLayer.restoreElection(modelElection);
        return null;
    }

    @Override
    public void storeElection(Election election) throws EVException {
        persistenceLayer.storeElection(election);
    }

    @Override
    public void deleteElection(Election election) throws EVException {
        persistenceLayer.deleteElection(election);
    }

    @Override
    public VoteRecord createVoteRecord(Ballot ballot, Voter voter, Date date) throws EVException {
        VoteRecord record = new VoterRecordImpl( date, voter, ballot);
        return record;
    }

    @Override
    public VoteRecord createVoteRecord() {
        return new VoterRecordImpl();
    }

    @Override
    public List<VoteRecord> findVoteRecord(VoteRecord modelVoteRecord) throws EVException {
        persistenceLayer.restoreVoteRecord(modelVoteRecord);
        return null;
    }

    @Override
    public void storeVoteRecord(VoteRecord voteRecord) throws EVException {
        persistenceLayer.storeVoteRecord(voteRecord);
    }

    @Override
    public void deleteVoteRecord(VoteRecord voteRecord) throws EVException {
        persistenceLayer.deleteVoteRecord(voteRecord);
    }
}
