package main.javaSrc.DBHelpers;

import java.sql.Date;
import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import main.javaSrc.Entities.*;
import main.javaSrc.Entities.EntityImpl.ElectionsOfficerImpl;
import main.javaSrc.helpers.EVException;
import main.javaSrc.helpers.Logger;

public class ObjectLayerImpl implements ObjectLayer {

    private static Logger log = new Logger( ObjectLayerImpl.class);

    PersistenceLayer persistenceLayer;


    public ObjectLayerImpl(PersistenceLayer persistenceLayer) {

        this.persistenceLayer = persistenceLayer;
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
    public Voter createVoter(String firstName, String lastName, String userName, String password, String emailAddress, String address, int age) throws EVException {
        return null;
    }

    @Override
    public Voter createVoter() {
        return null;
    }

    @Override
    public List<Voter> findVoter(Voter modelVoter) throws EVException {
        return null;
    }

    @Override
    public void storeVoter(Voter voter) throws EVException {

    }

    @Override
    public void deleteVoter(Voter voter) throws EVException {

    }

    @Override
    public PoliticalParty createPoliticalParty(String name) throws EVException {
        return null;
    }

    @Override
    public PoliticalParty createPoliticalParty() {
        return null;
    }

    @Override
    public List<PoliticalParty> findPoliticalParty(PoliticalParty modelPoliticalParty) throws EVException {
        return null;
    }

    @Override
    public void storePoliticalParty(PoliticalParty politicalParty) throws EVException {

    }

    @Override
    public void deletePoliticalParty(PoliticalParty politicalParty) throws EVException {

    }

    @Override
    public ElectoralDistrict createElectoralDistrict(String name) throws EVException {
        return null;
    }

    @Override
    public ElectoralDistrict createElectoralDistrict() {
        return null;
    }

    @Override
    public List<ElectoralDistrict> findElectoralDistrict(ElectoralDistrict modelElectoralDistrict) throws EVException {
        return null;
    }

    @Override
    public void storeElectoralDistrict(ElectoralDistrict electoralDistrict) throws EVException {

    }

    @Override
    public void deleteElectoralDistrict(ElectoralDistrict electoralDistrict) throws EVException {

    }

    @Override
    public Ballot createBallot(Date openDate, Date closeDate, boolean approved, ElectoralDistrict electoralDistrict) throws EVException {
        return null;
    }

    @Override
    public Ballot createBallot() {
        return null;
    }

    @Override
    public List<Ballot> findBallot(Ballot modelBallot) throws EVException {
        return null;
    }

    @Override
    public void storeBallot(Ballot ballot) throws EVException {

    }

    @Override
    public void deleteBallot(Ballot ballot) throws EVException {

    }

    @Override
    public Candidate createCandidate(String name, PoliticalParty politicalParty, Election election) throws EVException {
        return null;
    }

    @Override
    public Candidate createCandidate() {
        return null;
    }

    @Override
    public List<Candidate> findCandidate(Candidate modelCandidate) throws EVException {
        return null;
    }

    @Override
    public void storeCandidate(Candidate candidate) throws EVException {

    }

    @Override
    public void deleteCandidate(Candidate candidate) throws EVException {

    }

    @Override
    public Issue createIssue(String question) throws EVException {
        return null;
    }

    @Override
    public Issue createIssue() {
        return null;
    }

    @Override
    public List<Issue> findIssue(Issue modelIssue) throws EVException {
        return null;
    }

    @Override
    public void storeIssue(Issue issue) throws EVException {

    }

    @Override
    public void deleteIssue(Issue issue) throws EVException {

    }

    @Override
    public Election createElection(String office, boolean isPartisan) throws EVException {
        return null;
    }

    @Override
    public Election createElection() {
        return null;
    }

    @Override
    public List<Election> findElection(Election modelElection) throws EVException {
        return null;
    }

    @Override
    public void storeElection(Election election) throws EVException {

    }

    @Override
    public void deleteElection(Election election) throws EVException {

    }

    @Override
    public VoteRecord createVoteRecord(Ballot ballot, Voter voter, Date date) throws EVException {
        return null;
    }

    @Override
    public VoteRecord createVoteRecord() {
        return null;
    }

    @Override
    public List<VoteRecord> findVoteRecord(VoteRecord modelVoteRecord) throws EVException {
        return null;
    }

    @Override
    public void storeVoteRecord(VoteRecord voteRecord) throws EVException {

    }

    @Override
    public void deleteVoteRecord(VoteRecord voteRecord) throws EVException {

    }
}
