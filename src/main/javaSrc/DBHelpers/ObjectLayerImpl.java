package main.javaSrc.DBHelpers;

import java.sql.Date;
import java.util.List;

import main.javaSrc.Entities.*;
import main.javaSrc.Entities.EntityImpl.*;
import main.javaSrc.helpers.EVException;
import main.javaSrc.helpers.Logger;

public class ObjectLayerImpl implements ObjectLayer {

    private static Logger log = new Logger( ObjectLayerImpl.class);

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

        return persistenceLayer.restoreElectionsOfficer(modelElectionsOfficer);
    }

    @Override
    public ElectionsOfficer storeElectionsOfficer(ElectionsOfficer electionsOfficer) throws EVException {

       return persistenceLayer.storeElectionsOfficer(electionsOfficer);
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
        return persistenceLayer.restoreVoter(modelVoter);
    }

    @Override
    public Voter storeVoter(Voter voter) throws EVException {
        return persistenceLayer.storeVoter(voter);
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
        return persistenceLayer.restorePoliticalParty(modelPoliticalParty);
    }

    @Override
    public PoliticalParty storePoliticalParty(PoliticalParty politicalParty) throws EVException {
        return persistenceLayer.storePoliticalParty(politicalParty);
    }

    @Override
    public void deletePoliticalParty(PoliticalParty politicalParty) throws EVException {
        persistenceLayer.deletePoliticalParty(politicalParty);
    }

    @Override
    public ElectoralDistrict createElectoralDistrict(String name, int zip) throws EVException {
        ElectoralDistrict district = new ElectoralDistrictImpl(name, zip);
        return district;
    }

    @Override
    public ElectoralDistrict createElectoralDistrict() {
        return new ElectoralDistrictImpl();
    }

    @Override
    public List<ElectoralDistrict> findElectoralDistrict(ElectoralDistrict modelElectoralDistrict) throws EVException {
        return persistenceLayer.restoreElectoralDistrict(modelElectoralDistrict);
    }

    @Override
    public ElectoralDistrict storeElectoralDistrict(ElectoralDistrict electoralDistrict) throws EVException {
        boolean isNew = electoralDistrict.isPersistent();
        ElectoralDistrict district = persistenceLayer.storeElectoralDistrict(electoralDistrict);
        if(!isNew){
            Ballot ballot = persistenceLayer.storeBallot(new BallotImpl(new Date(1,1,1),new Date(1,1,2),true));
            persistenceLayer.storeElectoralDistrictHasBallotBallot(district,ballot);
        }

        return district;
    }

    @Override
    public void deleteElectoralDistrict(ElectoralDistrict electoralDistrict) throws EVException {
        persistenceLayer.deleteElectoralDistrict(electoralDistrict);
    }

    @Override
    public Ballot createBallot(Date openDate, Date closeDate, boolean approved) throws EVException {
        Ballot ballot = new BallotImpl(openDate, closeDate, approved);
        return ballot;
    }

    @Override
    public Ballot createBallot() {
        return new BallotImpl();
    }

    @Override
    public List<Ballot> findBallot(Ballot modelBallot) throws EVException {

        return persistenceLayer.restoreBallot(modelBallot);
    }

    @Override
    public Ballot storeBallot(Ballot ballot) throws EVException {
        return persistenceLayer.storeBallot(ballot);
    }

    @Override
    public void deleteBallot(Ballot ballot) throws EVException {
        persistenceLayer.deleteBallot(ballot);
    }

    @Override
    public Candidate createCandidate(String name) throws EVException {
        Candidate candidate = new CandidateImpl(name);
        return candidate;
    }

    @Override
    public Candidate createCandidate() {
        return new CandidateImpl();
    }

    @Override
    public List<Candidate> findCandidate(Candidate modelCandidate) throws EVException {
        return persistenceLayer.restoreCandidate(modelCandidate);
    }

    @Override
    public Candidate storeCandidate(Candidate candidate) throws EVException {
        return persistenceLayer.storeCandidate(candidate);
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
        return persistenceLayer.restoreIssue(modelIssue);
    }

    @Override
    public Issue storeIssue(Issue issue) throws EVException {
        return persistenceLayer.storeIssue(issue);
    }

    @Override
    public void deleteIssue(Issue issue) throws EVException {
        persistenceLayer.deleteIssue(issue);
    }

    @Override
    public void deleteLink(Issue issue) throws EVException {
        persistenceLayer.deleteIssueFromAllAssociations(issue);
    }
    @Override
    public Election createElection(String office, boolean isPartisan) throws EVException {
        Election election = new ElectionImpl(office, isPartisan);
        return election;
    }

    @Override
    public Election createElection() {
        return new ElectionImpl();
    }

    @Override
    public List<Election> findElection(Election modelElection) throws EVException {
        return persistenceLayer.restoreElection(modelElection);
    }

    @Override
    public Election storeElection(Election election) throws EVException {
        return persistenceLayer.storeElection(election);
    }

    @Override
    public void deleteElection(Election election) throws EVException {
        persistenceLayer.deleteElection(election);
    }

    @Override
    public void deleteLink(Election election) throws EVException {
        persistenceLayer.deleteElectionFromAllAssociations(election);
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
        return persistenceLayer.restoreVoteRecord(modelVoteRecord);
    }

    @Override
    public VoteRecord storeVoteRecord(VoteRecord voteRecord) throws EVException {
        return persistenceLayer.storeVoteRecord(voteRecord);
    }

    @Override
    public void deleteVoteRecord(VoteRecord voteRecord) throws EVException {
        persistenceLayer.deleteVoteRecord(voteRecord);
    }

    @Override
    public void createLink(Ballot ballot, BallotItem ballotItem) throws EVException {
        persistenceLayer.storeBallotIncludesBallotItem(ballot,ballotItem);
    }

    @Override
    public Ballot getBallot(BallotItem ballotItem) throws EVException {
        return persistenceLayer.restoreBallotIncludesBallotItem(ballotItem);
    }

    @Override
    public List<BallotItem> getBallotItems(Ballot ballot) throws EVException {
        return persistenceLayer.restoreBallotIncludesBallotItem(ballot);
    }

    @Override
    public void deleteLink(Ballot ballot, BallotItem ballotItem) throws EVException {
        persistenceLayer.deleteBallotIncludesBallotItem(ballot,ballotItem);
    }

    @Override
    public void deleteLink(Ballot ballot) throws EVException {
        persistenceLayer.deleteBallotFromAllAssociations(ballot);
    }

    @Override
    public void createLink(Candidate candidate, Election election) throws EVException {
        persistenceLayer.deleteCandidateIsCandidateInElection(candidate);
        persistenceLayer.storeCandidateIsCandidateInElection(candidate,election);
    }

    @Override
    public Election getElection(Candidate candidate) throws EVException {
        return persistenceLayer.restoreCandidateIsCandidateInElection(candidate);
    }

    @Override
    public List<Candidate> getCandidates(Election election) throws EVException {
        return persistenceLayer.restoreCandidateIsCandidateInElection(election);
    }

    @Override
    public void deleteLink(Candidate candidate, Election election) throws EVException {
        persistenceLayer.deleteCandidateIsCandidateInElection(candidate, election);
    }

    @Override
    public void deleteLink(ElectoralDistrict electoralDistrict, Ballot ballot) throws EVException {
        persistenceLayer.deleteElectoralDistrictHasBallotBallot(electoralDistrict,ballot);
    }

    @Override
    public Ballot getBallot(ElectoralDistrict district) throws EVException {
        return persistenceLayer.restoreElectoralDistrictHasBallotBallot(district);
    }

    @Override
    public ElectoralDistrict getDistrict(Ballot ballot) throws EVException {
        return persistenceLayer.restoreElectoralDistrictHasBallotBallot(ballot);
    }

    @Override
    public void deleteLink(ElectoralDistrict electoralDistrict, Voter voter) throws EVException {
        persistenceLayer.deleteVoterBelongsToElection(voter,electoralDistrict);
    }

    @Override
    public void deleteLink(PoliticalParty party, Candidate candidate) throws EVException {
        persistenceLayer.deleteCandidateIsMemberOfPoliticalParty(candidate,party);
    }

    @Override
    public List<Candidate> getCandidates(PoliticalParty politicalParty) throws EVException {
        return persistenceLayer.restoreCandidateIsMemberOfPoliticalParty(politicalParty);
    }

    @Override
    public PoliticalParty getParty(Candidate candidate) throws EVException {
        return persistenceLayer.restoreCandidateIsMemberOfPoliticalParty(candidate);
    }

    @Override
    public void createLink(ElectoralDistrict electoralDistrict, Ballot ballot) throws EVException {
        persistenceLayer.storeElectoralDistrictHasBallotBallot(electoralDistrict,ballot);
    }

    @Override
    public void createLink(ElectoralDistrict electoralDistrict, Voter voter) throws EVException {
        persistenceLayer.storeVoterBelongsToElectoralDistrict(voter,electoralDistrict);
    }

    @Override
    public List<Voter> getVoters(ElectoralDistrict district) throws EVException {
        return persistenceLayer.restoreVoterBelongsToElectoralDistrict(district);
    }

    @Override
    public ElectoralDistrict getDistrict(Voter voter) throws EVException {
        return persistenceLayer.restoreVoterBelongsToElectoralDistrict(voter);
    }

    @Override
    public void createLink(PoliticalParty party, Candidate candidate) throws EVException {
        persistenceLayer.storeCandidateIsMemberOfPoliticalParty(candidate, party);
    }

    @Override
    public User getUser(Token token) throws EVException {
        return persistenceLayer.restoreUserGivenToken(token);
    }

    @Override
    public Token createToken(String tokenValue) throws EVException {
        return new TokenImpl(tokenValue);
    }

    @Override
    public Token createToken() throws EVException {
        return new TokenImpl();
    }

    @Override
    public VoteRecord createVoterRecord() {
        return new VoterRecordImpl();
    }

    @Override
    public VoteRecord createVoterRecord(Date date, Voter voter, Ballot ballot) {
        VoteRecord voteRecord = new VoterRecordImpl(date,voter,ballot);
        return voteRecord;
    }

    @Override
    public void deleteLink(Candidate candidate) throws EVException {
        persistenceLayer.deleteCandidateFromAllAssociations(candidate);
    }

    @Override
    public void deleteLink(ElectoralDistrict district) throws EVException {
        persistenceLayer.deleteElectoralDistrictFromAllAssociations(district);
    }

    @Override
    public void deleteLink(PoliticalParty party) throws EVException{
        persistenceLayer.deletePoliticalPartyFormAllAssociations(party);
    }

    @Override
    public void deleteLink(Voter voter) throws EVException {
        persistenceLayer.deleteVoterFromAllAssociations(voter);
    }

    @Override
    public void deleteLinkParty(Candidate candidate) throws EVException {
        persistenceLayer.deleteCandidateFromParties(candidate);
    }

    @Override
    public void storeSysState(String open) throws EVException{
        persistenceLayer.storeSysState(open);
    }

    @Override
    public String findSysOpen() throws EVException {
        return persistenceLayer.restoreSysOpen();
    }

    @Override
    public List<Ballot> getBallots(BallotItem ballotItem) throws EVException {
        return persistenceLayer.restoreBallotsIncludesBallotItem(ballotItem);
    }

    public void setPersistenceLayer(PersistenceLayer persistenceLayer) {
        this.persistenceLayer = persistenceLayer;
    }

    public PersistenceLayer getPersistenceLayer() {
        return persistenceLayer;
    }

}
