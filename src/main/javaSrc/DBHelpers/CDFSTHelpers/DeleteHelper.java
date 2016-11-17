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
                        objectLayer.deleteLink((Ballot) entity);
                        objectLayer.deleteBallot((Ballot) entity);
                    }
                    break;
                case "Candidate":
                    if (sourced.equals("true")) {
                        entity = mapper.readValue(dbExchange.getRequestBody(), CandidateImpl.class);
                        objectLayer.deleteCandidate((Candidate) entity);
                    }
                    break;
                case "Election":
                    if (sourced.equals("true")) {
                        entity = mapper.readValue(dbExchange.getRequestBody(), ElectionImpl.class);
                        objectLayer.deleteLink((Election) entity);
                        objectLayer.deleteElection((Election) entity);
                    }
                    break;
                case "ElectionOfficer":
                    if (sourced.equals("true")) {
                        entity = mapper.readValue(dbExchange.getRequestBody(), ElectionsOfficerImpl.class);
                        objectLayer.deleteElectionsOfficer((ElectionsOfficer) entity);
                    }
                    break;
                case "ElectoralDistrict":
                    if (sourced.equals("true")) {
                        entity = mapper.readValue(dbExchange.getRequestBody(), ElectoralDistrictImpl.class);
                        objectLayer.deleteElectoralDistrict((ElectoralDistrict) entity);
                    }
                    break;
                case "Issue":
                    if (sourced.equals("true")) {
                        entity = mapper.readValue(dbExchange.getRequestBody(), IssueImpl.class);
                        objectLayer.deleteLink((Issue) entity);
                        objectLayer.deleteIssue((Issue) entity);
                    }
                    break;
                case "PoliticalParty":
                    if (sourced.equals("true")) {
                        entity = mapper.readValue(dbExchange.getRequestBody(), PoliticalPartyImpl.class);
                        objectLayer.deletePoliticalParty((PoliticalParty) entity);
                    }
                    break;
                case "Voter":
                    if (sourced.equals("true")) {
                        entity = mapper.readValue(dbExchange.getRequestBody(), VoterImpl.class);
                        objectLayer.deleteVoter((Voter) entity);
                    }
                    break;
                case "Ballot_Election":
                    if(sourced.equals("true")){
                        String[] ballot_ballotItem = parseObjects(dbExchange.getRequestBody());
                        Ballot ballot = mapper.readValue(ballot_ballotItem[0],BallotImpl.class);
                        BallotItem ballotItem = mapper.readValue(ballot_ballotItem[1],ElectionImpl.class);
                        objectLayer.deleteLink(ballot,ballotItem);
                    }
                    break;
                case "Ballot_Issue":
                    if(sourced.equals("true")){
                        String[] ballot_ballotItem = parseObjects(dbExchange.getRequestBody());
                        Ballot ballot = mapper.readValue(ballot_ballotItem[0],BallotImpl.class);
                        BallotItem ballotItem = mapper.readValue(ballot_ballotItem[1],IssueImpl.class);
                        objectLayer.deleteLink(ballot,ballotItem);
                    }
                    break;
                case "Election_Candidate":
                    if(sourced.equals("true")){
                        String[] election_candidate = parseObjects(dbExchange.getRequestBody());
                        Election election = mapper.readValue(election_candidate[0],ElectionImpl.class);
                        Candidate candidate = mapper.readValue(election_candidate[1],CandidateImpl.class);
                        objectLayer.deleteLink(candidate,election);
                    }
                    break;
                case "District_Ballot":
                    if(sourced.equals("true")){
                        String[] district_ballots = parseObjects(dbExchange.getRequestBody());
                        ElectoralDistrict electoralDistrict = mapper.readValue(district_ballots[0],ElectoralDistrictImpl.class);
                        Ballot ballot = mapper.readValue(district_ballots[1],BallotImpl.class);
                        objectLayer.deleteLink(electoralDistrict,ballot);
                    }
                    break;
                case "District_Voter":
                    if(sourced.equals("true")){
                        String[] district_ballots = parseObjects(dbExchange.getRequestBody());
                        ElectoralDistrict electoralDistrict = mapper.readValue(district_ballots[0],ElectoralDistrictImpl.class);
                        Voter voter = mapper.readValue(district_ballots[1],VoterImpl.class);
                        objectLayer.deleteLink(electoralDistrict,voter);
                    }
                    break;
                case "Party_Candidate":
                    if(sourced.equals("true")){
                        String[] party_candidate = parseObjects(dbExchange.getRequestBody());
                        PoliticalParty party = mapper.readValue(party_candidate[0],PoliticalPartyImpl.class);
                        Candidate candidate = mapper.readValue(party_candidate[1],CandidateImpl.class);
                        objectLayer.deleteLink(party,candidate);
                    }
                    break;
                case "VoterRecord":
                    if(sourced.equals("true")){
                        entity = mapper.readValue(dbExchange.getRequestBody(), VoterRecordImpl.class);
                        objectLayer.deleteVoteRecord((VoteRecord) entity);
                    }
                    break;
                default:
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
