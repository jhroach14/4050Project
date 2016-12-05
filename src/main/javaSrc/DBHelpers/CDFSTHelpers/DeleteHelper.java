package main.javaSrc.DBHelpers.CDFSTHelpers;

import main.javaSrc.DBHelpers.DbConnHelper;
import main.javaSrc.Entities.*;
import main.javaSrc.Entities.EntityImpl.*;
import main.javaSrc.HttpClasses.DBExchange;
import main.javaSrc.helpers.EVException;
import main.javaSrc.helpers.Logger;
import org.codehaus.jackson.map.ObjectMapper;

import java.io.IOException;
import java.util.List;

/**
 * Created by User on 10/30/2016.
 */
@SuppressWarnings("Duplicates")
public class DeleteHelper extends CDFSTHelper {
    private static Logger log = new Logger(DeleteHelper.class);

    public DeleteHelper(DBExchange dbExchange, DbConnHelper dbConnHelper) {
        super(dbExchange,dbConnHelper);
    }

    public List<Entity> execute(){

        String objectType = dbExchange.getDBRequestObjectType();
        String sourced = dbExchange.getParam("sourced");
        ObjectMapper mapper = new ObjectMapper();

        try{
            switch (objectType){

                case "Ballot":
                    if (sourced.equals("true")) {
                        entity = mapper.readValue(dbExchange.getRequestBody(), BallotImpl.class);
                        log.out("attempting to delete Ballot with id "+entity.getId());
                        objectLayer.deleteLink((Ballot) entity);
                        objectLayer.deleteBallot((Ballot) entity);
                    }
                    break;
                case "Candidate":
                    if (sourced.equals("true")) {
                        entity = mapper.readValue(dbExchange.getRequestBody(), CandidateImpl.class);
                        log.out("attempting to delete Candidate with id "+entity.getId());
                        objectLayer.deleteLink((Candidate) entity);
                        objectLayer.deleteCandidate((Candidate) entity);
                    }
                    break;
                case "Election":
                    if (sourced.equals("true")) {
                        entity = mapper.readValue(dbExchange.getRequestBody(), ElectionImpl.class);
                        log.out("attempting to delete Election with id "+entity.getId());
                        objectLayer.deleteLink((Election) entity);
                        objectLayer.deleteElection((Election) entity);
                    }
                    break;
                case "ElectionOfficer":
                    if (sourced.equals("true")) {
                        entity = mapper.readValue(dbExchange.getRequestBody(), ElectionsOfficerImpl.class);
                        log.out("attempting to delete ElectionOfficer with id "+entity.getId());
                        objectLayer.deleteElectionsOfficer((ElectionsOfficer) entity);
                    }
                    break;
                case "ElectoralDistrict":
                    if (sourced.equals("true")) {
                        entity = mapper.readValue(dbExchange.getRequestBody(), ElectoralDistrictImpl.class);
                        log.out("attempting to delete ElectoralDistrict with id "+entity.getId());
                        objectLayer.deleteLink((ElectoralDistrict)entity);
                        objectLayer.deleteElectoralDistrict((ElectoralDistrict) entity);
                    }
                    break;
                case "Issue":
                    if (sourced.equals("true")) {
                        entity = mapper.readValue(dbExchange.getRequestBody(), IssueImpl.class);
                        log.out("attempting to delete Issue with id "+entity.getId());
                        objectLayer.deleteLink((Issue) entity);
                        objectLayer.deleteIssue((Issue) entity);
                    }
                    break;
                case "PoliticalParty":
                    if (sourced.equals("true")) {
                        entity = mapper.readValue(dbExchange.getRequestBody(), PoliticalPartyImpl.class);
                        log.out("attempting to delete PoliticalParty with id "+entity.getId());
                        objectLayer.deleteLink((PoliticalParty)entity);
                        objectLayer.deletePoliticalParty((PoliticalParty) entity);
                    }
                    break;
                case "Voter":
                    if (sourced.equals("true")) {
                        entity = mapper.readValue(dbExchange.getRequestBody(), VoterImpl.class);
                        log.out("attempting to delete Voter with id "+entity.getId());
                        objectLayer.deleteLink((Voter)entity);
                        objectLayer.deleteVoter((Voter) entity);
                    }
                    break;
                case "Ballot_Election":
                    if(sourced.equals("true")){
                        String[] ballot_ballotItem = parseObjects(dbExchange.getRequestBody());
                        Ballot ballot = mapper.readValue(ballot_ballotItem[0],BallotImpl.class);
                        BallotItem ballotItem = mapper.readValue(ballot_ballotItem[1],ElectionImpl.class);
                        log.out("attempting to delete link between Ballot with id "+ballot.getId()+" and Election with id "+ballotItem.getId());
                        objectLayer.deleteLink(ballot,ballotItem);
                    }
                    break;
                case "Ballot_Issue":
                    if(sourced.equals("true")){
                        String[] ballot_ballotItem = parseObjects(dbExchange.getRequestBody());
                        Ballot ballot = mapper.readValue(ballot_ballotItem[0],BallotImpl.class);
                        BallotItem ballotItem = mapper.readValue(ballot_ballotItem[1],IssueImpl.class);
                        log.out("attempting to delete link between Ballot with id "+ballot.getId()+" and Issue with id "+ballotItem.getId());
                        objectLayer.deleteLink(ballot,ballotItem);
                    }
                    break;
                case "Election_Candidate":
                    if(sourced.equals("true")){
                        String[] election_candidate = parseObjects(dbExchange.getRequestBody());
                        Election election = mapper.readValue(election_candidate[0],ElectionImpl.class);
                        Candidate candidate = mapper.readValue(election_candidate[1],CandidateImpl.class);
                        log.out("attempting to delete link between Election with id "+election.getId()+" and Candidate with id "+candidate.getId());
                        objectLayer.deleteLink(candidate,election);
                    }
                    break;
                case "District_Ballot":
                    if(sourced.equals("true")){
                        String[] district_ballots = parseObjects(dbExchange.getRequestBody());
                        ElectoralDistrict electoralDistrict = mapper.readValue(district_ballots[0],ElectoralDistrictImpl.class);
                        Ballot ballot = mapper.readValue(district_ballots[1],BallotImpl.class);
                        log.out("attempting to delete link between District with id "+electoralDistrict.getId()+" and Ballot with id "+ballot.getId());
                        objectLayer.deleteLink(electoralDistrict,ballot);
                    }
                    break;
                case "District_Voter":
                    if(sourced.equals("true")){
                        String[] district_ballots = parseObjects(dbExchange.getRequestBody());
                        ElectoralDistrict electoralDistrict = mapper.readValue(district_ballots[0],ElectoralDistrictImpl.class);
                        Voter voter = mapper.readValue(district_ballots[1],VoterImpl.class);
                        log.out("attempting to delete link between District with id "+electoralDistrict.getId()+" and Voter with id "+voter.getId());
                        objectLayer.deleteLink(electoralDistrict,voter);
                    }
                    break;
                case "Party_Candidate":
                    if(sourced.equals("true")){
                        String[] party_candidate = parseObjects(dbExchange.getRequestBody());
                        PoliticalParty party = mapper.readValue(party_candidate[0],PoliticalPartyImpl.class);
                        Candidate candidate = mapper.readValue(party_candidate[1],CandidateImpl.class);
                        log.out("attempting to delete link between Party with id "+party.getId()+" and Candidate with id"+candidate.getId());
                        objectLayer.deleteLink(party,candidate);
                    }
                    break;
                case "VoterRecord":
                    if(sourced.equals("true")){
                        entity = mapper.readValue(dbExchange.getRequestBody(), VoterRecordImpl.class);
                        log.out("attempting to delete VoterRecord with id "+entity.getId());
                        objectLayer.deleteVoteRecord((VoteRecord) entity);
                    }
                    break;
                default:
                    log.error("Unsupported object type "+objectType);
                    break;
            }
        }catch (IOException e){
            e.printStackTrace();
        }catch (EVException e){
            e.printStackTrace();
        }
        dbConnHelper.commit(connection);
        entities.add(entity);
        return entities;
    }
}
