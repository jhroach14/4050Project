package main.javaSrc.DBHelpers.CDFSHelpers;

import main.javaSrc.DBHelpers.DbConnHelper;
import main.javaSrc.Entities.*;
import main.javaSrc.Entities.EntityImpl.*;
import main.javaSrc.HttpClasses.DBExchange;
import main.javaSrc.helpers.EVException;
import main.javaSrc.helpers.Logger;
import org.codehaus.jackson.map.ObjectMapper;

import java.io.IOException;
import java.util.List;


public class FindHelper extends CDFSTHelper {
    private static Logger log = new Logger(FindHelper.class);

    public FindHelper(DBExchange dbExchange , DbConnHelper dbConnHelper) {

        super(dbExchange,dbConnHelper);
    }

    public List<Entity> execute(){

        String objectType = dbExchange.getDBRequestObjectType();
        String sourced = dbExchange.getParam("sourced");
        ObjectMapper mapper = new ObjectMapper();
        Entity model;

        try{
            switch (objectType){

                case "Ballot":
                    if (sourced.equals("true")) {
                        model = mapper.readValue(dbExchange.getRequestBody(), BallotImpl.class);
                        entities.addAll(objectLayer.findBallot((Ballot) model));
                    }
                    break;
                case "Candidate":
                    if (sourced.equals("true")) {
                        model = mapper.readValue(dbExchange.getRequestBody(), CandidateImpl.class);
                        entities.addAll(objectLayer.findCandidate((Candidate) model));
                    }
                    break;
                case "Election":
                    if (sourced.equals("true")) {
                        model = mapper.readValue(dbExchange.getRequestBody(), ElectionImpl.class);
                        entities.addAll(objectLayer.findElection((Election) model));
                    }
                    break;
                case "ElectionOfficer":
                    if (sourced.equals("true")) {
                        model = mapper.readValue(dbExchange.getRequestBody(), ElectionsOfficerImpl.class);
                        entities.addAll(objectLayer.findElectionsOfficer((ElectionsOfficer) model));
                    }
                    break;
                case "ElectoralDistrict":
                    if (sourced.equals("true")) {
                        model = mapper.readValue(dbExchange.getRequestBody(), ElectoralDistrictImpl.class);
                        entities.addAll(objectLayer.findElectoralDistrict((ElectoralDistrict) model));
                    }
                    break;
                case "Issue":
                    if (sourced.equals("true")) {
                        model = mapper.readValue(dbExchange.getRequestBody(), IssueImpl.class);
                        entities.addAll(objectLayer.findIssue((Issue) model));
                    }
                    break;
                case "PoliticalParty":
                    if (sourced.equals("true")) {
                        model = mapper.readValue(dbExchange.getRequestBody(), PoliticalPartyImpl.class);
                        entities.addAll(objectLayer.findPoliticalParty((PoliticalParty) model));
                    }
                    break;
                case "Voter":
                    if (sourced.equals("true")) {
                        model = mapper.readValue(dbExchange.getRequestBody(), VoterImpl.class);
                        entities.addAll(objectLayer.findVoter((Voter) model));
                    }
                    break;
                case "VoterRecord":
                    if (sourced.equals("true")) {
                        model = mapper.readValue(dbExchange.getRequestBody(), VoterRecordImpl.class);
                        entities.addAll(objectLayer.findVoteRecord((VoteRecord) model));
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
        return entities;
    }
}
