package main.javaSrc.DBHelpers.CDFSHelpers;

import main.javaSrc.Entities.*;
import main.javaSrc.HttpClasses.DBExchange;
import main.javaSrc.helpers.EVException;
import main.javaSrc.helpers.Logger;
import org.codehaus.jackson.map.ObjectMapper;

import java.io.IOException;


public class FindHelper extends CDFSHelper {
    private static Logger log = new Logger(FindHelper.class);

    public FindHelper(DBExchange dbExchange) {
        super(dbExchange);
    }

    public Entity execute(){

        String objectType = dbExchange.getDBRequestObjectType();
        String sourced = dbExchange.getParam("sourced");
        ObjectMapper mapper = new ObjectMapper();

        try{
            switch (objectType){

                case "Ballot":
                    if (sourced.equals("true")) {
                        entity = mapper.readValue(dbExchange.getRequestBody(), Ballot.class);
                        objectLayer.findBallot((Ballot) entity);
                    }
                    break;
                case "Candidate":
                    if (sourced.equals("true")) {
                        entity = mapper.readValue(dbExchange.getRequestBody(), Candidate.class);
                        objectLayer.findCandidate((Candidate) entity);
                    }
                    break;
                case "Election":
                    if (sourced.equals("true")) {
                        entity = mapper.readValue(dbExchange.getRequestBody(), Election.class);
                        objectLayer.findElection((Election) entity);
                    }
                    break;
                case "ElectionsOfficer":
                    if (sourced.equals("true")) {
                        entity = mapper.readValue(dbExchange.getRequestBody(), ElectionsOfficer.class);
                        objectLayer.findElectionsOfficer((ElectionsOfficer) entity);
                    }
                    break;
                case "ElectoralDistrict":
                    if (sourced.equals("true")) {
                        entity = mapper.readValue(dbExchange.getRequestBody(), ElectoralDistrict.class);
                        objectLayer.findElectoralDistrict((ElectoralDistrict) entity);
                    }
                    break;
                case "Issue":
                    if (sourced.equals("true")) {
                        entity = mapper.readValue(dbExchange.getRequestBody(), Issue.class);
                        objectLayer.findIssue((Issue) entity);
                    }
                    break;
                case "PoliticalParty":
                    if (sourced.equals("true")) {
                        entity = mapper.readValue(dbExchange.getRequestBody(), PoliticalParty.class);
                        objectLayer.findPoliticalParty((PoliticalParty) entity);
                    }
                    break;
                case "Voter":
                    if (sourced.equals("true")) {
                        entity = mapper.readValue(dbExchange.getRequestBody(), Voter.class);
                        objectLayer.findVoter((Voter) entity);
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
        return entity;
    }
}
