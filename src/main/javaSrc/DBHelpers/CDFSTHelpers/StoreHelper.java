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


public class StoreHelper extends CDFSTHelper {
    private static Logger log = new Logger(StoreHelper.class);

    public StoreHelper(DBExchange dbExchange, DbConnHelper dbConnHelper) {

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
                        entity = objectLayer.storeBallot((Ballot) entity);
                    }
                    break;
                case "Candidate":
                    if (sourced.equals("true")) {
                        entity = mapper.readValue(dbExchange.getRequestBody(), CandidateImpl.class);
                        entity = objectLayer.storeCandidate((Candidate) entity);
                    }
                    break;
                case "Election":
                    if (sourced.equals("true")) {
                        entity = mapper.readValue(dbExchange.getRequestBody(), ElectionImpl.class);
                        entity = objectLayer.storeElection((Election) entity);
                    }
                    break;
                case "ElectionOfficer":
                    if (sourced.equals("true")) {
                        entity = mapper.readValue(dbExchange.getRequestBody(), ElectionsOfficerImpl.class);
                        entity = objectLayer.storeElectionsOfficer((ElectionsOfficer) entity);
                    }
                    break;
                case "ElectoralDistrict":
                    if (sourced.equals("true")) {
                        entity = mapper.readValue(dbExchange.getRequestBody(), ElectoralDistrictImpl.class);
                        entity = objectLayer.storeElectoralDistrict((ElectoralDistrict) entity);
                    }
                    break;
                case "Issue":
                    if (sourced.equals("true")) {
                        entity = mapper.readValue(dbExchange.getRequestBody(), IssueImpl.class);
                        entity = objectLayer.storeIssue((Issue) entity);
                    }
                    break;
                case "PoliticalParty":
                    if (sourced.equals("true")) {
                        entity = mapper.readValue(dbExchange.getRequestBody(), PoliticalPartyImpl.class);
                        entity = objectLayer.storePoliticalParty((PoliticalParty) entity);
                    }
                    break;
                case "Voter":
                    if (sourced.equals("true")) {
                        entity = mapper.readValue(dbExchange.getRequestBody(), VoterImpl.class);
                        entity = objectLayer.storeVoter((Voter) entity);
                    }
                    break;
                case "Ballot_Election":
                    if (sourced.equals("true")) {
                        String[] ballot_ballotItem = parseObjects(dbExchange.getRequestBody());
                        Ballot ballot = mapper.readValue(ballot_ballotItem[0], BallotImpl.class);
                        BallotItem ballotItem = mapper.readValue(ballot_ballotItem[1], ElectionImpl.class);
                        objectLayer.createLink(ballot, ballotItem);
                    }
                    break;
                case "Ballot_Issue":
                    if (sourced.equals("true")) {
                        String[] ballot_ballotItem = parseObjects(dbExchange.getRequestBody());
                        Ballot ballot = mapper.readValue(ballot_ballotItem[0], BallotImpl.class);
                        BallotItem ballotItem = mapper.readValue(ballot_ballotItem[1], IssueImpl.class);
                        objectLayer.createLink(ballot, ballotItem);
                    }
                    break;
                case "Election_Candidate":
                    if (sourced.equals("true")) {
                        String[] election_candidate = parseObjects(dbExchange.getRequestBody());
                        Election election = mapper.readValue(election_candidate[0], ElectionImpl.class);
                        Candidate candidate = mapper.readValue(election_candidate[1], CandidateImpl.class);
                        objectLayer.createLink(candidate, election);
                    }
                    break;
                case "District_Ballot":
                    if(sourced.equals("true")){
                        String[] district_ballots = parseObjects(dbExchange.getRequestBody());
                        ElectoralDistrict electoralDistrict = mapper.readValue(district_ballots[0],ElectoralDistrictImpl.class);
                        Ballot ballot = mapper.readValue(district_ballots[1],BallotImpl.class);
                        objectLayer.createLink(electoralDistrict,ballot);
                    }
                    break;
                case "District_Voter":
                    if(sourced.equals("true")){
                        String[] district_ballots = parseObjects(dbExchange.getRequestBody());
                        ElectoralDistrict electoralDistrict = mapper.readValue(district_ballots[0],ElectoralDistrictImpl.class);
                        Voter voter = mapper.readValue(district_ballots[1],VoterImpl.class);
                        objectLayer.createLink(electoralDistrict,voter);
                    }
                    break;
                case "Party_Candidate":
                    if(sourced.equals("true")){
                        String[] party_candidate = parseObjects(dbExchange.getRequestBody());
                        PoliticalParty party = mapper.readValue(party_candidate[0],PoliticalPartyImpl.class);
                        Candidate candidate = mapper.readValue(party_candidate[1],CandidateImpl.class);
                        objectLayer.createLink(party,candidate);
                    }
                    break;
                case "VoterRecord":
                    if (sourced.equals("true")) {
                        entity = mapper.readValue(dbExchange.getRequestBody(), VoterRecordImpl.class);
                        entity = objectLayer.storeVoteRecord((VoteRecord)entity);
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
