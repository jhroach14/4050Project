package main.javaSrc.DBHelpers;

import main.javaSrc.Entities.*;
import main.javaSrc.helpers.EVException;

import java.sql.Date;
import java.util.List;


public interface ObjectLayer {

    List<Ballot> getBallots(BallotItem ballotItem) throws EVException;

    void setPersistenceLayer(PersistenceLayer persistenceLayer);
    
    public ElectionsOfficer createElectionsOfficer(String firstName, String lastName, String userName,
                                                   String password, String emailAddress, String address,String state, int zip, String city ) throws EVException;

    
    public ElectionsOfficer createElectionsOfficer();

    
    public List<ElectionsOfficer> findElectionsOfficer(ElectionsOfficer modelElectionsOfficer ) throws EVException;

    
    public ElectionsOfficer storeElectionsOfficer(ElectionsOfficer electionsOfficer ) throws EVException;

    
    public void deleteElectionsOfficer( ElectionsOfficer electionsOfficer ) throws EVException;

    
    public Voter createVoter(String firstName, String lastName, String userName, String password,
                             String emailAddress, String address, int age ,String state, int zip, String city) throws EVException;

    
    public Voter createVoter();

    
    public List<Voter> findVoter( Voter modelVoter ) throws EVException;

    
    public Voter storeVoter(Voter voter ) throws EVException;

    
    public void deleteVoter( Voter voter ) throws EVException;

    
    public PoliticalParty createPoliticalParty(String name ) throws EVException;

    
    public PoliticalParty createPoliticalParty();

    
    public List<PoliticalParty> findPoliticalParty( PoliticalParty modelPoliticalParty ) throws EVException;

    
    public PoliticalParty storePoliticalParty(PoliticalParty politicalParty ) throws EVException;

    
    public void deletePoliticalParty( PoliticalParty politicalParty ) throws EVException;

    
    public ElectoralDistrict createElectoralDistrict(String name , int zip) throws EVException;

    
    public ElectoralDistrict createElectoralDistrict();

    
    public List<ElectoralDistrict> findElectoralDistrict( ElectoralDistrict modelElectoralDistrict ) throws EVException;

    
    public ElectoralDistrict storeElectoralDistrict(ElectoralDistrict electoralDistrict ) throws EVException;

    
    public void deleteElectoralDistrict( ElectoralDistrict electoralDistrict ) throws EVException;

    
    public Ballot createBallot(Date openDate, Date closeDate, boolean approved) throws EVException;

    
    public Ballot createBallot();

    
    public List<Ballot> findBallot( Ballot modelBallot ) throws EVException;

    
    public Ballot storeBallot(Ballot ballot ) throws EVException;

    
    public void deleteBallot( Ballot ballot ) throws EVException;

    
    public Candidate createCandidate( String name) throws EVException;

    
    public Candidate createCandidate();

    
    public List<Candidate> findCandidate( Candidate modelCandidate ) throws EVException;

    
    public Candidate storeCandidate(Candidate candidate ) throws EVException;

    
    public void deleteCandidate( Candidate candidate ) throws EVException;

    
    public Issue createIssue( String question ) throws EVException;

    
    public Issue createIssue();

    
    public List<Issue> findIssue( Issue modelIssue ) throws EVException;

    
    public Issue storeIssue(Issue issue ) throws EVException;

    
    public void deleteIssue( Issue issue ) throws EVException;

    
    public Election createElection( String office, boolean isPartisan ) throws EVException;

    
    public Election createElection();

    
    public List<Election> findElection( Election modelElection ) throws EVException;

    
    public Election storeElection(Election election ) throws EVException;

    
    public void deleteElection( Election election ) throws EVException;

    
    public VoteRecord createVoteRecord( Ballot ballot, Voter voter, Date date ) throws EVException;

    
    public VoteRecord createVoteRecord();

    
    public List<VoteRecord> findVoteRecord( VoteRecord modelVoteRecord ) throws EVException;

    
    public VoteRecord storeVoteRecord(VoteRecord voteRecord ) throws EVException;

    
    public void deleteVoteRecord( VoteRecord voteRecord ) throws EVException;

    public void createLink(Ballot ballot,BallotItem ballotItem) throws EVException;

    public Ballot getBallot(BallotItem ballotItem) throws EVException;

    public List<BallotItem> getBallotItems(Ballot ballot)throws EVException;

    public void deleteLink(Ballot ballot, BallotItem ballotItem)throws EVException;

    public void deleteLink(Ballot ballot)throws EVException;

    public void deleteLink(Issue issue) throws EVException;

    public void deleteLink(Election election) throws EVException;

    public void createLink(Candidate candidate,Election election) throws EVException;

    public Election getElection(Candidate candidate) throws EVException;

    public List<Candidate> getCandidates(Election election)throws EVException;

    public void deleteLink(Candidate candidate, Election election)throws EVException;

    void createLink(ElectoralDistrict electoralDistrict, Ballot ballot) throws EVException;
    void deleteLink(ElectoralDistrict electoralDistrict, Ballot ballot) throws EVException;
    Ballot getBallot(ElectoralDistrict district)throws EVException;
    ElectoralDistrict getDistrict(Ballot ballot)throws EVException;

    void createLink(ElectoralDistrict electoralDistrict, Voter voter) throws EVException;
    List<Voter> getVoters(ElectoralDistrict district)throws EVException;
    ElectoralDistrict getDistrict(Voter voter)throws EVException;
    void deleteLink(ElectoralDistrict electoralDistrict, Voter voter) throws EVException;

    void deleteLink(PoliticalParty party, Candidate candidate) throws EVException;
    List<Candidate> getCandidates(PoliticalParty politicalParty) throws EVException;
    PoliticalParty getParty(Candidate candidate) throws EVException;
    void createLink(PoliticalParty party, Candidate candidate) throws EVException;

    public User getUser(Token token) throws EVException;

    public Token createToken(String tokenValue) throws EVException;

    public Token createToken() throws EVException;

    VoteRecord createVoterRecord();

    VoteRecord createVoterRecord(Date date, Voter voter, Ballot ballot);

    void deleteLink(Candidate entity) throws EVException;

    void deleteLink(ElectoralDistrict entity) throws EVException;

    void deleteLink(PoliticalParty entity) throws EVException;

    void deleteLink(Voter entity)throws EVException;

    void deleteLinkParty(Candidate candidate) throws EVException;

    void storeSysState(String open) throws EVException;

    String findSysOpen() throws EVException;
}


